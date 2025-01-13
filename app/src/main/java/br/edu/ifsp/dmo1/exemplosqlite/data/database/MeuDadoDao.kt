package br.edu.ifsp.dmo1.exemplosqlite.data.database

import android.content.ContentValues
import br.edu.ifsp.dmo1.exemplosqlite.data.model.MeuDado

class MeuDadoDao(private val dbHelper: DatabaseHelper) {

    fun insert(meuDado: MeuDado) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO, meuDado.texto)
        }
        db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_NAME, null, values)
    }

    fun getAll(): List<MeuDado> {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ID,
            DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO
        )

        val cursor = db.query(DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null)
        val dados = mutableListOf<MeuDado>()

        cursor.use {
            while (it.moveToNext()) {
                dados.add(
                    MeuDado(id = it.getInt(0), texto = it.getString(1))
                )
            }
        }

        return dados
    }
}