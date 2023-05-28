package com.mte.fitnessapp.ui.exercises

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mte.fitnessapp.R
import com.mte.fitnessapp.databinding.FragmentExercisesDetailBinding
import com.mte.fitnessapp.model.exercises.ExercisesItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class ExercisesDetailFragment : Fragment() {

    private var _binding : FragmentExercisesDetailBinding? = null
    private val binding get() = _binding!!
    
    private val args by navArgs<ExercisesDetailFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExercisesDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        binding.exercise = args.exercisearg
        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(0)
            .build()
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = args.exercisearg?.videoUrl
                if (videoId != null) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        }, false, iFramePlayerOptions)


        binding.xButton.setOnClickListener {
            onDestroyView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _binding = null
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            binding.youtubePlayerView.matchParent()
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            binding.youtubePlayerView.wrapContent()
//        }
//    }

}