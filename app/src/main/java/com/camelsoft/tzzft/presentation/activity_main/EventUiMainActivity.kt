package com.camelsoft.tzzft.presentation.activity_main

import com.camelsoft.tzzft.domain.models.MInfo

sealed class EventUiMainActivity {
    data class ShowError(val message: String): EventUiMainActivity()
    data class ShowInfo(val message: String): EventUiMainActivity()
    data class ShowProgress(val show: Boolean): EventUiMainActivity()
    data class PublicBankInfo(val bankInfo: MInfo): EventUiMainActivity()
}
