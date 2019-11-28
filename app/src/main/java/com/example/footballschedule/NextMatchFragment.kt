package com.example.footballschedule
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.footballschedule.activity.MatchDetailActivity
import com.example.footballschedule.adapter.EventAdapter
import com.example.footballschedule.api.TheSportDBApi
import com.example.footballschedule.interfaceView.MatchView
import com.example.footballschedule.model.MatchDetail
import com.example.footballschedule.util.MatchPresenter
import com.example.footballschedule.util.invisible
import com.example.footballschedule.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment : Fragment() , MatchView {

    private var match: MutableList<MatchDetail> = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var adapter: EventAdapter
    private var leagueId = "4331"
    private var fixture = 1
    private lateinit var emptyDataView : LinearLayout




    companion object {
        fun newInstance( fixture:Int,leagueId: String): NextMatchFragment {
            val fragment = NextMatchFragment()
            fragment.leagueId = leagueId
            fragment.fixture = fixture
            return fragment
        }

        const val lay_match = 1
    }






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return createView(AnkoContext.create(ctx))}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val theSportDBApi = TheSportDBApi(leagueId)
        val api = if (fixture == 1)theSportDBApi.getnextsechdule() else theSportDBApi.getprevsechdule()
        val gson = Gson()
        presenter = MatchPresenter(this, api, gson)
        adapter = EventAdapter(match){
            ctx.startActivity<MatchDetailActivity>("EVENT" to it)

        }

        recyclerView.adapter = adapter
        swipe.onRefresh {
            presenter.getList()
        }

        presenter.getList()
    }

    override fun showLoading() {
        swipe.isRefreshing = true
        emptyDataView.invisible()
    }

    override fun hideLoading() {
        swipe.isRefreshing = false
        emptyDataView.invisible()

    }

    override fun showEmptyData() {
        recyclerView.invisible()
        emptyDataView.visible()

    }

    override fun showList(data: List<MatchDetail>) {
        swipe.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
        emptyDataView.invisible()

    }




    @SuppressLint("SetTextI18n")
    fun createView(ui: AnkoContext<Context>) = with(ui){
        relativeLayout{
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)


            swipe = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                recyclerView = recyclerView {
                    id = lay_match
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
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
            }.lparams  {
                centerInParent()

            }

        }
    }

}
