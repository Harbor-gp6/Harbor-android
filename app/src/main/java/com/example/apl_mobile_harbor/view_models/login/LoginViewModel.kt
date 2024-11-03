package com.example.apl_mobile_harbor.view_models.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.classes.auth.Usuario
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import com.example.apl_mobile_harbor.interfaces.ApiHarbor.LoginResponse
import com.example.apl_mobile_harbor.interfaces.ApiHarbor.LoginRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val loginApi: ApiHarbor, private val tokenManager: TokenManager) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    var email by mutableStateOf("vitoramosc@gmail.com")
    var senha by mutableStateOf("040709qq")

    var manterConectado by mutableStateOf(false)

    var isEmAndamento by mutableStateOf(false)

    var emailOuSenhaEmBranco by mutableStateOf(false)

    var erroApi by mutableStateOf(false)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login() {
        viewModelScope.launch {
            if (email.isNotBlank() && senha.isNotBlank()) {
                isEmAndamento = true
                try {
                    val loginRequest = LoginRequest(email, senha)
                    val response = loginApi.login(loginRequest)
                    if (response.isSuccessful) {
                        _loginResponse.value = response.body()
                        _loginResponse.value?.let {

                            val usuario = Usuario(it.userId, it.nome, it.email, it.idEmpresa, it.token)
                            tokenManager.saveToken(usuario, manterConectado)
                        }
                    } else {
                        _error.value = "Login failed: ${response.errorBody()?.string()}"
                        erroApi = true
                    }
                } catch (e: Exception) {
                    _error.value = "Error: ${e.message}"
                    erroApi = true
                } finally {
                    delay(3000)
                    isEmAndamento = false
                }
            } else {
                emailOuSenhaEmBranco = true
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
    }
}
