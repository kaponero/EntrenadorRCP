package com.glsoftware.entrenadorrcp.presentation

import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.glsoftware.entrenadorrcp.R
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.*
import com.glsoftware.entrenadorrcp.presentation.permissions.SystemBroadcastReceiver
import com.glsoftware.entrenadorrcp.ui.theme.*

@Composable
fun AboutScreen(navController: NavController,
          onBluetoothStateChanged:()->Unit,
) {

    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
        val action = bluetoothState?.action ?:return@SystemBroadcastReceiver
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED){
            onBluetoothStateChanged()
        }
    }

    MainScreen(navController, onBluetoothStateChanged)

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(
                    listOf(
                        Red500.copy(1f),
                        white.copy(0.15f)
                    )
                ))
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = "EntrenadorRCP",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .padding(AppTheme.dimens.large),
                    color = white_color
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(AppTheme.dimens.large*4))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.large),
            elevation = 10.dp,

            ) {
            Row(
                modifier = Modifier
                    .padding(AppTheme.dimens.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column() {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ArcIndicator(
                            modifier = Modifier
                                .background(gray)
                                .size(AppTheme.dimens.radio2)
                                .padding(AppTheme.dimens.medium),
                            initialValue = 110,
                            primaryColor = white,
                            secondaryColor = Red200,
                            terciaryColor = Red700,
                            circleRadius = AppTheme.dimens.radio2f
                        )
                        Text(
                            modifier = Modifier
                                .padding(AppTheme.dimens.medium),
                            text = "Para mejorar la maniobra de RCP las compresiones por minuto se deben mantener " +
                                    "entre 100 y 120 (zona verde) en todo el entrenamiento",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BarIndicator(
                            modifier = Modifier
                                .background(gray)
                                .size(AppTheme.dimens.radio2)
                                .padding(AppTheme.dimens.medium),
                            initialValue = 50,
                            primaryColor = white,
                            secondaryColor = Red200,
                            terciaryColor = Red700,
                            circleRadius = AppTheme.dimens.radio2f
                        )
                        Text(
                            modifier = Modifier.padding(AppTheme.dimens.mediumLarge),
                            text = "La barra deslizante de la compresion del torax debe llegar hasta los extremos superior" +
                                    " e inferior para una correcta insuflación",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(AppTheme.dimens.radio2)
                                .background(gray)
                                .padding(AppTheme.dimens.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.hand_ok),
                                contentDescription = "mano ok",
                                modifier = Modifier
                                    .size(AppTheme.dimens.mano_size2)
                                    .border(
                                        BorderStroke(4.dp, white),
                                        CircleShape
                                    )
                                    .padding(AppTheme.dimens.medium)
                            )
                        }
                        Text(
                            modifier = Modifier.padding(AppTheme.dimens.medium),
                            text = "Cuando el icono este en verde significa que la posicion de las" +
                                    " manos es correcta",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.large*7)
                .padding(AppTheme.dimens.large,AppTheme.dimens.medium),
            elevation = 10.dp,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(AppTheme.dimens.medium),
                    text = "EntrenadorRCP es una aplicación realizada por Labanca Alvaro y García Sebastián " +
                            "como parte del Proyecto Final para la carrera Bioingeniería" ,
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.body1
                )

            }
        }
        Text(
            modifier = Modifier.padding(AppTheme.dimens.medium),
            text = "Version 1.03 - 4-03-2023",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    }

}
/*
//MainScreen(navController,onBluetoothStateChanged)
SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
    val action = bluetoothState?.action ?:return@SystemBroadcastReceiver
    if (action == BluetoothAdapter.ACTION_STATE_CHANGED){
        onBluetoothStateChanged()
    }
}
val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)
val lifecycleOwner = LocalLifecycleOwner.current
val bleConnectionState = viewModel.connectionState
DisposableEffect(
    key1 = lifecycleOwner,
    effect = {
        val observer = LifecycleEventObserver{_,event ->
            if(event == Lifecycle.Event.ON_START){
                permissionState.launchMultiplePermissionRequest()
                if(permissionState.allPermissionsGranted && bleConnectionState == ConnectionState.Disconnected){
                    viewModel.reconnect()
                }
            }
            if(event == Lifecycle.Event.ON_STOP){
                if (bleConnectionState == ConnectionState.Connected){
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
LaunchedEffect(key1 = permissionState.allPermissionsGranted){
    if(permissionState.allPermissionsGranted){
        if(bleConnectionState == ConnectionState.Uninitialized){
            viewModel.initializeConnection()
        }
    }
}
LaunchedEffect(false){
    reset_tiempo()
    reset_scores()
}
//interfaz grafica
Box(
    modifier = Modifier
        .fillMaxSize(),
    contentAlignment = Alignment.Center
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            //.height(580.dp)
            //.aspectRatio(1f)
            .background(
                darkGray,
                //RoundedCornerShape(10.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(bleConnectionState == ConnectionState.CurrentlyInitializing){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CircularProgressIndicator(
                    color = Bordo
                )
                if(viewModel.initializingMessage != null){
                    Text(
                        text = viewModel.initializingMessage!!
                    )
                }
            }
        }else if(!permissionState.allPermissionsGranted){
            Text(
                text = "Vaya a la configuración de la aplicación y permita los permisos que faltan.",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center
            )
        }else if(viewModel.errorMessage != null){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.errorMessage!!
                )
                Button(
                    onClick = {
                        if(permissionState.allPermissionsGranted){
                            viewModel.initializeConnection()
                        }
                    }
                ) {
                    Text(
                        "Intentar nuevamente"
                    )
                }
            }
        }else if(bleConnectionState == ConnectionState.Connected){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                rememberCountdownTimerState(viewModel.frequency,viewModel.compresion,viewModel.position)
                Text(
                    text = "${if(tiempo.minutos>9){
                        tiempo.minutos
                    }
                    else{
                        "0" +tiempo.minutos
                    }
                    } : ${if(tiempo.segundos>9){
                        tiempo.segundos
                    }
                        else{
                        "0" +tiempo.segundos
                        }
                    }" ,
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(30.dp))
                        ArcIndicator(
                            modifier = Modifier
                                .height(250.dp)
                                .width(250.dp),
                            initialValue = viewModel.frequency,
                            primaryColor = white,
                            secondaryColor = Purple200,
                            terciaryColor = Purple700,
                            circleRadius = 230f
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        BarIndicator(
                            modifier = Modifier
                                .size(250.dp),
                            initialValue = viewModel.compresion,
                            primaryColor = white,
                            secondaryColor = Purple200 ,
                            terciaryColor = Purple700,
                            circleRadius = 230f
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                val posision_manos: String
                if(viewModel.position == 1 ){
                    Image(
                        painter = painterResource(id = R.drawable.hand_ok),
                        contentDescription = "mano ok",
                        modifier = Modifier
                            .size(80.dp)
                            .border(
                                BorderStroke(4.dp, white),
                                CircleShape
                            )
                            .padding(10.dp)
                        )
                }else{
                    Image(
                        painter = painterResource(id = R.drawable.hand_no),
                        contentDescription = "mano no",
                        modifier = Modifier
                            .size(80.dp)
                            .border(
                                BorderStroke(4.dp, white),
                                CircleShape
                            )
                            .padding(10.dp)
                    )}
                Spacer(modifier = Modifier.height(80.dp))
                Row() {
                    Button(onClick = {
                        reset_tiempo()
                        reset_scores()
                    }) {
                        Text("Reiniciar")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(onClick = {
                        navController.navigate(Screen.StartScreen.route) {
                            //popUpTo(Screen.StartScreen.route)
                        }
                    }) {
                        Text("Terminar")
                    }
                }
            }
        }else if(bleConnectionState == ConnectionState.Disconnected){
            Button(onClick = {
                viewModel.initializeConnection()
            }) {
                Text("Iniciar nuevamente")
            }
        }
    }
}*/