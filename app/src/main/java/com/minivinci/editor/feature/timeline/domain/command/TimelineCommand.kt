package com.minivinci.editor.feature.timeline.domain.command

import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

interface TimelineCommand {
    fun execute(project: TimelineProject): TimelineProject
    fun undo(project: TimelineProject): TimelineProject
}
