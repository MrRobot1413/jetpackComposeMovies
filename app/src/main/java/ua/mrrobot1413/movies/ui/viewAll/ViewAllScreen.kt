package ua.mrrobot1413.movies.ui.viewAll

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.misc.Constants
import ua.mrrobot1413.movies.misc.Utils
import ua.mrrobot1413.movies.ui.theme.Accent
import ua.mrrobot1413.movies.ui.theme.Primary
import ua.mrrobot1413.movies.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAllScreen(
    viewModel: ViewAllViewModel = hiltViewModel(),
    navController: NavController,
    type: Int
) {
    val state by viewModel.state.collectAsState()
    if (state.isFirstLoad) {
        viewModel.loadNextItems(
            when (type) {
                2 -> ListType.NEWEST
                3 -> ListType.UPCOMING
                else -> ListType.POPULAR
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Primary,
                        Primary,
                        Secondary
                    )
                )
            )
    ) {

        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.go_back),
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(
                        id = when (type) {
                            2 -> R.string.newest
                            3 -> R.string.upcoming
                            else -> R.string.popular
                        }
                    ),
                    fontFamily = FontFamily(Utils.fonts),
                    fontWeight = FontWeight.W600,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Accent
            ),
        )
        if (state.isFirstLoad && state.isLoading) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                CircularProgressIndicator(
                    modifier = Modifier.then(
                        Modifier
                            .size(40.dp)
                    ),
                    color = Accent
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        } else {
            AnimatedVisibility(
                visible = state.isConnected && state.error == null,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.align(CenterHorizontally)) {
                        val listState = rememberLazyGridState()

                        LazyVerticalGrid(
                            state = listState,
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(0.dp, 10.dp, 0.dp, 80.dp),
                            modifier = Modifier.padding(16.dp, 0.dp)
                        ) {
                            itemsIndexed(state.items) { i, movie ->
                                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                                    viewModel.loadNextItems(
                                        when (type) {
                                            2 -> ListType.NEWEST
                                            3 -> ListType.UPCOMING
                                            else -> ListType.POPULAR
                                        }
                                    )
                                }
                                MovieItem(
                                    movieId = movie.id ?: 1,
                                    imageUrl = Constants.IMG_URL + movie.posterPath,
                                    navController = navController
                                )
                            }
                        }
                        if (state.isLoading && !listState.isScrollInProgress) {
                            CircularProgressIndicator(
                                modifier = Modifier.then(
                                    Modifier
                                        .padding(bottom = 20.dp)
                                        .size(40.dp)
                                        .zIndex(2f)
                                        .align(BottomCenter)
                                ),
                                color = Accent
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = !state.isConnected || state.error != null,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ErrorMessage(loadMovies = {
                        viewModel.loadNextItems(
                            when (type) {
                                2 -> ListType.NEWEST
                                3 -> ListType.UPCOMING
                                else -> ListType.POPULAR
                            }
                        )
                    })
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movieId: Int,
    imageUrl: String,
    navController: NavController
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                // navigate to details
                navController.navigate("${Constants.DETAILED}/$movieId")
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .placeholder(R.drawable.ic_movie)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .align(Alignment.Center),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
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