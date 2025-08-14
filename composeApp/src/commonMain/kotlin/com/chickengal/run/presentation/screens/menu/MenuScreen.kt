package com.chickengal.run.presentation.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.btn_records
import chickenroadactic.composeapp.generated.resources.btn_settings
import chickenroadactic.composeapp.generated.resources.btn_shop
import chickenroadactic.composeapp.generated.resources.btn_skins
import chickenroadactic.composeapp.generated.resources.btn_start
import com.chickengal.run.presentation.shared.CustomText
import com.chickengal.run.presentation.shared.InteractiveButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuScreen(
    onPlayClick: () -> Unit,
    onShopClick: () -> Unit,
    onSkinsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onRecordsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomText(
            text = "Main Menu",
            fontSize = 35.sp
        )

        Spacer(modifier = Modifier.height(200.dp))

        InteractiveButton(
            image = painterResource(Res.drawable.btn_start),
            onClick = onPlayClick,
            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
        )

        InteractiveButton(
            image = painterResource(Res.drawable.btn_skins),
            onClick = onSkinsClick,
            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
        )

        InteractiveButton(
            image = painterResource(Res.drawable.btn_shop),
            onClick = onShopClick,
            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
        )

        InteractiveButton(
            image = painterResource(Res.drawable.btn_records),
            onClick = onRecordsClick,
            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
        )

        InteractiveButton(
            image = painterResource(Res.drawable.btn_settings),
            onClick = onSettingsClick,
            modifier = Modifier
                .width(140.dp)
                .height(70.dp)
        )
    }
}