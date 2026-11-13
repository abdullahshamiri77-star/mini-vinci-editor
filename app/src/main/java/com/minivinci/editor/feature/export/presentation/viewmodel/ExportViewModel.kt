package com.minivinci.editor.feature.export.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.feature.export.domain.engine.ExportEngine
import com.minivinci.editor.feature.export.domain.model.ExportConfig
import com.minivinci.editor.feature.export.domain.model.ExportState
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExportViewModel(
    private val exportEngine: ExportEngine = ExportEngine()
) : ViewModel() {

    val exportState: StateFlow<ExportState> = exportEngine.exportState

    fun startExportProcess(project: TimelineProject, outputPath: String) {
        val config = ExportConfig(outputPath = outputPath)
        viewModelScope.launch {
            exportEngine.startExport(project, config)
        }
    }

    fun resetExportState() {
        exportEngine.resetState()
    }
}
