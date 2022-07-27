package ru.nikitae57.cirrussandbox.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.nikitae57.cirrussandbox.ViewModelsFactory
import ru.nikitae57.cirrussandbox.databinding.FragmentMainBinding
import ru.nikitae57.cirrussandbox.second.SecondActivity
import ru.nikitae57.cirrussandbox.utils.State
import ru.nikitae57.cirrussandbox.utils.observeStateFlow

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        initViewModel()
        super.onAttach(context)
    }

    override fun onStart() {
        observeViewModel()
        viewModel.startLoading()
        super.onStart()
    }

    private fun initViewModel() {
        if (arguments?.getBoolean(IS_TEST_EXTRA) != true) {
            viewModel = ViewModelProvider(this, ViewModelsFactory(resources))[MainViewModel::class.java]
        }
    }

    private fun observeViewModel() {
        observeStateFlow(viewModel.stateFlow, ::onStateUpdate)
        observeStateFlow(viewModel.navigateToSecondActivityEventFlow, ::navigateToSecondActivity)
    }

    private fun navigateToSecondActivity(ignored: Boolean) {
        startActivity(Intent(context, SecondActivity::class.java))
    }

    private fun onStateUpdate(state: State<MainStateModel>) {
        when (state) {
            is State.Loading -> applyLoadingState()
            is State.Success -> applySuccessState(state.data)
            is State.Error -> applyErrorState(state)
            else -> Unit
        }
    }

    private fun applyErrorState(state: State.Error) = binding.run {
        progressBar.isVisible = false
        label.apply {
            text = state.message
            isVisible = true
        }
        button.apply {
            text = state.retryLabel
            isVisible = true
            setOnClickListener { state.retryAction() }
        }
    }

    private fun applySuccessState(stateModel: MainStateModel) = binding.run {
        progressBar.isVisible = false
        label.apply {
            text = stateModel.labelText
            isVisible = true
        }
        button.apply {
            text = stateModel.buttonText
            isVisible = true
            setOnClickListener { stateModel.buttonAction() }
        }
    }

    private fun applyLoadingState() = binding.run {
        progressBar.isVisible = true
        label.isVisible = false
        button.isVisible = false
    }

    companion object {
        private const val IS_TEST_EXTRA = "IS_TEST_EXTRA"

        fun newTestInstance(mainViewModel: MainViewModel) = MainFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_TEST_EXTRA, true)
            }
            viewModel = mainViewModel
        }
    }
}