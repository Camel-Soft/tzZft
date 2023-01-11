package com.camelsoft.tzzft.presentation.activity_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.camelsoft.tzzft.presentation.api.IInfoApi
import dagger.hilt.android.lifecycle.HiltViewModel
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









    private fun sendEventUi(event: EventUiMainActivity) {
        viewModelScope.launch {
            _eventsUi.send(event)
        }
    }
}
