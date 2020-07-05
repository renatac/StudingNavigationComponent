package com.example.navigationcomponentapp.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.navigationcomponentapp.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    //Não preciso, pois a navegação será controlado e criação
    //dos Fragmentos é gerenciada pelo componente de navegação
//    companion object {
//        fun newInstance() = LoginFragment()
//    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    //Quando minha view já estiver criada, todos os meus componentes já estiverem inflados
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        //(Livecycler onwer e um Observer)
       viewModel.authenticationStateEvent.observe(viewLifecycleOwner, Observer { authenticationState->
            when(authenticationState){
                is LoginViewModel.AuthenticationState.invalidAuthentication -> {
                    //Poderia tb usar o AppCompatEditText
                    val validationFields: Map<String, TextInputLayout> = initValidationFields()
                    //como invalidAuthentication retorna os campos que estão inválidos:
                    authenticationState.fields.forEach { fieldError ->
                        //É o inputLayoutLoginUsername
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }

       })

        buttonLoginSignIn.setOnClickListener {
            val username = inputLoginUsername.text.toString()
            val password = inputLoginPassword.text.toString()
            viewModel.authentication(username, password)
        }
    }

    private fun initValidationFields() = mapOf(
            //first aqui é a chave do companion object que tá no LoginViewModel
            LoginViewModel.INPUT_USERNAME.first to inputLayoutLoginUsername,
            LoginViewModel.INPUT_PASSWORD.first to inputLayoutLoginPassword
            //Faço isso
    )
}
