package com.fabianospdev.volunteerscompose.features.login.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode


fun isRunningRoboletric():Boolean {
    return try {
        Class.forName("org.robolectric.Robolectric") != null
        true
    } catch (_: Throwable){
        false
    }
}
