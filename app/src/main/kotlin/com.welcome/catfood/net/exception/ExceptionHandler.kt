package com.welcome.catfood.net.exception

import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import com.welcome.catfood.config.Config
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * <pre>
 *     author : wxq
 *     e-mail :
 *     time   : 2018/11/27
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ExceptionHandler {
    companion object {

        @JvmField
        val SUCCESS = 0

        @JvmField
        val UNKOWN_ERROR = 1000

        @JvmField
        val SERVER_ERROR = 1001

        @JvmField
        val NETWORK_ERROR = 1002

        @JvmField
        val PARSE_ERROR = 1003

        fun handleException(e: Throwable): ResponeThrowable {
            var errCode = UNKOWN_ERROR
            var errMessage = "未知错误，请稍后再试~"

            if (e is SocketTimeoutException || e is ConnectException) {
                Logger.e(Config.TAG, "网络连接异常:" + e.message)
                errCode = NETWORK_ERROR
                errMessage = "网络连接异常"
            } else if (e is JsonParseException ||
                e is JSONException ||
                e is ParseException
            ) {
                Logger.e(Config.TAG, "数据解析异常:" + e.message)
                errCode = PARSE_ERROR
                errMessage = "数据解析异常"
            } else if (e is UnknownHostException) {
                Logger.e(Config.TAG, "网络连接异常:" + e.message)
                errCode = NETWORK_ERROR
                errMessage = "网络连接异常"
            } else if (e is IllegalArgumentException) {
                Logger.e(Config.TAG, "参数异常:" + e.message)
                errCode = SERVER_ERROR
                errMessage = "参数异常"
            } else {
                try {
                    Logger.e(Config.TAG, "异常:" + e.message)
                } catch (e: Exception) {
                    Logger.e(Config.TAG, "未知异常")
                }
            }
            return ResponeThrowable(errCode, errMessage)
        }
    }
}