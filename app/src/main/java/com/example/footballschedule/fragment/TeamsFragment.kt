package com.example.footballschedule.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.footballschedule.R
import com.example.footballschedule.activity.DetailLeagueActivity
import com.example.footballschedule.adapter.MainAdapter
import com.example.footballschedule.api.TheLeagueDBApi
import com.example.footballschedule.interfaceView.LeagueMainView
import com.example.footballschedule.invisible
import com.example.footballschedule.league.League
import com.example.footballschedule.mainleague.LeagueMainPresenter
import com.example.footballschedule.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_teams.*
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class TeamsFragment : Fragment(), LeagueMainView {
    private lateinit var adapter: MainAdapter
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: LeagueMainPresenter



    companion object{
        const val PARCELABLE_ID_DATA = "id data"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val theSportDBApi = TheLeagueDBApi("Soccer")
        val api = theSportDBApi.getTeams()
        val gson = Gson()
        presenter = LeagueMainPresenter(this, api, gson)

        adapter = MainAdapter(context,leagues){
            context?.startActivity<DetailLeagueActivity>(PARCELABLE_ID_DATA to it)
        }

        recycle_league.adapter = adapter
        recycle_league.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycle_league.setHasFixedSize(true)

        presenter.getList()

    }

    override fun showLeague(data: List<League>) {
        progress.invisible()
        leagues.clear()
        leagues.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun showLoading() {
        progress.visible()

    }

    override fun showHiding() {

    }
}
