package com.main.note.create;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.R;

import androidx.appcompat.app.AppCompatActivity;

public class NewNoteActivity extends AppCompatActivity {

  public static final String NOTE_ADDED = "new_note";
  private EditText edt;
  private Button btnDSave;
  private TextView toolbarTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_note);
    initView();
    toolbarTitle.setText("New Note");
    btnDSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent resultIntent = new Intent();

        if (TextUtils.isEmpty(edt.getText())) {
          setResult(RESULT_CANCELED, resultIntent);
        } else {
          String note = edt.getText().toString().trim();
          resultIntent.putExtra(NOTE_ADDED, note);
          setResult(RESULT_OK, resultIntent);
        }
        finish();
      }
    });
  }

  private void initView() {
    edt = findViewById(R.id.edt);
    btnDSave = findViewById(R.id.btnDSave);
    toolbarTitle = findViewById(R.id.toolbar_title);
  }
}