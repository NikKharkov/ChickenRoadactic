package com.chickengal.run.platform

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(onBack: () -> Unit)