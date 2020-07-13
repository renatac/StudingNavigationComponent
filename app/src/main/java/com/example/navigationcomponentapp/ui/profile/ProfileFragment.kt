package com.example.navigationcomponentapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.navigationcomponentapp.R
import com.example.navigationcomponentapp.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

//    No Android Architecture Components do Livecycle, compartilhar dados entre as activities utilizando um ViewModel,
//    usando a vinculação de um ViewModel a uma activity, ou seja, a um escopo , que é a activity e com isso
//    conseguimos ter acesso a esse mesmo ViewModel entre os fragmentos do App.
//    LoginFragment e ProfileFragment e irei compartilhar os dados de um ViewModel com esses dois Fragmentos,
//    usando o conceito de ViewModel compartilhado com ViewModel StoreOwner, que a activity que implementará
//    esse cara e ela consegue guardar no escopo dela a referência do ViewModel para no caso quando eu precisar
//    do ViewModel nos Fragmentos podemos chamar de forma prática para poder utilizar o ViewModel.

    //Se o ProfileFragment chamar o by activityViewModels do LoginViewModel
    //e esse LoginViewModel já tiver sido criado, eu recebo a mesma instância,
    //se não o crio e o coloco para o escopo da activity
    private val loginViewModel: LoginViewModel by activityViewModels()
    //com o activityViewModels(), o Framework do android, o livecycle faz a
    //criação do viewModel. E o escopo dele é o minha activity.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    //Quando toda a minha hierarquia de view do meu layout estiver criada:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.authenticationStateEvent.observe(viewLifecycleOwner, Observer {
            authenticationState->
            when(authenticationState){
                //Verifico se o usuário está logado
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    textProfileUsername.text =
                            getString(R.string.profile_text_username, loginViewModel.username)
                }
                is LoginViewModel.AuthenticationState.Unauthenticated->{
                    //vá pra login
                    findNavController().navigate(R.id.loginFragment)
                }
            }

        })
    }

//    //para ter a função de forma estática
//    companion object {
//        fun returnInstance(): ProfileFragment {
//            return ProfileFragment()
//        }
//    }
}
