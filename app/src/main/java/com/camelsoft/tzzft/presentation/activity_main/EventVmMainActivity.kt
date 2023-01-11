package com.camelsoft.tzzft.presentation.activity_main

sealed class EventVmMainActivity {
    data class PerformRequest(val bin: Int): EventVmMainActivity()
}
