package com.example.tugasday5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    Spinner spinner;
    public static final String[] languanges = {"Select Languanges", "Indonesia","Italia", "Spanyol"};
    EditText etnamapembeli, etkodebarang, etjumlahbarang;
    RadioGroup radiogroup;
    RadioButton rbgold, rbsilver, rbreguler;
    Button btnproses;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etnamapembeli = findViewById(R.id.etnamapembeli);
        etkodebarang = findViewById(R.id.etkodebarang);
        etjumlahbarang = findViewById(R.id.etjumlahbarang);
        radiogroup = findViewById(R.id.radiogroup);
        rbgold = findViewById(R.id.rbemas);
        rbsilver = findViewById(R.id.rbperak);
        rbreguler = findViewById(R.id.rbbiasa);
        btnproses = findViewById(R.id.btnproses);

        btnproses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaPembeli = etnamapembeli.getText().toString().trim();
                String kodeBarang = etkodebarang.getText().toString().trim();
                int jumlahbarang = Integer.parseInt(etjumlahbarang.getText().toString().trim());

                double hargaBarang = getHargaBarang(kodeBarang); // Mengambil harga barang
                if (hargaBarang == -1) {
                    Toast.makeText(MainActivity.this, "Kode Barang Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    return;
                }

                double totalHarga = hargaBarang * jumlahbarang;
                double diskonMember = diskonMember(totalHarga);
                double diskonHarga = diskonHarga(totalHarga);

                // Hitung total bayar setelah diskon
                double totalBayar = totalHarga - diskonMember - diskonHarga;

                String namaBarang = getNamaBarang(kodeBarang);
                tampilkanBon(namaPembeli, kodeBarang, namaBarang, hargaBarang, jumlahbarang, totalHarga, diskonMember, diskonHarga, totalBayar);
            }
        });
    }

    private double getHargaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "IPX":
                return 5725300;
            case "AV4":
                return 9150999;
            case "MP3":
                return 28999999;
            default:
                return -1;
        }
    }

    private String getNamaBarang(String kodeBarang) {
        switch (kodeBarang) {
            case "IPX":
                return "Iphone X";
            case "AV4":
                return "Asus Vivobook 14";
            case "MP3":
                return "MACBOOK PRO M3";
            default:
                return null;
        }
    }

    private double diskonMember(double totalHarga) {
        double diskonMember = 0;
        int selectedRadioButtonId = radiogroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.rbemas) {
            diskonMember = totalHarga * 0.1;
        } else if (selectedRadioButtonId == R.id.rbperak) {
            diskonMember = totalHarga * 0.05;
        } else if (selectedRadioButtonId == R.id.rbbiasa) {
            diskonMember = totalHarga * 0.02;

        }
        return diskonMember;
    }

    private double diskonHarga(double totalHarga) {
        if (totalHarga > 10000000) {
            return 100000;
        }
        return 0;
    }

    private void tampilkanBon(String namaPembeli, String kodeBarang, String namaBarang, double hargaBarang, int jumlahBarang, double totalHarga, double potonganHarga, double diskon, double totalBayar) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        String hargabarang = "Rp " + decimalFormat.format(hargaBarang);
        String hargaJumlah= decimalFormat.format(jumlahBarang);
        String hargatotal = "Rp " + decimalFormat.format(totalHarga);
        String hargabayar = "Rp " + decimalFormat.format(totalBayar);
        String hargaPotongan = "Rp " + decimalFormat.format(potonganHarga);
        String Diskontotal = "Rp " + decimalFormat.format(diskon);

        String BonBelanja = getString(R.string.nama_pembeli)  + namaPembeli + "\n"
                + getString(R.string.kode_barang)  + kodeBarang + "\n"
                + getString(R.string.nama_barang)  + namaBarang + "\n\n"
                + getString(R.string.harga_barang) + hargabarang + "\n"
                + getString(R.string.jumlah_barang) +hargaJumlah + "\n"
                + getString(R.string.total_harga_barang) + hargatotal + "\n\n"
                + getString(R.string.diskon_membership)+ hargaPotongan + "\n"
                + getString(R.string.diskon_barang) + Diskontotal + "\n\n"
                + getString(R.string.total_pembayaran)+ hargabayar;

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("Bon Belanja", BonBelanja);
        startActivity(intent);
    }
}
