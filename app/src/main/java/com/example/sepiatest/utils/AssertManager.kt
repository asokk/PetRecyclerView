package com.example.sepiatest.utils

import android.app.Application

object AssertManager {
    private fun loadJson(filename: String, context: Application) =
        AssertsManager.openStringFile(filename, context)
}