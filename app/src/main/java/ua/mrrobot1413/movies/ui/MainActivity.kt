package ua.mrrobot1413.movies.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ua.mrrobot1413.movies.misc.Constants.DETAILED
import ua.mrrobot1413.movies.misc.Constants.HOME
import ua.mrrobot1413.movies.misc.Constants.ID
import ua.mrrobot1413.movies.misc.Constants.LIST_TYPE
import ua.mrrobot1413.movies.misc.Constants.MAIN
import ua.mrrobot1413.movies.misc.Constants.SEARCH
import ua.mrrobot1413.movies.misc.Constants.TYPE
import ua.mrrobot1413.movies.misc.Constants.VIEW_ALL
import ua.mrrobot1413.movies.ui.detailed.DetailedScreen
import ua.mrrobot1413.movies.ui.home.HomeScreen
import ua.mrrobot1413.movies.ui.search.SearchScreen
import ua.mrrobot1413.movies.ui.theme.MoviesTheme
import ua.mrrobot1413.movies.ui.viewAll.ListType
import ua.mrrobot1413.movies.ui.viewAll.ViewAllScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = MAIN) {
                    navigation(
                        startDestination = HOME,
                        route = MAIN
                    ) {
                        composable(
                            HOME
                        ) {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            "${DETAILED}/{${ID}}",
                            arguments = listOf(navArgument(ID) { type = NavType.IntType })
                        ) {
                            DetailedScreen(
                                navController = navController,
                                id = it.arguments?.getInt(ID) ?: 1
                            )
                        }
                        composable(
                            "${VIEW_ALL}/{${TYPE}}",
                            arguments = listOf(navArgument(TYPE) { type = NavType.IntType })
                        ) {
                            ViewAllScreen(
                                navController = navController,
                                type = it.arguments?.getInt(TYPE) ?: 1
                            )
                        }
                        composable(
                            SEARCH
                        ) {
                            SearchScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}