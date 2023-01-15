package com.camelsoft.tzzft.domain.use_cases.use_case_info

import com.camelsoft.tzzft.R
import com.camelsoft.tzzft._common.App.Companion.getAppContext
import com.camelsoft.tzzft.data.net.NetApi
import com.camelsoft.tzzft.domain.models.MInfo
import com.camelsoft.tzzft.presentation.api.IInfoApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class UseCaseInfoImpl(private val netApi: NetApi): IInfoApi {

    override suspend fun getInfo(bin: String): Flow<EventProgress<MInfo>> = flow {
        try {
            emit(EventProgress.Progress(show = true))

            if (bin.length < 6) {
                emit(EventProgress.Progress(show = false))
                emit(EventProgress.UnSuccess(
                    message = getAppContext().resources.getString(R.string.info_short_bin
                )))
                return@flow
            }

            val response = netApi.getResponseInfo(bin = bin)
            if (response.code() != 200) {
                emit(EventProgress.Progress(show = false))
                when (response.code()) {
                    400 -> emit(EventProgress.UnSuccess(
                        message = getAppContext().resources.getString(R.string.info_400
                    )))
                    404 -> emit(EventProgress.UnSuccess(
                        message = getAppContext().resources.getString(R.string.info_404
                    )))
                    else -> emit(EventProgress.UnSuccess(
                        message = getAppContext().resources.getString(R.string.info_from_server)
                                +": ${response.code()} - ${response.message()}"
                    ))
                }
                return@flow
            }

            emit(EventProgress.Progress(show = false))
            response.body()?.let {
                val mInfo = it
                mInfo.dateTime = System.currentTimeMillis()
                emit(EventProgress.Success(data = mInfo))
            }?: emit(EventProgress.UnSuccess(
                message = getAppContext().resources.getString(R.string.info_response_body
            )))
        }
        catch (e: ConnectException) {
            e.printStackTrace()
            emit(EventProgress.Progress(show = false))
            emit(EventProgress.Error(getAppContext().resources.getString(R.string.error_сonnect)))
        }
        catch (e: SSLPeerUnverifiedException) {
            e.printStackTrace()
            emit(EventProgress.Progress(show = false))
            emit(EventProgress.Error(getAppContext().resources.getString(R.string.error_ssl_unverified)))
        }
        catch (e: SSLHandshakeException) {
            e.printStackTrace()
            emit(EventProgress.Progress(show = false))
            emit(EventProgress.Error(getAppContext().resources.getString(R.string.error_handshake)))
        }
        catch (e: UnknownHostException) {
            e.printStackTrace()
            emit(EventProgress.Progress(show = false))
            emit(EventProgress.Error(getAppContext().resources.getString(R.string.error_unknown_host)))
        }
        catch (e: InterruptedIOException) {
            e.printStackTrace()
            emit(EventProgress.Progress(show = false))
            emit(EventProgress.Error(getAppContext().resources.getString(R.string.error_interrupted_io)))
        }
        catch (e: Exception) {
            e.printStackTrace()
            emit(EventProgress.Progress(show = false))
            emit(EventProgress.Error("[UseCaseInfoImpl.getInfo] ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO).buffer(capacity = 100, onBufferOverflow = BufferOverflow.DROP_OLDEST)
}
