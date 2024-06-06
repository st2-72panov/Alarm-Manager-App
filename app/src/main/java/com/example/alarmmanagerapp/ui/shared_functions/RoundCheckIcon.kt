package com.example.alarmmanagerapp.ui.shared_functions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.alarmmanagerapp.R
import com.example.alarmmanagerapp.ui.AppColor

@Composable
fun RoundCheckIcon(
    isSelected: Boolean
) = Box {
    val targetCheckColor by animateColorAsState(
        targetValue = if (isSelected) AppColor.contrast else AppColor.dimmest,
        animationSpec = tween(200), ""
    )

    Icon(
        ImageVector.vectorResource(R.drawable.baseline_circle_32),
        null,
        tint = if (isSelected) AppColor.lightest else AppColor.dimmest
    )
    if (isSelected)
        Icon(
            ImageVector.vectorResource(R.drawable.round_check_circle_32),
            null,
            tint = targetCheckColor
        )
}