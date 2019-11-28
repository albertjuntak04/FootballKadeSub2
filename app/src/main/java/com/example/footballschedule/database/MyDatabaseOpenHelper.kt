package com.example.footballschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.footballschedule.model.Team
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //Here you create tables
        db?.createTable(Favorite.TABLE_FAVORITE, true,
            Favorite.ID to org.jetbrains.anko.db.INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT ,
            Favorite.AWAY_ID to TEXT,
            Favorite.HOME_ID to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.AWAY_TEAM to TEXT,
            Favorite.HOME_TEAM to TEXT,
            Favorite.EVENT_DATE to TEXT
            )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db?.dropTable(Favorite.TABLE_FAVORITE, true)

    }

}

// Access property for Context
val Context.db: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)