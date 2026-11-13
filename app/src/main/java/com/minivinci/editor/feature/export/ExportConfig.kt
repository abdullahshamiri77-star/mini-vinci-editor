package com.minivinci.editor.feature.export.domain.model

data class ExportConfig(
    val outputPath: String,
    val width: Int = 1080,
    val height: Int = 1920,
    val fps: Int = 30,
    val videoBitrate: Int = 5_000_000 // 5 Mbps
)

sealed class ExportState {
    object Idle : ExportState()
    data class Progress(val progressPercentage: Float) : ExportState()
    data class Success(val finalPath: String) : ExportState()
    data class Error(val message: String) : ExportState()
}
