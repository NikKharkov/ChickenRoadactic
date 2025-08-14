package com.chickengal.run.presentation.screens.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.purple_box
import com.chickengal.run.presentation.screens.leaderboard.components.LeaderboardCardEntry
import com.chickengal.run.presentation.shared.TopBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LeaderboardScreen(
    onBackClick: () -> Unit,
    leaderboardViewModel: LeaderboardViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopBar(
            onBackClick = onBackClick,
            screenTitle = "Leaderboard"
        )

        Box(
            modifier = Modifier
                .width(330.dp)
                .height(420.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.purple_box),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    LeaderboardCardEntry(
                        index = "#",
                        name = "Name",
                        points = "Points"
                    )
                }

                items(leaderboardViewModel.topLeaderboard) { entry ->
                    LeaderboardCardEntry(
                        index = entry.rank.toString(),
                        name = entry.name,
                        points = entry.score.toString()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}