package com.faviansa.newsnow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.faviansa.newsnow.domain.model.News
import com.faviansa.newsnow.utils.Helper

@Composable
fun HeadlineNewsCard(
    news: News,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onNewsClick: (String) -> Unit,
    onToggleFavorite: (News) -> Unit,
) {
    val newsImageRequest = ImageRequest.Builder(LocalContext.current)
        .data(news.urlToImage)
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .width(300.dp)
            .height(200.dp)
            .padding(8.dp)
            .clickable { onNewsClick(news.url) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            focusedElevation = 8.dp
        )
    ) {
        Box {
            AsyncImage(
                model = newsImageRequest,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.75f)
                            )
                        )
                    )
            )

            // Bookmark button
            IconButton(
                onClick = { onToggleFavorite(news) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = Helper.formatCardDate(news.publishedAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 1
                )
            }
        }
    }
}