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
import com.minivinci.editor.core.timeline.ClipSplitter
import com.minivinci.editor.core.timeline.TimelineClip
import com.minivinci.editor.core.timeline.Timecode

@Composable
fun MainEditorScreen(
    videoEngine: VideoEngine,
    audioEngine: AudioEngine,
    projectViewModel: ProjectViewModel = viewModel() // حقن الـ ViewModel لربط قاعدة البيانات
) {
    // حالة التايم لاين والمقاطع الحالية
    var selectedClip by remember { mutableStateOf<TimelineClip?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)) // خلفية داكنة تناسب البرامج الاحترافية مثل DaVinci
    ) {
        // 1. شاشة المعاينة العليا (Preview Window)
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

        // 2. شريط الأدوات والتحكم بالقطع (Toolbar / Blade Action)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2D2D2D))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                // تفعيل خوارزمية القص وتفكيك المقطع وحفظ الحالة في قاعدة البيانات
                selectedClip?.let { clip ->
                    val splitPoint = Timecode(frame = 50) // مثال لنقطة القص
                    val result = ClipSplitter.splitClip(clip, splitPoint)
                    if (result != null) {
                        // تم القص بنجاح وحفظ المشروع في قاعدة البيانات Room لتجربة الربط
                        projectViewModel.createNewProject("مشروع مقصوص - ${System.currentTimeMillis()}")
                    }
                }
            }) {
                Text("Blade / Split (قص)")
            }
        }

        // 3. مساحة التايم لاين متعدد المسارات (Multitrack Timeline)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .background(Color(0xFF252525)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Multitrack Timeline (V1, V2, A1)",
                color = Color.LightGray
            )
        }
    }
}
