package com.minivinci.editor.feature.timeline.domain.model

data class TimelineTrack(
    val id: String,
    val trackIndex: Int,
    val trackType: TimelineClipType,
    val clips: List<TimelineClip> = emptyList(),
    val isMuted: Boolean = false,
    val isLocked: Boolean = false
)
