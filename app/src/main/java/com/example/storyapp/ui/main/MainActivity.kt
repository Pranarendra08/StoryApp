package com.example.storyapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.api.response.ListStoryItem
import com.example.storyapp.ui.upload.UploadActivity
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.helper.AuthPreference
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.ui.adapter.StoryAdapter
import com.example.storyapp.ui.detail.DetailActivity
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.login.LoginViewModel
import com.example.storyapp.ui.maps.MapsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private val loginViewModel by viewModels<LoginViewModel> {
//        ViewModelFactory.getInstance(application)
//    }
//
//    private val mainViewModel by viewModels<MainViewModel> {
//        ViewModelFactory.getInstance(application)
//    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authPreference = AuthPreference(this)
        if (authPreference.getValue("key") == "") {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

//        mainViewModel.getStory()

//        mainViewModel.listStory.observe(this) {
//            setListStory(it)
//            getData()
//        }

        getData()

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }
    }

    private fun getData() {
        val adapter = StoryAdapter()
        binding.rvListStory.adapter = adapter
        binding.rvListStory.layoutManager = LinearLayoutManager(this)

        mainViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

//    private fun setListStory(listStoryItems: PagingData<ListStoryItem>?) {
//        if (listStoryItems != null) {
//            binding.rvListStory.layoutManager = LinearLayoutManager(this)
//            val adapter = StoryAdapter()//listStoryItems
//            binding.rvListStory.adapter = adapter
//
//            adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
//                override fun onItemCLicked(data: ListStoryItem) {
//                    startActivity(Intent(this@MainActivity, DetailActivity::class.java)
//                        .putExtra("story_id", data.id))
//                }
//            })
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.logout -> {
//                loginViewModel.logout()
//                startActivity(Intent(this, LoginActivity::class.java))
//                finishAffinity()
//                true
//            }
            R.id.maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                return true
            }
            else -> {
                true
            }
        }
    }
}