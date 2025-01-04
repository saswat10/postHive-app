package com.saswat10.posthive.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.domain.Comment
import com.saswat10.network.models.domain.Post
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SinglePostViewState {
    object Loading : SinglePostViewState
    object Error : SinglePostViewState
    data class Success(val data: Post) : SinglePostViewState

}

sealed interface CommentsState {
    object Loading : CommentsState
    object Error : CommentsState
    data class Success(val data: List<Comment>) : CommentsState
}

@HiltViewModel
class SinglePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val dataStorage: DataStorage
) : ViewModel(){

    private val _postState = MutableStateFlow<SinglePostViewState>(SinglePostViewState.Loading)
    val postState = _postState.asStateFlow()

    private val _commentState = MutableStateFlow<CommentsState>(CommentsState.Loading)
    val commentState =_commentState.asStateFlow()

    fun getPostById(id: Int) = viewModelScope.launch {
        val token = dataStorage.getBearerToken()
        if (token != null) {
            postRepository.fetchPostById(id, token).onSuccess { post ->
                _postState.update {
                    SinglePostViewState.Success(data = post)
                }
            }.onFailure {
                _postState.update { SinglePostViewState.Error }
            }
        }
    }

    fun getCommentsByPostId(id: Int) = viewModelScope.launch {
        postRepository.fetchComments(id).onSuccess { commentList ->
            _commentState.update {
                CommentsState.Success(data = commentList)
            }
        }.onFailure {
            _commentState.update { CommentsState.Error }
        }
    }
}