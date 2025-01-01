package com.saswat10.posthive

import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.saswat10.network.KtorClient
import com.saswat10.network.models.remote.Registration
import com.saswat10.network.models.remote.RegistrationResponse
import com.saswat10.network.writeSomething
import com.saswat10.posthive.screens.CreateUpdatePost
import com.saswat10.posthive.screens.DiscoverScreen
import com.saswat10.posthive.screens.LoginScreen
import com.saswat10.posthive.screens.ProfileScreen
import com.saswat10.posthive.screens.RegisterScreen
import com.saswat10.posthive.screens.SearchScreen
import com.saswat10.posthive.screens.SinglePost
import com.saswat10.posthive.ui.theme.PostHiveTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostHiveTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        DiscoverScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PostHiveTheme {
        Greeting("Android")
    }
}