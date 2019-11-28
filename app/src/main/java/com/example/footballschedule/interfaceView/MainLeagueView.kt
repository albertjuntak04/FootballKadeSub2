package com.example.footballschedule.util

import com.example.footballschedule.Team
import com.example.footballschedule.league.League

interface MainLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<League>)
    fun showEmptyData()
}