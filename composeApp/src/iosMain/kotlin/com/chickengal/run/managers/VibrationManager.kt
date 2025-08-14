package com.chickengal.run.managers

import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle

actual object VibrationManager {
    private val impactGenerator = UIImpactFeedbackGenerator(UIImpactFeedbackStyle.UIImpactFeedbackStyleMedium)

    actual fun vibrate(duration: Long) {
        impactGenerator.impactOccurred()
    }
}