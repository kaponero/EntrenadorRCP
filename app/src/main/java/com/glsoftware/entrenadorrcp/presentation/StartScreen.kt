package com.glsoftware.entrenadorrcp.presentation

import android.bluetooth.BluetoothAdapter
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.glsoftware.entrenadorrcp.MainActivity.Companion.prefs
import com.glsoftware.entrenadorrcp.R
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.calculo_puntuación
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.scores
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.tiempo
import com.glsoftware.entrenadorrcp.presentation.permissions.SystemBroadcastReceiver
import com.glsoftware.entrenadorrcp.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


@Composable
fun StartScreen(navController: NavController,onBluetoothStateChanged:()->Unit){

    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
        val action = bluetoothState?.action ?:return@SystemBroadcastReceiver
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED){
            onBluetoothStateChanged()
        }
    }

    LaunchedEffect(false){

        prefs.saveTiempo(tiempo.minutos,tiempo.segundos)
        prefs.saveScores(scores.cpm,scores.desplaza,scores.posicion, scores.contador, scores.cantidad)
    }

    var dividendo = scores.contador
    if (dividendo == 0){
        dividendo = 1
    }

    var puntaje = calculo_puntuación()

    if(AppTheme.orientation == Orientation.Portrait) {
        MainScreen(navController,onBluetoothStateChanged)
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Red500.copy(1f),
                                white.copy(0.15f)
                            )
                        ),)
                    .weight(1f),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Text(
                        text = "Puntuación",
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .padding(AppTheme.dimens.medium),
                        color = white_color
                    )
                    Spacer(modifier = Modifier.height(AppTheme.dimens.large))
                    CircularIndicator(
                        modifier = Modifier
                            .size(AppTheme.dimens.radio1),
                        initialValue = puntaje,
                        primaryColor = white,
                        secondaryColor = Red200,
                        circleRadius = AppTheme.dimens.radio1f
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
                .fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(AppTheme.dimens.large*17))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.large),
                elevation = 10.dp,

                ) {
                Row(
                    modifier = Modifier
                        .padding(AppTheme.dimens.large),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tiempo",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1
                        )
                        Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
                        Image(
                            painter = painterResource(id = R.drawable.timelapse),
                            contentDescription = "tiempo de entrenamiento",
                        )
                    }
                    Text(
                        text = "${
                            if (tiempo.minutos > 9) {
                                tiempo.minutos
                            } else {
                                "0" + tiempo.minutos
                            }
                        } : ${
                            if (tiempo.segundos > 9) {
                                tiempo.segundos
                            } else {
                                "0" + tiempo.segundos
                            }
                        } minutos",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            Spacer(modifier = Modifier.height(AppTheme.dimens.small))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.large),
                elevation = 10.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(AppTheme.dimens.large),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Comp. por Min.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1
                        )
                        Image(
                            painter = painterResource(id = R.drawable.lat_minute),
                            contentDescription = "compresiones por minuto",
                            modifier = Modifier.padding(AppTheme.dimens.medium, AppTheme.dimens.medium)
                        )
                        Text(
                            text = "${
                                if (scores.cantidad==0L) { 
                                    0
                                }
                                else { 
                                    (scores.cpm.toFloat()/scores.cantidad).roundToInt()
                                }
                            }",
                            //text = "${((scores.cpm.toFloat())/(60*tiempo.minutos + tiempo.segundos)*100).roundToInt()}%",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "Posición",
                            textAlign = TextAlign.Center,
                            //color= gray,
                            style = MaterialTheme.typography.body1
                        )
                        Image(
                            painter = painterResource(id = R.drawable.hand_pos),
                            contentDescription = "posicion de la mano",
                            modifier = Modifier.padding(AppTheme.dimens.medium, AppTheme.dimens.medium),
                        )
                        Text(
                            //text = "${((scores.posicion.toFloat()) / (60 * tiempo.minutos + tiempo.segundos) * 100).roundToInt()}%",
                            text = "${scores.posicion}",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Desplazamiento",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1
                        )
                        Image(
                            painter = painterResource(id = R.drawable.compresion),
                            contentDescription = "compresiones",
                            modifier = Modifier
                                .rotate(90f)
                                .padding(AppTheme.dimens.medium,AppTheme.dimens.medium)
                        )
                        Text(
                            //text = "${(((scores.desplaza.toFloat()) / dividendo) * 2).roundToInt()}%",
                            text = "${
                                if (scores.contador==0){
                                    0
                                }
                                else{
                                    (((scores.desplaza.toFloat()) / scores.contador) * 2).roundToInt()
                                }
                            }%",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
    /*else{
        Column (modifier = Modifier.fillMaxSize()){
            Row(modifier = Modifier
                .fillMaxSize()
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = Brush.verticalGradient(
                            listOf(
                                Red500.copy(1f),
                                white.copy(0.20f)
                            )
                        ))
                )
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Puntuación",
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(AppTheme.dimens.medium),
                color = white_color,
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.small))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
                )
            {
                CircularIndicator(
                    modifier = Modifier
                        .size(AppTheme.dimens.large*13),
                    initialValue = puntaje,
                    primaryColor = white,
                    secondaryColor = Red200,
                    circleRadius = 400f/(AppTheme.dimens.small/1.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.dimens.medium),
                        elevation = 10.dp,
                        )
                    {

                        Row(
                            modifier = Modifier
                                .padding(AppTheme.dimens.large),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Tiempo",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1
                                )
                                Spacer(modifier = Modifier.height(AppTheme.dimens.medium))
                                Image(
                                    painter = painterResource(id = R.drawable.timelapse),
                                    contentDescription = "tiempo de entrenamiento"
                                )
                            }
                            Text(
                                text = "${
                                    if (tiempo.minutos > 9) {
                                        tiempo.minutos
                                    } else {
                                        "0" + tiempo.minutos
                                    }
                                } : ${
                                    if (tiempo.segundos > 9) {
                                        tiempo.segundos
                                    } else {
                                        "0" + tiempo.segundos
                                    }
                                } minutos",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(AppTheme.dimens.small))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppTheme.dimens.medium),
                        elevation = 10.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(AppTheme.dimens.large),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Comp. por Min.",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.lat_minute),
                                    contentDescription = "compresiones por minuto",
                                    modifier = Modifier.padding(AppTheme.dimens.medium, AppTheme.dimens.medium)
                                )
                                Text(
                                    text = "${(scores.cpm.toFloat() / scores.cantidad).roundToInt()}",
                                    //text = "${((scores.cpm.toFloat())/(60*tiempo.minutos + tiempo.segundos)*100).roundToInt()}%",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                Text(
                                    text = "Posición",
                                    textAlign = TextAlign.Center,
                                    //color= gray,
                                    style = MaterialTheme.typography.body1
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.hand_pos),
                                    contentDescription = "posicion de la mano",
                                    modifier = Modifier.padding(AppTheme.dimens.medium, AppTheme.dimens.medium),
                                )
                                Text(
                                    text = "${((scores.posicion.toFloat()) / (60 * tiempo.minutos + tiempo.segundos) * 100).roundToInt()}%",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Desplazamiento",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.compresion),
                                    contentDescription = "compresiones",
                                    modifier = Modifier
                                        .rotate(90f)
                                        .padding(AppTheme.dimens.medium,AppTheme.dimens.medium)
                                )
                                Text(
                                    text = "${(((scores.desplaza.toFloat()) / dividendo) * 2).roundToInt()}%",
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    }
                }
            }
            MainScreen(navController,onBluetoothStateChanged)
        }
    }
    */
}

