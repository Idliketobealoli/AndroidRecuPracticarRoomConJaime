package com.example.androidrecupracticarroomconjaime;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidrecupracticarroomconjaime.data.Element;
import com.example.androidrecupracticarroomconjaime.data.RoomDB;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etMain;
    Button bAdd, bDeleteAll;
    RecyclerView recycler;
    ElementRecyclerAdapter adapter;
    LinearLayoutManager manager;
    List<Element> elements;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMain = findViewById(R.id.et_main);
        bAdd = findViewById(R.id.b_add_main);
        bDeleteAll = findViewById(R.id.b_deleteAll_main);
        recycler = findViewById(R.id.rv_main);

        database = RoomDB.getInstance(this);
        elements = database.mainDao().findAll();

        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        adapter = new ElementRecyclerAdapter(elements, this);
        recycler.setAdapter(adapter);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etMain.getText().toString().trim();

                if (!text.isEmpty()) {
                    Element element = new Element();
                    element.setText(text);
                    element.setImage(R.drawable.picture_default);

                    database.mainDao().insert(element);

                    etMain.setText("");

                    elements.clear();
                    elements.addAll(database.mainDao().findAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        bDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDao().deleteAll(elements);

                elements.clear();
                elements.addAll(database.mainDao().findAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}