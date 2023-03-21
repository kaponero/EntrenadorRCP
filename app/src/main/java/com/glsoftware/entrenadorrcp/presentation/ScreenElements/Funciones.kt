package com.glsoftware.entrenadorrcp.presentation.ScreenElements

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.glsoftware.entrenadorrcp.ui.theme.white
//import com.glsoftware.entrenadorrcp.MainActivity.Companion.prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.pow
import kotlin.math.sqrt

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

    tiempo.segundos = 0
    tiempo.minutos = 0
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
        scores.posicion = ((posicion_correcta.value.toFloat()/(posicion_correcta.value + posicion_incorrecta.value))*100).toLong()
    return posicion_manos.value
}

@Composable
fun calculo_puntuación ():Int{

    var desvio_estandar = 0f
    if (scores.cantidad != 0L)
        desvio_estandar = sqrt(((scores.cpm.toFloat()/scores.cantidad)-110).pow(2))

    //val puntaje_frecuencia = 100 - ((100/110) * desvio_estandar)

    val puntaje_frecuencia = when{
        desvio_estandar <= 10f -> 100
        desvio_estandar in 11f..15f -> 90
        desvio_estandar in 15f..20f -> 85
        desvio_estandar in 20f..25f -> 85
        desvio_estandar in 25f..30f -> 85
        desvio_estandar in 30f..35f -> 80
        desvio_estandar in 35f..40f -> 75
        desvio_estandar in 40f..45f -> 70
        desvio_estandar in 45f..50f -> 65
        desvio_estandar in 50f..55f -> 60
        desvio_estandar in 55f..60f -> 55
        desvio_estandar in 60f..65f -> 50
        desvio_estandar in 65f..70f -> 45
        desvio_estandar in 70f..75f -> 40
        desvio_estandar in 75f..80f -> 35
        desvio_estandar in 80f..85f -> 30
        desvio_estandar in 85f..90f -> 25
        desvio_estandar in 90f..95f -> 20
        desvio_estandar in 95f..100f -> 15
        desvio_estandar in 100f..105f -> 10
        desvio_estandar in 105f..110f -> 5


        else -> 0
    }

    var puntaje_desplazamiento = 0
    if (scores.contador != 0)
        puntaje_desplazamiento = (((scores.desplaza.toFloat())/scores.contador)*2).toInt()

    val puntaje_posicion = scores.posicion.toInt()

    var puntaje = (puntaje_frecuencia.toFloat()/100 * puntaje_posicion.toFloat()/100 * puntaje_desplazamiento.toFloat()/100)*100

    return puntaje.toInt()
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
