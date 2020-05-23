package com.example.konovodov_hw_422_customlist;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.graphics.drawable.Drawable;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Генератор случайностей
    private Random random = new Random();
    // Наш адаптер
    private ItemsDataAdapter adapter;
    private ItemsDataAdapter adapter1;
    // Список картинок, которые мы будем брать для нашего списка
    private List<Drawable> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);
        setSupportActionBar(toolbar);

        fillImages();

        // При тапе по кнопке добавим один новый элемент списка
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomItemData();
            }
        });
        // Создаем и устанавливаем адаптер на наш список
        adapter = new ItemsDataAdapter(this, null);

        listView.setAdapter(adapter);

        // При долгом тапе по элементу списка будем показывать его данные
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showItemData(position);
                //adapter.removeItem(position);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_open_notes) {
            Intent intentNotes = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intentNotes);
            return true;
        }
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Открыть настройки", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Заполним различными картинками, которые встроены в сам Android
    // ContextCompat обеспечит нам поддержку старых версий Android
    private void fillImages() {


        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_report_image));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_add));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_agenda));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_camera));
        images.add(ContextCompat.getDrawable(MainActivity.this,
                android.R.drawable.ic_menu_call));
    }

    // Создадим ну почти случайные данные для нашего списка.
    // random.nextInt(граница_последнего_элемента)
    // Для каждого элемента мы возьмем 1 случайную картинку
    // из 5, которые мы сделали вначале.

    private void generateRandomItemData() {

        adapter.addItem(new ItemData(
                images.get(random.nextInt(images.size())),
                getResources().getStringArray(R.array.Hello)[(adapter.getCount() * 2) % 16],
                getString(R.string.subTitleCategory) + " " + adapter.getCount(),
                getResources().getStringArray(R.array.Hello)[((adapter.getCount() * 2) % 16) + 1]));

    }

    // Покажем сообщение с данными
    private void showItemData(int position) {
        ItemData itemData = adapter.getItem(position);
        Toast.makeText(MainActivity.this,
                "Title: " + itemData.getTitle() + "\n" +
                        "SubtitleCat: " + itemData.getSubtitleCategory() + "\n" +
                        "Subtitle: " + itemData.getSubtitle() + "\n",
                Toast.LENGTH_SHORT).show();
    }
}