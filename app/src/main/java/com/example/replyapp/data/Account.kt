package com.example.replyapp.data

import androidx.annotation.DrawableRes

/*
项目:ReplyApp
包名：com.example.replyapp.data
作者: bobo
发布日期及时间: 2025/2/6  09:36
*/
data class Account(
    val id: Long,
    val uid: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val altEmail:String,
    @DrawableRes val avatar: Int,
    var isCurrentAccount:Boolean = false
){
    val fullName:String = "$firstName $lastName"
}
