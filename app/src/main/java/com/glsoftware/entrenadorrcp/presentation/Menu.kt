package com.glsoftware.entrenadorrcp.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.glsoftware.entrenadorrcp.presentation.components.BottomNavigationBar
import com.glsoftware.entrenadorrcp.presentation.Screen.*


@Composable
fun MainScreen(navController: NavController,
               onBluetoothStateChanged:()->Unit){

    val navigationItems = listOf(
        StartScreen,
        RcpScreen,
        AboutScreen
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController as NavHostController, items = navigationItems)}
    ) {

    }


}