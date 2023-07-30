package com.kmm.android.di

import android.app.Application
import com.kmm.data.SqlDelightNoteDataSource
import com.kmm.data.local.DatabaseDriverFactory
import com.kmm.database.NoteDatabase
import com.kmm.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(application: Application) : SqlDriver {
        return DatabaseDriverFactory(application).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDatasource(driver: SqlDriver) : NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}