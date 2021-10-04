package com.snoy.composeshopper

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.snoy.composeshopper.cart.CartScreen
import com.snoy.composeshopper.cart.CartViewModelFactory
import com.snoy.composeshopper.catalog.CatalogScreen

//ref= https://google.github.io/accompanist/navigation-animation/

@ExperimentalAnimationApi
@Composable
fun ShopperApp() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = "catalog") {
        composable(
            route = "catalog"
        ) {
            CatalogScreen(onCartClick = {
                navController.navigate(route = "cart")
            })
        }
        composable("cart",
            enterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { 1080 }, animationSpec = tween(500))
            },
            exitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { -1080 }, animationSpec = tween(500))
            },
            popEnterTransition = { _, _ ->
                slideInHorizontally(initialOffsetX = { -1080 }, animationSpec = tween(500))
            },
            popExitTransition = { _, _ ->
                slideOutHorizontally(targetOffsetX = { 1080 }, animationSpec = tween(500))
            }) {
            CartScreen(viewModel = viewModel(factory = CartViewModelFactory()), onBackPress = {
                navController.popBackStack()//.navigate(route = "catalog")
            })
        }
    }
}