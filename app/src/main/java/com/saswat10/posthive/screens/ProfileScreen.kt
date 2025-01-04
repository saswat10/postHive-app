package com.saswat10.posthive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.saswat10.posthive.components.LoadingIndicator
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar
import com.saswat10.posthive.components.UserPostListComponent
import com.saswat10.posthive.viewmodels.ProfileViewModel
import com.saswat10.posthive.viewmodels.ProfileViewState

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {


    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit, block = {
        profileViewModel.getMyself()
    })


    Column {
        Toolbar("Profile")


        when (val state = uiState) {
            is ProfileViewState.Loading -> LoadingIndicator()
            is ProfileViewState.Success -> {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(imageVector = Icons.Rounded.AccountBox, null, Modifier.size(120.dp))
                        Column {
                            Text("Username: ${state.data.name}")
                            Text("Email: ${state.data.email}")
                            Text("Joined: ${state.data.joinedOn}")
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Logout",
                                modifier = Modifier
                                    .clickable {
                                        profileViewModel.logout()
                                        navController.navigate("auth") {
                                            popUpTo("profile") { inclusive = true }
                                        }
                                    }
                                    .background(
                                        MaterialTheme.colorScheme.secondary,
                                        RoundedCornerShape(10)
                                    )
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(10)),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                    HorizontalDivider()
                    Text(
                        "Your Posts",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    HorizontalDivider()
                }
                LazyColumn {
                    state.data.posts.forEachIndexed { index, post ->
                        item {
                            UserPostListComponent(
                                post,
                                function = { navController.navigate("post_detail/${post.id}") })

                        }
                    }
                }
            }

            is ProfileViewState.Error -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
            }

        }


    }
}