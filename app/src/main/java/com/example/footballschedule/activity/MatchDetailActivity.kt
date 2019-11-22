package com.example.footballschedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.footballapi.api.TheSportDBApi
import com.example.footballschedule.R
import com.example.footballschedule.changeFormatDate
import com.example.footballschedule.model.MatchDetail
import com.example.footballschedule.strToDate
import com.example.footballschedule.util.EventDetailPresenter
import com.example.footballschedule.util.EventDetailView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.support.v4.onRefresh


class MatchDetailActivity : AppCompatActivity(), EventDetailView {


    private lateinit var event: MatchDetail
    private lateinit var presenter: EventDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        event = intent.getParcelableExtra("EVENT")

        val date = strToDate(event.eventDate)
        date_match.text = changeFormatDate(date)

        txt_home.text = event.homeTeam
        home_score.text = event.homeScore

        txt_away.text = event.awayTeam
        away_score.text = event.awayScore

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"

        val apiMatchDetail = TheSportDBApi(event.eventId).getmatchDetail()
        val apiHomeTeam = TheSportDBApi(event.homeTeamId).getTeamDetail()
        val apiAwayTeam = TheSportDBApi(event.awayTeamId).getTeamDetail()
        val gson = Gson()
        presenter = EventDetailPresenter(this, apiMatchDetail, apiHomeTeam, apiAwayTeam, gson)
        presenter.getEventDetail()

        swipe_view.onRefresh {
            presenter.getEventDetail()
        }
        swipe_view.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

    }

    override fun showLoading() {
        swipe_view.isRefreshing = true
    }

    override fun hideloading() {
        swipe_view.isRefreshing = false

    }

    override fun showDetail(
        eventDetail: MatchDetail,
        hometeam: MatchDetail,
        awayTeam: MatchDetail
    ) {
       Picasso.get().load(hometeam.teamBadge).into(img_home)
        txt_home.text = eventDetail.homeTeam
        home_score.text = if (eventDetail.homeScore.isNullOrEmpty()) "-" else eventDetail.homeScore
        away_score.text=if (eventDetail.awayScore.isNullOrEmpty()) "-" else eventDetail.awayScore
        Picasso.get().load(awayTeam.teamBadge).into(img_away)
        txt_away.text = eventDetail.awayTeam
        home_goals.text =if (eventDetail.homeGoalDetails.isNullOrEmpty())"-" else eventDetail.homeGoalDetails?.replace(";",";\n")
        away_goals.text = if (eventDetail.awayGoalsDetails.isNullOrEmpty())"-" else eventDetail.awayGoalsDetails?.replace(";",";\n")
        home_keeper.text = if (eventDetail.homeLineupGoalKeeper.isNullOrEmpty())"-" else eventDetail.homeLineupGoalKeeper?.replace(";",";\n")
        away_keeper.text = if (eventDetail.awayLineupGoalKeeper.isNullOrEmpty())"-" else eventDetail.awayLineupGoalKeeper?.replace(";",";\n")
        home_defense.text = if (eventDetail.homeLineupDefense.isNullOrEmpty())"-" else eventDetail.homeLineupDefense?.replace(";",";\n")
        away_defense.text = if (eventDetail.awayLineupDefense.isNullOrEmpty())"-" else eventDetail.awayLineupDefense?.replace(";",";\n")
        midfield_home.text = if (eventDetail.homeLineupMidfield.isNullOrEmpty())"-" else eventDetail.homeLineupMidfield?.replace(";",";\n")
        midfield_away.text = if (eventDetail.awayLineupMidfield.isNullOrEmpty())"-" else eventDetail.awayLineupMidfield?.replace(";",";\n")
        forward_home.text = if (eventDetail.homeLineupForward.isNullOrEmpty())"-" else eventDetail.homeLineupForward?.replace(";",";\n")
        forward_away.text =if (eventDetail.awayLineupForward.isNullOrEmpty())"-" else eventDetail.awayLineupForward?.replace(";",";\n")
        subtitutes_home.text = if (eventDetail.homeLineupSubstitutes.isNullOrEmpty())"-" else eventDetail.homeLineupSubstitutes?.replace(";",";\n")
        subtitutes_away.text = if (eventDetail.awayLineupSubstitutes.isNullOrEmpty())"-" else eventDetail.awayLineupSubstitutes?.replace(";",";\n")

        hideloading()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}
