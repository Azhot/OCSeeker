package fr.azhot.ocseeker.presentation.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import fr.azhot.ocseeker.databinding.FragmentPlayerBinding
import fr.azhot.ocseeker.util.DASH_URI


class PlayerFragment : Fragment() {


    // variables
    private lateinit var binding: FragmentPlayerBinding
    private var player: SimpleExoPlayer? = null


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        player = SimpleExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player
        player?.setMediaItem(MediaItem.fromUri(DASH_URI))
        player?.prepare()
        player?.playWhenReady = true
    }

    override fun onPause() {
        player?.playWhenReady = false
        player?.stop()
        super.onPause()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}