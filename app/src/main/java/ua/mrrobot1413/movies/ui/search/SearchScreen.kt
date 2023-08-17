package ua.mrrobot1413.movies.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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
import ua.mrrobot1413.movies.ui.home.Lists
import ua.mrrobot1413.movies.ui.theme.Accent
import ua.mrrobot1413.movies.ui.theme.MainTextColor
import ua.mrrobot1413.movies.ui.theme.Primary
import ua.mrrobot1413.movies.ui.theme.Secondary
import ua.mrrobot1413.movies.ui.viewAll.ListType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

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
                    text = stringResource(R.string.search),
                    fontFamily = FontFamily(Utils.fonts),
                    fontWeight = FontWeight.W600,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Accent
            ),
        )
        SearchView(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp), viewModel = viewModel)
        List(state = state.value, navController = navController, viewModel = viewModel)
    }
}

@Composable
fun List(modifier: Modifier = Modifier, state: SearchScreenState, viewModel: SearchViewModel, navController: NavController) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
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
                        viewModel.searchMovies()
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
                            .align(Alignment.BottomCenter)
                    ),
                    color = Accent
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel
) {
    val searchText = remember {
        mutableStateOf(TextFieldValue())
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp))
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        value = searchText.value,
        maxLines = 1,
        singleLine = true,
        onValueChange = {
            searchText.value = it
            viewModel.query = it.text
            viewModel.searchMovies()
        },
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