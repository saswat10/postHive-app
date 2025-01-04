package com.saswat10.posthive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.remote.RemoteCreatePost
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val dataStorage: DataStorage
) : ViewModel() {

    private val _id = MutableStateFlow(-1)
    val id: StateFlow<Int> = _id.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _postState = MutableStateFlow<SinglePostViewState>(SinglePostViewState.Loading)
    val postState = _postState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _title.value = newTitle
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    fun getPostById(id: Int) = viewModelScope.launch {
        val token = dataStorage.getBearerToken()
        if (token != null) {
            postRepository.fetchPostById(id, token).onSuccess { post ->
                _postState.update {
                    SinglePostViewState.Success(data = post)
                }
                _title.value = post.title
                _content.value = post.content
                _id.value = post.id
            }.onFailure {
                _postState.update { SinglePostViewState.Error }
            }
        }
    }

    fun updatePost() {
        viewModelScope.launch {
            _isSubmitting.value = true
            val token = dataStorage.getBearerToken()
            if (token != null) {
                postRepository.updatePost(
                    body = RemoteCreatePost(content.value, title = title.value),
                    token = token,
                    id = id.value
                ).onSuccess {
                    _isSubmitting.value = false
                    _title.value = ""
                    _content.value = ""
                }.onFailure {
                    _isSubmitting.value = false
                    _title.value = ""
                    _content.value = ""
                    _postState.update { SinglePostViewState.Error }
                }
            }
        }
    }
}