package com.example.mobileapp12;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private LinearLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.tableLayout);
        Button displayButton = findViewById(R.id.displayButton);

        database = SQLiteDatabase.openOrCreateDatabase(":memory:", null);

        database.execSQL("CREATE TABLE IF NOT EXISTS people (id INTEGER PRIMARY KEY, name TEXT, achievement TEXT)");

        database.execSQL("INSERT INTO people (name, achievement) VALUES ('Linus Torvalds', 'Создание Linux')");
        database.execSQL("INSERT INTO people (name, achievement) VALUES ('Ada Lovelace', 'Первый алгоритм')");
        database.execSQL("INSERT INTO people (name, achievement) VALUES ('Alan Turing', 'Теория вычислимости')");

        displayButton.setOnClickListener(v -> displayDatabase());
    }

    private void displayDatabase() {

        tableLayout.removeAllViews();

        Cursor cursor = database.rawQuery("SELECT name, achievement FROM people", null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String achievement = cursor.getString(1);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(name);
            nameTextView.setTextSize(20);
            nameTextView.setTypeface(null, android.graphics.Typeface.BOLD);
            nameTextView.setPadding(8, 8, 8, 8);

            TextView achievementTextView = new TextView(this);
            achievementTextView.setText(achievement);
            achievementTextView.setTextSize(16);
            achievementTextView.setTypeface(null, android.graphics.Typeface.ITALIC);
            achievementTextView.setPadding(8, 8, 8, 16);

            tableLayout.addView(nameTextView);
            tableLayout.addView(achievementTextView);

            View divider = new View(this);
            divider.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    2
            ));
            divider.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            tableLayout.addView(divider);
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();
        }
    }
}