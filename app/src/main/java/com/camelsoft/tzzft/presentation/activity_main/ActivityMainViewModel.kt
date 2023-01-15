package com.camelsoft.tzzft.presentation.activity_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelsoft.tzzft.domain.models.MInfo
import com.camelsoft.tzzft.domain.use_cases.use_case_info.EventProgress
import com.camelsoft.tzzft.presentation.api.IInfoApi
import com.camelsoft.tzzft.presentation.api.IStorageApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel @Inject constructor(
    private val iInfoApi: IInfoApi,
    private val iStorageApi: IStorageApi
): ViewModel() {

    private val _eventsUi = Channel<EventUiMainActivity>()
    val eventsUi = _eventsUi.receiveAsFlow()

    private val _mInfo = MutableLiveData(MInfo())
    val mInfo: LiveData<MInfo> = _mInfo

    private val _listMInfo = MutableLiveData<List<MInfo>>()
    val listMInfo: LiveData<List<MInfo>> = _listMInfo

    private val cehInit = CoroutineExceptionHandler { _, throwable ->
        sendEventUi(EventUiMainActivity.ShowError(
            "[ActivityMainViewModel.init] ${throwable.localizedMessage}"
        ))
    }

    init {
        viewModelScope.launch(Dispatchers.Main + cehInit) {
            _listMInfo.value = iStorageApi.getAllInfo()
        }
    }

    fun eventsVm(event: EventVmMainActivity) {
        when (event) {
            is EventVmMainActivity.PerformRequest -> {
                viewModelScope.launch(Dispatchers.Main + cehEvents) {
                    iInfoApi.getInfo(bin = event.bin).collect {
                        when (it) {
                            is EventProgress.Success -> {
                                _mInfo.value = it.data
                                iStorageApi.insertInfo(it.data)
                                _listMInfo.value = iStorageApi.getAllInfo()
                                sendEventUi(EventUiMainActivity.ScrollToPosHist(
                                    position = _listMInfo.value!!.size-1
                                ))
                            }
                            is EventProgress.UnSuccess -> {
                                _mInfo.value = MInfo()
                                sendEventUi(EventUiMainActivity.ShowInfo(it.message))
                            }
                            is EventProgress.Progress ->
                                sendEventUi(EventUiMainActivity.ShowProgress(it.show))
                            is EventProgress.Error -> {
                                _mInfo.value = MInfo()
                                sendEventUi(EventUiMainActivity.ShowError(it.message))
                            }
                        }
                    }
                }
            }
            is EventVmMainActivity.ShowFromHistory -> {
                _mInfo.value = _listMInfo.value?.get(event.id)
            }
        }
    }

    private val cehEvents = CoroutineExceptionHandler { _, throwable ->
        _mInfo.value = MInfo()
        sendEventUi(EventUiMainActivity.ShowError(
            "[ActivityMainViewModel.eventsVm] ${throwable.localizedMessage}"
        ))
    }

    private fun sendEventUi(event: EventUiMainActivity) {
        viewModelScope.launch {
            _eventsUi.send(event)
        }
    }
}
