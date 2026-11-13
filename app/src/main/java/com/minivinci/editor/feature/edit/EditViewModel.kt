package com.minivinci.editor.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.core.database.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// نموذج يمثل مسار التعديل (فيديو أو صوت)
data class MediaTrack(
    val trackId: String,
    val trackType: TrackType,
    val name: String,
    val isMuted: Boolean = false
)

enum class TrackType {
    VIDEO, AUDIO
}

class EditViewModel(
    private val repository: ProjectRepository
) : ViewModel() {

    // قائمة المسارات النشطة في شاشة التعديل
    private val _tracks = MutableStateFlow<List<MediaTrack>>(
        listOf(
            MediaTrack("v1", TrackType.VIDEO, "Video Track 1"),
            MediaTrack("a1", TrackType.AUDIO, "Audio Track 1")
        )
    )
    val tracks: StateFlow<List<MediaTrack>> = _tracks.asStateFlow()

    // حالة تحديد مسار أو مقطع معين
    private val _selectedTrackId = MutableStateFlow<String?>(null)
    val selectedTrackId: StateFlow<String?> = _selectedTrackId.asStateFlow()

    /**
     * تحديد مسار معين للتعديل
     */
    fun selectTrack(trackId: String) {
        _selectedTrackId.value = trackId
    }

    /**
     * كتم أو إلغاء كتم مسار معين (ميزة أساسية في الـ Edit)
     */
    fun toggleTrackMute(trackId: String) {
        viewModelScope.launch {
            val currentList = _tracks.value.map { track ->
                if (track.trackId == trackId) {
                    track.copy(isMuted = !track.isMuted)
                } else {
                    track
                }
            }
            _tracks.value = currentList
        }
    }
}
