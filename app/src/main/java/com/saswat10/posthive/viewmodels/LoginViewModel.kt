package com.saswat10.posthive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.remote.RemoteCreatePost
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStorage: DataStorage
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting.asStateFlow()

    private val _uiState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(newTitle: String) {
        _email.value = newTitle
    }

    fun onPasswordChange(newContent: String) {
        _password.value = newContent
    }

    fun login() {
        viewModelScope.launch {
            _isSubmitting.value = true
            _uiState.update { LoginState.Loading }
            userRepository.loginUser(
                _email.value, _password.value
            ).onSuccess {
                _isSubmitting.value = false
                _email.value = ""
                _password.value = ""

                _uiState.update {
                    LoginState.Success
                }

                dataStorage.saveBearerToken(it.accessToken)
                Log.d("token", dataStorage.getBearerToken() ?: "")
            }.onFailure {exception->
                _isSubmitting.value = false
                _email.value = ""
                _password.value = ""

                _uiState.update {
                    LoginState.Error(exception.message.toString())
                }
            }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            val x = dataStorage.getBearerToken()
            Log.d("x", x ?: "")
        }
    }
}

sealed class LoginState{
    object Idle: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    data class Error(val message: String): LoginState()
}