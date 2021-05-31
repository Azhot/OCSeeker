package fr.azhot.ocseeker.presentation.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import fr.azhot.ocseeker.databinding.ActivityMainBinding
import fr.azhot.ocseeker.network.api.ApiService
import fr.azhot.ocseeker.network.api.RetrofitBuilder
import fr.azhot.ocseeker.network.util.Status.*
import fr.azhot.ocseeker.presentation.ui.base.MainViewModelFactory
import fr.azhot.ocseeker.presentation.ui.main.adapter.ContentListAdapter
import fr.azhot.ocseeker.presentation.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {


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
        setupViewBinding()
        setupRecyclerView()
        setUpObserver()
        setUpEditText()
    }

    private fun setUpEditText() {
        binding.searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getContents(binding.searchField.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }


    // functions
    private fun setupViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            contentListAdapter = ContentListAdapter()
            adapter = contentListAdapter
        }
    }

    private fun setUpObserver() {
        viewModel.contents.observe(this) { resource ->
            when (resource.status) {
                SUCCESS -> {
                    Log.d(MainActivity::class.simpleName, "SUCCESS : ${resource.data} ")
                    binding.progressBar.visibility = View.GONE
                    contentListAdapter.submitList(resource.data ?: emptyList())
                }
                ERROR -> {
                    Log.d(MainActivity::class.simpleName, "SUCCESS : ${resource.data} ")
                    contentListAdapter.submitList(emptyList())
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
                LOADING -> {
                    Log.d(MainActivity::class.simpleName, "SUCCESS : ${resource.data} ")
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}