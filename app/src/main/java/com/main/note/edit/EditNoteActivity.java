package com.main.note.edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.R;
import com.main.common.Constants;
import com.main.model.Note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class EditNoteActivity extends AppCompatActivity {

  private EditText edtNote;
  private Button btnUpdate;
  private Button btnCancel;
  private Bundle bundle;
  private String noteId;
  private LiveData<Note> note;

  private EditNoteViewModel noteViewModel;
  private TextView toolbarTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_note);
    initView();
    toolbarTitle.setText("Edit Note");

    bundle = getIntent().getExtras();
    if (bundle != null) {
      noteId = bundle.getString(Constants.NOTE_ID);

    }
    noteViewModel =
        new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(
            EditNoteViewModel.class);
    note = noteViewModel.getNote(noteId);
    note.observe(this, new Observer<Note>() {
      @Override
      public void onChanged(Note note) {
        edtNote.setText(note.getNote());
      }
    });

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        updateNote(view);
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        cancelUpdate(view);
      }
    });
  }

  private void initView() {
    edtNote = findViewById(R.id.edtNote);
    btnUpdate = findViewById(R.id.btnUpdate);
    btnCancel = findViewById(R.id.btnCancel);
    toolbarTitle = findViewById(R.id.toolbar_title);
  }

  public void updateNote(View view) {
    String note = edtNote.getText().toString().trim();
    Intent resultIntent = new Intent();
    resultIntent.putExtra(Constants.NOTE_ID, noteId);
    resultIntent.putExtra(Constants.UPDATED_NOTE, note);
    setResult(RESULT_OK, resultIntent);
    finish();
  }

  public void cancelUpdate(View view) {
    finish();
  }

}