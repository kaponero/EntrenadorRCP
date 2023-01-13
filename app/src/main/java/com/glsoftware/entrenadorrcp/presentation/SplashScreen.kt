package com.glsoftware.entrenadorrcp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.glsoftware.entrenadorrcp.MainActivity.Companion.prefs
import com.glsoftware.entrenadorrcp.R
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.scores
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.tiempo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){

    LaunchedEffect(key1 = true){
        delay(2000)
        navController.popBackStack()
        navController.navigate(Screen.StartScreen.route)

        tiempo.minutos = prefs.getMinutos()
        tiempo.segundos = prefs.getSegundos()
        scores.cpm = prefs.getCPM()
        scores.desplaza = prefs.getDes()
        scores.posicion = prefs.getPos()
        scores.contador = prefs.getCon()
        scores.cantidad = prefs.getCAN()
        //prefs.delete()
    }

    Logos()
}

@Composable
fun Logos(){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        //Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_ingenieria),
            contentDescription = "Logo Engineering",
            modifier = Modifier
                .scale(0.7f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.iconohd),
                contentDescription = "Logo App",
            )
            Text(
                text = "EntrenadorRCP",
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        Image(
            painter = painterResource(id = R.drawable.glslogohd),
            contentDescription = "Logo GLS",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    Logos()

}