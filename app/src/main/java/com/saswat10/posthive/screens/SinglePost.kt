package com.saswat10.posthive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.saswat10.posthive.components.Comments
import com.saswat10.posthive.components.LoadingIndicator
import com.saswat10.posthive.components.PostCardComponent
import com.saswat10.posthive.components.Toolbar
import com.saswat10.posthive.viewmodels.CommentsState
import com.saswat10.posthive.viewmodels.SinglePostViewModel
import com.saswat10.posthive.viewmodels.SinglePostViewState

@Composable
fun SinglePost(
    viewModel: SinglePostViewModel = hiltViewModel(),
    navController: NavHostController,
    postId: Int,
    onBackClicked: () -> Unit
) {
    val postState by viewModel.postState.collectAsStateWithLifecycle()
    val commentsState by viewModel.commentState.collectAsStateWithLifecycle()
    val voteNumber = viewModel.voteNumber.value
    val commentNumber = viewModel.commentNumber.value
    val hasVoted = viewModel.hasVoted.value

    val comments by viewModel.comments.collectAsStateWithLifecycle()

    val comment by viewModel.comment.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPostById(postId)
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.getCommentsByPostId(postId)
    }

    val isUpdateEnabled = viewModel.isUpdateEnabled.value

    Column {
        Toolbar("", onBackAction = { onBackClicked() })
        LazyColumn {
            when (val state = postState) {
                is SinglePostViewState.Loading -> item { LoadingIndicator() }
                is SinglePostViewState.Error -> {
                    item {
                        Text("Error Loading the content")
                    }
                }

                is SinglePostViewState.Success -> item {
                    PostCardComponent(
                        post = state.data,
                        onDelete = {
                            if (isUpdateEnabled) {
                                viewModel.deletePost(postId)
                                navController.navigateUp()
                            } else null
                        },
                        onEdit = {
                            if (isUpdateEnabled) {
                                navController.navigate("update_screen/$postId")
                            } else null
                        },
                        function = { viewModel.toggleVote(postId) },
                        hasVoted = hasVoted,
                        commentNumber = comments.size,
                        voteNumber = voteNumber
                    )
                }
            }
            item {
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = comment, onValueChange = { viewModel.onCommentChange(it) },
                        shape = RoundedCornerShape(25),
                        placeholder = { Text("Add a comment") },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(25)).weight(1f),
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            viewModel.addComment(postId)
                        },
                        content = { Icon(contentDescription = "null", imageVector =  Icons.AutoMirrored.Rounded.Send)}
                    )
                }
            }

            when (val state = commentsState) {
                is CommentsState.Loading -> item { LoadingIndicator() }
                is CommentsState.Error -> {
                    item {
                        Text("Error Loading the content")
                    }
                }

                is CommentsState.Success -> {

                    items(comments) { comment ->
                        Comments(comment)
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(16.dp))
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