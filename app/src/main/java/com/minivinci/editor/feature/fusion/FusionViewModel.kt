
package com.minivinci.editor.feature.fusion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FusionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FusionParameters())
    val uiState: StateFlow<FusionParameters> = _uiState.asStateFlow()

    fun updateOpacity(opacity: Float) {
        _uiState.value = _uiState.value.copy(opacity = opacity)
    }

    fun updateScale(scale: Float) {
        _uiState.value = _uiState.value.copy(scale = scale)
    }
}
