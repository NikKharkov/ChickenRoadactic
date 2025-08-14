package com.chickengal.run.presentation.screens.leaderboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chickengal.run.presentation.shared.CustomText

@Composable
fun LeaderboardCardEntry(
    index: String,
    name: String,
    points: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomText(
            text = index,
            fontSize = 16.sp
        )

        CustomText(
            text = name,
            fontSize = 16.sp
        )

        CustomText(
            text = points,
            fontSize = 16.sp
        )
    }
}