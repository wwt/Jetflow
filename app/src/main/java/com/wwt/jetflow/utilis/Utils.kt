package com.wwt.jetflow.utilis

fun String.singleLine(separator: String = " ") = lineSequence()
    .map { it.trim() }
    .filter { it.isNotBlank() }
    .joinToString(separator)