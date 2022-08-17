package com.rbt.merchant.utils

import com.rbt.merchant.BuildConfig

/**
 * HttpRoutes to reach the server base url that stored from app config
*/
object HttpRoutes {
    const val BASE_URL_API: String = BuildConfig.SERVER_HOST
}