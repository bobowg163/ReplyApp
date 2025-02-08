package com.example.replyapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.replyapp.data.Email
import com.example.replyapp.data.EmailsRepository
import com.example.replyapp.data.EmailsRepositoryImpl
import com.example.replyapp.ui.utils.ReplyContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/*
项目:ReplyApp
包名：com.example.replyapp.model
作者: bobo
发布日期及时间: 2/8/25 Saturday  10:35 PM
*/
class ReplyHomeViewModel(private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()) :
    ViewModel() {
    private val _uiState = MutableStateFlow(ReplyHomeUIState(loading = true))
    val uiState: StateFlow<ReplyHomeUIState> = _uiState

    init {
        observeEmails()
    }

    private fun observeEmails() {
        viewModelScope.launch {
            emailsRepository.getAllEmails().catch { ex ->
                _uiState.value = ReplyHomeUIState(error = ex.message)
            }.collect { emails ->
                _uiState.value = ReplyHomeUIState(emails = emails, openedEmail = emails.first())
            }
        }
    }

    fun setOpenedEmail(emailId: Long, contentType: ReplyContentType) {
        val email = uiState.value.emails.find { it.id == emailId }
        _uiState.value = _uiState.value.copy(
            openedEmail = email,
            isDetailOnlyOpen = contentType == ReplyContentType.SINGLE_PANE
        )
    }

    fun toggleSelectedEmail(emailId: Long) {
        val currentSelection = uiState.value.selectedEmails
        _uiState.value = _uiState.value.copy(
            selectedEmails = if (currentSelection.contains(emailId)) currentSelection.minus(emailId)
            else currentSelection.plus(
                emailId
            )
        )
    }

    fun closeDetailScreen() {
        _uiState.value = _uiState.value.copy(
            isDetailOnlyOpen = false,
            openedEmail = _uiState.value.emails.first()
        )
    }
}

data class ReplyHomeUIState(
    val emails: List<Email> = emptyList(),
    val selectedEmails: Set<Long> = emptySet(),
    val openedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)