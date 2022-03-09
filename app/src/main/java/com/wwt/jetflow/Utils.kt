package com.wwt.jetflow

fun String.singleLine(separator: String = " ") = lineSequence()
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .joinToString(separator)