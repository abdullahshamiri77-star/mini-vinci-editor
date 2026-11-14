package com.minivinci.editor.feature.timeline.domain.command

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

class SplitClipCommand(
    private val trackId: String,
    private val clipId: String,
    private val splitTimeUs: Long
) : TimelineCommand {

    private var originalClip: TimelineClip? = null

    override fun execute(project: TimelineProject): TimelineProject {
        var modified = false
        val updatedTracks = project.tracks.map { track ->
            if (track.trackId == trackId && !track.isLocked) {
                val newClips = mutableListOf<TimelineClip>()
                for (clip in track.clips) {
                    if (clip.clipId == clipId) {
                        val clipStart = clip.startTimeUs
                        val clipEnd = clip.startTimeUs + clip.durationUs

                        // التحقق من أن نقطة القص تقع داخل حدود المقطع حصراً
                        if (splitTimeUs > clipStart && splitTimeUs < clipEnd) {
                            originalClip = clip // حفظ المقطع الأصلي لغرض الـ Undo

                            val leftDuration = splitTimeUs - clipStart
                            val rightDuration = clipEnd - splitTimeUs

                            // الجزأ الأيمن والأيسر بعد القص
                            val leftClip = clip.copy(
                                clipId = "${clip.clipId}_L",
                                durationUs = leftDuration,
                                outPointUs = clip.inPointUs + leftDuration
                            )

                            val rightClip = clip.copy(
                                clipId = "${clip.clipId}_R",
                                startTimeUs = splitTimeUs,
                                durationUs = rightDuration,
                                inPointUs = clip.inPointUs + leftDuration
                            )

                            newClips.add(leftClip)
                            newClips.add(rightClip)
                            modified = true
                        } else {
                            newClips.add(clip)
                        }
                    } else {
                        newClips.add(clip)
                    }
                }
                track.copy(clips = newClips)
            } else {
                track
            }
        }
        return if (modified) project.copy(tracks = updatedTracks) else project
    }

    override fun undo(project: TimelineProject): TimelineProject {
        val targetClip = originalClip ?: return project
        val updatedTracks = project.tracks.map { track ->
            if (track.trackId == trackId) {
                // استبدال الجزأين المقصوصين وإعادة المقطع الأصلي مكانهما وترتيب المسار زمنيّاً
                val filteredClips = track.clips.filterNot { 
                    it.startTimeUs >= targetClip.startTimeUs && 
                    it.startTimeUs < (targetClip.startTimeUs + targetClip.durationUs) 
                }
                track.copy(clips = (filteredClips + targetClip).sortedBy { it.startTimeUs })
            } else {
                track
            }
        }
        return project.copy(tracks = updatedTracks)
    }
}
