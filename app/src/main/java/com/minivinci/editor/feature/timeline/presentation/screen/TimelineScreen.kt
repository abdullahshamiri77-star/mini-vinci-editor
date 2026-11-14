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
    val currentPositionUs by viewModel.currentPositionUs.collectAsState()

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            viewModel.addRealClip(selectedUri.toString(), "v1")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Mini-DaVinci Studio",
                    color = Color.White,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "FPS: ${projectState.fps} | Multi-Track Engine",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { videoPickerLauncher.launch("video/*") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("استيراد", color = Color.White, fontSize = 12.sp)
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424242)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text("قص (Blade)", color = Color.White, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "شاشة المعاينة التفاعلية (Preview)",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Playhead Time: ${currentPositionUs / 1_000_000}s",
                    color = Color(0xFFFFC107),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Timeline – المسارات (V4 إلى V1 و A1)",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(projectState.tracks) { track ->
                TrackRowItem(track = track)
            }
        }
    }
}

@Composable
fun TrackRowItem(track: TimelineTrack) {
    val scrollState = rememberScrollState()
    val isVideo = track.trackType == TrackType.VIDEO
    val trackName = track.trackId.uppercase()
    
    val defaultHint = when(track.trackId) {
        "v4" -> "ارفع تأثيرات بصرية هنا"
        "v3" -> "ارفع النصوص والعناوين هنا"
        "v2" -> "ارفع مقاطع إضافية هنا"
        "v1" -> "المقطع الرئيسي (اسحب الفيديو هنا)"
        else -> "المسار الصوتي الأساسي والمؤثرات"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF1E1E1E)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(45.dp)
                .fillMaxHeight()
                .background(if (isVideo) Color(0xFF2C2C2C) else Color(0xFF1B5E20)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = trackName,
                color = Color.White,
                fontSize = 12.sp,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF121212))
                .horizontalScroll(scrollState)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (track.clips.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = defaultHint,
                        color = Color.DarkGray,
                        fontSize = 11.sp
                    )
                }
            } else {
                track.clips.forEach { clip ->
                    Surface(
                        modifier = Modifier
                            .width(130.dp)
                            .fillMaxHeight(0.85f),
                        color = if (isVideo) Color(0xFF1976D2) else Color(0xFF2E7D32),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Box(
                            modifier = Modifier.padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "مقطع: ${clip.clipId.takeLast(6)}",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
