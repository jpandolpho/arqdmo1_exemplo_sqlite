package br.edu.ifsp.dmo1.exemplosqlite.data.database

import android.content.ContentValues
import br.edu.ifsp.dmo1.exemplosqlite.data.model.MeuDado

class MeuDadoDao(private val dbHelper: DatabaseHelper) {

    fun insert(meuDado: MeuDado) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO, meuDado.texto)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_NUMERO, meuDado.numero)
        }
        db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_NAME, null, values)
    }

    fun getAll(): List<MeuDado> {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ID,
            DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO,
            DatabaseHelper.DATABASE_KEYS.COLUMN_NUMERO
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
                    MeuDado(
                        id = it.getInt(0),
                        texto = it.getString(1),
                        numero = it.getInt(2))
                )
            }
        }

        return dados
    }

    fun getById(id: Int): MeuDado? {
        val dado: MeuDado?
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ID,
            DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO,
            DatabaseHelper.DATABASE_KEYS.COLUMN_NUMERO
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ID} = ?"
        val whereArgs = arrayOf(id.toString())

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            columns,
            where,
            whereArgs,
            null,
            null,
            null
        )

        cursor.use {
            dado = if (cursor.moveToNext()) {
                MeuDado(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2))
            }else {
                null
            }
        }

        return dado
    }

    fun update(meuDado: MeuDado) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO, meuDado.texto)
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_NUMERO, meuDado.numero)
        }

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ID} = ?"
        val whereArgs = arrayOf(meuDado.id.toString())

        db.update(
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            values,
            where,
            whereArgs
        )
    }

    fun delete(meuDado: MeuDado) {
        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ID} = ?"
        val whereArgs = arrayOf(meuDado.id.toString())
        val db = dbHelper.writableDatabase
        db.delete(
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            where,
            whereArgs
        )
    }
}