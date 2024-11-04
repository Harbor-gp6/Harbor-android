package com.example.apl_mobile_harbor.view_models.prestador

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.classes.prestador.Prestador
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

data class PrestadorViewModel(val apiHarbor: ApiHarbor, val usuario: Usuario): ViewModel() {

    val prestadores = mutableStateListOf<Prestador>()

    fun getPrestadores() {
        viewModelScope.launch {
            try {
                val response = usuario.idEmpresa?.let { apiHarbor.getPrestadores(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        prestadores.clear()
                        response.body()?.let { prestadores.addAll(it) }
                    } else {
                        Log.e("api", "Erro ao buscar prestadores")
                    }
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

}
