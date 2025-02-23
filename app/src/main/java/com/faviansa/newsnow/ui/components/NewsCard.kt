package com.faviansa.newsnow.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.faviansa.newsnow.domain.model.News

@Composable
fun NewsCard(
    news: News,
    modifier: Modifier = Modifier,
    onNewsClick: (String) -> Unit
) {
    val newsImageRequest = ImageRequest.Builder(LocalContext.current)
        .data(news.urlToImage)
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onNewsClick(news.url) },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .height(120.dp)
                .padding(8.dp)
        ) {
            // News Image
            AsyncImage(
                model = news.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp))
            )

            // News Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = news.source,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = news.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = news.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = news.publishedAt,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}