package com.imagerecognizer.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button birdIdientifier;
    private Button foodIdentifier;
    private Button insectIdentifier;
    private Button plantIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birdIdientifier = findViewById(R.id.bt_birdIdentifier);
        foodIdentifier = findViewById(R.id.bt_foodIdentifier);
        insectIdentifier = findViewById(R.id.bt_insectIdentifier);
        plantIdentifier = findViewById(R.id.bt_plantIdentifier);

        birdIdientifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BirdIdentifier.class);
                startActivity(intent);

            }
        });

        foodIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodIdentifier.class);
                startActivity(intent);
            }
        });

        insectIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsectIdentifier.class);
                startActivity(intent);
            }
        });

        plantIdentifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlantIdentifier.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact:
                Intent intent1 = new Intent(MainActivity.this, Contact.class);
                startActivity(intent1);
                return true;
            case R.id.privacypolicy:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://zroby05.github.io/datenschutzbifpidentifier/home.html"));
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}