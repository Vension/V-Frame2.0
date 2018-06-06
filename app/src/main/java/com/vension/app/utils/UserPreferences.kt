package com.vension.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.vension.app.MyApplication


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/22 9:51
 * 描  述：用户配置信息
 * ========================================================
 */

object UserPreferences {

    private const val NAME = "andy."
    private const val KEY_IS_USER_LOGIN = "is_user_login"

    /**
     * 保存用户是否登录配置
     */
    fun saveUserIsLogin(isUserLogin: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(KEY_IS_USER_LOGIN, isUserLogin)
        editor.apply()
    }

    /**
     * 获取用户是否登录
     */
    fun getUserIsLogin() = getSharedPreferences().getBoolean(KEY_IS_USER_LOGIN, false)


    private fun getSharedPreferences(): SharedPreferences {
        return MyApplication.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

}