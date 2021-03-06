package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesInfoAdapter extends RecyclerView.Adapter<NotesInfoAdapter.NotesInfoHolder> {


    private Context context;
    private ArrayList<Info> notesInfoList;
    private NotesCellClickListener listener;

    public NotesInfoAdapter(Context context,ArrayList<Info> notesInfoList){
        this.context=context;
        this.notesInfoList=notesInfoList;
    }
    public NotesInfoAdapter(){

    }



    public void setContext(Context context) {
        this.context = context;
    }

  //  public ArrayList<Info> getNotesInfoList() {
    //    return notesInfoList;
  //  }

    public void setNotesInfoList(ArrayList<Info> notesInfoList) {
        this.notesInfoList = notesInfoList;
    }

    @NonNull

    @Override
    public NotesInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.cell_notes_info, parent,false);
        NotesInfoHolder holder = new NotesInfoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesInfoAdapter.NotesInfoHolder holder, int position) {


       final Info note=notesInfoList.get(position);

        holder.mTvNotesTitle.setText(note.iTitle);
        holder.mTvNotesNote.setText(note.iNote);

        holder.mLlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onEditClicked(note);
                }
            }
        });
        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onDeleteClicked(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesInfoList.size();
    }

    public void setListener(NotesCellClickListener listener) {
        this.listener=listener;
   }

    class NotesInfoHolder extends RecyclerView.ViewHolder{

        private TextView mTvNotesTitle;
        private TextView mTvNotesNote;
        private LinearLayout mLlEdit;
        private ImageView mIvDelete;

        public NotesInfoHolder(@NonNull View itemView) {
            super(itemView);

            mTvNotesTitle=itemView.findViewById(R.id.tv_title);
            mTvNotesNote=itemView.findViewById(R.id.tv_note);

            mLlEdit=itemView.findViewById(R.id.ll_edit_note);
            mIvDelete=itemView.findViewById(R.id.iv_delete);
        }
    }
    public interface NotesCellClickListener{
        void onEditClicked(Info notesInfo);
        void onDeleteClicked(Info notesInfo);
    }
}
