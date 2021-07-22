package com.production.navdemo1

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.production.navdemo1.model.Book

class MainActivityViewModel: ViewModel() {
    var booksList = arrayListOf<Book>()
    var searchParameter:Int = 0
}