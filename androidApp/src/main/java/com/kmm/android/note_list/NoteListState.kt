package com.kmm.android.note_list

import com.kmm.domain.note.Note

data class NoteListState (
    val  notesList: List<Note> = emptyList(),
    val searchQuery: String = "",
    val isSearchActive: Boolean = false
    )