package com.saswat10.posthive.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.domain.User
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStorage: DataStorage
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            dataStorage.removeBearerToken()
        }
    }

    private val _uiState = MutableStateFlow<ProfileViewState>(ProfileViewState.Loading)
    val uiState = _uiState.asStateFlow()

    private var fetchedUser = mutableSetOf<User>()

    fun getMyself() {
        viewModelScope.launch {
            if(fetchedUser.isNotEmpty()) return@launch
            val token = dataStorage.getBearerToken()
            if (token != null) {
                userRepository.getMyInfo(token = token)
                    .onSuccess { user ->
                        fetchedUser.clear()
                        fetchedUser.add(user)
                        _uiState.update {
                            ProfileViewState.Success(
                                data = user
                            )
                        }
                        dataStorage.saveUserId(user.id.toString())
                        Log.d("id", user.id.toString())
                    }.onFailure { exception ->
                        _uiState.update {
                            ProfileViewState.Error(
                                message = exception.message.toString()
                            )
                        }
                    }
            }
        }
    }
}

sealed interface ProfileViewState {
    object Loading : ProfileViewState
    data class Error(val message: String) : ProfileViewState
    data class Success(val data: User) : ProfileViewState
}