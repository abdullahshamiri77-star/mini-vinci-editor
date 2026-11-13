package com.minivinci.editor.feature.timeline.domain.usecase

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.operation.AddClipOperation

class AddClip(private val addClipOperation: AddClipOperation = AddClipOperation()) {
    operator fun invoke(project: TimelineProject, targetTrackId: String, clip: TimelineClip): TimelineProject {
        return addClipOperation.execute(project, targetTrackId, clip)
    }
}
