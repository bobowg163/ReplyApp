package com.example.replyapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.replyapp.R
import com.example.replyapp.data.Email

/*
项目:ReplyApp
包名：com.example.replyapp.ui.components
作者: bobo
发布日期及时间: 2/10/25 Monday  11:51 PM
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyDockedSearchBar(
    emails: List<Email>,
    onSearchItemSelected: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<Email>() }
    val onExpandedChange: (Boolean) -> Unit = {
        expanded = it
    }

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(
                emails.filter {
                    it.subject.startsWith(
                        prefix = query,
                        ignoreCase = true
                    ) || it.sender.fullName.startsWith(
                        prefix =
                        query,
                        ignoreCase = true
                    )
                }
            )
        }
    }

    DockedSearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    query = it
                },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = stringResource(id = R.string.search_emails)) },
                leadingIcon = {
                    if (expanded) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .clickable {
                                    expanded = false
                                    query = ""
                                },
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search),
                            modifier = Modifier.padding(start = 16.dp),
                        )
                    }
                },
                trailingIcon = {
                    ReplyProfileImage(
                        drawableResource = R.drawable.avatar_6,
                        description = stringResource(id = R.string.profile),
                        modifier = Modifier
                            .padding(12.dp)
                            .size(32.dp)
                    )
                },
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        content = {
            if (searchResults.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items = searchResults, key = { it.id }) { email ->
                        ListItem(
                            headlineContent = { Text(email.subject) },
                            supportingContent = { Text(email.sender.fullName) },
                            leadingContent = {
                                ReplyProfileImage(
                                    drawableResource = email.sender.avatar,
                                    description = stringResource(id = R.string.profile),
                                    modifier = Modifier
                                        .size(32.dp)
                                )
                            },
                            modifier = Modifier.clickable {
                                onSearchItemSelected.invoke(email)
                                query = ""
                                expanded = false
                            }
                        )
                    }
                }
            } else if (query.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_item_found),
                    modifier = Modifier.padding(16.dp)
                )
            } else
                Text(
                    text = stringResource(id = R.string.no_search_history),
                    modifier = Modifier.padding(16.dp)
                )
        }
    )
}
