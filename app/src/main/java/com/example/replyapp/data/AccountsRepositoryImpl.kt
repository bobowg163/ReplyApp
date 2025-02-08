package com.example.replyapp.data

import com.example.replyapp.data.local.LocalAccountsDataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2025/2/6  10:05
*/
class AccountsRepositoryImpl:AccountsRepository {
    override fun getDefaultUserAccount(): Flow<Account>  = flow {
        emit(LocalAccountsDataProvider.getDefaultUserAccount())
    }

    override fun getAllUserAccounts(): Flow<List<Account>> = flow {
        emit(LocalAccountsDataProvider.allUserAccounts)
    }

    override fun getContactAccountByUid(uid: Long): Flow<Account> = flow {
        emit(LocalAccountsDataProvider.getContactAccountByUid(uid))
    }
}