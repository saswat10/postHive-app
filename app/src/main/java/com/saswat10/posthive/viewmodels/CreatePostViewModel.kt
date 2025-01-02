package com.saswat10.posthive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.remote.RemoteCreatePost
import com.saswat10.posthive.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    fun createPost() {
        viewModelScope.launch {
            _isSubmitting.value = true
            postRepository.createNewPost(
                body = RemoteCreatePost(content.value, title = title.value),
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjo1LCJleHAiOjE3MzU4NTMyNzZ9.TbWh5HN0Ke8o2v_pPgB6JUPFvti5LX9G1CfP5WpFIh4"
            ).onSuccess {
                _isSubmitting.value = false
                _title.value = ""
                _content.value = ""
                _error.value = ""
            }.onFailure {
                _error.value = it.message.toString()
                _isSubmitting.value = false
                _title.value = ""
                _content.value = ""
            }
        }
    }
}