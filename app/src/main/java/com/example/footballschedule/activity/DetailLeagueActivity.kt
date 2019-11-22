package com.example.footballschedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballapi.api.ApiRepository
import com.example.footballapi.api.TheSportDBApi
import com.example.footballschedule.*
import com.example.footballschedule.adapter.TeamAdapter
import com.example.footballschedule.league.League
import com.example.footballschedule.league.LeaguePresenter
import com.example.footballschedule.league.LeagueView
import com.example.footballschedule.util.MainPresenter
import com.example.footballschedule.util.MainView
import com.example.footballschedule.util.invisible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import kotlinx.android.synthetic.main.detail_league.*



class DetailLeagueActivity : AppCompatActivity(), MainView, LeagueView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var matchPresenter:LeaguePresenter
    private lateinit  var leagueName: String
    private var savedInstanceState: Bundle? = null
    private  var leagueId = "4328"
    private var fixture = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        val league = intent.getParcelableExtra<League>(MainActivity.PARCELABLE_ID_DATA)
        leagueId = league.leagueId.toString()


        nav_button.setOnNavigationItemSelectedListener {
            item -> when(item.itemId){
            R.id.nav_next ->{
                fixture = 1
                openFragment(
                    NextMatchFragment.newInstance(
                        fixture,
                        leagueId
                    )
                )
                return@setOnNavigationItemSelectedListener true
            }
            R.id.nav_prev -> {
                fixture = 2
                openFragment(
                    NextMatchFragment.newInstance(
                        fixture,
                        leagueId
                    )
                )
                return@setOnNavigationItemSelectedListener  true
            }
        }
            false
        }

        nav_button.selectedItemId = R.id.nav_prev


        val listTeam = findViewById<RecyclerView>(R.id.rv_club)

        adapter = TeamAdapter(this,teams)
        listTeam.adapter= adapter
        listTeam.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listTeam.setHasFixedSize(true)


        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        leagueName = league?.leagueName.toString()
        presenter.getTeamList(leagueName)

        //Detail dari League
        val theSportDBApi = TheSportDBApi(leagueId)
        val api = theSportDBApi.getLeague()
        matchPresenter = LeaguePresenter(this, api, gson)
        matchPresenter.getList()



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }


    private fun openFragment(fragment: Fragment){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,fragment,fragment.javaClass.simpleName)
                .commit()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showTeamList(data: List<Team>) {
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
        empty_data.invisible()
    }

    override fun showEmptyData() {
        empty_data.visible()

    }

    override fun showList(data: League) {
        txt_liga.text= data.leagueName
        twitter_league.text = data.leagueTwitter
        description_league.text = data.leagueDescription
        country_league.text = data.leagueCountry
        Picasso.get().load(data.leagueLogo).into(img_liga)

    }
}
