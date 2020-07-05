package com.example.navigationcomponentapp.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.navigationcomponentapp.R

private val navOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_rigth)
    .build()

//Para usar Extension em kotlin: fun Class que desejo extender ponto nome da fun
fun NavController.navigateWithAnimations(destinationId : Int){
    this.navigate(destinationId, null, navOptions)
}
