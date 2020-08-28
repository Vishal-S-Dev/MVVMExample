package com.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.main.R;
import com.main.common.Constants;
import com.main.model.Note;
import com.main.note.adapter.NoteListAdapter;
import com.main.note.create.NewNoteActivity;
import com.main.note.create.NoteViewModel;
import com.main.note.edit.EditNoteActivity;

import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements NoteListAdapter.OnItemClickListeners {
  private static final String TAG = HomeFragment.class.getSimpleName();
  public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
  public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  private String mParam1;
  private String mParam2;

  private RecyclerView recyclerView;
  private FloatingActionButton fab;

  private NoteViewModel noteViewModel;
  private NoteListAdapter noteListAdapter;
  private Context mContext;
  private Toolbar toolbar;
  private TextView toolbarTitle;

  public HomeFragment() {
  }

  public static HomeFragment newInstance(String param1, String param2) {
    HomeFragment fragment = new HomeFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    initView(view);
    toolbarTitle.setText("NOTES");

    noteListAdapter = new NoteListAdapter(getActivity(), HomeFragment.this);
    recyclerView.setAdapter(noteListAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent i = new Intent(getActivity(), NewNoteActivity.class);
        startActivityForResult(i, NEW_NOTE_ACTIVITY_REQUEST_CODE);
      }
    });

    /*
     * Use this when NoteViewModel class extends ViewModel
     * noteViewModel= new ViewModelProvider(getActivity()).get(NoteViewModel.class);
     */


    /*
     * Use this when NoteViewModel class extends AndroidViewModel
     */

    noteViewModel =
        new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(
            NoteViewModel.class);

    noteViewModel.getAllNotes().observe(getActivity(), new Observer<List<Note>>() {
      @Override
      public void onChanged(List<Note> notes) {
        noteListAdapter.setNotes(notes);
      }
    });


    return view;
  }

  private void initView(View v) {

    recyclerView = v.findViewById(R.id.recyclerView);
    fab = v.findViewById(R.id.fab);
    toolbar = v.findViewById(R.id.toolbar);
    toolbarTitle = v.findViewById(R.id.toolbar_title);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

      String note_id = UUID.randomUUID().toString();
      Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
      noteViewModel.insert(note);

      Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

    } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
      Note note = new Note(
          data.getStringExtra(Constants.NOTE_ID),
          data.getStringExtra(Constants.UPDATED_NOTE)
      );
      noteViewModel.Update(note);

      Toast.makeText(
          getActivity(), "Updated", Toast.LENGTH_SHORT)
          .show();
    } else {

      Toast.makeText(getActivity(), "Not Saved", Toast.LENGTH_SHORT).show();

    }
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override
  public void onClick(Note note) {
    Intent intent = new Intent(getActivity(), EditNoteActivity.class);
    intent.putExtra(Constants.NOTE_ID, note.getId());
    startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
  }

  @Override
  public void onDelete(Note note) {
    noteViewModel.deleteNote(note);
  }
}