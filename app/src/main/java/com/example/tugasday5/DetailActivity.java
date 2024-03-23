package com.example.tugasday5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String bonBelanja = getIntent().getStringExtra("Bon Belanja");

        TextView textViewDeskripsi = findViewById(R.id.tvdesc);
        textViewDeskripsi.setText(bonBelanja);

        Button btnShare = findViewById(R.id.btnshare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, bonBelanja);
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.Bagikan_Dengan)));
            }
        });
    }
}
