package com.minivinci.editor.feature.timeline.domain.model

/**
 * يمثل معدل الإطارات (Frame Rate) بصيغة كسرية (Rational) لتجنب أخطاء التقريب
 */
data class FrameRate(
    val numerator: Int,
    val denominator: Int
) {
    val fps: Double
        get() = numerator.toDouble() / denominator.toDouble()

    companion object {
        val FPS_24 = FrameRate(24, 1)
        val FPS_25 = FrameRate(25, 1)
        val FPS_30 = FrameRate(30, 1)
        val FPS_60 = FrameRate(60, 1)
        val FPS_23_976 = FrameRate(24000, 1001)
        val FPS_29_97 = FrameRate(30000, 1001)
        val FPS_59_94 = FrameRate(60000, 1001)
    }
}

data class TimelineProject(
    val projectId: String,
    val projectName: String,
    val fps: Int = 30,
    val frameRate: FrameRate = FrameRate.FPS_30,
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
