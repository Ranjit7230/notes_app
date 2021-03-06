 package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

 public class Editor extends AppCompatActivity {

    private DBHelper dbHelper;
    private boolean isEdit=false;
    private Info editNoteInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

       final EditText mEtTitle=findViewById(R.id.et_title);
        final EditText mEtNote=findViewById(R.id.et_note);

        Button mBtnSave=findViewById(R.id.btn_save);
        Button mBtnCancel=findViewById(R.id.btn_cancel);


        dbHelper=new DBHelper(Editor.this);
        Bundle data=getIntent().getExtras();
        if(data!=null) {
            isEdit = data.getBoolean("IS_EDIT");
            editNoteInfo = (Info) data.getSerializable("NOTE_INFO");

            if (isEdit) {
                mEtTitle.setText(editNoteInfo.iTitle);
                mEtNote.setText(editNoteInfo.iNote);
            }
        }
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEtTitle.getText().toString();
                String note=mEtNote.getText().toString();

                Info notes1=new Info();
                notes1.iTitle=title;
                notes1.iNote=note;

                if(!isEdit) {
                    dbHelper.insertDataToDatabase(notes1, dbHelper.getWritableDatabase());
                }
                else {
                    notes1.id=editNoteInfo.id;

                    dbHelper.updateNoteData(dbHelper.getWritableDatabase(),notes1);
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                Toast.makeText(Editor.this,"saved...!",Toast.LENGTH_LONG).show();

                mEtTitle.setText("");
                mEtNote.setText("");

            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<Info> notesList = dbHelper.getNotesInfoFromDatabase(dbHelper.getReadableDatabase());
//                Toast.makeText(Editor.this,""+notesList.size(),Toast.LENGTH_LONG).show();
                startActivity(new Intent(Editor.this,MainActivity.class));
            }
        });
    }
}