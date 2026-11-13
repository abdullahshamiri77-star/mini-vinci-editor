package com.minivinci.editor.feature.timeline.domain.operation

import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

class RemoveClipOperation {
    fun execute(project: TimelineProject, clipId: String): TimelineProject {
        val updatedTracks = project.tracks.map { track ->
            val filteredClips = track.clips.filter { it.id != clipId }
            if (filteredClips.size != track.clips.size) {
                track.copy(clips = filteredClips)
            } else {
                track
            }
        }
        return project.copy(tracks = updatedTracks)
    }
}
