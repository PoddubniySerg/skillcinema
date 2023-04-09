package ru.skillbox.core.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import ru.skillbox.core.R

fun Fragment.navigateTo(
    actionId: Int,
    hostId: Int? = null,
    data: Bundle? = null,
    options: NavOptions? = null
) {
    val navController =
        if (hostId == null) {
            Navigation.findNavController(requireActivity(), R.id.app_nav_host)
        } else {
            Navigation.findNavController(requireActivity(), hostId)
        }

    navController.navigate(actionId, data, options)
}