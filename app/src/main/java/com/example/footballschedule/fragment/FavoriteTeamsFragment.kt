package com.example.footballschedule.fragment


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.footballschedule.R
import com.example.footballschedule.adapter.FavoriteAdapter
import com.example.footballschedule.database.Favorite
import com.example.footballschedule.database.database
import com.example.footballschedule.util.invisible
import com.example.footballschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamsFragment : Fragment(), AnkoComponent<Context>{
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var emptyDataView: LinearLayout
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progress: ProgressBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        listTeam.layoutManager = layoutManager
        listTeam.adapter = FavoriteAdapter(favorites,context )
        swipeRefresh.onRefresh {
            showFavorite()
        }
        progress.visible()
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    @SuppressLint("SetTextI18n")
    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                listTeam = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

            progress = progressBar{


            }.lparams{
                height = dip(45)
                width = dip(45)
                centerInParent()
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
     fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            progress.invisible()
            emptyDataView.invisible()
           // adapter.notifyDataSetChanged()
        }

    }
}
