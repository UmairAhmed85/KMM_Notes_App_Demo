package com.kmm.android.note_list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmm.domain.note.Note
import com.kmm.domain.note.NoteDataSource
import com.kmm.domain.note.SearchNotes
import com.kmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val searchNotes = SearchNotes()
    private val notes = savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchString = savedStateHandle.getStateFlow("searchedText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(notes, searchString, isSearchActive) { notes, searchString, isSearchActive ->
        NoteListState(
            notesList = searchNotes.execute(notes, searchString),
            searchQuery = searchString,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteListState())

    /*init {
        viewModelScope.launch {
            (1..10).forEach {
                noteDataSource.insertNote(
                    Note(null, "Note $it", "This is  note $it", Note.generateRandomColor(), DateTimeUtil.now())
                )
            }
        }
    }*/

    fun loadNotes() {
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }

    fun onSearchTextChanged(query: String) {
        viewModelScope.launch {
            savedStateHandle["searchedText"] = query
        }
    }

    fun onToggleSearch() {
        viewModelScope.launch {
            savedStateHandle["isSearchActive"] = !isSearchActive.value
            if (!isSearchActive.value) {
                savedStateHandle["searchedText"] = ""
            }
        }
    }

    fun deleteNotes(id: Long) {
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }

    fun insertNotes(note: Note) {
        viewModelScope.launch {
            noteDataSource.insertNote(note)
        }
    }


}