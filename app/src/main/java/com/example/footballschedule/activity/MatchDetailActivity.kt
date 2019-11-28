package com.example.footballschedule.activity

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.footballschedule.R
import com.example.footballschedule.api.TheSportDBApi
import com.example.footballschedule.changeFormatDate
import com.example.footballschedule.database.Favorite
import com.example.footballschedule.database.database
import com.example.footballschedule.interfaceView.EventDetailView
import com.example.footballschedule.model.MatchDetail
import com.example.footballschedule.strToDate
import com.example.footballschedule.util.EventDetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MatchDetailActivity : AppCompatActivity(), EventDetailView {
    private lateinit var event: MatchDetail
    private lateinit var presenter: EventDetailPresenter
    private  var id: String = ""
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        val intent = intent

        event = intent.getParcelableExtra("EVENT")
        id = event.eventId.toString()

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


        favoriteState()
    }



    override fun showLoading() {
        swipe_view.isRefreshing = true
    }

    override fun hideLoading() {
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

        hideLoading()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            R.id.add_to_favorites ->{
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to event.eventId,
                    Favorite.HOME_ID to event.homeTeamId,
                    Favorite.AWAY_ID to event.awayTeamId,
                    Favorite.HOME_SCORE to event.homeScore,
                    Favorite.AWAY_SCORE to event.awayScore,
                    Favorite.AWAY_TEAM to event.awayTeam,
                    Favorite.HOME_TEAM to event.homeTeam,
                    Favorite.EVENT_DATE to event.eventDate)
            }

        } catch (e: SQLiteConstraintException){

        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(ID_EVENT = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                    "id" to id)
            }

        } catch (e: SQLiteConstraintException){

        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}
