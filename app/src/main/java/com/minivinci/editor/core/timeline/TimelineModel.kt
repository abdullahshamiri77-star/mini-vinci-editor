package com.minivinci.editor.core.timeline

// الحفاظ على Timecode إذا لم يكن موجوداً في النماذج الجديدة، أو الاعتماد على ProjectDomainModels
data class Timecode(val frame: Long, val fps: Double = 30.0) {
    fun toMillis(): Long = (frame * 1000 / fps).toLong()
}
