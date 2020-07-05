package com.example.navigationcomponentapp.ui.start

//import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.navigationcomponentapp.R
import com.example.navigationcomponentapp.extensions.navigateWithAnimations
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    //propriedade pra o meu listner
//    private lateinit var listner: onButtonClicked

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    //criei para eu ter acesso aos meus componentes de view
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //kotlin extensions que vem padrão no gradle, utilizando synthetic
        buttonNext.setOnClickListener {
//           listner.buttonClicked() // método da main activity que troca o fragment
         //Tenho que recuperar o NavController
//            findNavController().navigate(R.id.action_startFragment_to_profileFragment)// recomendado utilizar o id da ação, só usar
            //o id da tela qd for pra outra tela que tá fora desse grafo
            findNavController().navigateWithAnimations(R.id.action_startFragment_to_profileFragment)
        }
    }

    //criei
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is onButtonClicked) //como o context é a minha activity e se ela implementar essa interface onButtonClicked
//        {
//           listner = context
//        }
//    }

    //porque eu não posso instanciar um Fragment diretamente
//    companion object{
//        fun returnInstance(): StartFragment{
//            return  StartFragment()
//        }
//    }

//    interface onButtonClicked{
//        fun buttonClicked()
//    }
}
