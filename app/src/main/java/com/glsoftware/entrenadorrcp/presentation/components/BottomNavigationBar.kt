package com.glsoftware.entrenadorrcp.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.glsoftware.entrenadorrcp.presentation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Screen>
){
    val currentRoute= currentRoute(navController)

    BottomNavigation {
        items.forEach {screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title)},
                label = { Text(screen.title)},
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route){
                        //navController.popBackStack()
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}