package com.example.navigationcomponentapp.ui.registration.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.navigationcomponentapp.R
import com.example.navigationcomponentapp.extensions.dismissError
import com.example.navigationcomponentapp.ui.registration.RegistrationViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile_data.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileDataFragment : Fragment() {

    //Iniciando o viewModel para que este Fragment ouça as mudanças
    //registrationViewModel está escopado a minha activity
    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val validationFields = initValidationFields()
        listenToRegistrationViewModelEvents(validationFields)
        registrationViewListeners()
        registerDeviceBackStackCallback()
    }

    private fun initValidationFields() = mapOf(
        RegistrationViewModel.INPUT_NAME.first to inputLayoutProfileDataName,
        RegistrationViewModel.INPUT_BIO.first to inputLayoutProfileDataBio
    )

    fun listenToRegistrationViewModelEvents(validationFields : Map<String, TextInputLayout>){
        //Escutando eventos do viewModel
        registrationViewModel.registrationStateEvent.observe(viewLifecycleOwner, Observer {
                registrationState->
            when(registrationState){
                //Abaixo são as formas de fazer validações de campo usando o viewModel
                //com a estratégia de par valor

                is RegistrationViewModel.RegistrationState.CollectCredentials-> {
                    //Como passar dados de uma tela pra outra usando a API de
                    //navegação
                    //Navegação correta de uma tela para outra, passando argumento de um
                    //forma segura
                    val name = inputProfileDataName.text.toString()
                    val directions = ProfileDataFragmentDirections
                        .actionProfileDataFragmentToChooseCredentialsFragment(name)
                    findNavController().navigate(directions)
                }

                is RegistrationViewModel.RegistrationState.InvalidProfiledata -> {
                    registrationState.fields.forEach{ fieldError->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }
        })
    }

    //Separei o que é de listener
    private fun registrationViewListeners(){
        buttonProfileDataNext.setOnClickListener {
            val name = inputProfileDataName.text.toString()
            val bio = inputProfileDataBio.text.toString()

            registrationViewModel.collectProfileData(name, bio)
        }

        inputProfileDataName.addTextChangedListener {
            inputLayoutProfileDataName.dismissError()
        }

        inputProfileDataBio.addTextChangedListener {
            inputLayoutProfileDataBio.dismissError()
        }
    }

    //Funções de callback do botão voltar
    private fun registerDeviceBackStackCallback(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            cancelRegistration()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cancelRegistration()
        return true
    }

    private fun cancelRegistration(){
        registrationViewModel.userCancelledRegistration()
        findNavController().popBackStack(R.id.loginFragment, false)
    }
}
