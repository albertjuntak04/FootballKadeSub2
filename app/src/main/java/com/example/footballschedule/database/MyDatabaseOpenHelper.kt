package com.example.footballschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.footballschedule.database.Favorite
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "MyMatches.db", null, 1) {
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
        db?.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to org.jetbrains.anko.db.INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT + UNIQUE,
            Favorite.HOME_ID to TEXT,
            Favorite.AWAY_ID to TEXT,
            Favorite.HOME_SCORE to TEXT,
            Favorite.AWAY_SCORE to TEXT,
            Favorite.AWAY_TEAM to TEXT,
            Favorite.HOME_TEAM to TEXT,
            Favorite.EVENT_DATE to TEXT
            )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db?.dropTable(Favorite.TABLE_FAVORITE, true)

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        super.onDowngrade(db, oldVersion, newVersion)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)