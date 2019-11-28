package com.example.footballschedule.util


import com.example.footballschedule.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showEmptyData()
}