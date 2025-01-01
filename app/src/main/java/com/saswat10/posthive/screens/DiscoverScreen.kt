package com.saswat10.posthive.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saswat10.posthive.components.LoadingIndicator
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar
import com.saswat10.posthive.viewmodels.DiscoverViewModel
import com.saswat10.posthive.viewmodels.DiscoverViewState

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.refreshAllPosts()
    }

    Column {
        Toolbar("Discover")
        when (val state = uiState) {
            is DiscoverViewState.Loading -> LoadingIndicator()
            is DiscoverViewState.Error -> {
                Text("Error Loading the content")
            }

            is DiscoverViewState.Success -> {
                LazyColumn {
                    state.data.forEach {
                        item {
                            PostListComponent(it)
                        }
                    }
                }
            }
        }

    }
}