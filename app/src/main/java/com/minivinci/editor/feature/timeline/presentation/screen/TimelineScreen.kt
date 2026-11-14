package com.minivinci.editor.feature.timeline.presentation.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.model.TimelineTrack
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TrackType
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel

@Composable
fun TimelineScreen(viewModel: TimelineViewModel) {
    val projectState by viewModel.projectState.collectAsState()

    // منتقي الملفات الفعلي لاستيراد فيديوهات الهاتف (.mp4)
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            // تمرير مسار الفيديو الحقيقي للمحرك فوراً باستخدام addRealClip الصحيحة
            viewModel.addRealClip(selectedUri.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        // --- شريط العنوان وزر الاستيراد الحقيقي ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = projectState.projectName,
                    color = Color.White,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "FPS: ${projectState.fps} | Real Multi-Track Timeline",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            // زر الاستيراد الفعلي المرتبط بذاكرة الهاتف
            Button(
                onClick = { videoPickerLauncher.launch("video/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text("استيراد فيديو +", color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- شاشة المعاينة (Preview Window) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "شاشة المعاينة التفاعلية (Preview)",
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- مسارات التايملاين الحقيقية (Tracks & Clips) ---
        Text(
            text = "Timeline Tracks",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(projectState.tracks) { track ->
                TrackRowView(track = track)
            }
        }
    }
}

@Composable
fun TrackRowView(track: TimelineTrack) {
    val scrollState = rememberScrollState()
    val trackLabel = if (track.trackType == TrackType.VIDEO) "VIDEO Track (${track.trackId})" else "AUDIO Track (${track.trackId})"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1E1E1E))
            .padding(8.dp)
    ) {
        Text(
            text = trackLabel,
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF2C2C2C))
                .horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (track.clips.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "المسار فارغ (اضغط استيراد فيديو لإضافة مقطع)", color = Color.DarkGray, fontSize = 11.sp)
                }
            } else {
                track.clips.forEach { clip ->
                    Surface(
                        modifier = Modifier
                            .width(120.dp)
                            .fillMaxHeight(),
                        color = Color(0xFF1976D2),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "مقطع: ${clip.clipId.takeLast(6)}",
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
