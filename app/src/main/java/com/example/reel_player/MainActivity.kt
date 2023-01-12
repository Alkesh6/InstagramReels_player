package com.example.reel_player


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.reel_player.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: videoAdapter
    private val videos = ArrayList<video>()
    private val exoPlayerItems = ArrayList<ExoPlayerItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videos.add(
            video(
                "Blossom",
                "https://cdn.pixabay.com/vimeo/328940142/Buttercups%20-%2022634.mp4?width=1304&hash=2df4ff27ac821dcb2174355e8051bd782697fcb4"
            )
        )

        videos.add(
            video(
                "Dandelion",
                 "https://cdn.pixabay.com/vimeo/161687568/Dandelion%20-%202719.mp4?width=1920&hash=9342281cd0a54528ed7b3cd4c59df1a2856eb73b"
            )
        )

        videos.add(
            video(
                "Sunflowers",
                "https://cdn.pixabay.com/vimeo/318978093/Sunflowers%20-%2021530.mp4?width=1920&hash=0ccc8761910881873887850335daaf80f8669b1d"
            )
        )

        videos.add(
            video(
                "Sunset",
                "https://cdn.pixabay.com/vimeo/230853041/Sunflower%20Field%20-%2011496.mp4?width=1920&hash=8968e544bc5065e97c92ce731c526c000bca6be3"
            )
        )

        videos.add(
            video(
                 "Butterfly",
                "https://cdn.pixabay.com/vimeo/452154245/Butterfly%20-%2047876.mp4?width=1920&hash=c9451640a4903d47bca42dc8a8d7e14ad39db504"
            )
        )

        videos.add(
            video(
                "globe flower",
                 "https://cdn.pixabay.com/vimeo/361827510/Globe%20Flower%20-%2027148.mp4?width=1920&hash=8de359d457fcf3f77869480bfde1de6c0945c027"

            )
        )

        videos.add(
            video(
                "Bee",
                 "https://cdn.pixabay.com/vimeo/352026548/Bee%20-%2025785.mp4?width=1920&hash=55027a883f05874f9b76efbe742076f20025e267"

            )
        )

        adapter = videoAdapter(this, videos, object : videoAdapter.OnVideoPreparedListener {
            override fun onVideoPrepared(exoPlayerItem: ExoPlayerItem) {
                exoPlayerItems.add(exoPlayerItem)
            }
        })

        binding.viewPager2.adapter = adapter

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val previousIndex = exoPlayerItems.indexOfFirst { it.exoPlayer.isPlaying }
                if (previousIndex != -1) {
                    val player = exoPlayerItems[previousIndex].exoPlayer
                    player.pause()
                    player.playWhenReady = false
                }
                val newIndex = exoPlayerItems.indexOfFirst { it.position == position }
                if (newIndex != -1) {
                    val player = exoPlayerItems[newIndex].exoPlayer
                    player.playWhenReady = true
                    player.play()
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()

        val index = exoPlayerItems.indexOfFirst { it.position == binding.viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.pause()
            player.playWhenReady = false
        }
    }

    override fun onResume() {
        super.onResume()

        val index = exoPlayerItems.indexOfFirst { it.position == binding.viewPager2.currentItem }
        if (index != -1) {
            val player = exoPlayerItems[index].exoPlayer
            player.playWhenReady = true
            player.play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (exoPlayerItems.isNotEmpty()) {
            for (item in exoPlayerItems) {
                val player = item.exoPlayer
                player.stop()
                player.clearMediaItems()
            }
        }
    }
}