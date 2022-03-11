package com.wwt.jetflow.welcome

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * In many cases Compose can detect stability/immutability by itself. In some cases you may
 * choose to mark it explicitly.
 */
@Stable
sealed class NavHostDestination : Parcelable {



    @Immutable
    @Parcelize
    object Login : NavHostDestination()

    @Immutable
    @Parcelize
    data class ServiceType(val email: String ) : NavHostDestination()





}

interface AcceptsResultFromFifth {
    val resultFromFifth: MutableState<String?>
}