package com.ht.testnotes.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.ht.testnotes.R;
import com.ht.testnotes.pojo.Notes;

import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA
 * Project: com.ht.com.ht.mynote.activities
 * Author: 安诺爱成长
 * Email: 1399487511@qq.com
 * Date: 2015/5/2
 */
public class EditActivity extends Activity {

    private EditText edittitle;
    private EditText editcontent;
    private Notes notes;
    private UriMatcher uriMatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edittitle = (EditText) findViewById(R.id.eidttitle);
        editcontent = (EditText) findViewById(R.id.editcontent);

        Intent intent = getIntent();
        notes = (Notes) intent.getSerializableExtra("notes");
        edittitle.setText(notes.getTitle());
        editcontent.setText(notes.getContent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String titleStr = edittitle.getText().toString();
        String contentStr = editcontent.getText().toString();

        if (!(notes.getTitle().equals(titleStr)) || !(notes.getContent().equals(contentStr))) {
            notes.setTitle(titleStr);
            notes.setContent(contentStr);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sDateFormat.format(System.currentTimeMillis());
            notes.setTime(time);


            if (editNotes(notes) > 0)
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    private int editNotes(Notes notes) {
        ContentResolver resolver = getContentResolver();
        String uriStr = "content://com.ht.mynote.contentprovider.MyContentProvider/mynotes";
        Uri uri = Uri.parse(uriStr);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", notes.getTitle());
        contentValues.put("content", notes.getContent());
        contentValues.put("time", notes.getTime());
        return resolver.delete(uri, "_id = ?", new String[]{notes.getId() + ""});
    }
}

