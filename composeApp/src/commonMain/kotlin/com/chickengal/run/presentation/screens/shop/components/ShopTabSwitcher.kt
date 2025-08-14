package com.chickengal.run.presentation.screens.shop.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chickengal.run.domain.shop.ShopTab
import com.chickengal.run.presentation.shared.CustomText
import com.chickengal.run.presentation.theme.Purple
import com.chickengal.run.presentation.theme.Yellow

@Composable
fun ShopTabSwitcher(
    currentTab: ShopTab,
    onTabSelected: (ShopTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(240.dp)
            .height(40.dp)
            .background(
                brush = Purple,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TabButton(
            text = "Abilities",
            isSelected = currentTab == ShopTab.MODIFIERS,
            onClick = { onTabSelected(ShopTab.MODIFIERS) },
            modifier = Modifier.weight(1f)
        )

//        TabButton(
//            text = "Egg Packs",
//            isSelected = currentTab == ShopTab.EGG_PACKS,
//            onClick = { onTabSelected(ShopTab.EGG_PACKS) },
//            modifier = Modifier.weight(1f)
//        )
    }
}

@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() }
            .border(
                width = 2.dp,
                brush = if (isSelected) Yellow else SolidColor(Color.Transparent),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        CustomText(
            text = text,
            fontSize = 16.sp
        )
    }
}