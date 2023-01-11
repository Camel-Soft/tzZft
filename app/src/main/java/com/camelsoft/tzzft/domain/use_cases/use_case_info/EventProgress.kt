package com.camelsoft.tzzft.domain.use_cases.use_case_info

sealed class EventProgress<T> {
    class Success<T>(val data: T): EventProgress<T>()
    class UnSuccess<T>(val message: String): EventProgress<T>()
    class Error<T>(val message: String): EventProgress<T>()
    class Progress<T>(val show: Boolean): EventProgress<T>()
}
