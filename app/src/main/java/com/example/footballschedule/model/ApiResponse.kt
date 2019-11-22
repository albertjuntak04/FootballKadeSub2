package com.example.footballapi.model

import com.example.footballschedule.model.MatchDetail

data class ApiResponse(
    val teams: List<MatchDetail>,
    val events: List<MatchDetail>,
    val event: List<MatchDetail>)