package com.example.footballschedule.interfaceView


import com.example.footballschedule.league.League

interface MainLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<League>)
    fun showEmptyData()
}