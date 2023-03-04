package com.glsoftware.entrenadorrcp

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.glsoftware.entrenadorrcp.presentation.MainScreen
import com.glsoftware.entrenadorrcp.presentation.NavSplash
import com.glsoftware.entrenadorrcp.presentation.Navigation
import com.glsoftware.entrenadorrcp.presentation.Screen
import com.glsoftware.entrenadorrcp.presentation.ScreenElements.Prefs
import com.glsoftware.entrenadorrcp.ui.theme.EntrenadorRCPTheme
import com.glsoftware.entrenadorrcp.ui.theme.rememberWindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject lateinit var bluetoothAdapter: BluetoothAdapter

    companion object{
        lateinit var prefs: Prefs
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = Prefs(this)
        setContent {
            val window = rememberWindowSizeClass()
            EntrenadorRCPTheme(window) {
                //window?.statusBarColor = Color.Gray.toArgb()
                Navigation(
                    onBluetoothStateChanged = {
                        showBluetoothDialog()
                    }
                )
            }
        }

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    }

    override fun onStart() {
        super.onStart()
        showBluetoothDialog()
    }

    private var isBluetoothDialogAlreadyShown = false
    private fun showBluetoothDialog(){
        if (!bluetoothAdapter.isEnabled){
            if (!isBluetoothDialogAlreadyShown){
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startBluetoothIntentForResult.launch(enableBluetoothIntent)
                isBluetoothDialogAlreadyShown = true
            }
        }
    }

    private val startBluetoothIntentForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            isBluetoothDialogAlreadyShown = false
            if (result.resultCode != Activity.RESULT_OK){
                showBluetoothDialog()
            }
        }
}