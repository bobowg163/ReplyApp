package com.example.replyapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.replyapp.R
import kotlinx.serialization.Serializable

/*
项目:ReplyApp
包名：com.example.replyapp.navigation
作者: bobo
发布日期及时间: 2/8/25 Saturday  11:21 PM
*/

sealed interface Route {
    @Serializable
    data object Inbox : Route
    @Serializable
    data object Articles : Route
    @Serializable
    data object DirectMessages : Route
    @Serializable
    data object Groups : Route
}

data class ReplyTopLevelDestination(
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class ReplyNavigationActions(
    private val navController: NavHostController
) {
    fun navigateTo(destination: ReplyTopLevelDestination){
        navController.navigate(destination.route){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    ReplyTopLevelDestination(
        route = Route.Inbox,
        selectedIcon = Icons.Default.Inbox,
        unselectedIcon = Icons.Default.Inbox,
        iconTextId = R.string.tab_inbox
    ),

    ReplyTopLevelDestination(
        route = Route.Articles,
        selectedIcon = Icons.AutoMirrored.Filled.Article,
        unselectedIcon = Icons.AutoMirrored.Filled.Article,
        iconTextId = R.string.tab_article
    ),

    ReplyTopLevelDestination(
        route = Route.DirectMessages,
        selectedIcon = Icons.Outlined.ChatBubbleOutline,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconTextId = R.string.tab_inbox
    ),

    ReplyTopLevelDestination(
        route = Route.Groups,
        selectedIcon = Icons.Default.People,
        unselectedIcon = Icons.Default.People,
        iconTextId = R.string.tab_article
    ),

)