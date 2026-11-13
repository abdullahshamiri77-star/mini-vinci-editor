package com.minivinci.editor.feature.color

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.core.database.ProjectRepository
import com.minivinci.editor.feature.color.engine.ColorParameters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ColorViewModel(
    private val repository: ProjectRepository? = null
) : ViewModel() {

    // حالة معايير التلوين الحالية
    private val _colorParameters = MutableStateFlow(ColorParameters())
    val colorParameters: StateFlow<ColorParameters> = _colorParameters.asStateFlow()

    /**
     * تحديث قيمة التعريض (Exposure)
     */
    fun updateExposure(newExposure: Float) {
        viewModelScope.launch {
            _colorParameters.value = _colorParameters.value.copy(exposure = newExposure)
        }
    }

    /**
     * تحديث قيمة التباين (Contrast)
     */
    fun updateContrast(newContrast: Float) {
        viewModelScope.launch {
            _colorParameters.value = _colorParameters.value.copy(contrast = newContrast)
        }
    }

    /**
     * تحديث درجة حرارة اللون (Temperature in Kelvin)
     */
    fun updateTemperature(newTemp: Float) {
        viewModelScope.launch {
            _colorParameters.value = _colorParameters.value.copy(temperature = newTemp)
        }
    }

    /**
     * تحديث تشبع الألوان (Saturation)
     */
    fun updateSaturation(newSaturation: Float) {
        viewModelScope.launch {
            _colorParameters.value = _colorParameters.value.copy(saturation = newSaturation)
        }
    }

    /**
     * إعادة تعيين قيم الألوان إلى الوضع الافتراضي الأصلي
     */
    fun resetColors() {
        viewModelScope.launch {
            _colorParameters.value = ColorParameters()
        }
    }
}
