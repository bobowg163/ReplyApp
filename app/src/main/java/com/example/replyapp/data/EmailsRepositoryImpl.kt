package com.example.replyapp.data

import com.example.replyapp.data.local.LocalEmailsDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2/8/25  6:35 PM
*/

class EmailsRepositoryImpl : EmailsRepository {
    override fun getAllEmails(): Flow<List<Email>> = flow {
        emit(LocalEmailsDataProvider.allEmails)
    }

    override fun getCategoryEmails(category: MailboxType): Flow<List<Email>> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.filter { it.mailbox == category }
        emit(categoryEmails)
    }

    override fun getAllFolders(): List<String> {
        return LocalEmailsDataProvider.getAllFolders()
    }

    override fun getEmailFromId(id: Long): Flow<Email?> = flow {
        val categoryEmails = LocalEmailsDataProvider.allEmails.firstOrNull { it.id == id }
    }

}