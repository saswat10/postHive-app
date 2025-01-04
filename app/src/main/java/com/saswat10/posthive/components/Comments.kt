package com.saswat10.posthive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.HorizontalDivider
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
fun Comments(comment: Comment, onEdit: (() -> Unit)? = null,
             onDelete: (() -> Unit)? = null,) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            ,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.AccountBox,
            contentDescription = null,
            Modifier.padding(start = 4.dp),
            tint = MaterialTheme.colorScheme.secondary
        )
        Column(
            Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5))
                .border(1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(5))
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    "@${comment.username}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    comment.createdAt,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.primary)
            Text(comment.content, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp), style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(2.dp))
            Row(horizontalArrangement = Arrangement.End.also { Arrangement.spacedBy(8.dp) }, modifier = Modifier.fillMaxWidth().padding(end = 8.dp)) {
                if (onEdit != null) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        Modifier
                            .clickable { onEdit() }
                            .size(20.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
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
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewComments() {
    Column {
        Comments(comment = Comment(1, "ecernwaka", "dkw", "today"), {}, {})
        Comments(comment = Comment(1, "ecernwaka", "dkw", "today"), {}, {})
        Comments(comment = Comment(1, "ecernwaka", "dkw", "today"), {}, {})
        Comments(comment = Comment(1, "ecernwaka", "dkw", "today"), {}, {})
//        Comments()
//        Comments()
    }
}