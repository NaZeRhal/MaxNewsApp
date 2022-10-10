package com.maxrzhe.com.maxnewsapp.presentation.news_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
fun NewsCard(
    modifier: Modifier = Modifier,
    article: ArticleModel,
    onNavigateToDetails: (id: Long) -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 160.dp)
            .padding(vertical = 4.dp)
            .clickable { onNavigateToDetails(article.id) },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .weight(0.25f)
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
                                .width(80.dp)
                                .height(120.dp)
                        )
                    }
                    is AsyncImagePainter.State.Loading -> Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            strokeWidth = 3.dp
                        )
                    }
                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.75f)
                    .align(Alignment.Top)
            ) {
                Column(modifier = Modifier.weight(0.8f)) {
                    Text(
                        text = article.title ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = article.description ?: "",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colors.onBackground,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.2f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    article.source?.name?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            color = HighTextGray,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.75.sp
                        )
                    }
                    article.publishedAt?.let { dateTime ->
                        val date = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME)
                        val time = date.format(DateTimeFormatter.ofPattern("HH:mm"))
                        Text(
                            text = time,
                            fontSize = 12.sp,
                            color = HighTextGray,
                            letterSpacing = 0.75.sp
                        )
                    }
                }
            }
        }
    }
}