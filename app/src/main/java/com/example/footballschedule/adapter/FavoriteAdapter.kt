package com.example.footballschedule.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballschedule.R
import com.example.footballschedule.adapter.EventAdapter.MatchUI.Companion.date
import com.example.footballschedule.changeFormatDate
import com.example.footballschedule.database.Favorite
import com.example.footballschedule.strToDate
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FavoriteAdapter(private val event: List<Favorite>, val context: Context?)
    :RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(event[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val matchDate : TextView = itemView.findViewById(date)
        private val homeTeam : TextView = itemView.findViewById(MatchUI.homeTeam)
        private val awayTeam : TextView = itemView.findViewById(MatchUI.awayTeam)
        private val scoreHome : TextView = itemView.findViewById(MatchUI.homeScore)
        private val awayScore : TextView = itemView.findViewById(MatchUI.awayScore)

        fun bindItem(event: Favorite){
            val date = strToDate(event.eventDate)
            matchDate.text = changeFormatDate(date)
            homeTeam.text = event.homeTeam
            scoreHome.text = event.homeScore

            awayTeam.text = event.awayTeam
            awayScore.text = event.awayScore
        }

    }



    override fun getItemCount() = event.size



    class MatchUI: AnkoComponent<ViewGroup> {
        companion object{
            const val date = 1
            const val homeTeam = 2
            const val homeScore = 3
            const val awayScore = 4
            const val awayTeam = 5
            const val vs = 6
        }
        @SuppressLint("RtlHardcoded")
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
            cardView {
                lparams(width = matchParent, height = 200){
                    gravity = Gravity.CENTER
                    margin = dip(4)
                    radius = 14f

                }

                verticalLayout {

                    textView("Minggu, 18 Agustus 2018"){
                        id = date
                    }.lparams(width = wrapContent, height = wrapContent){
                        gravity = Gravity.CENTER
                        margin = dip(8)
                    }

                    relativeLayout {

                        textView("Chelsea"){
                            id = homeTeam
                            textColor = Color.BLACK
                            gravity = Gravity.RIGHT
                            R.style.TeamStyle_Match


                        }.lparams(width = wrapContent, height = wrapContent){
                            leftOf(homeScore)
                            rightMargin = dip(10)
                        }

                        textView("3"){
                            id = homeScore
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                            R.style.TeamStyle_Match
                        }.lparams(width = wrapContent, height = wrapContent){
                            leftOf(vs)
                        }

                        textView("VS"){
                            id = vs
                            R.style.TeamStyle_Match
                        }.lparams(width = wrapContent, height = wrapContent){
                            centerInParent()
                            leftMargin = dip(6)
                            rightMargin = dip(6)
                        }

                        textView("2"){
                            id = awayScore
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK
                            R.style.TeamStyle_Match
                        }.lparams(width = wrapContent, height = wrapContent){
                            rightOf(vs)
                        }

                        textView("Arsenal"){
                            id = awayTeam
                            R.style.TeamStyle_Match
                            textColor = Color.BLACK
                            gravity = Gravity.LEFT
                        }.lparams(width = wrapContent, height = wrapContent){
                            rightOf(awayScore)
                            leftMargin = dip(10)
                        }

                    }.lparams(width = matchParent, height = wrapContent)

                }.lparams(width = matchParent, height = wrapContent){
                    gravity = Gravity.CENTER
                    bottomMargin = dip(8)
                }
            }
        }

    }
}