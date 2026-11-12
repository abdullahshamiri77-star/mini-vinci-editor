package com.minivinci.editor.core.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    // حالة المشروع الحالي المحفوظ
    private val _currentProjectName = MutableStateFlow("مشروع بدون عنوان")
    val currentProjectName: StateFlow<String> = _currentProjectName.asStateFlow()

    /**
     * إنشاء مشروع جديد وحفظه في النظام
     */
    fun createNewProject(projectName: String) {
        viewModelScope.launch {
            _currentProjectName.value = projectName
            // منطق الإدخال الفعلي في قاعدة بيانات Room سيسجل هنا
        }
    }

    /**
     * حفظ حالة التايم لاين والمقاطع الحالية
     */
    fun saveProjectState() {
        viewModelScope.launch {
            // تنفيذ عمليات الحفظ المستدام في الخلفية
        }
    }
}
