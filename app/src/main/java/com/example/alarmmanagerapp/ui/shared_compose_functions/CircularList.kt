package com.example.alarmmanagerapp.ui.shared_compose_functions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmmanagerapp.ui.AppColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularList(
    initialItem: Int,
    onItemSelected: (number: Int) -> Unit,
    maximalItem: Int,
    numberOfDisplayedItems: Int = 5,
    itemScaleFact: Double = 1.618,
) {
    val fontSize = 14.sp
    val itemHeight = 30.dp
    val largeNumberColor = AppColor.light
    val mediumNumberColor = AppColor.dim
    val smallNumberColor = AppColor.dimmest

    val items = (0..maximalItem).toList()
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember {
        mutableIntStateOf(0)
    }
    var itemsState by remember {
        mutableStateOf(items)
    }
    LaunchedEffect(null) {
        var targetIndex = items.indexOf(initialItem) - 2
        targetIndex += ((Int.MAX_VALUE / 2) / items.size) * items.size
        itemsState = items
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(targetIndex)
    }
    LazyColumn(
        modifier = Modifier
            .width(70.dp)
            .height(itemHeight * numberOfDisplayedItems),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(
            lazyListState = scrollState
        )
    ) {
        items(
            count = Int.MAX_VALUE,
            itemContent = { i ->
                val item = itemsState[i % itemsState.size]
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            val y = coordinates.positionInParent().y - itemHalfHeight - 100
                            val parentHalfHeight =
                                (coordinates.parentCoordinates?.size?.height ?: 0) / 2f
                            val isSelected =
                                (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)
                            if (isSelected && lastSelectedIndex != i) {
                                onItemSelected(i % itemsState.size)
                                lastSelectedIndex = i
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val targetColor by animateColorAsState(
                        targetValue = when (lastSelectedIndex - i) {
                            0 -> largeNumberColor
                            1, -1 -> mediumNumberColor
                            else -> smallNumberColor
                        }, tween(200), ""
                    )
                    val targetSize by animateIntAsState(
                        targetValue = (fontSize.value * if (lastSelectedIndex == i) itemScaleFact else 1.0).toInt(),
                        tween(200), ""
                    )
                    Text(
                        item.toString(),
                        color = targetColor,
                        fontSize = targetSize.sp,
                    )
                }
            }
        )
    }
}
