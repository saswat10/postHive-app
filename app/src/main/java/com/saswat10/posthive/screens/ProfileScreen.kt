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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saswat10.posthive.components.PostListComponent
import com.saswat10.posthive.components.Toolbar

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    Column {
        Toolbar("Profile")
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
                    Text("Username: @Username")
                    Text("Email: some@email.com")
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Logout",
                        modifier = Modifier
                            .clickable {
                            }
                            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(10))
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
            repeat(5){
                item{
//                    PostListComponent()
                }
            }
        }
    }
}