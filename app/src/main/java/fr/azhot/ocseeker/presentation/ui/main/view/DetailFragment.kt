package fr.azhot.ocseeker.presentation.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import fr.azhot.ocseeker.databinding.FragmentDetailBinding
import fr.azhot.ocseeker.domain.model.Content
import fr.azhot.ocseeker.util.OCS_API_URL
import fr.azhot.ocseeker.util.OCS_STATIC_URL
import org.json.JSONObject


class DetailFragment : Fragment() {


    // variables
    private lateinit var binding: FragmentDetailBinding
    private lateinit var navController: NavController


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val args: DetailFragmentArgs by navArgs()
        updateUI(args.content)
        setupPlayButton()
    }


    // functions
    private fun updateUI(content: Content) {
        Glide.with(this)
            .load(OCS_STATIC_URL + content.fullScreenImageUrl)
            .centerCrop()
            .into(binding.image)
        binding.title.text = content.title
        binding.subtitle.text = content.subtitle
        fetchPitch(content.detailLink) { binding.pitch.text = it }
    }

    private fun fetchPitch(detailLink: String?, usePitch: (pitch: String) -> Unit) {
        val url = OCS_API_URL + detailLink
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                usePitch(getPitchFromResponse(response))
            }, { error ->
                error.printStackTrace()
                Toast.makeText(context, "An error has occurred !", Toast.LENGTH_SHORT).show()
            })

        Volley
            .newRequestQueue(context)
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

    private fun setupPlayButton() {
        binding.playButton.setOnClickListener {
            // open player here
        }
    }
}