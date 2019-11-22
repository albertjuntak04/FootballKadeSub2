package com.example.footballschedule.api

import android.net.Uri
import com.example.footballschedule.BuildConfig

class TheLeagueDBApi(val id: String?) {
    private fun urlBuild(path: String?) = Uri.parse(BuildConfig.BASE_URL).buildUpon()
        .appendPath("api")
        .appendPath("v1")
        .appendPath("json")
        .appendPath(BuildConfig.TSDB_API_KEY)
        .appendPath(path)
        .appendQueryParameter("s", id)
        .build().toString()

    fun getTeams() = urlBuild("search_all_leagues.php")
}