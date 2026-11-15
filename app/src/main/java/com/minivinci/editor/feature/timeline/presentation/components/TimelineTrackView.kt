package com.minivinci.editor.feature.timeline.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip

@Composable
fun TimelineTrackRow(
    trackId: String,
    clips: List<TimelineClip>,
    onClipClick: (TimelineClip) -> Unit,
    onScrub: (Float) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1E1E1E))
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    offsetX += dragAmount
                    onScrub(offsetX)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- تسمية المسار (مثل V1, V2, A1) ---
        Box(
            modifier = Modifier
                .width(45.dp)
                .fillMaxHeight()
                .background(Color(0xFF2C2C2C)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = trackId.uppercase(),
                color = Color.Yellow,
                fontSize = 12.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        // --- مساحة المقاطع المرئية داخل المسار ---
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF141414)),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(clips) { clip ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .background(Color(0xFF1976D2))
                        .padding(4.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "مقطع فيديو",
                        color = Color.White,
                        fontSize = 10.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
