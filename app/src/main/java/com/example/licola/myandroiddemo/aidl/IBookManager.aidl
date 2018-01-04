// IBookManager.aidl
package com.example.licola.myandroiddemo.aidl;

import com.example.licola.myandroiddemo.aidl.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
