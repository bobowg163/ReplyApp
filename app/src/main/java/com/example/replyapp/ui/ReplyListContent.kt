package com.example.replyapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.example.replyapp.R
import com.example.replyapp.data.Email
import com.example.replyapp.data.local.LocalEmailsDataProvider
import com.example.replyapp.model.ReplyHomeUIState
import com.example.replyapp.ui.components.EmailDetailAppBar
import com.example.replyapp.ui.components.ReplyDockedSearchBar
import com.example.replyapp.ui.components.ReplyEmailListItem
import com.example.replyapp.ui.components.ReplyEmailThreadItem
import com.example.replyapp.ui.theme.ReplyAppTheme
import com.example.replyapp.ui.utils.ReplyContentType
import com.example.replyapp.ui.utils.ReplyNavigationType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

/*
项目:ReplyApp
包名：com.example.replyapp.ui
作者: bobo
发布日期及时间: 2/10/25 Monday  6:45 PM
*/

@Composable
fun ReplyInboxScreen(
    contentType: ReplyContentType,
    replyHomeUIState: ReplyHomeUIState,
    navigationType: ReplyNavigationType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    toggleSelectedEmail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = contentType) {
        if (contentType == ReplyContentType.SINGLE_PANE && !replyHomeUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    val emailLazyListState = rememberLazyListState()
    if (contentType == ReplyContentType.DUAL_PANE) {
        TwoPane(
            first = {
                ReplyEmailList(
                    emails = replyHomeUIState.emails,
                    openedEmail = replyHomeUIState.openedEmail,
                    selectedEmail = replyHomeUIState.selectedEmails,
                    toggleSelectedEmail = toggleSelectedEmail,
                    emailLazyListState = emailLazyListState,
                    navigateToDetail = navigateToDetail,
                )
            },
            second = {
                ReplyEmailDetail(
                    email = replyHomeUIState.openedEmail ?: replyHomeUIState.emails.first(),
                    isFullScreen = false,
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            ReplySinglePaneContent(
                replyHomeUIState = replyHomeUIState,
                toggleEmailSelection = toggleSelectedEmail,
                emailLazyListState = emailLazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            )
            if (navigationType == ReplyNavigationType.BOTTOM_NAVIGATION) {
                ExtendedFloatingActionButton(
                    text = {
                        Text(
                            text = stringResource(id = R.string.compose),
                        )
                    },
                    icon = {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.compose)
                        )
                    },
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    expanded = emailLazyListState.lastScrolledBackward || !emailLazyListState.lastScrolledBackward

                )
            }
        }
    }
}

@Composable
fun ReplySinglePaneContent(
    replyHomeUIState: ReplyHomeUIState,
    toggleEmailSelection: (Long) -> Unit,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, ReplyContentType) -> Unit
) {
    if (replyHomeUIState.openedEmail != null && replyHomeUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        ReplyEmailDetail(
            email = replyHomeUIState.openedEmail
        ) {
            closeDetailScreen()
        }
    } else {
        ReplyEmailList(
            emails = replyHomeUIState.emails,
            openedEmail = replyHomeUIState.openedEmail,
            selectedEmail = replyHomeUIState.selectedEmails,
            toggleSelectedEmail = toggleEmailSelection,
            emailLazyListState = emailLazyListState,
            navigateToDetail = navigateToDetail,
            modifier = modifier
        )
    }
}

@Composable
fun ReplyEmailDetail(
    email: Email,
    modifier: Modifier = Modifier,
    isFullScreen: Boolean = true,
    onBackPress: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        item {
            EmailDetailAppBar(email, isFullScreen) {
                onBackPress()
            }
        }
        items(items = email.threads, key = { it.id }) { email ->
            ReplyEmailThreadItem(email = email)
        }
        item {
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@Composable
fun ReplyEmailList(
    emails: List<Email>,
    openedEmail: Email?,
    selectedEmail: Set<Long>,
    toggleSelectedEmail: (Long) -> Unit,
    emailLazyListState: LazyListState,
    navigateToDetail: (Long, ReplyContentType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        ReplyDockedSearchBar(
            emails = emails,
            onSearchItemSelected = { searchedEmail ->
                navigateToDetail(searchedEmail.id, ReplyContentType.SINGLE_PANE)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            state = emailLazyListState
        ) {
            items(items = emails, key = { it.id }) { email ->
                ReplyEmailListItem(
                    email = email,
                    navigateToDetail = { emailId ->
                        navigateToDetail(emailId, ReplyContentType.SINGLE_PANE)
                    },
                    toggleSelection = toggleSelectedEmail,
                    isSelected = selectedEmail.contains(email.id),
                    isOpened = openedEmail?.id == email.id
                )
            }
            item {
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReplySinglePaneContentPreview() {
    ReplyAppTheme {
        ReplySinglePaneContent(
            replyHomeUIState = ReplyHomeUIState(emails = LocalEmailsDataProvider.allEmails),
            toggleEmailSelection = {},
            emailLazyListState = rememberLazyListState(),
            closeDetailScreen = {},
            navigateToDetail = { _, _ -> }
        )
    }
}

