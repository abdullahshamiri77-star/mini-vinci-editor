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
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel

@Composable
fun TimelineScreen(viewModel: TimelineViewModel) {
    val projectState by viewModel.projectState.collectAsState()

    // منتقي الملفات الحقيقي لاستيراد الفيديوهات من ذاكرة الهاتف
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
        // --- الشريط العلوي لبرنامج VisionStudio ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = projectState.projectName,
                    color = Color.White,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "FPS: ${projectState.fps} | DaVinci Engine Active",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }

            Button(
                onClick = { videoPickerLauncher.launch("video/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Text("استيراد فيديو +", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- شاشة المعاينة الاحترافية (Preview Window) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "شاشة المعاينة التفاعلية (جاهزة لربط ExoPlayer)",
                color = Color.LightGray,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- مسارات التايملاين الحقيقية (V4 إلى V1 و A1) ---
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(projectState.tracks) { track ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = track.trackId.uppercase(),
                            color = Color.Yellow,
                            modifier = Modifier.width(40.dp),
                            fontSize = 12.sp
                        )
                        Text(
                            text = if (track.clips.isEmpty()) "المسار فارغ (اضغط استيراد لإضافة مقطع)" else "عدد المقاطع: ${track.clips.size}",
                            color = Color.LightGray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
