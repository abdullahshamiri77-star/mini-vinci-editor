package com.minivinci.editor.feature.timeline.domain.model

data class TimelineProject(
    val id: String,
    val name: String,
    val fps: Int = 30,
    val tracks: List<TimelineTrack> = emptyList(),
    val durationMs: Long = 0L
)
