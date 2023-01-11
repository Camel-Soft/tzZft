package com.camelsoft.tzzft.presentation.api

import com.camelsoft.tzzft.domain.models.MInfo
import com.camelsoft.tzzft.domain.use_cases.use_case_info.EventProgress
import kotlinx.coroutines.flow.Flow

interface IInfoApi {
    suspend fun getInfo(bin: Int): Flow<EventProgress<MInfo>>
}
