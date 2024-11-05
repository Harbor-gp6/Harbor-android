package com.example.apl_mobile_harbor.view_models.prestador

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.classes.empresa.Empresa
import com.example.apl_mobile_harbor.classes.prestador.Prestador
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

data class PrestadorViewModel(val apiHarbor: ApiHarbor, val usuario: Usuario): ViewModel() {

    val prestadores = mutableStateListOf<Prestador>()

    val _prestadorAtual = MutableLiveData<Prestador?>()
    val prestadorAtual: LiveData<Prestador?> get() = _prestadorAtual

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
                } else {
                    Log.e("api", "Lista nula")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun getPrestadorPorId(id: Int) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getPrestadorPorId(id)
                if (response.isSuccessful) {
                    response.body()?.let { _prestadorAtual.value = it }
                } else {
                    Log.e("api", "Erro ao buscar prestador")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

}
