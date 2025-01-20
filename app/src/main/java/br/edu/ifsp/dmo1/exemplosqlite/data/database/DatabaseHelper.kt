package br.edu.ifsp.dmo1.exemplosqlite.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_KEYS.DATABASE_NAME, null, DATABASE_KEYS.DATABASE_VERSION){

    object DATABASE_KEYS {
        const val DATABASE_NAME = "exemplo_database.db"
        const val DATABASE_VERSION = 3
        const val TABLE_NAME = "tb_meu_dado"
        const val COLUMN_ID = "id"
        const val COLUMN_TEXTO = "texto"
        const val COLUMN_NUMERO = "numero"
    }

    private companion object {
        const val CREATE_TABLE_V1 = "CREATE TABLE ${DATABASE_KEYS.TABLE_NAME} (${DATABASE_KEYS.COLUMN_TEXTO} TEXT)"
        const val CREATE_TABLE_V2 = "CREATE TABLE ${DATABASE_KEYS.TABLE_NAME} (" +
                "${DATABASE_KEYS.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DATABASE_KEYS.COLUMN_TEXTO} TEXT NOT NULL)"
        const val CREATE_TABLE_V3 = "CREATE TABLE ${DATABASE_KEYS.TABLE_NAME} (" +
                "${DATABASE_KEYS.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DATABASE_KEYS.COLUMN_TEXTO} TEXT NOT NULL, " +
                "${DATABASE_KEYS.COLUMN_NUMERO} INTEGER )"

        const val DROP_TABLE = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_NAME}";
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_V3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        when {
            oldVersion < 2 -> {
                updateToVersion2(db)
            }
            oldVersion < 3 -> {
                updateToVersion3(db)
            }
        }
    }

    private fun updateToVersion2(db: SQLiteDatabase) {
        var sql = "ALTER TABLE ${DATABASE_KEYS.TABLE_NAME} RENAME TO ${DATABASE_KEYS.TABLE_NAME}_OLD"
        db.execSQL(sql)

        db.execSQL(CREATE_TABLE_V2)

        sql = "INSERT INTO ${DATABASE_KEYS.TABLE_NAME} (${DATABASE_KEYS.COLUMN_TEXTO}) " +
                "SELECT ${DATABASE_KEYS.COLUMN_TEXTO} " +
                "FROM ${DATABASE_KEYS.TABLE_NAME}_OLD"
        db.execSQL(sql)

        sql = "DROP TABLE ${DATABASE_KEYS.TABLE_NAME}_OLD"
        db.execSQL(sql)
    }

    private fun updateToVersion3(db: SQLiteDatabase) {
        val sql =  "ALTER TABLE ${DATABASE_KEYS.TABLE_NAME} " +
                "ADD COLUMN ${DATABASE_KEYS.COLUMN_NUMERO} INTEGER DEFAULT -1"

        db.execSQL(sql)
    }
}