package com.glsoftware.entrenadorrcp.data

data class RcpResult(
    val frequency:Int,
    val compression:Int,
    val position:Int,
    val connectionState: ConnectionState
)