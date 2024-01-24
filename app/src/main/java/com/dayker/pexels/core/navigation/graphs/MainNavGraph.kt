package com.dayker.pexels.core.navigation.graphs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.dayker.pexels.R
import com.dayker.pexels.core.navigation.graphs.Graph.MAIN_GRAPH_ROUTE
import com.dayker.pexels.core.navigation.navanimations.intoLeftAnimation
import com.dayker.pexels.core.navigation.navanimations.intoRightAnimation
import com.dayker.pexels.core.navigation.navanimations.outLeftAnimation
import com.dayker.pexels.core.navigation.navanimations.outRightAnimation
import com.dayker.pexels.core.navigation.navanimations.scaleInAnimation
import com.dayker.pexels.core.navigation.navanimations.scaleOutAnimation
import com.dayker.pexels.presentation.bookmarks.BookmarksScreen
import com.dayker.pexels.presentation.details.DetailsScreen
import com.dayker.pexels.presentation.home.HomeScreen
import com.dayker.pexels.presentation.profile.ProfileScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    windowSize: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    navigation(
        route = MAIN_GRAPH_ROUTE,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable(
            route = NavigationBarScreen.Home.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Profile.route -> {
                        intoLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Profile.route -> {
                        outRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(
            route = NavigationBarScreen.Favorites.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Home.route -> {
                        intoRightAnimation()
                    }

                    NavigationBarScreen.Profile.route -> {
                        intoLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Home.route -> {
                        outLeftAnimation()
                    }

                    NavigationBarScreen.Profile.route -> {
                        outRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            BookmarksScreen(
                modifier = modifier,
                navController = navController,
            )
        }
        composable(
            route = NavigationBarScreen.Profile.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Home.route -> {
                        intoRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Favorites.route, NavigationBarScreen.Home.route -> {
                        outLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            ProfileScreen(
                modifier = modifier,
                navController = navController,
                windowSize = windowSize
            )
        }
        composable(
            route = DetailsScreen.DETAILS_ROUTE + "?${DetailsScreen.IMAGE_ID_PARAM}={${DetailsScreen.IMAGE_ID_PARAM}}&${DetailsScreen.IS_IMAGE_CURATED_PARAM}={${DetailsScreen.IS_IMAGE_CURATED_PARAM}}&${DetailsScreen.IS_IMAGE_BOOKMARK}={${DetailsScreen.IS_IMAGE_BOOKMARK}}",
            arguments = listOf(
                navArgument(
                    name = DetailsScreen.IMAGE_ID_PARAM
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = DetailsScreen.IS_IMAGE_CURATED_PARAM
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument(
                    name = DetailsScreen.IS_IMAGE_BOOKMARK
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            enterTransition = {
                scaleInAnimation()
            },
            popEnterTransition = {
                scaleInAnimation()
            },
            exitTransition = {
                scaleOutAnimation()
            },
            popExitTransition = {
                scaleOutAnimation()
            }
        ) {
            DetailsScreen(navController = navController)
        }
    }
}

sealed class NavigationBarScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val inactiveIcon: Int,
    @DrawableRes val activeIcon: Int,
) {
    object Home : NavigationBarScreen(
        route = "home",
        title = R.string.home,
        activeIcon = R.drawable.picked_home,
        inactiveIcon = R.drawable.home,
    )

    object Favorites : NavigationBarScreen(
        route = "favorites",
        title = R.string.favorites,
        activeIcon = R.drawable.picked_favorites,
        inactiveIcon = R.drawable.favorites,
    )

    object Profile : NavigationBarScreen(
        route = "profile",
        title = R.string.profile,
        activeIcon = R.drawable.profile_icon,
        inactiveIcon = R.drawable.person_icon_outlined,
    )

    companion object {
        fun getAllNavigationScreens(): List<NavigationBarScreen> {
            return listOf(Home, Favorites, Profile)
        }
    }
}


object DetailsScreen {
    const val DETAILS_ROUTE = "details_route"
    const val IMAGE_ID_PARAM = "movie_id"
    const val IS_IMAGE_CURATED_PARAM = "image_curated"
    const val IS_IMAGE_BOOKMARK = "image_bookmark"
}