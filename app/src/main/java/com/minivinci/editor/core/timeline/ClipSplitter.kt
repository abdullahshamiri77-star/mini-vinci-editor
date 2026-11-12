package com.minivinci.editor.core.timeline

object ClipSplitter {
    /**
     * يقوم بقسمة المقطع (Clip) عند إطار محدد لإنتاج مقطعين جديدين
     */
    data class SplitResult(val leftClip: Clip, val rightClip: Clip)

    fun splitClip(clip: Clip, splitFrame: Long): SplitResult? {
        // التحقق مما إذا كانت نقطة القص تقع داخل حدود المقطع
        if (splitFrame <= clip.startFrame || splitFrame >= clip.startFrame + clip.durationFrames) {
            return null
        }

        val firstDuration = splitFrame - clip.startFrame
        val secondDuration = (clip.startFrame + clip.durationFrames) - splitFrame

        val leftClip = clip.copy(
            durationFrames = firstDuration
        )

        val rightClip = clip.copy(
            id = "${clip.id}_split_${System.currentTimeMillis()}",
            startFrame = splitFrame,
            sourceInFrame = clip.sourceInFrame + firstDuration,
            durationFrames = secondDuration
        )

        return SplitResult(leftClip, rightClip)
    }
}
