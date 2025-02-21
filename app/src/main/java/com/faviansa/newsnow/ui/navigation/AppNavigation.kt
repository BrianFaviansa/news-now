package com.faviansa.newsnow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faviansa.newsnow.ui.detail.DetailScreen
import com.faviansa.newsnow.ui.favorite.FavoriteScreen
import com.faviansa.newsnow.ui.home.HomeScreen
import com.faviansa.newsnow.ui.search.SearchScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        modifier = modifier
    ) {
        composable<Routes.Home> {
            HomeScreen(
                onNavigate = { index ->
                    when (index) {
                        0 -> {}
                        1 -> navController.navigate(Routes.Search)
                        2 -> navController.navigate(Routes.Favorite)
                    }
                }
            )
        }
        composable<Routes.NewsDetail> {
            DetailScreen(
                onNavigateUp = { navController.navigateUp() },
                newsLink = ""
            )
        }
        composable<Routes.Search> {
            SearchScreen(
                onNavigate = { index ->
                    when (index) {
                        0 -> navController.navigate(Routes.Home)
                        1 -> {}
                        2 -> navController.navigate(Routes.Favorite)
                    }
                }
            )
        }
        composable<Routes.Favorite> {
            FavoriteScreen(
                onNavigate = { index ->
                    when (index) {
                        0 -> navController.navigate(Routes.Home)
                        1 -> navController.navigate(Routes.Search)
                        2 -> {}
                    }
                }
            )
        }
    }
}