package com.kmm.domain.note

import com.kmm.domain.time.DateTimeUtil

class SearchNotes {
    fun execute(notes: List<Note>, query: String): List<Note>{
        if (query.isBlank()){
            return notes
        }

        return notes.filter { it.title.trim().toLowerCase().contains(query.toLowerCase()) ||
            it.content.trim().toLowerCase().contains(query.toLowerCase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}