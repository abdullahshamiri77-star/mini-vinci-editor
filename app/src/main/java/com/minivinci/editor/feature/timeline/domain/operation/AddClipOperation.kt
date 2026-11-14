package com.minivinci.editor.feature.timeline.domain.operation

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

class AddClipOperation {
    operator fun invoke(project: TimelineProject, trackId: String, clip: TimelineClip): TimelineProject {
        val updatedTracks = project.tracks.map { track ->
            if (track.trackId == trackId) {
                track.copy(clips = track.clips + clip)
            } else {
                track
            }
        }
        return project.copy(tracks = updatedTracks)
    }
}
