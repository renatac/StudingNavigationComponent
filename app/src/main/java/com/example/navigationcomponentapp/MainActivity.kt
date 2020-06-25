package com.example.navigationcomponentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigationcomponentapp.ui.start.StartFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.myContainer, StartFragment.returnInstance())
            .commit()  //aqui de fato ele coloca meu Fragment dentro do FrameLayout

    }


}
