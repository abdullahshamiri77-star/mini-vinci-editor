package com.minivinci.editor.feature.timeline.domain.model

data class TimelineTrack(
    val trackId: String,
    val trackType: TrackType,
    val clips: List<TimelineClip>,
    val isMuted: Boolean = false
)

enum class TrackType {
    VIDEO, AUDIO
}

data class TimelineClip(
    val clipId: String,
    val mediaPath: String,
    val startTimeUs: Long, // نقطة البداية على التايملاين
    val durationUs: Long,  // المدة الزمنية
    val inPointUs: Long = 0, // نقطة القطع الداخلية من المقطع الأصلي
    val outPointUs: Long
)

data class AdvancedTimelineProject(
    val projectId: String,
    val projectName: String,
    val fps: Int = 30,
    val tracks: List<TimelineTrack> = emptyList()
)
