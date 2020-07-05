package com.example.navigationcomponentapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponentapp.R

//Obs que esse LoginViewModel foi criado ao eu criar o Fragment,
//escolhi a opção de criar já com Viewmodel

class LoginViewModel : ViewModel() {

    //Para representar o estado do meu usuário, se é logado ou não
    //Coloco aqui dentro todos os estados que o usuário pode ter caso o login estiver errado, estiver certo
    sealed class AuthenticationState(){                //<username,password>
        //Estrutura de List para retornar os erros para poder ter facilidade no fragment para
        //pode mapear os erros, fazendo um forEach e mapear e setar no campo correto, em vez de
        //fazer isso de forma manual, tendo que ter um liveData pra cada um dos componentes do
        //layout que eu quisesse mostrar o erro. Do jeito que tá faço uma validação só
        //pra todos os componentes
        class InvalidAuthentication(val fields: List<Pair<String, Int>>)
            : AuthenticationState() //herança
        object Authenticated //é object pq não recebo parâmetro na construção do objeto
            : AuthenticationState() //herança
        object Unauthenticated //é object pq não recebo parâmetro na construção do objeto
            : AuthenticationState() //herança
    }

    //Obs que o MutableLiveData ser do tipo AuthenticationState ajuda, pois se eu tivesse
    //várias propriedades, então eu teria que ter vários observers no LoginFragment
    val authenticationStateEvent = MutableLiveData<AuthenticationState>()

    //Assim que ele criar meu Fragment
    init {
        authenticationStateEvent.value = AuthenticationState.Unauthenticated
    }

    fun authentication(username: String, password: String){
        if(isValidForm(username, password)){
            //Usuário está authenticado
            authenticationStateEvent.value = AuthenticationState.Authenticated
        }
    }

    //validação e gerenciamento de estado no viewModel
    private fun isValidForm(username: String, password: String): Boolean{
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if(username.isEmpty()){
            invalidFields.add(INPUT_USERNAME)
        }
        if(password.isEmpty()){
            invalidFields.add(INPUT_PASSWORD)
        }
        if(invalidFields.isNotEmpty()){
            authenticationStateEvent.value = AuthenticationState.InvalidAuthentication(invalidFields)
            return false
        }
        return true
    }

    companion object{
        //criando par chave e valor
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.login_input_layout_error_invalid_username
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.login_input_layout_error_invalid_password
    }


}
