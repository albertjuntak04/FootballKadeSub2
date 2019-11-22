package com.example.footballschedule.league

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
    @SerializedName("strLeague")
    var leagueName: String? = null,

    @SerializedName("strTwitter")
    var leagueTwitter: String? = null,

    @SerializedName("strgender")
    var leagueGender: String? = null,

    @SerializedName("strCountry")
    var leagueCountry: String? = null,

    @SerializedName("strPoster")
    var leagueLogo: String? = null,

    @SerializedName("strDescriptionEN")
    var leagueDescription :String? = null,

    @SerializedName("idLeague")
    var leagueId : String? = null
):Parcelable