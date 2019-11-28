package com.example.footballschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballschedule.R
import com.example.footballschedule.model.Team
import com.squareup.picasso.Picasso

class TeamAdapter(private val context: Context, private val teams:List<Team>)
    :RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    class TeamViewHolder (view: View):RecyclerView.ViewHolder(view){

        private val teamBadge: ImageView = view.findViewById(R.id.img_team)
        fun  bindItem(teams: Team){
            Picasso.get().load(teams.teamBadge).into(teamBadge)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TeamViewHolder(
     LayoutInflater.from(context).inflate(R.layout.club_item, parent, false)
    )



    override fun getItemCount() : Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
       holder.bindItem(teams[position])
    }

}