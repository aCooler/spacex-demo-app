package com.example.myspacexdemoapp.ui.launch.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.myspacexdemoapp.databinding.YoutubeBinding
import com.example.myspacexdemoapp.ui.UIModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubeViewHolder(binding: YoutubeBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var youTubePlayer: YouTubePlayer
    private val youtubeView: YouTubePlayerView = binding.youtubePlayerView
    fun onBindView(model: UIModel.Youtube) {
        youtubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(initializedYouTubePlayer: YouTubePlayer) {
                youTubePlayer = initializedYouTubePlayer
                youTubePlayer.cueVideo(model.id, 0f)
            }
        })
    }
}
