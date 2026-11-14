package com.minivinci.editor.feature.timeline.domain.operation

import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

class RemoveClipOperation {
    operator fun invoke(project: TimelineProject, clipId: String): TimelineProject {
        val updatedTracks = project.tracks.map { track ->
            val updatedClips = track.clips.filter { it.clipId != clipId }
            track.copy(clips = updatedClips)
        }
        return project.copy(tracks = updatedTracks)
    }
}
