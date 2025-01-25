package com.saswat10.posthive.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saswat10.network.models.domain.Post
import com.saswat10.posthive.R

@Composable
fun PostListComponent(
    post: Post,
    function: () -> Unit,
    toggle: () -> Unit,
    hasVoted: Boolean,
    votes: Int
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { function() },
        colors = CardDefaults.outlinedCardColors(),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                    Column {
                        Text(
                            text = post.owner,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = post.createdAt,
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.unbookmark),
                    contentDescription = null,
                    Modifier
                        .clip(RoundedCornerShape(50))
                        .clickable {}
                        .background(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                        .padding(6.dp)


                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Default.Favorite,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${post.votes} Likes",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,

                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    contentDescription = null,
                    painter = painterResource(R.drawable.comments),
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${post.comments} Comments",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewComponent() {
    Column {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row { }
                Text(text = "Title", style = MaterialTheme.typography.titleLarge)
                Row {
                    Text(text = "24 Likes", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "11 Comments", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row { }
                Text(text = "Title", style = MaterialTheme.typography.titleLarge)
                Row {
                    Text(text = "24 Likes", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "11 Comments", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                        Column {
                            Text(
                                text = "Username",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "2 days ago",
                                style = MaterialTheme.typography.bodySmall,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Title", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Write some content over here to show that it has to be big enough, I guess 20 words should be more than enough....",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(text = "24 Likes", style = MaterialTheme.typography.labelMedium)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "11 Comments", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
