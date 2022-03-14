package com.wwt.jetflow.utilis

import android.content.Context

class ResourcesHelper(
    private val applicationContext: Context
) {
    fun getStringResource(id: Int): String {
        return applicationContext.getString(id)
    }

    fun getStringResource(id: Int, value: String): String {
        return applicationContext.getString(id, value)
    }

    fun getStringResource(id: Int, value1: String, value2: String): String {
        return applicationContext.getString(id, value1, value2)
    }
}