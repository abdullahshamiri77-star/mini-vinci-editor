package com.minivinci.editor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MediaImportView(
    onDismiss: () -> Unit,
    onMediaSelected: (String) -> Unit
) {
    val bgPanel = Color(0xFF1E1E1E)
    val cardColor = Color(0xFF2D2D2D)
    val textLight = Color(0xFFE0E0E0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .background(bgPanel)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("استيراد فيديو أو صورة", color = textLight, fontSize = 16.sp, style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = onDismiss) {
                    Text("إغلاق", color = Color.Red, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(12) { index ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(cardColor)
                            .clickable {
                                onMediaSelected("video_item_${index + 1}.mp4")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "مقطع ${index + 1}",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
