package com.example.sepiatest.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object AssertsManager {

    fun openStringFile(fileName: String, context: Context): String {
        return try {
            val reader = context.assets.open(fileName).bufferedReader()

            reader.useLines { sequence: Sequence<String> ->
                sequence.reduce { a, b -> "$a\n$b" }
            }
        } catch (e: Exception) {
            Timber.e("AssertsManager - ${e.message}")
            ""
        }
    }

    fun <T> convertJsonStringToObject(jsonString: String, clazz: Class<T>): T =
        Gson().fromJson(jsonString, clazz)

    fun getJsonStreamReader(file: String?, context: Context): InputStreamReader? {
        var reader: InputStreamReader? = null
        try {
            val `in`: InputStream? = file?.let { context.assets.open(it) }
            reader = InputStreamReader(`in`)
        } catch (ioe: IOException) {
            Log.e("launch", "error : $ioe")
        }
        return reader
    }

    fun loadJsonFile(name: String): String {
        return File("src/main/assets/$name")
            .inputStream()
            .readBytes()
            .toString(Charsets.UTF_8)
    }
}
