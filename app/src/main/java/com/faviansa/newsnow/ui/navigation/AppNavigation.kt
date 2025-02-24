package com.faviansa.newsnow.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.faviansa.newsnow.ui.detail.DetailScreen
import com.faviansa.newsnow.ui.favorite.FavoriteScreen
import com.faviansa.newsnow.ui.favorite.FavoriteViewModel
import com.faviansa.newsnow.ui.home.HomeScreen
import com.faviansa.newsnow.ui.home.HomeViewModel
import com.faviansa.newsnow.ui.search.SearchScreen
import com.faviansa.newsnow.ui.search.SearchViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        modifier = modifier
    ) {
        composable<Routes.Home> {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val headlineNews = homeViewModel.headlineNews.collectAsLazyPagingItems()
            val economicNews = homeViewModel.economicNews.collectAsLazyPagingItems()
            val favoriteNewsTitles by homeViewModel.favoriteTitles.collectAsStateWithLifecycle()

            HomeScreen(
                onNavigate = { index ->
                    when (index) {
                        0 -> {}
                        1 -> navController.navigate(Routes.Search)
                        2 -> navController.navigate(Routes.Favorite)
                    }
                },
                onNewsClick = { newsUrl ->
                    navController.navigate(Routes.NewsDetail(newsUrl))
                },
                headlineNews = headlineNews,
                economicNews = economicNews,
                favoriteNewsTitles = favoriteNewsTitles,
                onToggleFavorite = { homeViewModel.toggleFavoriteStatus(it) },
                snackbarHostState = snackbarHostState,
                snackbarEvent = homeViewModel.snackbarEvent
            )
        }
        composable<Routes.NewsDetail> { backStackEntry ->
            val newsLink = backStackEntry.toRoute<Routes.NewsDetail>().newsLink
            DetailScreen(
                onNavigateUp = { navController.navigateUp() },
                newsLink = newsLink
            )
        }
        composable<Routes.Search> {
            val searchViewModel: SearchViewModel = hiltViewModel()
            val searchedNews = searchViewModel.searchedNews.collectAsLazyPagingItems()
            val favoriteNewsTitles by searchViewModel.favoriteTitles.collectAsStateWithLifecycle()

            SearchScreen(
                onNavigate = { index ->
                    when (index) {
                        0 -> navController.navigate(Routes.Home)
                        1 -> {}
                        2 -> navController.navigate(Routes.Favorite)
                    }
                },
                snackbarHostState = snackbarHostState,
                snackbarEvent = searchViewModel.snackbarEvent,
                searchedNews = searchedNews,
                favoriteNewsTitles = favoriteNewsTitles,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackClick = { navController.navigateUp() },
                onNewsClick = { newsUrl ->
                    navController.navigate(Routes.NewsDetail(newsUrl))
                },
                onSearch = { searchViewModel.searchNews(it) },
                onToggleFavorite = { searchViewModel.toggleFavoriteStatus(it) },
            )
        }
        composable<Routes.Favorite> {
            val favoriteViewModel: FavoriteViewModel = hiltViewModel()
            val favoriteNews = favoriteViewModel.favoriteNews.collectAsLazyPagingItems()
            val favoriteNewsTitles by favoriteViewModel.favoriteTitles.collectAsStateWithLifecycle()

            FavoriteScreen(
                onNavigate = { index ->
                    when (index) {
                        0 -> navController.navigate(Routes.Home)
                        1 -> navController.navigate(Routes.Search)
                        2 -> {}
                    }
                },
                onNewsClick = { newsUrl ->
                    navController.navigate(Routes.NewsDetail(newsUrl))
                },
                favoriteNews = favoriteNews,
                favoriteNewsTitles = favoriteNewsTitles,
                onToggleFavorite = { favoriteViewModel.toggleFavoriteStatus(it) },
                snackbarHostState = snackbarHostState,
                snackbarEvent = favoriteViewModel.snackbarEvent
            )
        }
    }
}