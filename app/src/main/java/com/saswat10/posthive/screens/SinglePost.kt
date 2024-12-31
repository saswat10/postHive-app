package com.saswat10.posthive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saswat10.posthive.components.Comments
import com.saswat10.posthive.components.PostCardComponent
import com.saswat10.posthive.components.Toolbar

@Composable
fun SinglePost() {
    Column {
        Toolbar("", onBackAction = {})
        LazyColumn{
            item{PostCardComponent(false)}
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        "Add your comment",
                        onValueChange = {},
                        shape = RoundedCornerShape(25),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(25)).padding(12.dp)
                    )
                }
            }
            repeat(11){
                item{ Comments()}
            }
        }
    }
}