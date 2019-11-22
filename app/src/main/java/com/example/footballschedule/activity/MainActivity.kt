package com.example.footballschedule.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.footballapi.api.TheSportDBApi
import com.example.footballschedule.DataClub
import com.example.footballschedule.R
import com.example.footballschedule.adapter.MainAdapter
import com.example.footballschedule.api.TheLeagueDBApi
import com.example.footballschedule.api.TheSportTeamApi
import com.example.footballschedule.league.League
import com.example.footballschedule.league.LeagueView
import com.example.footballschedule.mainleague.LeagueMainPresenter
import com.example.footballschedule.mainleague.LeagueMainView
import com.example.footballschedule.util.MatchPresenter
import com.example.footballschedule.util.invisible
import com.example.footballschedule.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_league.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx

class MainActivity : AppCompatActivity(), LeagueMainView {
    private lateinit var adapter: MainAdapter
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: LeagueMainPresenter
    companion object{
        public const val PARCELABLE_ID_DATA = "id data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val theSportDBApi = TheLeagueDBApi("Soccer")
        val api = theSportDBApi.getTeams()
        val gson = Gson()
        presenter = LeagueMainPresenter(this, api, gson)

        adapter = MainAdapter(this,leagues){
            startActivity<DetailLeagueActivity>(PARCELABLE_ID_DATA to it)
        }


        recyclerView = findViewById(R.id.recycle_league)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        presenter.getList()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
            searchView?.queryHint = "Search Matches"
            searchView?.setOnQueryTextListener(object  : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    startActivity<SearchEventActivity>("query" to p0)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false

                }

            })

        return true
    }

    override fun showLeague(data: List<League>) {
        leagues.clear()
        leagues.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun showLoading() {

    }

    override fun showHiding() {


    }




}
