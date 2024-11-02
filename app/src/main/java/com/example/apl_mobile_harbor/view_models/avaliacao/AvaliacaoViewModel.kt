package com.example.apl_mobile_harbor.view_models.avaliacao

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.avaliacao.Avaliacao
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class AvaliacaoViewModel(private val apiHarbor: ApiHarbor): ViewModel() {

    var mediaPrestador = mutableStateOf<Double?>(0.0)

    val avaliacoesPrestador = mutableStateListOf<Avaliacao>()

    fun getMedia() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getMedia()
                if (response.isSuccessful) {
                    response.body()?.let { mediaPrestador.value = it }
                } else {
                    Log.e("api", "Erro ao buscar média")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun getAvaliacoes() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getAvaliacoes()
                if (response.isSuccessful) {
                    avaliacoesPrestador.clear()
                    response.body()?.let { avaliacoesPrestador.addAll(it) }
                } else {
                    Log.e("api", "Erro ao buscar avaliacoes")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }
}
