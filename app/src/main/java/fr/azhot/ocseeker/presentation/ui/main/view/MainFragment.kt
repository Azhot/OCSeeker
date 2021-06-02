package fr.azhot.ocseeker.presentation.ui.main.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import fr.azhot.ocseeker.R
import fr.azhot.ocseeker.databinding.FragmentMainBinding
import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.network.api.ApiService
import fr.azhot.ocseeker.network.api.RetrofitBuilder
import fr.azhot.ocseeker.network.util.Status
import fr.azhot.ocseeker.presentation.ui.base.MainViewModelFactory
import fr.azhot.ocseeker.presentation.ui.main.adapter.ContentListAdapter
import fr.azhot.ocseeker.presentation.ui.main.viewmodel.MainViewModel
import java.util.*


class MainFragment : Fragment(), ContentListAdapter.Interaction {


    // variables
    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController
    private lateinit var contentListAdapter: ContentListAdapter
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            RetrofitBuilder.createService(ApiService::class.java)
        )
    }

    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setupRecyclerView()
        setUpObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        setupSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemSelected(content: Content) {
        navController.navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(content))
    }


    // functions
    private fun setupSearchView(menu: Menu) {
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        val delay = 1000L
        var timer = Timer()
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    timer.cancel()
                    if (newText == null || newText.length < 2) {
                        contentListAdapter.submitList(emptyList())
                        binding.emptySearch.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                    binding.progressBar.visibility = View.VISIBLE
                    timer = Timer()
                    timer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                viewModel.getContents(newText)
                            }
                        },
                        delay
                    )
                    return false
                }
            })
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            contentListAdapter = ContentListAdapter(this@MainFragment)
            adapter = contentListAdapter
        }
    }

    private fun setUpObserver() {
        viewModel.contents.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    contentListAdapter.submitList(
                        if (resource.data != null) {
                            binding.emptySearch.visibility = View.GONE
                            resource.data
                        } else {
                            binding.emptySearch.visibility = View.VISIBLE
                            emptyList()
                        }
                    )
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                    contentListAdapter.submitList(emptyList())
                    binding.emptySearch.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}