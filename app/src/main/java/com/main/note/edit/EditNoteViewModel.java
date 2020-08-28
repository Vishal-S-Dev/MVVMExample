package com.main.note.edit;

import android.app.Application;

import com.main.dao.NoteDao;
import com.main.db.NoteRoomDatabase;
import com.main.model.Note;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Created by Vishal Shegaonkar
 */
public class EditNoteViewModel extends AndroidViewModel {

  private final String TAG = EditNoteViewModel.class.getSimpleName();

  private NoteDao noteDao;
  private NoteRoomDatabase noteDB;

  public EditNoteViewModel(Application application) {
    super(application);
    noteDB = NoteRoomDatabase.getDatabase(application);
    noteDao = noteDB.noteDao();
  }

  public LiveData<Note> getNote(String noteId){
    return noteDao.getNote(noteId);
  }

}
