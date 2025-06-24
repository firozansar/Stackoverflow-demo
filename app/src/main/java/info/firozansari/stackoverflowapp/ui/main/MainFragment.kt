package info.firozansari.stackoverflowapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import info.firozansari.stackoverflowapp.R
import info.firozansari.stackoverflowapp.api.ApiStatus
import info.firozansari.stackoverflowapp.databinding.MainFragmentBinding
import androidx.core.net.toUri

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var  adapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = QuestionAdapter(QuestionAdapter.OnClickListener {
            requireActivity().startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    it.link?.toUri()
                )
            )
        })
        binding.recyclerView.apply {
            adapter = adapter
        }
        observeApiStatus()
    }

    private fun updateStatusView(resourceId: Int) {
        binding.statusView.setImageResource(resourceId)
    }

    private fun observeApiStatus() {
        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    ApiStatus.LOADING -> {
                        binding.statusView.visibility = View.VISIBLE
                        updateStatusView(R.drawable.loading_animation)
                    }
                    ApiStatus.ERROR -> {
                        binding.statusView.visibility = View.VISIBLE
                        updateStatusView(R.drawable.ic_connection_error)
                    }
                    ApiStatus.DONE -> {
                        binding.statusView.visibility = View.GONE
                        observeQuestions()
                    }
                }
            }
        })
    }

    private fun observeQuestions() {
        viewModel.questions.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}