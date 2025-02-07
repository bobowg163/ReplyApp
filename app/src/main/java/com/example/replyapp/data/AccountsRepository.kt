package com.example.replyapp.data

import kotlinx.coroutines.flow.Flow

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2025/2/6  10:01
*/
interface AccountsRepository {
    fun getDefaultUserAccount(): Flow<Account>
    fun getAllUserAccounts(): Flow<List<Account>>
    fun getContactAccountByUid(uid: Long): Flow<Account>
}