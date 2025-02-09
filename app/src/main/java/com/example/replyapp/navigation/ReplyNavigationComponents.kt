package com.example.replyapp.navigation

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

/*
项目:ReplyApp
包名：com.example.replyapp.navigation
作者: bobo
发布日期及时间: 2/9/25 Sunday  8:52 PM
*/

private fun WindowSizeClass.isCompact() =
    windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
            windowHeightSizeClass == WindowHeightSizeClass.COMPACT

class ReplyNavSuiteScope(
    val navSuiteType:NavigationSuiteType
)


@Composable
fun ReplyNavigationWrapper() {

}