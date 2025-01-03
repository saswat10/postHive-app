package com.saswat10.posthive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.saswat10.network.KtorClient
import com.saswat10.posthive.viewmodels.RegisterViewModel


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavHostController,
    onButtonClicked: () -> Unit
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            "Registration",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(20.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.AccountBox,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            placeholder = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(25)),
        )
        OutlinedTextField(
            value = email, onValueChange = { viewModel.onEmailChange(it) }, shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.Email,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            placeholder = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(25)),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.Lock,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            placeholder = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(25)),
        )

        Spacer(Modifier.height(2.dp))

        Text(
            "REGISTER",
            modifier = Modifier
                .clickable { viewModel.register()
                }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(25))
                .padding(16.dp)
                .clip(RoundedCornerShape(25)),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an Account? ", fontSize = 18.sp)
            Text(
                "Sign In",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onButtonClicked() }
            )
        }
    }
}

