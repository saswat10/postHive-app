package com.saswat10.posthive.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar

@Composable
fun DiscoverScreen() {

    Column {
        Toolbar("Discover")
        LazyColumn {
            repeat(20) {
                item { PostListComponent() }
            }
        }
    }
}