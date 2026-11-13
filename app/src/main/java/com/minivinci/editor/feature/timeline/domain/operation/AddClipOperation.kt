package com.minivinci.editor.feature.timeline.domain.operation

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.model.TimelineTrack

class AddClipOperation {
    fun execute(project: TimelineProject, targetTrackId: String, clip: TimelineClip): TimelineProject {
        val updatedTracks = project.tracks.map { track ->
            if (track.id == targetTrackId) {
                track.copy(clips = track.clips + clip)
            } else {
                track
            }
        }
        return project.copy(tracks = updatedTracks)
    }
}
