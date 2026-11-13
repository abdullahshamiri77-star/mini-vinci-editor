package com.minivinci.editor.feature.cut

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CutScreen(
    viewModel: CutViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val currentPosition by viewModel.currentPosition.collectAsState()
    val clips by viewModel.clips.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VisionStudio - Cut Page", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E1E1E))
            )
        },
        containerColor = Color(0xFF121212)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // شاشة معاينة الفيديو (Player Preview Window)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.Black)
                    .border(1.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Playhead Position: ${currentPosition}ms",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // شريط التايم لاين والمقاطع (Timeline & Clips Track)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Timeline Tracks",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (clips.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No clips added. Import media to start cutting.", color = Color.DarkGray)
                    }
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(clips) { clip ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF2E6DA4)),
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(120.dp)
                                    .clickable {
                                        // تنفيذ القطع عند النقر على المقطع كمثال تجريبي للمؤشر الحالي
                                        viewModel.performBladeCut(clip.id, currentPosition)
                                    }
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Clip: ${clip.id.take(6)}",
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // أدوات التحكم والقطع (Blade / Split Controls)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = {
                        // محاكاة تحريك المؤشر وتجربة القطع على اول مقطع إن وجد
                        if (clips.isNotEmpty()) {
                            val target = clips.first()
                            viewModel.performBladeCut(target.id, target.startTime + (target.duration / 2))
                        }
                    }
                ) {
                    Text("Blade / Split (✂️)")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA64C)),
                    onClick = {
                        // تقديم المؤشر للأمام للتجربة
                        viewModel.updatePosition(currentPosition + 1000L)
                    }
                ) {
                    Text("Forward 1s (+)")
                }
            }
        }
    }
}

