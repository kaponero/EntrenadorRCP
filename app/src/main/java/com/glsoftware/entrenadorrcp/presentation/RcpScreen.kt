package com.glsoftware.entrenadorrcp.presentation

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.glsoftware.entrenadorrcp.R
import com.glsoftware.entrenadorrcp.data.ConnectionState
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.*
import com.glsoftware.entrenadorrcp.presentation.permissions.PermissionUtils
import com.glsoftware.entrenadorrcp.presentation.permissions.SystemBroadcastReceiver
import com.glsoftware.entrenadorrcp.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RcpScreen(navController: NavController,
                    onBluetoothStateChanged:()->Unit,
                    viewModel: RCPViewModel = hiltViewModel()
) {
    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED) { bluetoothState ->
        val action = bluetoothState?.action ?: return@SystemBroadcastReceiver
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            onBluetoothStateChanged()
        }
    }

    val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)
    val lifecycleOwner = LocalLifecycleOwner.current
    val bleConnectionState = viewModel.connectionState

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    permissionState.launchMultiplePermissionRequest()
                    if (permissionState.allPermissionsGranted && bleConnectionState == ConnectionState.Disconnected) {
                        viewModel.reconnect()
                    }
                }
                if (event == Lifecycle.Event.ON_STOP) {
                    if (bleConnectionState == ConnectionState.Connected) {
                        viewModel.disconnect()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    LaunchedEffect(key1 = permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            if (bleConnectionState == ConnectionState.Uninitialized) {
                viewModel.initializeConnection()
            }
        }
    }

    LaunchedEffect(false) {
        reset_tiempo()
        reset_scores()
    }

    //interfaz grafica
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                //.height(580.dp)
                //.aspectRatio(1f)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            darkGray.copy(1f),
                            darkGray.copy(0.95f),
                            darkGray.copy(0.80f),
                            darkGray.copy(0.75f),
                            darkGray.copy(0.65f),
                            darkGray.copy(0.55f),
                            darkGray.copy(0.40f),
                            Red700.copy(0.35f),
                        )
                    ),
                    //RoundedCornerShape(10.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (bleConnectionState == ConnectionState.CurrentlyInitializing) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Bordo
                    )
                    if (viewModel.initializingMessage != null) {
                        Text(
                            text = viewModel.initializingMessage!!,
                            style = MaterialTheme.typography.body1,
                            color = white_color,
                        )
                        //timer()
                    }
                }
            } else if (!permissionState.allPermissionsGranted) {
                Text(
                    text = "Vaya a la configuración de la aplicación y permita los permisos que faltan.",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = white_color
                )
            } else if (viewModel.errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.errorMessage!!,
                        style = MaterialTheme.typography.body1,
                        color = white_color
                    )
                    Button(
                        onClick = {
                            if (permissionState.allPermissionsGranted) {
                                viewModel.initializeConnection()
                            }
                        }
                    ) {
                        Text(
                            text = "Intentar nuevamente",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(10.dp),
                            textAlign = TextAlign.Center,
                            color = white_color
                        )
                    }
                }
                //}else if(bleConnectionState == ConnectionState.Connected){
            } else {
                if (!isIniciar(viewModel.position))
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Coloque las manos correctamente para comenzar",
                            color = white_color,
                            style = MaterialTheme.typography.h4,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp, AppTheme.dimens.large),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        //rememberCountdownTimerState(viewModel.frequency, viewModel.compresion, viewModel.position)

                        //esta funcion muestra el contador en pantalla
                        timer()
                        //sumatoria de las compreciones por minuto
                        calculo_frecuencia(viewModel.frequency)
                        //calculo del dezplazamiento de insuflacion
                        calculo_desplazamiento(viewModel.compresion)

                        if (bleConnectionState == ConnectionState.Connected) {
                            Spacer(modifier = Modifier.height(AppTheme.dimens.mano_size2/3))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(2f),
                                    horizontalAlignment = Alignment.CenterHorizontally,

                                    ) {
                                    Spacer(modifier = Modifier.height(AppTheme.dimens.mano_size2/2))
                                    ArcIndicator(
                                        modifier = Modifier
                                            .size(AppTheme.dimens.radio1),
                                        initialValue = viewModel.frequency,
                                        primaryColor = white,
                                        secondaryColor = Red200,
                                        terciaryColor = Red700,
                                        circleRadius = AppTheme.dimens.radio1f
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    BarIndicator(
                                        modifier = Modifier
                                            .size(AppTheme.dimens.radio1),
                                        initialValue = viewModel.compresion,
                                        primaryColor = white,
                                        secondaryColor = Red200,
                                        terciaryColor = Red700,
                                        circleRadius = AppTheme.dimens.radio1f
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(AppTheme.dimens.mano_size2/2))

                            if (calculo_posicion(viewModel.position, viewModel.compresion)) {
                                Image(
                                    painter = painterResource(id = R.drawable.hand_ok),
                                    contentDescription = "mano ok",
                                    modifier = Modifier
                                        .size(AppTheme.dimens.mano_size1)
                                        .border(
                                            BorderStroke(4.dp, white),
                                            CircleShape
                                        )
                                        .padding(10.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.hand_no),
                                    contentDescription = "mano no",
                                    modifier = Modifier
                                        .size(AppTheme.dimens.mano_size1)
                                        .border(
                                            BorderStroke(4.dp, white),
                                            CircleShape
                                        )
                                        .padding(10.dp)
                                )
                            }
                            //calculo_desplazamiento(viewModel.compresion)
                            Spacer(modifier = Modifier.height(AppTheme.dimens.mano_size2))
                            Row() {
                                /*Button(onClick = {
                                    reset_tiempo()
                                    reset_scores()
                                }) {
                                    Text("Reiniciar")
                                }
                                Spacer(modifier = Modifier.width(20.dp))*/
                                Button(onClick = {
                                    if (bleConnectionState == ConnectionState.Connected) {
                                        viewModel.disconnect()
                                    }
                                    navController.popBackStack()
                                    navController.navigate(Screen.StartScreen.route) {
                                        //popUpTo(Screen.FrequencyScreen.route)
                                    }
                                }) {
                                    Text("Finalizar")
                                }
                            }
                            if (tiempo.minutos * 60 + tiempo.segundos == 600) {
                                navController.navigate(Screen.StartScreen.route)
                            }
                        }
                    }
                }
            }

            if (bleConnectionState == ConnectionState.Disconnected) {
                Button(onClick = {
                    viewModel.initializeConnection()
                }) {
                    Text("Iniciar nuevamente")
                }
            }
        }
    }
}