package com.minivinci.editor.feature.timeline.domain.engine

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.model.TimelineTrack
import com.minivinci.editor.feature.timeline.domain.model.TrackType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AdvancedTimelineEngine {

    private val _projectState = MutableStateFlow(
        TimelineProject(
            projectId = "project_1",
            projectName = "VisionStudio Cinematic Project",
            fps = 30,
            tracks = listOf(
                TimelineTrack(trackId = "track_1", trackType = TrackType.VIDEO),
                TimelineTrack(trackId = "track_2", trackType = TrackType.AUDIO)
            )
        )
    )
    val projectState: StateFlow<TimelineProject> = _projectState.asStateFlow()

    /**
     * إضافة مقطع جديد إلى مسار محدد
     */
    fun addClipToTrack(trackId: String, mediaPath: String, durationUs: Long) {
        _projectState.update { currentProject ->
            val updatedTracks = currentProject.tracks.map { track ->
                if (track.trackId == trackId) {
                    val newClips = mutableListOf<TimelineClip>()
                    newClips.addAll(track.clips)
                    
                    val newClip = TimelineClip(
                        clipId = "clip_${System.currentTimeMillis()}",
                        mediaPath = mediaPath,
                        startTimeUs = if (newClips.isEmpty()) 0L else newClips.maxOf { it.startTimeUs + it.durationUs },
                        durationUs = durationUs,
                        outPointUs = durationUs
                    )
                    newClips.add(newClip)
                    track.copy(clips = newClips)
                } else {
                    track
                }
            }
            currentProject.copy(tracks = updatedTracks)
        }
    }

    /**
     * تقسيم مقطع عند نقطة زمنية محددة (Razor / Split)
     */
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        _projectState.update { currentProject ->
            val updatedTracks = currentProject.tracks.map { track ->
                if (track.trackId == trackId) {
                    val newClips = mutableListOf<TimelineClip>()
                    track.clips.forEach { clip ->
                        if (clip.clipId == clipId && splitTimeUs > clip.startTimeUs && splitTimeUs < (clip.startTimeUs + clip.durationUs)) {
                            val firstPartDuration = splitTimeUs - clip.startTimeUs
                            val secondPartDuration = clip.durationUs - firstPartDuration

                            val clip1 = clip.copy(durationUs = firstPartDuration, outPointUs = firstPartDuration)
                            val clip2 = clip.copy(
                                clipId = "clip_${System.currentTimeMillis()}",
                                startTimeUs = splitTimeUs,
                                durationUs = secondPartDuration,
                                outPointUs = secondPartDuration
                            )
                            newClips.add(clip1)
                            newClips.add(clip2)
                        } else {
                            newClips.add(clip)
                        }
                    }
                    track.copy(clips = newClips)
                } else {
                    track
                }
            }
            currentProject.copy(tracks = updatedTracks)
        }
    }
}
