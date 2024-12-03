package com.example.apl_mobile_harbor.view_models.atividade

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.atividade.Atividade
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class AtividadeViewModel(private val apiHarbor: ApiHarbor): ViewModel() {

    val atividadesRecentes = mutableStateListOf<Atividade>()

    init {
        getAtividades()
    }

    private fun getAtividades() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getAtividades()
                if (response.isSuccessful) {
                    atividadesRecentes.clear()
                    response.body()?.let {
                        it.forEach { atividade ->
                            try {
                                val responsePedido = apiHarbor.getPedidoPorCodigo(atividade.codigoPedido)
                                atividade.pedido = responsePedido.body()
                            } catch (err: Exception) {
                                Log.e("api", err.toString())
                            }
                        }
                        atividadesRecentes.addAll(it)
                    }
                } else {
                    Log.e("api", "Erro ao buscar atividades")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }
}