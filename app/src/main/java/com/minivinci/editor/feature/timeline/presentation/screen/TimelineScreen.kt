package com.minivinci.editor.feature.timeline.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel

@Composable
fun TimelineScreen(viewModel: TimelineViewModel) {
    val project by viewModel.projectState.collectAsState()
    val playheadPos by viewModel.playheadPosition.collectAsState()

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(8.dp)
    ) {
        // شريط أدوات التايملاين (التحكم السريع)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2D2D2D))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🎞️ ${project.name}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Playhead: ${playheadPos}ms",
                color = Color.Cyan,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // منطقة عرض المسارات والزمن (Tracks & Clips Area)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScrollState)
                .horizontalScroll(horizontalScrollState)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (project.tracks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "لا توجد مسارات مضافة بعد. أضف مقطعك الأول لتبدأ الإبداع!",
                            color = Color.Gray
                        )
                    }
                } else {
                    project.tracks.forEach { track ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(Color(0xFF252525))
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Track ${track.trackIndex}",
                                color = Color.White,
                                modifier = Modifier.width(80.dp)
                            )
                            
                            // عرض المقاطع داخل المسار
                            Box(
                                modifier = Modifier
                                    .weight(1.0f)
                                    .fillMaxHeight()
                                    .background(Color(0xFF333333))
                            ) {
                                track.clips.forEach { clip ->
                                    // تمثيل مبدئي للمقطع داخل المسار
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(120.dp)
                                            .background(Color(0xFF0D47A1))
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = clip.id,
                                            color = Color.White,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
