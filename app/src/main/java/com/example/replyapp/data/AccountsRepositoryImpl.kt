package com.example.replyapp.data

import kotlinx.coroutines.flow.Flow

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2025/2/6  10:05
*/
class AccountsRepositoryImpl:AccountsRepository {
    override fun getDefaultUserAccount(): Flow<Account> {
        TODO("Not yet implemented")
    }

    override fun getAllUserAccounts(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override fun getContactAccountByUid(uid: Long): Flow<Account> {
        TODO("Not yet implemented")
    }
}