@Composable
fun CircularIndicator(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor:Color,
    minValue:Int = 0,
    maxValue:Int = 100,
    circleRadius:Float,
    //onPositionChange:(Int)->Unit

){
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    Box(modifier = modifier){
        Canvas(modifier = Modifier
            .fillMaxSize(),
        ){
            val width = size.width
            val height = size.height
            val circleThickness = width/25f
            circleCenter = Offset(x = width/2f, y = height/2f)

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )
            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )
            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f/maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f)/2f,
                    (height - circleRadius * 2f)/2f
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$positionValue",
                        circleCenter.x,
                        circleCenter.y + 45.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 38.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = white.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun calcular_puntuacion(div:Int): Int {

    val desvio = sqrt(((scores.cpm.toFloat()/scores.cantidad)-110).pow(2))

    val punta_cmp = 100-desvio.toInt()

    var puntuacion = ((punta_cmp)*.5 + (((scores.desplaza.toFloat())/div)*2)*.2 + ((scores.posicion.toFloat())/(60*tiempo.minutos + tiempo.segundos)*100)*.3).toInt()

    if (puntuacion < 0){
        puntuacion=0
    }
    else if (puntuacion>100){
        puntuacion=100
    }
    return puntuacion
}

@Preview(showBackground = true)
@Composable
fun Preview(){


    CircularIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(Red500),
        initialValue = 70,
        primaryColor = white,
        secondaryColor = Red200 ,
        circleRadius = 230f
    )
}