package com.chickengal.run.presentation.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import chickenroadactic.composeapp.generated.resources.Grobold
import chickenroadactic.composeapp.generated.resources.Res
import com.chickengal.run.presentation.theme.Yellow
import org.jetbrains.compose.resources.Font

@Composable
fun CustomText(
    text: String,
    fontSize: TextUnit
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = FontFamily(Font(Res.font.Grobold)),
            brush = Yellow,
            textAlign = TextAlign.Center
        )
    )
}