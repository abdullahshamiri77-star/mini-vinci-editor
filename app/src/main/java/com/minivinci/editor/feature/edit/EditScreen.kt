package com.minivinci.editor.feature.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun EditScreen(
    viewModel: EditViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val tracks by viewModel.tracks.collectAsState()
    val selectedTrackId by viewModel.selectedTrackId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VisionStudio - Edit Page (Multi-Track)", color = Color.White) },
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
            // شاشة معاينة الفيديو الرئيسية (Canvas / Viewer)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(Color.Black)
                    .border(1.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Multi-Track Timeline Preview",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // قسم عرض وإدارة المسارات (Tracks Panel)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF1E1E1E))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Timeline Tracks (A/V Separation)",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(tracks) { track ->
                        val isSelected = track.trackId == selectedTrackId
                        val borderColor = if (isSelected) Color(0xFF4CA64C) else Color.DarkGray

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (track.trackType == TrackType.VIDEO) Color(0xFF2E6DA4) else Color(0xFFB8860B)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .border(2.dp, borderColor, CardDefaults.shape)
                                .clickable {
                                    viewModel.selectTrack(track.trackId)
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = track.name,
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Type: ${track.trackType}",
                                        color = Color.LightGray,
                                        fontSize = 10.sp
                                    )
                                }

                                // زر كتم الصوت / تفعيل المسار (Mute Toggle)
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (track.isMuted) Color.Red else Color.DarkGray
                                    ),
                                    onClick = {
                                        viewModel.toggleTrackMute(track.trackId)
                                    },
                                    modifier.height(36.dp)
                                ) {
                                    Text(
                                        text = if (track.isMuted) "Muted" else "Active",
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // أدوات التحكم السريعة في التعديل
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = {
                        // إجراء إضافي للمسارات مستقبلاً
                    }
                ) {
                    Text("Split Track")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA64C)),
                    onClick = {
                        // إضافة مسار جديد كمثال حي للتفاعل
                    }
                ) {
                    Text("Add Track (+)")
                }
            }
        }
    }
}

