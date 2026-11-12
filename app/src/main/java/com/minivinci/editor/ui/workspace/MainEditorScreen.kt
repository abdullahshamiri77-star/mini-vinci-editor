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
    // حالة المقطع باستخدام نموذج Clip الجديد
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
            .background(Color(0xFF1E1E1E))
    ) {
        // 1. شاشة المعاينة العليا
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "VisionStudio — Preview Pipeline",
                color = Color.White
            )
        }

        // 2. شريط الأدوات والتحكم بالقطع (Blade / Split)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2D2D2D))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    selectedClip?.let { clip ->
                        val splitPoint = 50L
                        val result = ClipSplitter.splitClip(clip, splitPoint)
                        if (result != null) {
                            selectedClip = result.leftClip
                            projectViewModel.createNewProject("مشروع مقصوص - ${System.currentTimeMillis()}")
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
                    text = "Multitrack Timeline",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (selectedClip != null) "Active Clip: ${selectedClip!!.id} (Frames: ${selectedClip!!.durationFrames})" else "No Clip Selected",
                    color = Color(0xFF88CC88),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
