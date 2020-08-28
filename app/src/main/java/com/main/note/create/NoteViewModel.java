package com.main.note.create;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.main.dao.NoteDao;
import com.main.db.NoteRoomDatabase;
import com.main.model.Note;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Created by Vishal Shegaonkar
 */
public class NoteViewModel extends AndroidViewModel{

  private String TAG = NoteViewModel.class.getCanonicalName();
  private NoteDao noteDao;
  private NoteRoomDatabase noteDB;
  private LiveData<List<Note>> mAllNotes;

  public NoteViewModel(Application application) {
    super(application);
    noteDB = NoteRoomDatabase.getDatabase(application);
    noteDao = noteDB.noteDao();
    mAllNotes = noteDao.getAllNotes();
  }


  public void insert(Note note){ new InsertAsynkTask(noteDao).execute(note); }

  public LiveData<List<Note>>  getAllNotes(){ return mAllNotes;}

  public void Update(Note note){
    new UpdateAsyncTask(noteDao).execute(note);
  }

  public void deleteNote(Note note){
    new DeleteNoteAsyncTask(noteDao).execute(note);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    Log.i(TAG, "ViewModel Destroyed!");
  }

  private class OperationAsyncTask extends AsyncTask<Note, Void, Void>{
    NoteDao mAsyncNoteDao;

    public OperationAsyncTask(NoteDao mAsyncNoteDao) {
      this.mAsyncNoteDao = mAsyncNoteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
      return null;
    }
  }

  private class InsertAsynkTask extends OperationAsyncTask {

    public InsertAsynkTask(NoteDao noteDao) {
      super(noteDao);
    }

    @Override
    protected Void doInBackground(Note... notes) {
      mAsyncNoteDao.insert(notes[0]);
      return null;
    }
  }

  private class UpdateAsyncTask extends OperationAsyncTask{

    public UpdateAsyncTask(NoteDao noteDao) {
      super(noteDao);
    }

    @Override
    protected Void doInBackground(Note... notes) {
      mAsyncNoteDao.updateNote(notes[0]);
      return null;
    }
  }

  private class DeleteNoteAsyncTask extends OperationAsyncTask{

    public DeleteNoteAsyncTask(NoteDao noteDao) {
     super(noteDao);
    }

    @Override
    protected Void doInBackground(Note... notes) {
      mAsyncNoteDao.deleteNote(notes[0]);
      return null;
    }
  }
}
