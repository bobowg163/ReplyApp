package com.example.replyapp.data

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2025/2/6  09:40
*/
data class Email(
    val id:Long,
    val sender:Account,
    val recipients:List<Account> = emptyList(),
    val subject:String,
    val body:String,
    val attachments:List<EmailAttachment> = emptyList(),
    var isImportant:Boolean = false,
    var isStarred:Boolean = false,
    var mailbox: MailboxType = MailboxType.INBOX,
    val createdAt:String,
    val threads:List<Email> = emptyList()
)
