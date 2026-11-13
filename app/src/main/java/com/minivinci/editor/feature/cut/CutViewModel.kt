package com.minivinci.editor.feature.cut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.core.database.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// نموذج ممثل للمقطع الزمني داخل التايم لاين
data class TimelineClip(
    val id: String,
    val startTime: Long,
    val duration: Long,
    val videoPath: String
)

class CutViewModel(
    private val repository: ProjectRepository
) : ViewModel() {

    // حالة مؤشر التشغيل الحالي على التايم لاين (بالمللي ثانية)
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    // قائمة المقاطع الحالية في التايم لاين
    private val _clips = MutableStateFlow<List<TimelineClip>>(emptyList())
    val clips: StateFlow<List<TimelineClip>> = _clips.asStateFlow()

    /**
     * تحديث موضع مؤشر التشغيل (Playhead)
     */
    fun updatePosition(newPosition: Long) {
        _currentPosition.value = newPosition
    }

    /**
     * تنفيذ عملية القطع (Blade / Split) الفعلي عند الموضع الحالي للمؤشر
     */
    fun performBladeCut(clipId: String, cutPosition: Long) {
        viewModelScope.launch {
            val currentList = _clips.value.toMutableList()
            val targetIndex = currentList.indexOfFirst { it.id == clipId }

            if (targetIndex != -1) {
                val clip = currentList[targetIndex]
                
                // التأكد أن نقطة القطع تقع ضمن حدود المقطع
                if (cutPosition > clip.startTime && cutPosition < (clip.startTime + clip.duration)) {
                    val firstPartDuration = cutPosition - clip.startTime
                    val secondPartDuration = clip.duration - firstPartDuration

                    // إنشاء المقطع الأول بعد القطع
                    val firstClip = clip.copy(duration = firstPartDuration)
                    
                    // إنشاء المقطع الثاني الناتج عن التقسيم
                    val secondClip = clip.copy(
                        id = "${clip.id}_cut_${System.currentTimeMillis()}",
                        startTime = cutPosition,
                        duration = secondPartDuration
                    )

                    // استبدال المقطع القديم بالمقطعين الجديدين في التايم لاين
                    currentList.removeAt(targetIndex)
                    currentList.add(targetIndex, firstClip)
                    currentList.add(targetIndex + 1, secondClip)

                    _clips.value = currentList
                    
                    // TODO: مزامنة التعديل مع المحرك (VideoEngine) وقاعدة البيانات عبر المستودع
                }
            }
        }
    }
}
