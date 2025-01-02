package com.saswat10.posthive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saswat10.posthive.components.Comments
import com.saswat10.posthive.components.LoadingIndicator
import com.saswat10.posthive.components.PostCardComponent
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar
import com.saswat10.posthive.viewmodels.CommentsState
import com.saswat10.posthive.viewmodels.DiscoverViewState
import com.saswat10.posthive.viewmodels.SinglePostViewModel
import com.saswat10.posthive.viewmodels.SinglePostViewState

@Composable
fun SinglePost(
    viewModel: SinglePostViewModel = hiltViewModel()
) {
    val postState by viewModel.postState.collectAsStateWithLifecycle()
    val commentsState by viewModel.commentState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPostById(1)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getCommentsByPostId(1)
    }
    Column {
        Toolbar("", onBackAction = {})
        when (val state = postState) {
            is SinglePostViewState.Loading -> LoadingIndicator()
            is SinglePostViewState.Error -> {
                Text("Error Loading the content")
            }

            is SinglePostViewState.Success -> PostListComponent(state.data)
        }
        HorizontalDivider()
        when (val state = commentsState) {
            is CommentsState.Loading -> LoadingIndicator()
            is CommentsState.Error -> {
                Text("Error Loading the content")
            }

            is CommentsState.Success -> {
                LazyColumn {
                    state.data.forEach {
                        item {
                            Comments(it)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EditComment() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            "Add your comment",
            onValueChange = {},
            shape = RoundedCornerShape(25),
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(25)
                )
                .padding(12.dp)
        )
    }
}