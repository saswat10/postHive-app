package com.saswat10.posthive.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saswat10.network.models.domain.Comment

@Composable
fun Comments(comment: Comment) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountCircle,
            contentDescription = null,
            Modifier.padding(start = 4.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Column(
            Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(end=8.dp)
            ) {
                Text(
                    "@${comment.username}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(comment.createdAt, fontSize = 12.sp, color = MaterialTheme.colorScheme.tertiary)
            }
            Text(comment.content)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewComments() {
    Column {
//        Comments()
//        Comments()
//        Comments()
    }
}