package com.saswat10.posthive.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.remote.RemoteRegistration
import com.saswat10.posthive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel(){
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun onUsernameChange(it: String) {
        _username.value = it
    }

    fun onEmailChange(it: String) {
        _email.value = it
    }

    fun onPasswordChange(it: String) {
        _password.value = it
    }

    fun register() {
        viewModelScope.launch {
            _isSubmitting.value = true
            userRepository.registerUser(
                RemoteRegistration(_email.value, _username.value, _password.value)
            ).onSuccess {
                _isSubmitting.value = false
                _email.value = ""
                _password.value = ""
                _username.value = ""
                _error.value = ""
            }.onFailure {
                _error.value = it.message.toString()
                _isSubmitting.value = false
                _email.value = ""
                _password.value = ""
                _username.value= ""
                Log.d("Error", error.value)
            }
        }
    }
}