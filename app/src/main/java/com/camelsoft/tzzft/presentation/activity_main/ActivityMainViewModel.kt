package com.camelsoft.tzzft.presentation.activity_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelsoft.tzzft.domain.use_cases.use_case_info.EventProgress
import com.camelsoft.tzzft.presentation.api.IInfoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel @Inject constructor(
    private val iInfoApi: IInfoApi
): ViewModel() {

    private val _eventsUi = Channel<EventUiMainActivity>()
    val eventsUi = _eventsUi.receiveAsFlow()

//    private val _mInfo = MutableLiveData<MInfo>()
//    val mInfo: LiveData<MInfo> = _mInfo

    fun eventsVm(event: EventVmMainActivity) {
        when (event) {
            is EventVmMainActivity.PerformRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    iInfoApi.getInfo(bin = event.bin).collect {
                        when (it) {
                            is EventProgress.Success ->
                                sendEventUi(EventUiMainActivity.PublicBankInfo(it.data))
                            is EventProgress.UnSuccess ->
                                sendEventUi(EventUiMainActivity.ShowInfo(it.message))
                            is EventProgress.Progress ->
                                sendEventUi(EventUiMainActivity.ShowProgress(it.show))
                            is EventProgress.Error ->
                                sendEventUi(EventUiMainActivity.ShowError(it.message))
                        }
                    }
                }
            }
        }
    }

    private fun sendEventUi(event: EventUiMainActivity) {
        viewModelScope.launch {
            _eventsUi.send(event)
        }
    }
}
