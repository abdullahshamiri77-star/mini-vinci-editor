package com.minivinci.editor.feature.timeline.domain.engine

import com.minivinci.editor.feature.timeline.domain.model.AdvancedTimelineProject
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineTrack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class AdvancedTimelineEngine {

    private val _projectState = MutableStateFlow(
        AdvancedTimelineProject(
            projectId = UUID.randomUUID().toString(),
            projectName = "VisionStudio Cinematic Project",
            tracks = listOf(
                TimelineTrack(trackId = "track_v1", trackType = com.minivinci.editor.feature.timeline.domain.model.TrackType.VIDEO, clips = emptyList()),
                TimelineTrack(trackId = "track_a1", trackType = com.minivinci.editor.feature.timeline.domain.model.TrackType.AUDIO, clips = emptyList())
            )
        )
    )
    val projectState: StateFlow<AdvancedTimelineProject> = _projectState.asStateFlow()

    /**
     * إضافة مقطع جديد إلى مسار محدد
     */
    fun addClipToTrack(trackId: String, mediaPath: String, durationUs: Long) {
        val currentProject = _projectState.value
        val newClip = TimelineClip(
            clipId = UUID.randomUUID().toString(),
            mediaPath = mediaPath,
            startTimeUs = calculateNextStartTime(currentProject, trackId),
            durationUs = durationUs,
            outPointUs = durationUs
        )

        val updatedTracks = currentProject.tracks.map { track ->
            if (track.trackId == trackId) {
                track.copy(clips = track.clips + newClip)
            } else {
                track
            }
        }

        _projectState.value = currentProject.copy(tracks = updatedTracks)
    }

    /**
     * تقسيم مقطع معين عند زمن محدد (Split / Razor Tool)
     */
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        val currentProject = _projectState.value

        val updatedTracks = currentProject.tracks.map { track ->
            if (track.trackId == trackId) {
                val newClips = mutableListof<TimelineClip>()
                for (clip in track.clips) {
                    if (clip.clipId == clipId && splitTimeUs > clip.startTimeUs && splitTimeUs < (clip.startTimeUs + clip.durationUs)) {
                        val firstDuration = splitTimeUs - clip.startTimeUs
                        val secondDuration = clip.durationUs - firstDuration

                        // المقطع الأول بعد القص
                        val clipPart1 = clip.copy(
                            durationUs = firstDuration,
                            outPointUs = clip.inPointUs + firstDuration
                        )
                        // المقطع الثاني بعد القص
                        val clipPart2 = clip.copy(
                            clipId = UUID.randomUUID().toString(),
                            startTimeUs = splitTimeUs,
                            inPointUs = clip.inPointUs + firstDuration,
                            durationUs = secondDuration
                        )
                        newClips.add(clipPart1)
                        newClips.add(clipPart2)
                    } else {
                        newClips.add(clip)
                    }
                }
                track.copy(clips = newClips)
            } else {
                track
            }
        }

        _projectState.value = currentProject.copy(tracks = updatedTracks)
    }

    /**
     * حساب نقطة البداية للمقطع الجديد تلقائياً في نهاية المسار
     */
    private fun calculateNextStartTime(project: AdvancedTimelineProject, trackId: String): Long {
        val track = project.tracks.find { it.trackId == trackId } ?: return 0L
        return track.clips.maxOfOrNull { it.startTimeUs + it.durationUs } ?: 0L
    }
}
