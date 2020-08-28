package com.main.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.main.R;
import com.main.model.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishal Shegaonkar
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

  private LayoutInflater layoutInflater;
  private Context context;
  private List<Note> noteList;
  private TextView tvtitle;
  public OnItemClickListeners onItemClickListeners;


  public NoteListAdapter(Context context, OnItemClickListeners onItemClickListeners) {
    layoutInflater = LayoutInflater.from(context);
    this.context = context;
    this.onItemClickListeners = onItemClickListeners;
  }

  public interface OnItemClickListeners {
    void onClick(Note note);

    void onDelete(Note note);
  }

  @NonNull
  @Override
  public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
    NoteViewHolder viewHolder = new NoteViewHolder(itemView);
    return viewHolder;
  }


  @Override
  public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
    if (noteList != null) {
      final Note note = noteList.get(position);
      holder.setData(note.getNote(), position);
      holder.setListeners();
    } else {
      holder.tvtitle.setText("No Notes!");
    }
  }

  @Override
  public int getItemCount() {
    if (noteList != null) {
      return noteList.size();
    } else {
      return 0;
    }
  }

  public void setNotes(List<Note> notes) {
    noteList = notes;
    notifyDataSetChanged();
  }

  public class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView tvtitle;
    private int mPosition;
    private ImageButton ivDelete;
    private ImageButton ivEdit;

    public NoteViewHolder(@NonNull View itemView) {
      super(itemView);
      initView(itemView);
    }

    private void initView(View v) {
      tvtitle = v.findViewById(R.id.tvtitle);
      ivDelete = v.findViewById(R.id.ivDelete);
      ivEdit = v.findViewById(R.id.ivEdit);
    }

    public void setData(String note, int position) {
      tvtitle.setText(note);
      mPosition = position;
    }

    public void setListeners() {
      ivEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (onItemClickListeners != null) {
            onItemClickListeners.onClick(noteList.get(mPosition));
          }
        }
      });

      ivDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (onItemClickListeners != null) {
            onItemClickListeners.onDelete(noteList.get(mPosition));
          }
        }
      });
    }
  }


}
