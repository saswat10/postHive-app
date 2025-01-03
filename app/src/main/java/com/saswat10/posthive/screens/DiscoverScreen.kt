package com.saswat10.posthive.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    onClicked: (Int)-> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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
                            PostListComponent(it){
                                onClicked(it.id)
                            }
                        }
                    }
                }
            }
        }

    }
}