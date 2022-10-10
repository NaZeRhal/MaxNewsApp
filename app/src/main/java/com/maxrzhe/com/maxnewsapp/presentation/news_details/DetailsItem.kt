package com.maxrzhe.com.maxnewsapp.presentation.news_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.maxrzhe.com.maxnewsapp.R
import com.maxrzhe.com.maxnewsapp.domain.model.ArticleModel
import com.maxrzhe.com.maxnewsapp.ui.theme.HighTextGray
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailsItem(
    modifier: Modifier = Modifier,
    article: ArticleModel,
    onGoToSiteClick: (url: String) -> Unit
) {
    val content by remember {
        val text: String? = article.content?.substringBefore("[")
        mutableStateOf(text)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        DetailsImage(imageUrl = article.urlToImage)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                article.source?.name?.let {
                    Text(
                        text = it,
                        fontSize = 26.sp,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.75.sp
                    )
                }
                article.publishedAt?.let { dateTime ->
                    val date = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
                    val time = date.format(DateTimeFormatter.ofPattern("HH:mm"))
                    Text(
                        text = time,
                        fontSize = 18.sp,
                        color = HighTextGray,
                        letterSpacing = 0.75.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = article.title ?: "...",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            content?.let { text ->
                Text(text = text, textAlign = TextAlign.Justify, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            article.url?.let { url ->
                Text(
                    text = "Go To Site",
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable { onGoToSiteClick(url) })
            }
            Spacer(modifier = Modifier.height(24.dp))
            article.author?.let { author ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = author,
                        fontSize = 16.sp,
                        color = HighTextGray,
                        letterSpacing = 0.25.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun DetailsImage(modifier: Modifier = Modifier, imageUrl: String?) {
    val context = LocalContext.current
    Row(modifier = modifier.fillMaxWidth()) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
        ) {
            when (painter.state) {
                AsyncImagePainter.State.Empty -> Unit
                is AsyncImagePainter.State.Error -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_placeholder),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
                is AsyncImagePainter.State.Loading -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        strokeWidth = 3.dp
                    )
                }
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            }
        }
    }
}