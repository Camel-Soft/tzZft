package com.camelsoft.tzzft.presentation.activity_main

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.camelsoft.tzzft.R
import com.camelsoft.tzzft.databinding.ActivityMainBinding
import com.camelsoft.tzzft.presentation.dialogs.showError
import com.camelsoft.tzzft.presentation.dialogs.showInfo
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var weakContext: WeakReference<Context>
    private val viewModel: ActivityMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weakContext = WeakReference<Context>(this)
        supportActionBar?.title = resources.getString(R.string.title)

        handleEventsUi()
        btnClick()
    }

    private fun handleEventsUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsUi.collect { event ->
                when (event) {
                    is EventUiMainActivity.ShowError -> showError(weakContext.get()!!, event.message) {}
                    is EventUiMainActivity.ShowInfo -> showInfo(weakContext.get()!!, event.message) {}
                    is EventUiMainActivity.ShowProgress -> {}
                    is EventUiMainActivity.PublicBankInfo -> showInfo(weakContext.get()!!, event.bankInfo.brand!!) {}
                }
            }
        }
    }

    private fun btnClick() {
        binding.btnStart.setOnClickListener {
            if (binding.editBin.text.toString().length < 6) {
                showInfo(weakContext.get()!!, resources.getString(R.string.helper_text)) {}
                return@setOnClickListener
            }
            viewModel.eventsVm(EventVmMainActivity.PerformRequest(
                bin = binding.editBin.text.toString().toInt()
            ))
        }
    }
}
