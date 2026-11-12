package com.minivinci.editor.ui.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minivinci.editor.core.database.ProjectViewModel
import com.minivinci.editor.core.engine.AudioEngine
import com.minivinci.editor.core.engine.VideoEngine
import com.minivinci.editor.core.timeline.Clip
import com.minivinci.editor.core.timeline.ClipSplitter

@Composable
fun MainEditorScreen(
    videoEngine: VideoEngine,
    audioEngine: AudioEngine,
    projectViewModel: ProjectViewModel = viewModel()
) {
    // مراقبة حالة التشغيل والإطار الحالي من VideoEngine مباشرة
    val isPlaying by videoEngine.isPlaying.collectAsState()
    val currentFrame by videoEngine.currentFrame.collectAsState()

    var selectedClip by remember {
        mutableStateOf<Clip?>(
            Clip(
                id = "clip_01",
                mediaId = "media_01",
                startFrame = 0L,
                sourceInFrame = 0L,
                durationFrames = 150L,
                effects = emptyList()
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // DaVinci Dark Theme
    ) {
        // 1. شاشة المعاينة العليا (Preview Window)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isPlaying) "▶ Playing (Frame: $currentFrame)" else "⏸ Paused (Frame: $currentFrame)",
                    color = if (isPlaying) Color(0xFF88CC88) else Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "VisionStudio Live Preview Pipeline",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // 2. شريط أدوات التحكم والتشغيل (Playback Controls & Blade)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2D2D2D))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // زر التشغيل / الإيقاف المؤقت المباشر
            Button(
                onClick = {
                    if (isPlaying) {
                        videoEngine.pause()
                    } else {
                        videoEngine.play()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isPlaying) Color(0xFFCC3333) else Color(0xFF28A745)
                )
            ) {
                Text(if (isPlaying) "Pause (إيقاف)" else "Play (تشغيل)", color = Color.White)
            }

            // زر القص (Blade / Split)
            Button(
                onClick = {
                    selectedClip?.let { clip ->
                        val splitPoint = currentFrame.coerceIn(clip.startFrame + 1, clip.startFrame + clip.durationFrames - 1)
                        val result = ClipSplitter.splitClip(clip, splitPoint)
                        if (result != null) {
                            selectedClip = result.leftClip
                            projectViewModel.createNewProject("مشروع مقصوص عند الإطار $splitPoint")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007ACC))
            ) {
                Text("Blade / Split (قص)", color = Color.White)
            }
        }

        // 3. مساحة التايم لاين متعدد المسارات
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .background(Color(0xFF252525)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Multitrack Timeline Engine",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (selectedClip != null) "Active Clip: ${selectedClip!!.id} | Duration: ${selectedClip!!.durationFrames}f" else "No Clip Selected",
                    color = Color(0xFF88CC88),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
