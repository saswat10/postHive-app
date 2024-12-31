package com.saswat10.posthive.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar

@Composable
fun SearchScreen() {

    Column {
        Toolbar("Search")
        Column {
            AnimatedVisibility(visible = true) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(6.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                )
                BasicTextField(
                    onValueChange = {},
                    value = "Hello",
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface) + MaterialTheme.typography.titleMedium,
                    cursorBrush = Brush.linearGradient(
                        0.0f to MaterialTheme.colorScheme.onSurface,
                        0.3f to MaterialTheme.colorScheme.onSurface,
                        1.0f to MaterialTheme.colorScheme.onSurface,
                        start = Offset(0.0f, 50.0f),
                        end = Offset(0.0f, 100.0f)
                    ),
                    modifier = Modifier.weight(1f)
                )
                AnimatedVisibility(visible = true) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        modifier = Modifier.clickable {

                        })
                }


            }
            HorizontalDivider()
            LazyColumn {
                repeat(10) {
                    item {
                        PostListComponent()
                    }
                }
            }
        }
    }
}