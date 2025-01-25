package com.saswat10.posthive.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.saswat10.posthive.components.LoadingIndicator
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar
import com.saswat10.posthive.viewmodels.DiscoverViewModel
import com.saswat10.posthive.viewmodels.DiscoverViewState

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    navController: NavHostController,
    onClicked: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val postVotes = viewModel.postVotes


    LaunchedEffect(key1 = Unit) {
        viewModel.refreshAllPosts(forceRefresh = true)
    }

    Column{
        Toolbar("Post Hive")
        when (val state = uiState) {
            is DiscoverViewState.Loading -> LoadingIndicator(Modifier.weight(1f))
            is DiscoverViewState.Error -> {
                Column(
                    Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { viewModel.refreshAllPosts() }) { Text("Refresh") }
                }
            }

            is DiscoverViewState.Success -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
                    state.data.forEach {
                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                            PostListComponent(it, function = {onClicked(it.id)}, hasVoted = postVotes[it.id]?.second?:false, toggle = {viewModel.toggleVote(it.id)}, votes = postVotes[it.id]?.first?:0)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }
        }

    }
}