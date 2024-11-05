package com.example.apl_mobile_harbor.view_models.servico

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.servico.Servico
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class ServicoViewModel(val apiHarbor: ApiHarbor): ViewModel() {

    val servicos = mutableStateListOf<Servico>()

    fun getServicos() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getServicos()
                if (response.isSuccessful) {
                    servicos.clear()
                    response.body()?.let { servicos.addAll(it) }
                } else {
                    Log.e("api", "Erro ao buscar serviços")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }
}