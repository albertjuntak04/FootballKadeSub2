package com.example.footballschedule.interfaceView


import com.example.footballschedule.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showEmptyData()
}