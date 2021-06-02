package fr.azhot.ocseeker.presentation.ui.main.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import fr.azhot.ocseeker.databinding.ActivityDetailBinding
import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.util.OCS_API_URL
import fr.azhot.ocseeker.util.OCS_STATIC_URL
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {


    // variables
    private lateinit var binding: ActivityDetailBinding


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        val content = intent.getParcelableExtra<Content>("content")
        content?.let { updateUI(content) }
    }


    // functions
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun updateUI(content: Content) {
        Glide.with(this)
            .load(OCS_STATIC_URL + content.fullScreenImageUrl)
            .centerCrop()
            .into(binding.image)
        binding.title.text = content.title
        binding.subtitle.text = content.subtitle
        fetchPitchAndUpdateUI(content)
    }

    private fun fetchPitchAndUpdateUI(content: Content) {
        val url = OCS_API_URL + content.detailLink
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                binding.pitch.text = getPitchFromResponse(response)
            }, { error ->
                error.printStackTrace()
                Toast.makeText(this, "An error has occurred !", Toast.LENGTH_SHORT).show()
            })

        Volley
            .newRequestQueue(this)
            .add(request)
    }

    private fun getPitchFromResponse(response: JSONObject): String {
        val contents = response.getJSONObject("contents")
        return try {
            contents.getString("pitch")
        } catch (e: Exception) {
            contents.getJSONArray("seasons")
                .getJSONObject(0)
                .getString("pitch")
        }
    }
}