package com.glsoftware.entrenadorrcp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavSplash(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen1.SplashScreen.route){
        composable(Screen1.SplashScreen.route){
            SplashScreen(navController = navController)
        }
    }

}




sealed class Screen1(val route:String){
    object SplashScreen:Screen1("splash_screen")
}