package com.faviansa.newsnow.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsNowBottomNav(
    onNavigate: (Int) -> Unit,
    selectedItem: Int,
    modifier: Modifier = Modifier,
) {

    NavigationBar {
        val items = listOf("Home", "Search", "Saved")
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    when (index) {
                        0 -> Icon(Icons.Default.Home, contentDescription = item)
                        1 -> Icon(Icons.Default.Search, contentDescription = item)
                        2 -> Icon(Icons.Default.Bookmark, contentDescription = item)
                    }
                },
                label = { Text(item) },
                alwaysShowLabel = false,
                selected = selectedItem == index,
                onClick = {
                    onNavigate(index)
                }
            )
        }
    }
}