package com.kmm.android.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmm.domain.note.Note
import com.kmm.domain.time.DateTimeUtil


@Composable
fun NoteItem(
    note: Note,
    backgroundColor: Color,
    onNoteClick: () -> Unit,
    onNoteDelete: () -> Unit,
    modifier: Modifier = Modifier
) {

    val formattedDate = remember(note.created) {
        DateTimeUtil.formatNoteDate(note.created)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNoteClick() }
            .clip(RoundedCornerShape(5.dp))
            .background(color = backgroundColor)
            .padding(16.dp)

    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = note.title,
                style = TextStyle(color = Color.Black, fontSize = 22.sp)
            )
            IconButton(onClick = onNoteDelete) {
                Icon(Icons.Default.Close, contentDescription = "delete_note")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = note.content,
            style = TextStyle(fontSize = 15.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = formattedDate, modifier = Modifier.align(Alignment.End), style = TextStyle(fontSize = 11.sp))
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
fun PreviewNoteItem() {
    NoteItem(note = Note(null, "Note Title", "This is content in note", Note.generateRandomColor(), DateTimeUtil.now()),
             backgroundColor = Color.White, onNoteClick = { }, onNoteDelete = { })
}