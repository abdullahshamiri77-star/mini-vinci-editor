package com.minivinci.editor.feature.timeline.domain.command

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

class AddClipCommand(
    private val trackId: String,
    private val clip: TimelineClip
) : TimelineCommand {

    override fun execute(project: TimelineProject): TimelineProject {
        val updatedTracks = project.tracks.map { track ->
            if (track.trackId == trackId && !track.isLocked) {
                track.copy(clips = track.clips + clip)
            } else {
                track
            }
        }
        return project.copy(tracks = updatedTracks)
    }

    override fun undo(project: TimelineProject): TimelineProject {
        val updatedTracks = project.tracks.map { track ->
            if (track.trackId == trackId) {
                track.copy(clips = track.clips.filterNot { it.clipId == clip.clipId })
            } else {
                track
            }
        }
        return project.copy(tracks = updatedTracks)
    }
}
