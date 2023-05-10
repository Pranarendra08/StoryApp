package com.example.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.api.response.Story
import com.example.storyapp.databinding.ActivityDetailBinding
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.helper.dateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val storyId = intent.getStringExtra("story_id")

        storyId?.let { detailViewModel.getDetail(it) }

        detailViewModel.detailStory.observe(this) {
            setDetailStory(it)
        }
    }

    private fun setDetailStory(story: Story) {
        Glide.with(this)
            .load(story.photoUrl)
            .into(binding.ivDetailPhoto)

        binding.tvDetailName.text = story.name
        binding.tvDetailDescription.text = story.description
        binding.tvDetailDate.text = dateFormat(story.createdAt)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}