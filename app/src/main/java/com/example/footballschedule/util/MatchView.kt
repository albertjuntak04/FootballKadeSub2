package com.example.footballschedule.util

import com.example.footballschedule.model.MatchDetail

interface MatchView {
    fun showLoading()
    fun hideloading()
    fun showEmptyData()
    fun showList(data:List<MatchDetail>)
}