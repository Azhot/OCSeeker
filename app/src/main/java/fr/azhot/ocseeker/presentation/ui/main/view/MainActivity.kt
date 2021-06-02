package fr.azhot.ocseeker.presentation.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import fr.azhot.ocseeker.R
import fr.azhot.ocseeker.databinding.ActivityMainBinding
import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.network.api.ApiService
import fr.azhot.ocseeker.network.api.RetrofitBuilder
import fr.azhot.ocseeker.network.util.Status.*
import fr.azhot.ocseeker.presentation.ui.base.MainViewModelFactory
import fr.azhot.ocseeker.presentation.ui.main.adapter.ContentListAdapter
import fr.azhot.ocseeker.presentation.ui.main.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity(), ContentListAdapter.Interaction {


    // variables
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            RetrofitBuilder.createService(ApiService::class.java)
        )
    }
    private lateinit var contentListAdapter: ContentListAdapter


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupRecyclerView()
        setUpObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.let { setupSearchView(menu) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemSelected(content: Content) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("content", content)
        startActivity(intent)
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
            contentListAdapter = ContentListAdapter(this@MainActivity)
            adapter = contentListAdapter
        }
    }

    private fun setUpObserver() {
        viewModel.contents.observe(this) { resource ->
            when (resource.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (resource.data != null) {
                        contentListAdapter.submitList(resource.data)
                        binding.emptySearch.visibility = View.GONE
                    } else {
                        contentListAdapter.submitList(emptyList())
                        binding.emptySearch.visibility = View.VISIBLE
                    }
                }
                ERROR -> {
                    binding.emptySearch.visibility = View.VISIBLE
                    contentListAdapter.submitList(emptyList())
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
                LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}