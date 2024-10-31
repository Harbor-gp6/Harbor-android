package com.example.apl_mobile_harbor.view_models.pedidos

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.pedido.Pedido
import com.example.apl_mobile_harbor.classes.pedido.convertToDate
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class PedidosViewModel(private val tokenManager: TokenManager, private val apiHarbor: ApiHarbor): ViewModel() {
    val pedidos = mutableStateListOf<Pedido>()

    val pedidosPorData = mutableStateListOf<Pedido>()

    var selectedDate = mutableStateOf("")

    val usuario = tokenManager.getUsuario()

    fun atualizarListaDePedidos() {
        viewModelScope.launch {
            try {
                if (usuario != null) {
                    val retorno = apiHarbor.getPedidos()
                    if (retorno.isSuccessful) {
                        pedidos.clear()
                        pedidos.addAll(retorno.body()?.sortedBy { convertToDate(it.dataAgendamento) } ?: emptyList())
                    } else {
                        Log.i("erro", "Erro")
                    }
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun finalizarPedido(pedido: Pedido) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.finalizarPedido(pedido.codigoPedido)
                if (response.isSuccessful) {
                    pedidos.remove(pedido)
                } else {
                    Log.e("erro", "Erro ao finalizar")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun cancelarPedido(pedido: Pedido) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.cancelarPedido(pedido.codigoPedido)
                if (response.isSuccessful) {
                    pedidos.remove(pedido)
                } else {
                    Log.e("erro", "Erro ao cancelar")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun getPedidosPorData() {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getPedidosPorData(selectedDate.value)
                if (response.isSuccessful) {
                    pedidosPorData.clear()
                    response.body()?.let { pedidosPorData.addAll(it) }
                } else {
                    Log.e("erro", "Erro ao buscar por data")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }
}
