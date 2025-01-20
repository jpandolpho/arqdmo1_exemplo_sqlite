package br.edu.ifsp.dmo1.exemplosqlite.ui.main

interface ItemListDadoClickListener {

    fun clickEditItemList(id: Int, texto: String, valor: Int)

    fun clickDeleteItemList(id: Int)

}