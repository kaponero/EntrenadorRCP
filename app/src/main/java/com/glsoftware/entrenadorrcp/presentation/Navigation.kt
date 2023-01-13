package com.glsoftware.entrenadorrcp.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation(onBluetoothStateChanged:()->Unit
){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route){
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Screen.StartScreen.route){
            StartScreen(navController = navController,
                onBluetoothStateChanged)
        }
        composable(Screen.RcpScreen.route){
            RcpScreen(navController = navController,
                onBluetoothStateChanged)
        }
        composable(Screen.AboutScreen.route){
            AboutScreen(navController = navController,
                onBluetoothStateChanged)

        }
    }

}

sealed class Screen(
    val route:String,
    val title: String,
    val icon: ImageVector
){
    object StartScreen:Screen("start_screen","Inicio", Icons.Filled.Home)
    object RcpScreen:Screen("freq_screen","Entrenar",Icons.Filled.ArrowForward)
    object AboutScreen:Screen("about_screen","Acerca de", Icons.Filled.Info)
    object SplashScreen:Screen("splash_screen","splash",Icons.Filled.Build)
}