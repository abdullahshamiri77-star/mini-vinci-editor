package com.minivinci.editor.core.timeline

data class Timecode(val frame: Long, val fps: Double = 30.0) {
    fun toMillis(): Long = (frame * 1000 / fps).toLong()
}

data class TimelineClip(
    val id: String,
    val sourcePath: String,
    val startTime: Timecode,
    val duration: Timecode,
    val trackIndex: Int // V1, V2, A1, etc.
)

data class TimelineTrack(
    val id: String,
    val name: String,
    val type: TrackType,
    val clips: MutableList<TimelineClip> = mutableListOf()
)

enum class TrackType {
    VIDEO, AUDIO, TEXT, EFFECTS
}
