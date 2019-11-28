package com.example.footballschedule.interfaceView

import com.example.footballschedule.model.MatchDetail

interface EventDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(eventDetail: MatchDetail, hometeam: MatchDetail, awayTeam: MatchDetail)

}