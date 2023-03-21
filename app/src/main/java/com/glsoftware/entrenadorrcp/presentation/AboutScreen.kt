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