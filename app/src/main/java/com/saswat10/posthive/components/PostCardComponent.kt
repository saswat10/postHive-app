package com.saswat10.posthive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saswat10.network.models.domain.Post
import com.saswat10.posthive.ui.theme.AlucardYellow
import com.saswat10.posthive.ui.theme.DraculaPink

@Composable
fun PostCardComponent(
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    post: Post
) {
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HorizontalDivider(Modifier.height(2.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = null,
                Modifier
                    .padding(start = 4.dp)
                    .size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "@${post.owner}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,

                    )
                Text(
                    post.createdAt,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontStyle = FontStyle.Italic
                )
            }
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = null,
                Modifier
                    .padding(end = 4.dp)
                    .size(30.dp),
                tint = DraculaPink
            )
            if (onEdit != null) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    Modifier
                        .padding(end = 4.dp)
                        .size(30.dp)
                        .clickable{onEdit()},
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
            if (onDelete != null) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = null,
                    Modifier
                        .padding(end = 4.dp)
                        .size(30.dp)
                        .clickable { onDelete() },
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(post.title, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(
                post.content,
                fontSize = 18.sp
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("${post.votes} have upvoted", color = AlucardYellow, fontWeight = FontWeight.Bold)
            Text("${post.comments} Comments", color = AlucardYellow, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider(Modifier.height(2.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewPost() {
    Column {
//        PostCardComponent(onEdit = {}, onDelete = {})
//        PostCardComponent()
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            OutlinedTextField(
//                "Add your comment",
//                onValueChange = {},
//                shape = RoundedCornerShape(25),
//                modifier = Modifier.weight(1f)
//            )
//            Icon(
//                imageVector = Icons.AutoMirrored.Filled.Send,
//                null,
//                tint = MaterialTheme.colorScheme.onPrimary,
//                modifier = Modifier
//                    .background(
//                        MaterialTheme.colorScheme.primary,
//                        RoundedCornerShape(25)
//                    )
//                    .padding(12.dp)
//            )
//        }

//        Comments()
//        Comments()
    }
}