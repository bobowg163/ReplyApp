package com.example.replyapp.data

import kotlinx.coroutines.flow.Flow

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2/8/25  6: 29
*/
interface EmailsRepository {
    fun getAllEmails():Flow<List<Email>>
    fun getCategoryEmails(category:MailboxType):Flow<List<Email>>
    fun getAllFolders():List<String>
    fun getEmailFromId(id:Long):Flow<Email?>
}