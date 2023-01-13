package com.glsoftware.entrenadorrcp.data

import com.glsoftware.entrenadorrcp.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface RcpReceiveManager {

    val data: MutableSharedFlow<Resource<RcpResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}