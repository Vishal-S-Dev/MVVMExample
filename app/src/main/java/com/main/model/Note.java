package com.main.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Vishal Shegaonkar
 */

@Entity(tableName = "notes")
public class Note {
  @PrimaryKey
  @NonNull
  private String id;

  @NonNull
  @ColumnInfo(name = "note")
  private String mNote;

  public Note(@NonNull String id, @NonNull String mNote) {
    this.id = id;
    this.mNote = mNote;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getNote() {
    return this.mNote;
  }
}
