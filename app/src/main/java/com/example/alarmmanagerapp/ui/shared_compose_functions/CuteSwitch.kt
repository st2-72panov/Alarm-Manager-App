package com.example.alarmmanagerapp.ui.shared_compose_functions

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import com.example.alarmmanagerapp.ui.AppColor

@Composable
fun CuteSwitch(
    haptic: HapticFeedback,
    isOn: Boolean,
    onCheckedChangeEvent: () -> Unit
) = Switch(
    modifier = Modifier.scale(0.8f),
    checked = isOn,
    onCheckedChange = {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        onCheckedChangeEvent()
    },
    colors = SwitchDefaults.colors(
        checkedThumbColor = AppColor.lightest,
        uncheckedThumbColor = AppColor.lightest,
        checkedTrackColor = AppColor.contrast,
        uncheckedTrackColor = AppColor.dim
    )
)
