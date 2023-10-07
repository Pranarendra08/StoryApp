package com.example.storyapp

import com.example.storyapp.api.response.ListStoryItem

object DataDummy {
    fun generateDummyStory(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 0..10) {
            val story = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1648720795526_877kLrZI.jpg",
                "2022-03-31T09:59:55.530Z",
                "Dicoding",
                "Jadi Developer Harus Berani Keluar Zona Nyaman",
                "112.5617421",
                "story-ZBk-FmRMTBV71Hhf",
                "-7.9786395"
            )
            storyList.add(story)
        }
        return storyList
    }
}