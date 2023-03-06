package com.glsoftware.entrenadorrcp.presentation.ScreenElements

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.glsoftware.entrenadorrcp.ui.theme.white
//import com.glsoftware.entrenadorrcp.MainActivity.Companion.prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun rememberCountdownTimerState(
    cpm_value : Int,
    des_value: Int,
    pos_value :Int,
    endMillis : Long = 600000,
    step : Long = 1000,
): tiempo{
    val timerLeft = remember { mutableStateOf(0) }
    LaunchedEffect(endMillis, step){
        while (isActive && timerLeft.value < endMillis) {
            timerLeft.value = ((timerLeft.value + step).toInt())
            tiempo.segundos +=1
            if (tiempo.segundos == 60){
                tiempo.segundos = 0
                tiempo.minutos += 1
            }
            if (pos_value==1){
                scores.posicion+=1
            }
            delay(step)
        }
    }
    scores.cpm += cpm_value
    scores.cantidad ++
    //Log.d("score",(scores.cpm.toFloat()/scores.cantidad.toFloat()).toString())

    return tiempo
}

object tiempo{

    var minutos = 0
    var segundos = 0
}

object scores{
    var cpm:Long = 0
    var desplaza:Long = 0
    var posicion:Long = 1
    var contador:Int = 0
    var cantidad: Long = 0
}

fun reset_scores(){
    scores.cpm = 0
    scores.desplaza = 0
    scores.posicion = 1
    scores.contador = 0
    scores.cantidad = 0

}

fun reset_tiempo(){

    tiempo.segundos = 1
    tiempo.minutos = 1
}
@Composable
fun calculo_frecuencia(cmp_value:Int){
    val frecuancia = remember { mutableStateOf(0) }
    if (cmp_value != frecuancia.value){
        scores.cpm += cmp_value
        scores.cantidad++
    }
}


@Composable
fun calculo_desplazamiento(des_value : Int){
    val compresion_1 = remember { mutableStateOf(0) }
    val compresion_2 = remember { mutableStateOf(0) }

    if (compresion_2.value!=des_value){
        if (compresion_2.value>compresion_1.value && compresion_2.value>des_value){
            scores.desplaza+=compresion_2.value
            scores.contador++
        }
        compresion_1.value = compresion_2.value
        compresion_2.value = des_value
    }
}

//----------------------------------------------------------------------------------------------------------------------
@Composable
fun timer(
    endMillis : Long = 600000,
    step : Long = 1000,
){
    val timeLeft = remember { mutableStateOf(0) }
    val segundos_ = remember { mutableStateOf(0) }
    val minutos_ = remember { mutableStateOf(0) }
    LaunchedEffect(endMillis, step){
        while (isActive && timeLeft.value < endMillis) {
            timeLeft.value = ((timeLeft.value + step).toInt())
            segundos_.value++
            if (segundos_.value == 60){
                segundos_.value = 0
                minutos_.value++
            }
            delay(step)
        }
    }

    Text(
        text = "${
            if (minutos_.value > 9) {
                minutos_.value
            } else {
                "0" + minutos_.value
            }
        } : ${
            if (segundos_.value > 9) {
                segundos_.value
            } else {
                "0" + segundos_.value
            }
        }",
        style = MaterialTheme.typography.h1,
        color = white
        )

    tiempo.segundos = segundos_.value
    tiempo.minutos = minutos_.value
}
