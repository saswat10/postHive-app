package com.saswat10.posthive.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saswat10.network.models.domain.Post
import com.saswat10.posthive.ui.theme.AlucardYellow

@Composable
fun UserPostListComponent(post: Post, function: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { function() }
            .padding(vertical = 2.dp)
            .background( MaterialTheme.colorScheme.surface, RoundedCornerShape(4.dp))
            .padding(18.dp)
    ) {

        // Post Content Section
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Title
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // Metadata
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${post.votes} votes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${post.comments} comments",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )

            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "submitted ${post.createdAt}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            // Optional Thumbnail
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewRedditPostCardWithImage() {
//    UserPostListComponent()
//}