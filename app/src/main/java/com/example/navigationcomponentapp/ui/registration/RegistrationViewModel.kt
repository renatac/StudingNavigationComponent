package com.example.navigationcomponentapp.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponentapp.R
import com.example.navigationcomponentapp.ui.login.LoginViewModel.Companion.INPUT_PASSWORD
import com.example.navigationcomponentapp.ui.login.LoginViewModel.Companion.INPUT_USERNAME

class RegistrationViewModel : ViewModel() {

    //Para representar o estado do registro que será usado tanto pelo
    //ChooseCredentialsFragment quanto pelo ProfileDataFragment
    sealed class RegistrationState{
        object CollectProfileData : RegistrationState()
        object CollectCredentials : RegistrationState()
        object RegistrationCompleted : RegistrationState()
        class InvalidProfiledata(val fields: List<Pair<String, Int>>):
                RegistrationState()
        class InvalidCredentials(val fields: List<Pair<String, Int>>):
                RegistrationState()
    }

    private val _registrationStateEvent = MutableLiveData<RegistrationState>(RegistrationState.CollectProfileData)
    val registrationStateEvent: LiveData<RegistrationState> //LiveData não é mutável, não é alterado
        get() = _registrationStateEvent // Ele aponta para o _registrationStateEvent que só é acessado dentro do ViewModel e ele sim é mutável

    var authToken = ""
        private set

    //Código que o ProfileDataFragment irá utilizar

    fun collectProfileData(name: String, bio: String){
        if(isValidProfileData(name, bio)){
            _registrationStateEvent.value = RegistrationState.CollectCredentials
        }
    }
    private fun isValidProfileData(name: String, bio: String): Boolean{
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if(name.isEmpty()){
            invalidFields.add(INPUT_NAME)
        }

        if(bio.isEmpty()){
            invalidFields.add(INPUT_BIO)
        }

        if(invalidFields.isNotEmpty()){
            _registrationStateEvent.value = RegistrationState.InvalidProfiledata(invalidFields)
            return false
        }
        return true
    }

    //Código que o ChooseCredentialsFragment irá utilizar

    fun createCredentials(username: String, password: String){
        if(isValidProfileData(username, password)){
            this.authToken = "token"
            _registrationStateEvent.value = RegistrationState.RegistrationCompleted
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean{
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if(username.isEmpty()){
            invalidFields.add(INPUT_USERNAME)
        }

        if(password.isEmpty()){
            invalidFields.add(INPUT_PASSWORD)
        }

        if(invalidFields.isNotEmpty()){
            _registrationStateEvent.value = RegistrationState.InvalidCredentials(invalidFields)
            return false
        }

        return true
    }

    //Limpa o estado do nosso Registro quando o usuário sair, botão voltar...
    fun userCancelledRegistration(): Boolean{
        authToken = ""
        _registrationStateEvent.value = RegistrationState.CollectProfileData
        return true
    }

    companion object{
        val INPUT_NAME = "INPUT_NAME" to R.string.profile_data_input_layout_error_invalid_name
        val INPUT_BIO = "INPUT_BIO" to R.string.profile_data_input_layout_error_invalid_bio
        val INPUT_USERNAME = "INPUT_USERNAME" to R.string.choose_credentials_input_layout_error_invalid_usename
        val INPUT_PASSWORD = "INPUT_PASSWORD" to R.string.choose_credentials_input_layout_error_invalid_password
    }

}