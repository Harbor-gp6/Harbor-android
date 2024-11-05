package com.example.apl_mobile_harbor.view_models.pedidos

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.classes.empresa.Empresa
import com.example.apl_mobile_harbor.classes.pedido.Cliente
import com.example.apl_mobile_harbor.classes.pedido.Pedido
import com.example.apl_mobile_harbor.classes.pedido.PedidoCriacao
import com.example.apl_mobile_harbor.classes.pedido.PedidoPrestadorCriacao
import com.example.apl_mobile_harbor.classes.pedido.PedidoProdutoCriacao
import com.example.apl_mobile_harbor.classes.pedido.convertToDate
import com.example.apl_mobile_harbor.componentes.separarNomeCompleto
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import kotlinx.coroutines.launch

class PedidosViewModel(private val tokenManager: TokenManager, private val apiHarbor: ApiHarbor, private val usuario: Usuario): ViewModel() {
    val pedidos = mutableStateListOf<Pedido>()

    val pedidosPorData = mutableStateListOf<Pedido>()

    var selectedDate = mutableStateOf("")

    val _pedidoAtual = MutableLiveData<Pedido?>()

    val empresaPrestador = mutableStateOf<Empresa?>(null)

    val pedidoAtual: LiveData<Pedido?> get() = _pedidoAtual

    private val _pedidoCriacao = mutableStateOf<PedidoCriacao?>(null)
    val pedidoCriacao: State<PedidoCriacao?> = _pedidoCriacao

    fun atualizarListaDePedidos() {
        viewModelScope.launch {
            try {
                val retorno = apiHarbor.getPedidos()
                if (retorno.isSuccessful) {
                    pedidos.clear()
                    pedidos.addAll(retorno.body()?.sortedBy { convertToDate(it.dataAgendamento) } ?: emptyList())
                } else {
                    Log.i("erro", "Erro")
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

    fun getPedidoPorCodigo(codigo: String) {
        viewModelScope.launch {
            try {
                val response = apiHarbor.getPedidoPorCodigo(codigo)
                if (response.isSuccessful) {
                    response.body()?.let { _pedidoAtual.value = it }
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun Pedido.toPedidoCriacao(): PedidoCriacao {
        val (nome, sobrenome) = separarNomeCompleto(this.nomeCliente)
        return PedidoCriacao(
            cliente = Cliente(
                nome = nome,
                sobrenome = sobrenome, // Se você tiver sobrenome no Pedido, inclua aqui.
                telefone = this.telefoneCliente,
                cpf = this.cpfCliente,
                email = this.emailCliente
            ),
            cnpjEmpresa = (empresaPrestador.value?.cnpj ?: ""), // Preencha com o valor adequado, se disponível
            pedidoPrestador = this.pedidoPrestador.map {
                PedidoPrestadorCriacao(it.idPrestador, it.idServico)
            },
            pedidoProdutos = this.pedidoProdutos.map {
                PedidoProdutoCriacao(it.idProduto, it.quantidade)
            },
            dataAgentamento = this.dataAgendamento,
            formaPagamentoEnum = this.formaPagamentoEnum
        )
    }

    fun getEmpresa() {
        viewModelScope.launch {
            try {
                val response = usuario.idEmpresa?.let { apiHarbor.getEmpresaPorId(it) }
                if (response != null) {
                    if (response.isSuccessful) {
                        response.body().let { empresaPrestador.value = it }
                    } else {
                        Log.e("api", "Erro ao buscar empresa")
                    }
                } else {
                    Log.e("api", "Empresa nula")
                }
            } catch (err: Exception) {
                Log.e("api", err.toString())
            }
        }
    }

    fun carregarPedido(pedido: Pedido) {
        _pedidoCriacao.value = pedido.toPedidoCriacao()
    }

    // Atualizadores dos atributos de cliente
    fun atualizarAtributoCliente(campo: (Cliente) -> Cliente) {
        _pedidoCriacao.value = _pedidoCriacao.value?.copy(cliente = campo(_pedidoCriacao.value?.cliente ?: Cliente("", "", "", "", "")))
    }

    fun atualizarFormaPagamento(novaForma: String) {
        _pedidoCriacao.value = _pedidoCriacao.value?.copy(formaPagamentoEnum = novaForma)
    }

    fun atualizarPedidoPrestador(servicoId: Int, prestadorId: Int?) {
        _pedidoCriacao.value = _pedidoCriacao.value?.let { pedidoCriacao ->
            val novosPedidoPrestador = pedidoCriacao.pedidoPrestador.toMutableList().apply {
                // Remove qualquer pedido relacionado ao serviço
                removeIf { it.servicoId == servicoId }
                // Adiciona o novo pedido caso prestadorId não seja nulo
                prestadorId?.let {
                    add(PedidoPrestadorCriacao(prestadorId = it, servicoId = servicoId))
                }
            }
            pedidoCriacao.copy(pedidoPrestador = novosPedidoPrestador)
        }
    }

    fun atualizarPedidoProduto(produtoId: Int, quantidade: Int) {
        _pedidoCriacao.value = _pedidoCriacao.value?.let { pedidoCriacao ->
            val novosPedidoProdutos = pedidoCriacao.pedidoProdutos.toMutableList().apply {
                // Remove o produto se a quantidade for zero
                removeIf { it.id == produtoId }
                // Adiciona ou atualiza o produto se a quantidade for maior que zero
                if (quantidade > 0) {
                    add(PedidoProdutoCriacao(id = produtoId, quantidade = quantidade))
                }
            }
            pedidoCriacao.copy(pedidoProdutos = novosPedidoProdutos)
        }
    }


    fun atualizarPedidoCriacao() {
        val pedidoCriacao = _pedidoCriacao.value
        if (pedidoCriacao != null) {
            viewModelScope.launch {
                try {
                    val response =
                        _pedidoAtual.value?.let { apiHarbor.atualizarPedido(pedidoCriacao, it.idPedido) } // Chame sua função de API para atualização
                    if (response != null) {
                        if (response.isSuccessful) {
                            Log.i("sucesso", "Pedido atualizado com sucesso")
                            // Aqui, você pode atualizar o estado da UI ou realizar outras ações após o sucesso
                        } else {
                            Log.e("erro", "Erro ao atualizar pedido")
                        }
                    }
                } catch (err: Exception) {
                    Log.e("api", err.toString())
                }
            }
        } else {
            Log.e("erro", "PedidoCriacao é nulo")
        }
    }



}
