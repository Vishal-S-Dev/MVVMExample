package com.main.db;

import android.content.Context;

import com.main.dao.NoteDao;
import com.main.model.Note;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Vishal Shegaonkar
 */

@Database(entities = Note.class, version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {

  public abstract NoteDao noteDao();
  private static volatile NoteRoomDatabase noteRoomInstance;

  public static NoteRoomDatabase getDatabase(final Context context){
      if(noteRoomInstance == null){
        synchronized (NoteRoomDatabase.class){
          if(noteRoomInstance == null){
            noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                NoteRoomDatabase.class,"note_database").build();
          }

        }
      }
      return noteRoomInstance;
  }
}
