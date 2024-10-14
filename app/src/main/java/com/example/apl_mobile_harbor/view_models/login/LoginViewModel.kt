package com.example.apl_mobile_harbor.view_models.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apl_mobile_harbor.classes.auth.TokenManager
import com.example.apl_mobile_harbor.interfaces.ApiHarbor
import com.example.apl_mobile_harbor.interfaces.ApiHarbor.LoginResponse
import com.example.apl_mobile_harbor.interfaces.ApiHarbor.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(private val loginApi: ApiHarbor, private val tokenManager: TokenManager) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    var manterConectado by mutableStateOf(false)

    var emailOuSenhaEmBranco by mutableStateOf(false)

    var erroApi by mutableStateOf(false)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.isNotBlank() && password.isNotBlank()) {
                try {
                    val loginRequest = LoginRequest(username, password)
                    val response = loginApi.login(loginRequest)
                    if (response.isSuccessful) {
                        _loginResponse.value = response.body()
                        _loginResponse.value?.let { tokenManager.saveToken(it.token, manterConectado) }
                    } else {
                        _error.value = "Login failed: ${response.errorBody()?.string()}"
                        _loginResponse.value = null
                        erroApi = true
                    }
                } catch (e: Exception) {
                    _error.value = "Error: ${e.message}"
                    _loginResponse.value = null
                    erroApi = true
                }
            }
        }
    }
}
