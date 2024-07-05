package com.example.e_medib.features.profile_feature.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_medib.features.profile_feature.view_model.ProfileViewModel
import com.example.e_medib.navigations.ProfileScreenTabItem
import com.example.e_medib.ui.theme.*
import com.example.e_medib.utils.CustomDataStore
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HitungBMIdanBMRScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {

    val listTabs = listOf(ProfileScreenTabItem.BMIScreen, ProfileScreenTabItem.BMRScreen)
    val pagerState = rememberPagerState(initialPage = 0)

    val context = LocalContext.current
    val store = CustomDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    LaunchedEffect(Unit, block = {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Accept"] = "application/json"
        headerMap["Authorization"] = "Bearer ${tokenText.value}"
        profileViewModel.getAllBMI(headerMap)
        profileViewModel.getAllBMR(headerMap)
    })
    if (profileViewModel.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(color = mLightBlue)
        }
    } else
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Hitung BMI dan BMR Anda",
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
        ) {padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfileScreenTabs(tabs = listTabs, pagerState = pagerState)
            }
        }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreenTabs(
    tabs: List<ProfileScreenTabItem>,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = mWhite,
        indicator = { tabPositions ->
            Modifier
                .pagerTabIndicatorOffset(pagerState = pagerState, tabPositions = tabPositions)
        },
    ) {
        tabs.forEachIndexed { index, tabItem ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                selectedContentColor = mLightBlue,
                unselectedContentColor = mBlack,
                enabled = true,
                text = { Text(text = tabItem.title) },
            )
        }

    }


    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        tabs[page].screen()
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun HitungBMIScreenPreview() {
    val navController = rememberNavController()

    HitungBMIdanBMRScreen(navController)

}
