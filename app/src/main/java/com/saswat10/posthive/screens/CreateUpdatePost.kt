package com.saswat10.posthive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saswat10.posthive.components.Toolbar
import com.saswat10.posthive.viewmodels.CreatePostViewModel

@Composable
fun CreateUpdatePost(
    viewModel: CreatePostViewModel = viewModel()
) {

    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    Column {
        Toolbar("Create Post")
        LazyColumn {
            item {
                Column(Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { viewModel.onTitleChange(it) },
                        placeholder = { Text("Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                    )
                    Spacer(Modifier.height(20.dp))
                    OutlinedTextField(
                        value = content,
                        onValueChange = { viewModel.onContentChange(it) },
                        placeholder = { Text("Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface),
                        minLines = 10, maxLines = 25,

                        )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "Create",
                        modifier = Modifier
                            .clickable {
                            }
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(10))
                            .padding(16.dp)
                            .clip(RoundedCornerShape(25)),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }
    }
}