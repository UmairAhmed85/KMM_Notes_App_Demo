package com.kmm.data

import com.kmm.database.NoteDatabase
import com.kmm.domain.note.Note
import com.kmm.domain.note.NoteDataSource
import com.kmm.domain.time.DateTimeUtil
import database.NoteQueries
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant

class SqlDelightNoteDataSource(
    db: NoteDatabase
) : NoteDataSource {
    private val queries = db.noteQueries
    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes().executeAsList().map { it.toNote() }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}