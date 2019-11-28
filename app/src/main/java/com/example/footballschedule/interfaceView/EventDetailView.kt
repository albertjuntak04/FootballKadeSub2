package com.example.footballschedule.util

import com.example.footballschedule.model.MatchDetail

interface EventDetailView {
    fun showLoading()
    fun hideloading()
    fun showDetail(eventDetail: MatchDetail, hometeam: MatchDetail, awayTeam: MatchDetail)

}