package com.minivinci.editor.feature.timeline.domain.model

data class TimelineClip(
    val id: String,
    val mediaUri: String,
    val clipType: TimelineClipType,
    val startTimeMs: Long,
    val durationMs: Long,
    val inPointMs: Long = 0L,
    val outPointMs: Long,
    val volume: Float = 1.0f
)

enum class TimelineClipType {
    VIDEO,
    AUDIO,
    IMAGE
}
