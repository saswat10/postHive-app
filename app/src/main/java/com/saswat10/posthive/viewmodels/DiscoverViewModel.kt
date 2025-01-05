package com.saswat10.posthive.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saswat10.network.models.domain.Post
import com.saswat10.posthive.di.DataStorage
import com.saswat10.posthive.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface DiscoverViewState {
    object Loading : DiscoverViewState
    object Error : DiscoverViewState
    data class Success(val data: List<Post>) : DiscoverViewState

}

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val dataStorage: DataStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow<DiscoverViewState>(DiscoverViewState.Loading)
    val uiState = _uiState.asStateFlow()

    val postVotes = mutableStateMapOf<Int, Pair<Int, Boolean>>()

    private var fetchedPostCache = mutableListOf<Post>()

    fun toggleVote(postId: Int) {
        viewModelScope.launch {
            val isCurrentlyVoted = postVotes[postId]?.second ?: false
            val votes = postVotes[postId]?.first?: 0
            val token = dataStorage.getBearerToken()
            if (token != null) {
                if (isCurrentlyVoted) {
                    postRepository.toggleVote(postId, token)
                    postVotes[postId] = Pair(votes-1 , false)
                } else {
                    postRepository.toggleVote(postId, token)
                    postVotes[postId] = Pair(votes+1 , true)
                }
            }
        }
    }

    fun refreshAllPosts(forceRefresh: Boolean = false) = viewModelScope.launch {
        if(fetchedPostCache.isNotEmpty()) return@launch
        val token = dataStorage.getBearerToken()
        if (token != null) {

            postRepository.fetchPosts(token).onSuccess { postList ->
                fetchedPostCache.clear()
                fetchedPostCache = postList.toMutableList()
                postList.forEach { post ->
                    postVotes[post.id] =
                        Pair(post.votes, post.hasVoted) // Replace with actual field from API
                }
                _uiState.update {
                    DiscoverViewState.Success(data = postList)
                }

            }.onFailure {
                _uiState.update { DiscoverViewState.Error }
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