package com.example.e_medib.features.profile_feature.view

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.navigations.DownloadRekapScreenTabItem
import com.example.e_medib.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DowwnloadRekapScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {


    val tabRowItems = listOf(
        DownloadRekapScreenTabItem.Semua,
        DownloadRekapScreenTabItem._7Hari,
        DownloadRekapScreenTabItem._14Hari,
        DownloadRekapScreenTabItem._30Hari,
    )

    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit, block = {

    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Download Rekap Catatan Harian",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        color = mWhite
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "arrow_back",
                            tint = mWhite
                        )
                    }
                },
                backgroundColor = mLightBlue
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // TAB ROW
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = mWhite,
                divider = { Divider(modifier = Modifier.height(0.dp)) },
                indicator = { tabPositions ->
                    CustomIndicator(tabPositions = tabPositions, pagerState = pagerState)
                }) {
                tabRowItems.forEachIndexed { index, item ->
                    Tab(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp)),
                        text = { Text(text = item.title) },
                        selected = pagerState.currentPage == index,
                        selectedContentColor = mLightBlue,
                        unselectedContentColor = mGrayScale,
                        enabled = true,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    )
                }

            }

            HorizontalPager(count = tabRowItems.size, state = pagerState) {
                tabRowItems[pagerState.currentPage].screen()
            }

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition =
        updateTransition(pagerState.currentPage, label = "")

    val indicatorStart by transition.animateDp(
        label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(//Indicator end transition animation
        label = ""
    ) {
        tabPositions[it].right
    }

    Box(//Using a whole box around the Tab
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .fillMaxSize()
            .border(
                BorderStroke(2.dp, mLightBlue),
                RoundedCornerShape(50)
            )
            .padding(5.dp)

    )//You can also add a background, but then also use zIndex
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun DowwnloadRekapScreenPreview() {
    val navController = rememberNavController()

    DowwnloadRekapScreen(navController)

}
