package com.saswat10.posthive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Toolbar(
    title: String,
    onBackAction: (() -> Unit)? = null
) {
    Column() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBackAction != null) {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
//                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onBackAction() },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back Action"
                    )
                }
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.primary)

    }
}


@Preview(showBackground = true)
@Composable
fun ToolbarPreview(){
    Toolbar("All Posts", onBackAction = {})
}