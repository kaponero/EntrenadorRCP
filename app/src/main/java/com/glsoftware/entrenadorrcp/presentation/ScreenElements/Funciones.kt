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
    var posicion:Long = 0
    var contador:Int = 0
    var cantidad: Long = 0
}

fun reset_scores(){
    scores.cpm = 0
    scores.desplaza = 0
    scores.posicion = 0
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
fun calculo_desplazamiento(compresion : Int){
    val compresion_anterior1 = remember { mutableStateOf(0) }
    val compresion_anterior2 = remember { mutableStateOf(0) }

    if (compresion_anterior2.value!=compresion){
        if (compresion_anterior2.value>compresion_anterior1.value && compresion_anterior2.value>compresion){
            scores.desplaza+=compresion_anterior2.value
            scores.contador++
        }
        compresion_anterior1.value = compresion_anterior2.value
        compresion_anterior2.value = compresion
    }
}

@Composable
fun calculo_posicion (valor: Int, compresion:Int): Boolean {
    val posicion_manos = remember { mutableStateOf(false) }
    val compresion_anterior = remember { mutableStateOf(0) }

    val posicion_correcta = remember { mutableStateOf(0) }
    val posicion_incorrecta = remember { mutableStateOf(0) }

    if (compresion > compresion_anterior.value){
        posicion_manos.value = valor == 1
        if (posicion_manos.value)
            posicion_correcta.value++
        else
            posicion_incorrecta.value++
    }
    compresion_anterior.value = compresion

    if (posicion_correcta.value==0 && posicion_incorrecta.value==0)
        scores.posicion = 0
    else
        scores.posicion = (posicion_correcta.value/(posicion_correcta.value + posicion_incorrecta.value)).toLong()*100
    return posicion_manos.value
}

@Composable
fun isIniciar(manos: Int): Boolean{
    val manos_correctas = remember { mutableStateOf(false) }

    if (manos == 1){
        manos_correctas.value = true
    }
    return manos_correctas.value
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
