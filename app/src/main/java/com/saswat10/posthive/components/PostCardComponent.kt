package com.saswat10.posthive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saswat10.network.models.domain.Post

@Composable
fun PostCardComponent(
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    post: Post,
    function:()->Unit,
    voteNumber:Int,
    hasVoted: Boolean,
    commentNumber: Int,
    showButton: Boolean = false
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountBox,
                contentDescription = null,
                Modifier
                    .padding(start = 4.dp)
                    .size(50.dp),
                tint = Color.LightGray
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "@${post.owner}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    post.createdAt,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .clickable { function() }
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)

            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = null,
                    Modifier
                        .rotate(-90F)
                        .size(25.dp)
                        ,
                    tint = if (hasVoted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground,
                )
            }

        }
        HorizontalDivider()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                post.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(20.dp))
            Text(
                post.content,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    "$voteNumber upvotes",
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    "$commentNumber comments",
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(end = 10.dp)
            ) {

                if (onEdit != null) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        Modifier
                            .clickable { onEdit() }
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }else{
                    Spacer(Modifier.height(1.dp))
                }
                if (onDelete != null) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        Modifier
                            .clickable { onDelete() }
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }else{
                    Spacer(Modifier.height(1.dp))
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewPost() {
    Column {
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