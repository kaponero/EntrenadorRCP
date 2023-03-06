package com.glsoftware.entrenadorrcp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glsoftware.entrenadorrcp.data.ConnectionState
import com.glsoftware.entrenadorrcp.data.RcpReceiveManager
import com.glsoftware.entrenadorrcp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RCPViewModel @Inject constructor(
    private val rcpReceiveManager: RcpReceiveManager
): ViewModel(){

    var initializingMessage by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var frequency by mutableStateOf(0)
        private set

    var compresion by mutableStateOf(0)
        private set

    var position by mutableStateOf(0)
        private set

    var refresco by mutableStateOf(0)
        private set

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    private fun subscribeToChanges(){
        viewModelScope.launch {
            rcpReceiveManager.data.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        connectionState = result.data.connectionState
                        frequency = result.data.frequency
                        compresion = result.data.compression
                        position = result.data.position
                    }

                    is Resource.Loading -> {
                        initializingMessage = result.message
                        connectionState = ConnectionState.CurrentlyInitializing
                    }

                    is Resource.Error -> {
                        errorMessage = result.errorMessage
                        connectionState = ConnectionState.Uninitialized
                    }
                }
            }
        }
    }

    fun disconnect(){
        rcpReceiveManager.disconnect()
    }

    fun reconnect(){
        rcpReceiveManager.reconnect()
    }

    fun initializeConnection(){
        errorMessage = null
        subscribeToChanges()
        rcpReceiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        rcpReceiveManager.closeConnection()
    }
}