package com.saswat10.posthive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStorage: DataStorage
): ViewModel() {

    fun logout(){
        viewModelScope.launch {
            dataStorage.removeBearerToken()
        }
    }
}