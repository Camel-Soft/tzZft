package com.camelsoft.tzzft.presentation.activity_main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelsoft.tzzft.R
import com.camelsoft.tzzft._common.Constants.Companion.FLAG_COORDINATES
import com.camelsoft.tzzft._common.Constants.Companion.FLAG_PHONE
import com.camelsoft.tzzft._common.Constants.Companion.FLAG_URL
import com.camelsoft.tzzft.databinding.ActivityMainBinding
import com.camelsoft.tzzft.presentation.dialogs.showError
import com.camelsoft.tzzft.presentation.dialogs.showInfo
import com.camelsoft.tzzft.presentation.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weakContext: WeakReference<Context>
    private lateinit var weakView: WeakReference<View>
    private val viewModel: ActivityMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weakContext = WeakReference<Context>(this)
        weakView = WeakReference<View>(binding.root)
        supportActionBar?.title = resources.getString(R.string.title)

        binding.btnStart.setOnClickListener { performRequest() }
        binding.refreshLayout.setOnRefreshListener { performRequest() }

        handleEventsUi()
        initRvInfo()
        initRvHistory()
    }

    private fun initRvHistory() {
        val activityMainAdapterHistory = ActivityMainAdapterHistory()
        binding.recyclerHistory.layoutManager =
            LinearLayoutManager(weakContext.get()!!, RecyclerView.VERTICAL,false)
        binding.recyclerHistory.adapter = activityMainAdapterHistory
        viewModel.listMInfo.observe(this) { listMInfo->
            activityMainAdapterHistory.submitList(listMInfo.map { it.toPair() })
        }
        activityMainAdapterHistory.setOnItemClickListener = { id->
            viewModel.eventsVm(EventVmMainActivity.ShowFromHistory(id = id))
        }
    }

    private fun initRvInfo() {
        val activityMainAdapterInfo = ActivityMainAdapterInfo()
        binding.recyclerInfo.layoutManager =
            LinearLayoutManager(weakContext.get()!!, RecyclerView.VERTICAL,false)
        binding.recyclerInfo.adapter = activityMainAdapterInfo
        viewModel.mInfo.observe(this) { mInfo->
            activityMainAdapterInfo.submitList(mInfo.toTrippleList())
        }
        activityMainAdapterInfo.setOnItemClickListener = { id->
            val rec = activityMainAdapterInfo.getList()[id]
            rec.third?.let {
                when (it) {
                    FLAG_URL -> startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://${rec.second}"))
                    )
                    FLAG_PHONE -> {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${rec.second}")
                        startActivity(intent)
                    }
                    FLAG_COORDINATES -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("geo:${rec.second}")
                        startActivity(intent)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun handleEventsUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsUi.collect { event ->
                when (event) {
                    is EventUiMainActivity.ShowError ->
                        showError(weakContext.get()!!, event.message) {}
                    is EventUiMainActivity.ShowInfo ->
                        showInfo(weakContext.get()!!, event.message) {}
                    is EventUiMainActivity.ShowProgress ->
                        binding.refreshLayout.isRefreshing = event.show
                    is EventUiMainActivity.ScrollToPosHist ->
                        binding.recyclerHistory.scrollToPosition(event.position)
                }
            }
        }
    }

    private fun performRequest() {
        if (binding.editBin.text.toString().length < 6) {
            binding.refreshLayout.isRefreshing = false
            showInfo(weakContext.get()!!, resources.getString(R.string.helper_text)) {}
            return
        }
        viewModel.eventsVm(EventVmMainActivity.PerformRequest(
            bin = binding.editBin.text.toString()
        ))
        binding.editBin.setText("")
        hideKeyboard(weakContext.get()!!, weakView.get())
    }
}
