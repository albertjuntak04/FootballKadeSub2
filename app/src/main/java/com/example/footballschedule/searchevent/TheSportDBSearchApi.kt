package com.example.footballschedule.searchevent

import android.net.Uri
import com.example.footballschedule.BuildConfig

class TheSportDBSearchApi(val id: String) {
    private fun urlBuild(path: String?) = Uri.parse(BuildConfig.BASE_URL).buildUpon()
        .appendPath("api")
        .appendPath("v1")
        .appendPath("json")
        .appendPath(BuildConfig.TSDB_API_KEY)
        .appendPath(path)
        .appendQueryParameter("e", id)
        .build().toString()

    fun getSearchEvent() = urlBuild("searchevents.php")
}