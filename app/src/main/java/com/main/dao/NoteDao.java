package com.main.dao;

import com.main.model.Note;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Vishal Shegaonkar
 */

@Dao
public interface NoteDao {
  @Insert
  void insert(Note note);

  @Query("Select * from notes")
  LiveData<List<Note>> getAllNotes();

  @Query("SELECT * FROM notes WHERE id =:noteId")
  LiveData<Note> getNote(String noteId);

  @Update
  void updateNote(Note note);

  @Delete
  void deleteNote(Note note);


}
