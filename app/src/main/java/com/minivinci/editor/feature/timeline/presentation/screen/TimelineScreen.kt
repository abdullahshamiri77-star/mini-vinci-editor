package com.minivinci.editor.feature.timeline.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineTrack
import com.minivinci.editor.feature.timeline.domain.model.TrackType
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel

@Composable
fun TimelineScreen(viewModel: TimelineViewModel) {
    val project by viewModel.projectState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // داكن على طراز DaVinci
            .padding(16.dp)
    ) {
        // شريط العنوان العلوي للمشروع
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = project.projectName,
                    color = Color.White,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "FPS: ${project.fps} | Advanced Multi-Track Timeline",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            // زر تجريبي لإضافة مقطع فيديو للمسار الأول
            Button(
                onClick = {
                    val firstVideoTrack = project.tracks.firstOrNull { it.trackType == TrackType.VIDEO }
                    firstVideoTrack?.let {
                        viewModel.addClip(it.trackId, "sample_media_${System.currentTimeMillis()}.mp4", 5000000L) // 5 ثوانٍ
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5A93B)) // لون ذهبي تيمي
            ) {
                Text(text = "+ إضافة مقطع", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // منطقة عرض المسارات (Tracks & Clips Area)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFF333333), shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(project.tracks) { track ->
                    TrackRowItem(track = track, onClipClick = { clip ->
                        // تجربة التقسيم عند الضغط على المقطع منتصف مدته
                        viewModel.splitClip(track.trackId, clip.clipId, clip.startTimeUs + (clip.durationUs / 2))
                    })
                }
            }
        }
    }
}

@Composable
fun TrackRowItem(track: TimelineTrack, onClipClick: (TimelineClip) -> Unit) {
    val trackColor = if (track.trackType == TrackType.VIDEO) Color(0xFF2B5B84) else Color(0xFF3B8452)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(0xFF262626), shape = RoundedCornerShape(6.dp))
            .padding(8.dp)
    ) {
        // رأس المسار (اسم المسار ونوعه)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${track.trackType.name} Track (${track.trackId})",
                color = Color.LightGray,
                fontSize = 12.sp
            )
            if (track.isMuted) {
                Text(text = "Muted", color = Color.Red, fontSize = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // شريط المقاطع داخل المسار
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(4.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedSpacingCompatOrCustom() // استخدام التباعد البسيط
            ) {
                track.clips.forEach { clip ->
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(120.dp) // عرض تمثيلي للمقطع
                            .background(trackColor, shape = RoundedCornerShape(4.dp))
                            .border(1.dp, Color.White.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                            .clickable { onClipClick(clip) }
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                       Text(
                           text = "Clip: ${clip.clipId.take(4)}",
                           color = Color.White,
                           fontSize = 10.sp
                       )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

// دالة مساعدة سريعة للتباعد
@Composable
fun androidx.compose.foundation.layout.Arrangement.Horizontal.spacedSpacingCompatOrCustom() = Arrangement.spacedBy(4.dp)
