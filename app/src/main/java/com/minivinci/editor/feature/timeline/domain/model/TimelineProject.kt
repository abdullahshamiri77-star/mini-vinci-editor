package com.minivinci.editor.feature.timeline.domain.model

/**
 * النموذج الموحد لمشروع التايملاين المتقدم
 */
data class TimelineProject(
    val projectId: String,
    val projectName: String,
    val fps: Int = 30,
    val tracks: List<TimelineTrack> = emptyList()
)

data class TimelineTrack(
    val trackId: String,
    val trackType: TrackType,
    val clips: List<TimelineClip> = emptyList(),
    val isMuted: Boolean = false,
    val isLocked: Boolean = false
)

enum class TrackType {
    VIDEO,
    AUDIO
}

data class TimelineClip(
    val clipId: String,
    val mediaPath: String,
    val startTimeUs: Long,
    val durationUs: Long,
    val inPointUs: Long = 0,
    val outPointUs: Long,
    val speed: Float = 1.0f
)
