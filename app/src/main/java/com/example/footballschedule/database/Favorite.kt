package com.example.footballschedule.database

data class Favorite(val id: Long?, val eventId: String?, val homeTeamId: String?, val awayTeamId: String?,val homeScore: String? ,val awayScore: String?,
                    val awayTeam: String?, val homeTeam: String? , val eventDate: String?) {

    companion object{
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String ="ID_EVENT"
        const val HOME_ID: String= "HOME_ID"
        const val AWAY_ID: String = "AWAY_ID"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val AWAY_TEAM: String = "AWAY"
        const val HOME_TEAM: String ="HOME"
        const val EVENT_DATE: String = "DATE"
    }

}