package com.elearning.rekamiacademy.util

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController

object NavHelper {
    fun Fragment.safeNavigate(navDirection : NavDirections?,fragmentHostName : String){
        val navController = findNavController()
        when(val destination = navController.currentDestination){
            is FragmentNavigator.Destination -> {
                if(fragmentHostName == destination.className){
                    navController.navigate(navDirection!!)
                }
            }
            is DialogFragmentNavigator.Destination -> {
                if(fragmentHostName == destination.className){
                    navController.navigate(navDirection!!)
                }
            }
        }
    }
}