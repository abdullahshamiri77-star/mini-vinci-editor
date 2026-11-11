package com.minivinci.editor.ui.screens

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

data class TimelineTrackData(
    val id: String,
    val name: String,
    val type: TrackType,
    val contentDescription: String,
    val trackColor: Color
)

enum class TrackType {
    VIDEO, AUDIO
}

@Composable
fun MainEditorScreen(
    onImportClick: () -> Unit,
    onSplitClick: () -> Unit,
    onExtractAudioClick: () -> Unit
) {
    val bgDark = Color(0xFF181818)
    val panelColor = Color(0xFF222222)
    val accentBlue = Color(0xFF0066CC)
    val textLight = Color(0xFFE0E0E0)

    val tracksList = remember {
        listOf(
            TimelineTrackData("v4", "V4 (Overlays / FX)", TrackType.VIDEO, "ارفع تأثيرات بصرية هنا", Color(0xFF2C2C2C)),
            TimelineTrackData("v3", "V3 (Titles / Text)", TrackType.VIDEO, "ارفع النصوص والعناوين هنا", Color(0xFF2C2C2C)),
            TimelineTrackData("v2", "V2 (B-Roll / Cutaway)", TrackType.VIDEO, "ارفع مقاطع إضافية هنا", Color(0xFF2C2C2C)),
            TimelineTrackData("v1", "V1 (Main Footage)", TrackType.VIDEO, "المقطع الرئيسي (اسحب الفيديو هنا)", Color(0xFF333333)),
            TimelineTrackData("a1", "A1 (Main Audio)", TrackType.AUDIO, "المسار الصوتي الأساسي والمؤثرات", Color(0xFF1B3B2B))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(panelColor)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Mini-DaVinci Studio", color = textLight, fontSize = 16.sp, style = MaterialTheme.typography.titleMedium)
            
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(onClick = onImportClick, colors = ButtonDefaults.buttonColors(containerColor = accentBlue)) {
                    Text("استيراد", fontSize = 12.sp)
                }
                Button(onClick = onSplitClick, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                    Text("قص (Blade)", fontSize = 12.sp)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("شاشة المعاينة التفاعلية (Preview)", color = Color.Gray, fontSize = 14.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(panelColor)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = onExtractAudioClick) {
                Text("🎵 فصل الصوت", color = textLight, fontSize = 12.sp)
            }
            TextButton(onClick = { }) {
                Text("✨ مؤثرات بصرية", color = textLight, fontSize = 12.sp)
            }
            TextButton(onClick = { }) {
                Text("✂️ تفكيك المشاهد", color = textLight, fontSize = 12.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
                .background(panelColor)
                .padding(4.dp)
        ) {
            Text("Timeline - المسارات (V1 إلى V4)", color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 4.dp))
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(tracksList, key = { it.id }) { track ->
                    UniqueTrackRow(track = track) {
                        onImportClick()
                    }
                }
            }
        }
    }
}

@Composable
fun UniqueTrackRow(track: TimelineTrackData, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(track.trackColor)
            .border(0.5.dp, Color.DarkGray)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(70.dp)
                .fillMaxHeight()
                .background(Color(0xFF151515)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = track.id.uppercase(),
                color = Color.White,
                fontSize = 12.sp,
                style = MaterialTheme.typography.labelMedium
            )
        }
        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = track.contentDescription,
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}
