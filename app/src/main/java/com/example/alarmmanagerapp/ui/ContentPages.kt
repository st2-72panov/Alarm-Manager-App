package com.example.alarmmanagerapp.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.alarmmanagerapp.R
import com.example.alarmmanagerapp.databases.groups.PageGroupsViewModel
import com.example.alarmmanagerapp.databases.solo.PageSoloViewModel
import com.example.alarmmanagerapp.databases.solo.SolosEvent
import com.example.alarmmanagerapp.databases.solo.SortType
import com.example.alarmmanagerapp.ui.page_solo.PageSolo
import com.example.alarmmanagerapp.ui.shared_compose_functions.TopBarTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContentPages(
    pageSoloViewModel: PageSoloViewModel,
    pageGroupsViewModel: PageGroupsViewModel,
    navigateToSettings: () -> Unit
) = Column(
    Modifier.fillMaxSize()
) {

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState { 2 }
    val barSize = 70.dp

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                TopBarTitle(
                    if (selectedTabIndex == 0) "Простые будильники"
                    else "Группы будильников"
                )
            }, actions = {
                if (pagerState.currentPage == 0) pageSoloViewModel.also {
                    val pageSoloState by it.pageState.collectAsState()
                    SortButtonWithPopup(
                        pageSoloState.sortType,
                        { it.onEvent(SolosEvent.SortDB(SortType.Time)) },
                        { it.onEvent(SolosEvent.SortDB(SortType.IsOn)) }
                    )
                } else pageGroupsViewModel.also {
                    TODO("not implemented because the ")
                }

                SettingsButtonWithPopup { navigateToSettings() }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColor.background, titleContentColor = AppColor.light
            )
        )
    },

        bottomBar = {
            Row(
                modifier = Modifier
                    .height(barSize)
                    .fillMaxWidth()
                    .background(AppColor.background),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                AnimatedIconButtonWithText(
                    ImageVector.vectorResource(R.drawable.ac_solo_outlined_24),
                    "Простые",
                    selectedTabIndex == 0
                ) { selectedTabIndex = 0 }

                Spacer(Modifier.width(50.dp))

                AnimatedIconButtonWithText(
                    ImageVector.vectorResource(R.drawable.ac_group_outlined_24),
                    "Группы",
                    selectedTabIndex == 1
                ) { selectedTabIndex = 1 }
            }
        }) { contentPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(contentPadding)
        ) {
            HorizontalPager(state = pagerState) { index ->
                when (index) {
                    0 -> PageSolo(pageSoloViewModel.pageState, pageSoloViewModel::onEvent)
                    1 -> PageGroups(pageGroupsViewModel)
                }
            }
        }

        LaunchedEffect(pagerState.currentPage) { selectedTabIndex = pagerState.currentPage }
        LaunchedEffect(selectedTabIndex) { pagerState.animateScrollToPage(selectedTabIndex) }
    }
}

@Composable
fun AnimatedIconButtonWithText(
    icon: ImageVector, text: String, isSelected: Boolean, onClick: () -> Unit
) {
    val buttonSize = 70.dp
    val animationDuration = 200
    val targetColor by animateColorAsState(
        targetValue = getLightOrDim(isSelected),
        animationSpec = tween(durationMillis = animationDuration),
        ""
    )
    val coroutineScope = rememberCoroutineScope()

    IconButton(modifier = Modifier.requiredSize(buttonSize), onClick = {
        coroutineScope.launch {
            delay(animationDuration.toLong())
            onClick()
        }
    }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = targetColor)
            Spacer(Modifier.height(3.dp))
            Text(
                text,
                color = targetColor,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold
            )
        }
    }
}

@Composable
fun SettingsButtonWithPopup(
    navigateToSettings: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val haptic = LocalHapticFeedback.current
    IconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        isExpanded = true
    }) {
        Icon(
            ImageVector.vectorResource(R.drawable.baseline_more_vert_24),
            null,
            tint = AppColor.dim
        )
    }

    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            modifier = Modifier.background(AppColor.dimmest2),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            offset = DpOffset((-15).dp, (-5).dp),
            properties = PopupProperties()
        ) {
            DropdownMenuItem(text = {
                Text(
                    "Настройки",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }, onClick = {
                isExpanded = false
                navigateToSettings()
            }, colors = MenuDefaults.itemColors(AppColor.light)
            )
        }
    }
}


@Composable
fun SortButtonWithPopup(
    sortType: SortType,
    onSortByTimeChose: () -> Unit,
    onSortByIsOnChose: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { isExpanded = true }) {
        Icon(
            ImageVector.vectorResource(R.drawable.round_sort_24),
            null,
            tint = AppColor.dim
        )
    }

    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp))
    ) {
        DropdownMenu(
            modifier = Modifier.background(AppColor.dimmest2),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            MyDropdownMenuItem( sortType == SortType.Time,"По времени") {
                isExpanded = false
                onSortByTimeChose()
            }

            MyDropdownMenuItem(sortType == SortType.IsOn, "С рабочих") {
                isExpanded = false
                onSortByIsOnChose()
            }
        }
    }
}

@Composable
private fun MyDropdownMenuItem(
    isChosen: Boolean, name: String, onClick: () -> Unit
) = DropdownMenuItem(
    text = {
        Text(
            name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            fontSize = 16.sp
        )
    },
    onClick = { onClick() },
    colors = MenuDefaults.itemColors(
        getLightOrDim(isChosen)
    )
)