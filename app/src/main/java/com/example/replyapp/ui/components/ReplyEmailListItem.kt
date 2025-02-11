package com.example.replyapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.replyapp.data.Email

/*
项目:ReplyApp
包名：com.example.replyapp.ui.components
作者: bobo
发布日期及时间: 2/11/25 Tuesday  10:44 AM
*/

@Composable
fun ReplyEmailListItem(
    email: Email,
    navigateToDetail: (Long) -> Unit,
    toggleSelection: (Long) -> Unit,
    modifier: Modifier = Modifier,
    isOpened: Boolean = false,
    isSelected: Boolean = false,
) {

}
