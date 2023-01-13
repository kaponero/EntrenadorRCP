package com.glsoftware.entrenadorrcp.presentation.ScreenElements

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME = "RCPdtb"
    val SHARED_MINUTOS = "minutos"
    val SHARED_SEGUNDOS = "segundos"

    val SHARED_CPM = "com_por_minuto"
    val SHARED_DES = "desplazamiento"
    val SHARED_POS = "posision_mano"
    val SHARED_CON = "contador_compresiones"
    val SHARED_CAN = "cantidad_compresiones"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveTiempo(min:Int, seg:Int){
        storage.edit().putInt(SHARED_MINUTOS,min).apply()
        storage.edit().putInt(SHARED_SEGUNDOS,seg).apply()
    }

    fun saveScores(val1:Long,val2:Long, val3:Long, val4:Int, val5:Long){
        storage.edit().putLong(SHARED_CPM,val1).apply()
        storage.edit().putLong(SHARED_DES,val2).apply()
        storage.edit().putLong(SHARED_POS,val3).apply()
        storage.edit().putInt(SHARED_CON,val4).apply()
        storage.edit().putLong(SHARED_CAN,val5).apply()
    }

    fun delete(){
        storage.edit().remove(SHARED_CPM)
        storage.edit().remove(SHARED_DES)
        storage.edit().remove(SHARED_POS)
        storage.edit().remove(SHARED_CON)
        storage.edit().remove(SHARED_CAN)
        storage.edit().clear()
    }
    fun getCPM(): Long{
        val dato = storage.getLong(SHARED_CPM,0)
        return dato
    }

    fun getDes(): Long{
        val dato = storage.getLong(SHARED_DES,0)
        return dato
    }

    fun getPos(): Long{
        val dato = storage.getLong(SHARED_POS,0)
        return dato
    }

    fun getCon(): Int{
        val dato = storage.getInt(SHARED_CON,1)
        return dato
    }

    fun getCAN(): Long{
        val dato = storage.getLong(SHARED_CAN,1)
        return dato
    }

    fun getSegundos(): Int {
        val dato = storage.getInt(SHARED_SEGUNDOS,99)
        return dato
    }

    fun getMinutos(): Int {
        val dato = storage.getInt(SHARED_MINUTOS,99)
        return dato
    }



}

var actualizar_datos = true