package com.minivinci.editor.core.timeline

object ClipSplitter {
    /**
     * يقوم بتفكيك وشطر مقطع معين إلى مقطعين مستقلين عند نقطة زمنية محددة (Split Time)
     */
    fun splitClip(clip: TimelineClip, splitTime: Timecode): Pair<TimelineClip, TimelineClip>? {
        if (splitTime.frame <= clip.startTime.frame || 
            splitTime.frame >= (clip.startTime.frame + clip.duration.frame)) {
            return null // نقطة القص خارج حدود المقطع
        }

        val firstDurationFrames = splitTime.frame - clip.startTime.frame
        val secondDurationFrames = clip.duration.frame - firstDurationFrames

        val clipA = clip.copy(
            duration = Timecode(firstDurationFrames, clip.startTime.fps)
        )

        val clipB = TimelineClip(
            id = "${clip.id}_split_${System.currentTimeMillis()}",
            sourcePath = clip.sourcePath,
            startTime = splitTime,
            duration = Timecode(secondDurationFrames, clip.startTime.fps),
            trackIndex = clip.trackIndex
        )

        return Pair(clipA, clipB)
    }
}
