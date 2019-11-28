package com.example.footballschedule.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.footballschedule.NextMatchFragment
import com.example.footballschedule.R
import com.example.footballschedule.adapter.EventAdapter
import com.example.footballschedule.util.SearchEventPresenter
import com.example.footballschedule.model.MatchDetail
import com.example.footballschedule.searchevent.SearchView
import com.example.footballschedule.searchevent.TheSportDBSearchApi
import com.example.footballschedule.util.invisible
import com.example.footballschedule.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchEventActivity : AppCompatActivity(),
    SearchView {

    private var match: MutableList<MatchDetail> = mutableListOf()
    private lateinit var presenter: SearchEventPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var emptyDataView: LinearLayout
    private lateinit var theSportDBSearchApi: TheSportDBSearchApi



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchUI().setContentView(this)


        val query = intent.getStringExtra("query")
         theSportDBSearchApi = TheSportDBSearchApi(query)
        val api = theSportDBSearchApi.getSearchEvent()
        val gson = Gson()
        presenter = SearchEventPresenter(this, api, gson)
        adapter = EventAdapter(match) {
                startActivity<MatchDetailActivity>("EVENT" to it)

        }

        recyclerView.adapter = adapter
        presenter.getSearchTeamList()
    }



    override fun showLoading() {
        swipe.isRefreshing = true
        emptyDataView.invisible()
    }

    override fun hideLoading() {
        swipe.isRefreshing = false
        emptyDataView.invisible()

    }

    override fun showTeamList(data: List<MatchDetail>) {
        match.clear()
        val filterMatch = data.filter { it.eventSport == "Soccer" }
        match.addAll(filterMatch)
        adapter.notifyDataSetChanged()
    }

    override fun showEmptyData() {
        recyclerView.invisible()
        emptyDataView.visible()
    }

    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class MatchUI : AnkoComponent<SearchEventActivity> {
        @SuppressLint("SetTextI18n")
        override fun createView(ui: AnkoContext<SearchEventActivity>) = with(ui) {
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)


                swipe = swipeRefreshLayout {
                    setColorSchemeResources(
                        R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    recyclerView = recyclerView {
                        id = NextMatchFragment.lay_match
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                emptyDataView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    imageView {
                        setImageResource(R.drawable.ic_no_data)
                    }

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = "No Data Provided"
                    }
                }.lparams {
                    centerInParent()

                }

            }

        }

    }
}
