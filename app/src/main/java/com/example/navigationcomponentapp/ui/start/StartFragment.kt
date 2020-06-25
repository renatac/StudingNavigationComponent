package com.example.navigationcomponentapp.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.navigationcomponentapp.R

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    //porque eu não posso instanciar um Fragment diretamente
    companion object{
        fun returnInstance(): StartFragment{
            return  StartFragment()
        }
    }
}
