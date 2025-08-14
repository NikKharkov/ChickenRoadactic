package com.chickengal.run.presentation.screens.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.btn_laser_blast
import chickenroadactic.composeapp.generated.resources.btn_photon_pulse
import chickenroadactic.composeapp.generated.resources.btn_warp_dash
import chickenroadactic.composeapp.generated.resources.egg
import chickenroadactic.composeapp.generated.resources.ic_pause
import com.chickengal.run.managers.VibrationManager
import com.chickengal.run.presentation.shared.CustomText
import com.chickengal.run.presentation.shared.InteractiveButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun GameUI(
    currentRunCoins: Int,
    currentScore: Int,
    warpDashCount: Int,
    photonPulseCount: Int,
    laserBlastCount: Int,
    isWarpDashActive: Boolean,
    isPhotonPulseActive: Boolean,
    isLaserBlastActive: Boolean,
    isPaused: Boolean,
    onPause: () -> Unit,
    onWarpDashClick: () -> Unit,
    onPhotonPulseClick: () -> Unit,
    onLaserBlastClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.egg),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                CustomText(
                    text = "$currentRunCoins",
                    fontSize = 16.sp
                )
            }

            CustomText(
                text = "${currentScore}m",
                fontSize = 24.sp
            )

            InteractiveButton(
                image = painterResource(Res.drawable.ic_pause),
                onClick = {
                    onPause()
                    VibrationManager.vibrate(100)
                },
                modifier = Modifier.size(24.dp),
                enabled = !isPaused
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (warpDashCount > 0) {
                Box {
                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_warp_dash),
                        onClick = {
                            onWarpDashClick()
                            VibrationManager.vibrate(100)
                        },
                        modifier = Modifier.size(83.dp),
                        enabled = !isWarpDashActive
                    )

                    ModifierCountBadge(
                        count = warpDashCount,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
            }

            if (photonPulseCount > 0) {
                Box {
                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_photon_pulse),
                        onClick = {
                            onPhotonPulseClick()
                            VibrationManager.vibrate(100)
                        },
                        modifier = Modifier.size(83.dp),
                        enabled = !isPhotonPulseActive
                    )

                    ModifierCountBadge(
                        count = photonPulseCount,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
            }

            if (laserBlastCount > 0) {
                Box {
                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_laser_blast),
                        onClick = {
                            onLaserBlastClick()
                            VibrationManager.vibrate(100)
                        },
                        modifier = Modifier.size(83.dp),
                        enabled = !isLaserBlastActive
                    )

                    ModifierCountBadge(
                        count = laserBlastCount,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
            }
        }
    }
}