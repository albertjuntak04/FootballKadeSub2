package com.example.footballschedule.searchevent


import com.example.footballschedule.model.MatchDetail

interface SearchView {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<MatchDetail>)
        fun showEmptyData()

}