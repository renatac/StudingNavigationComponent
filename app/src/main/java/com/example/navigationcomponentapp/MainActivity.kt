package com.example.navigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.navigationcomponentapp.ui.start.ProfileFragment
import com.example.navigationcomponentapp.ui.start.StartFragment
import kotlinx.android.synthetic.main.activity_main.*

//StartFragment.onButtonClicked é a interface que está dentro do StartFragment
//MainActivity implementa essa interface
class MainActivity : AppCompatActivity(){ //, StartFragment.onButtonClicked {

    private lateinit var navController : NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                setSupportActionBar(myToolbar)
                navController = findNavController(R.id.navHostFragment)
                appBarConfiguration = AppBarConfiguration(navController.graph) //pôr no gradle.app:
                // kotlinOptions {
                //   jvmTarget = "1.8"            //dentro de android{...}
                //}
                setupActionBarWithNavController(navController, appBarConfiguration)//do AppBarConfiguration

//        supportFragmentManager.beginTransaction()
//            .add(R.id.myContainer, StartFragment.returnInstance())
//            .commit()  //aqui de fato ele coloca meu Fragment dentro do FrameLayout
    }

    override fun onSupportNavigateUp(): Boolean {
       return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        //Se for fragment ou se for activity
    }

//    override fun buttonClicked() {
//        //Fazendo a transição entre fragmentos
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.myContainer, ProfileFragment.returnInstance())
//            .commit()
//    }
}
