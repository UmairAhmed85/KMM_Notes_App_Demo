package com.kmm.data.local

import android.content.Context
import com.kmm.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
   actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = NoteDatabase.Schema,
            context = context,
            name = "NoteDatabase")
    }
}