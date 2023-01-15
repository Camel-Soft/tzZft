package com.camelsoft.tzzft.presentation.activity_main

sealed class EventVmMainActivity {
    data class PerformRequest(val bin: String): EventVmMainActivity()
    data class ShowFromHistory(val id: Int): EventVmMainActivity()
}
