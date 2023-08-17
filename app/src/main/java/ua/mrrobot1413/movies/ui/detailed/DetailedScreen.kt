package ua.mrrobot1413.movies.ui.detailed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.misc.Constants
import ua.mrrobot1413.movies.misc.Utils
import ua.mrrobot1413.movies.ui.theme.Primary
import ua.mrrobot1413.movies.ui.theme.Secondary

@Composable
fun DetailedScreen(
    viewModel: DetailedViewModel = hiltViewModel(), navController: NavController, id: Int
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovieDetails(id)
    }
    AnimatedVisibility(
        visible = state.isConnected && state.error == null,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Primary, Primary, Secondary
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            Poster(navController, movie = state.movie)
            Description(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                movie = state.movie
            )
            SimilarMovies(state = state, navController = navController)
        }
    }

    AnimatedVisibility(
        visible = !state.isConnected || state.error != null,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Primary, Primary, Secondary
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ErrorMessage {
                viewModel.getMovieDetails(id)
            }
        }
    }
}

@Composable
fun Poster(
    navController: NavController, modifier: Modifier = Modifier, movie: DetailedMovieResponse
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    sizeImage = it.size
                },
            model = Constants.IMG_URL + movie.posterPath,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .clickable { navController.popBackStack() },
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(R.string.arrow_back),
            tint = Color.White
        )
        Text(
            text = movie.title,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .align(Alignment.BottomStart)
                .zIndex(2f),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = W600,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Primary),
                        startY = sizeImage.height.toFloat() / 2,  // 1/3
                        endY = sizeImage.height.toFloat()
                    )
                )
        )
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier, movie: DetailedMovieResponse
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.widthIn(0.dp, 200.dp),
                text = movie.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = W600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Divider(
                modifier = Modifier
                    .rotate(90f)
                    .width(20.dp), color = Color.White, thickness = 2.dp
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(movie.genres) {
                    Text(it.name, color = Color.White)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = movie.runtime + " " + stringResource(R.string.minutes),
                color = Color.White,
            )
            Text(
                text = movie.releaseDate.take(4),
                color = Color.White,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ExpandableText(
                text = movie.overview
            )
        }
        ActionIcons(modifier = Modifier.padding(vertical = 32.dp))
    }
}

@Composable
fun SimilarMovies(
    modifier: Modifier = Modifier, state: DetailedScreenState, navController: NavController
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.similar_movies),
            fontWeight = W600,
            fontSize = 20.sp,
            color = Color.White
        )
        LazyRow(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(state.similarMovies.results) { index, movie ->
                MovieItem(
                    modifier = Modifier,
                    movieId = movie.id ?: 0,
                    imageUrl = (Constants.IMG_URL + movie.posterPath),
                    navController = navController,
                    index = index,
                    lastIndex = state.similarMovies.results.lastIndex
                )
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movieId: Int,
    imageUrl: String,
    navController: NavController,
    index: Int,
    lastIndex: Int
) {
    Column(
        modifier = when (index) {
            0 -> Modifier.padding(start = 16.dp, end = 0.dp)

            lastIndex -> Modifier.padding(
                start = 0.dp, end = 16.dp
            )

            else -> Modifier.padding(start = 0.dp, end = 0.dp)
        }
    ) {
        Box(modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                // navigate to details
                navController.navigate("${Constants.DETAILED}/$movieId")
            }) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
                    .placeholder(R.drawable.ic_movie).crossfade(true).build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .align(Alignment.Center),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@Composable
fun ActionIcons(
    modifier: Modifier = Modifier
) {
    Row(
        modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ActionItem(icon = Icons.Rounded.Add, text = stringResource(R.string.watchlist))
        ActionItem(icon = Icons.Rounded.Share, text = stringResource(R.string.share))
    }
}

@Composable
fun ActionItem(
    modifier: Modifier = Modifier, icon: ImageVector, text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon, contentDescription = text, tint = Color.White
        )
        Text(
            text = text, color = Color.White, fontFamily = FontFamily(Utils.fonts), fontSize = 14.sp
        )
    }
}

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 1,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null && lastLineIndex + 1 == textLayoutResult.lineCount && textLayoutResult.isLineEllipsized(
                lastLineIndex
            )
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (charRect.left > textLayoutResult.size.width - seeMoreSize.width)
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier) {
        Text(
            modifier = Modifier
                .clickable {
                    if(expanded) {
                        expanded = false
                        val lastLineIndex = minimizedMaxLines - 1
                        if (!expanded && textLayoutResult != null && seeMoreSize != null && lastLineIndex + 1 == textLayoutResult.lineCount && textLayoutResult.isLineEllipsized(
                                lastLineIndex
                            )
                        ) {
                            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
                            var charRect: Rect
                            do {
                                lastCharIndex -= 1
                                charRect = textLayoutResult.getCursorRect(lastCharIndex)
                            } while (charRect.left > textLayoutResult.size.width - seeMoreSize.width)
                            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
                            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                        }
                    } else {
                        expanded = true
                        cutText = null
                    }
                },
            color = White,
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(stringResource(id = R.string.show_more),
                color = White,
                fontWeight = W600,
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null) Modifier.offset(
                            x = with(density) { seeMoreOffset.x.toDp() },
                            y = with(density) { seeMoreOffset.y.toDp() },
                        )
                        else Modifier
                    )
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f))
        }
    }
}

@Composable
fun ErrorMessage(
    loadMovies: () -> Unit
) {
    Text(
        text = stringResource(R.string.something_went_wrong),
        fontFamily = FontFamily(Utils.fonts),
        fontSize = 24.sp,
        color = Color.White

    )
    Button(onClick = { loadMovies() }) {
        Icon(Icons.Default.Refresh, contentDescription = null)
        Text(text = stringResource(R.string.try_again))
    }
}