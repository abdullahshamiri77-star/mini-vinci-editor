package com.minivinci.editor.feature.timeline.domain.usecase

import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.operation.RemoveClipOperation

class RemoveClip(private val removeClipOperation: RemoveClipOperation = RemoveClipOperation()) {
    operator fun invoke(project: TimelineProject, clipId: String): TimelineProject {
        return removeClipOperation.execute(project, clipId)
    }
}
