package ua.mrrobot1413.movies.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W800
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.R
import ua.mrrobot1413.movies.misc.Constants
import ua.mrrobot1413.movies.misc.Constants.DETAILED
import ua.mrrobot1413.movies.misc.Constants.ID
import ua.mrrobot1413.movies.misc.Constants.SEARCH
import ua.mrrobot1413.movies.misc.Constants.VIEW_ALL
import ua.mrrobot1413.movies.misc.Utils
import ua.mrrobot1413.movies.misc.Utils.defaultTextStyle
import ua.mrrobot1413.movies.ui.theme.Accent
import ua.mrrobot1413.movies.ui.theme.MainTextColor
import ua.mrrobot1413.movies.ui.theme.Primary
import ua.mrrobot1413.movies.ui.theme.Secondary

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    Box(
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
        AnimatedVisibility(
            visible = state.isConnected && state.errorNewest == null && state.errorPopular == null,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(navController = navController)
                Lists(state, navController = navController)
            }
        }
        AnimatedVisibility(
            visible = !state.isConnected || state.errorNewest != null || state.errorPopular != null,
            enter = fadeIn(animationSpec = tween(300)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorMessage(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 10.dp, 16.dp, 0.dp)
    ) {
        Text(
            text = stringResource(R.string.home_header),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 22.sp,
            color = MainTextColor,
            style = defaultTextStyle.bodyLarge,
            fontWeight = W600
        )
        Spacer(modifier = Modifier.height(10.dp))
        SearchView(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val searchText = remember {
        mutableStateOf(TextFieldValue())
    }

    TextField(
        readOnly = true,
        enabled = false,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))
            .shadow(4.dp, shape = RoundedCornerShape(16.dp))
            .clickable { navController.navigate(SEARCH) },
        value = searchText.value,
        onValueChange = { searchText.value = it },
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                contentDescription = null,
                tint = Color.White
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Accent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Primary,
            textColor = MainTextColor
        ),
        textStyle = TextStyle(
            fontFamily = FontFamily(Utils.fonts),
            fontSize = 20.sp
        )
    )
}

@Composable
fun Lists(
    state: HomeScreenState,
    navController: NavController
) {
    MovieList(
        title = stringResource(id = R.string.popular),
        type = 1,
        state = state,
        navController = navController
    )
    MovieList(
        title = stringResource(id = R.string.newest),
        type = 2,
        state = state,
        navController = navController
    )
    MovieList(
        title = stringResource(id = R.string.upcoming),
        type = 3,
        state = state,
        navController = navController
    )
}

@Composable
fun MovieList(
    title: String,
    type: Int,
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    navController: NavController
) {

    Column(
        modifier = modifier
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 10.dp, 16.dp, 10.dp),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                style = defaultTextStyle.bodyLarge,
                fontWeight = W600,
                color = MainTextColor
            )
            Box {
                Text(
                    modifier = Modifier.clickable {
                        // Popular type
                        navController.navigate("${VIEW_ALL}/$type")
                    },
                    text = stringResource(R.string.view_all),
                    fontSize = 16.sp,
                    style = defaultTextStyle.bodyLarge,
                    fontWeight = W800,
                    color = Accent
                )
            }
        }
        if (when (type) {
                1 -> state.isLoadingPopular
                2 -> state.isLoadingNewest
                else -> state.isLoadingUpcoming
            }
        ) {
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
        } else {
            Row(
                modifier = Modifier
                    .height(260.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                val list = when (type) {
                    1 -> state.popularItems
                    2 -> state.newestItems
                    else -> state.upcomingItems
                }
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    itemsIndexed(list, key = { i, movie -> movie.id ?: i }) { index, movie ->
                        MovieItem(
                            modifier = Modifier,
                            movieId = movie.id ?: 0,
                            imageUrl = (Constants.IMG_URL + movie.posterPath),
                            navController = navController,
                            index = index,
                            lastIndex = list.lastIndex
                        )
                    }
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
    navController: NavController,
    index: Int,
    lastIndex: Int
) {
    Column(
        modifier = when (index) {
            0 -> Modifier
                .padding(start = 16.dp, end = 0.dp)

            lastIndex -> Modifier.padding(
                start = 0.dp,
                end = 16.dp
            )

            else -> Modifier
                .padding(start = 0.dp, end = 0.dp)
        }
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    // navigate to details
                    navController.navigate("${DETAILED}/$movieId")
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
}

@Composable
fun ErrorMessage(
    viewModel: HomeViewModel
) {
    Text(
        text = stringResource(R.string.something_went_wrong),
        fontFamily = FontFamily(Utils.fonts),
        fontSize = 24.sp,
        color = Color.White
    )
    Button(onClick = { viewModel.getMovies() }) {
        Icon(Icons.Default.Refresh, contentDescription = null)
        Text(text = stringResource(R.string.try_again))
    }
}