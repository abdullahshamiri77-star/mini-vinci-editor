package com.minivinci.editor.feature.color.engine

data class ColorParameters(
    // التصحيح الأساسي (Primary Color Correction)
    val exposure: Float = 0f,          // التعريض (-2.0 إلى +2.0)
    val contrast: Float = 1.0f,        // التباين (0.5 إلى 2.0)
    val brightness: Float = 0f,        // السطوع
    val temperature: Float = 5000f,    // درجة حرارة اللون (كالفن)
    val tint: Float = 0f,              // التينت (أخضر / أرجواني)
    val saturation: Float = 1.0f,      // تشبع الألوان (0.0 إلى 2.0)

    // عجلات الألوان (Color Wheels: Shadows, Midtones, Highlights)
    val shadowRed: Float = 0f,
    val shadowGreen: Float = 0f,
    val shadowBlue: Float = 0f,

    val midtoneRed: Float = 0f,
    val midtoneGreen: Float = 0f,
    val midtoneBlue: Float = 0f,

    val highlightRed: Float = 0f,
    val highlightGreen: Float = 0f,
    val highlightBlue: Float = 0f
) {
    companion5: Companion = Companion // لترتيب الثوابت الافتراضية للضبط اللوني
}
