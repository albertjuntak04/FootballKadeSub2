package com.example.footballschedule.interfaceView

import com.example.footballschedule.model.MatchDetail

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showList(data:List<MatchDetail>)
}