package com.saswat10.posthive.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
    onClicked: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.refreshAllPosts(forceRefresh = true)
    }

    Column{
        Toolbar("Discover")
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
                LazyColumn {
                    state.data.forEach {
                        item {
                            Log.d("posts", it.toString())
                            PostListComponent(it) {
                                onClicked(it.id)
                            }
                        }
                    }
                }
            }
        }

    }
}