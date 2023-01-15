package com.camelsoft.tzzft.presentation.activity_main

sealed class EventUiMainActivity {
    data class ShowError(val message: String): EventUiMainActivity()
    data class ShowInfo(val message: String): EventUiMainActivity()
    data class ShowProgress(val show: Boolean): EventUiMainActivity()
    data class ScrollToPosHist(val position: Int): EventUiMainActivity()
}
