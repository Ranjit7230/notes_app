package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotesInfoAdapter.NotesCellClickListener {

    private DBHelper dbHelper;
    private RecyclerView mRcNotesInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       mRcNotesInfo=findViewById(R.id.rc_note_infos);
       mRcNotesInfo.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        Toolbar mToolbar = findViewById(R.id.tl_main);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dbHelper=new DBHelper((this));
        loadDataToDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: onBackPressed();
            break;
            case R.id.action_create:
                Intent editor=new Intent(MainActivity.this,Editor.class);
                startActivity(editor);
                break;
        }
        return true;
    }

    private void loadDataToDatabase(){
        ArrayList<Info> notesInfo = dbHelper.getNotesInfoFromDatabase(dbHelper.getReadableDatabase());
        NotesInfoAdapter adapter=new NotesInfoAdapter(this,notesInfo);
        adapter.setListener(this);
        mRcNotesInfo.setAdapter(adapter);
    }


    @Override
    public void onEditClicked(Info notesInfo) {
      // Toast.makeText(MainActivity.this,"Edit clicked",Toast.LENGTH_LONG).show();
        Intent editIntent=new Intent(MainActivity.this,Editor.class);
        editIntent.putExtra("NOTE_INFO",notesInfo);
        editIntent.putExtra("IS_EDIT",true);
        startActivityForResult(editIntent,134);

    }

    @Override
    public void onDeleteClicked(Info notesInfo) {
        dbHelper.deleteNoteData(dbHelper.getWritableDatabase(),notesInfo);

        Toast.makeText(MainActivity.this,"Delete clicked...",Toast.LENGTH_LONG).show();
        loadDataToDatabase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==134){
            if(resultCode== Activity.RESULT_OK){
                loadDataToDatabase();
            }
            else {
                Toast.makeText(MainActivity.this,"Cancelled...!",Toast.LENGTH_LONG).show();
            }
        }
    }
}