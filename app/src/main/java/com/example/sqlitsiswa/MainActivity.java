package com.example.sqlitsiswa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends ListActivity {
    String dataSiswa[] = null;
    String dS[] = null;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tambah Siswa
        DatabaseHandler db = new DatabaseHandler(this);
        this.db = db;
        // Membaca Semua Siswa

        Log.d("Tambah Siswa: ", "Menambah Data Siswa..");
        db.addSiswa(new Siswa("001", "Ghiyatsi Miftahur Rahmat"));
        db.addSiswa(new Siswa("002", "Annisa Fitriana"));
        db.addSiswa(new Siswa("003", "Andina Nur Amalia"));
        db.addSiswa(new Siswa("004", "Najwa AuliaDhofiroh"));
        loadDb();
        registerForContextMenu(getListView());
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // TODOAuto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Action");
        menu.add(0,0,0,"Tambah");
        menu.add(0,1,1,"Hapus");
        menu.add(0,2,2,"Update");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
// TODOAuto-generated method stub
        try{
            switch(item.getItemId()){
                case 0:{
                    Class c =
                            Class.forName("com.example.sqlitsiswa.TambahActivity");
                    Intent i = new Intent(MainActivity.this,
                            c);
                    startActivity(i); break;
                }
                case 1:{
                    DatabaseHandler db = new DatabaseHandler(this);
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    String[] args = {String.valueOf(info.id) };
                    int xpos=Integer.parseInt(args[0]);
                    db.deleteRow(dS[xpos]);
                    this.loadDb();
                    break;
                }
                case 2:{
                    DatabaseHandler db = new DatabaseHandler(this);
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    String[] args ={String.valueOf(info.id)};
                    Log.d("args0 : ",args[0]);
                    int xpos=Integer.parseInt(args[0]);
                    db.getSiswa(dS[xpos]);
                    String namax=db.getSiswa(dS[xpos]).getNama();
                    Intent i = new Intent(this, UpdateActivity.class);
                    Bundle bun = new Bundle();
                    bun.putString("nis", dS[xpos]);
                    bun.putString("nama", namax);
                    i.putExtras(bun);
                    startActivity(i); break;
                }
            }
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void loadDb() {
        List<Siswa> siswa = db.getSemuaSiswa();
        dataSiswa= new String[siswa.size()];
        dS= new String[siswa.size()];
        int i=0;
        for(Siswa s : siswa) {
            String log = "NIS: "+ s.getNis() + ",Nama: "+ s.getNama();
            Log.d("Name: ", log);
            dataSiswa[i] = s.getNis() + " - "+ s.getNama();
            dS[i] = s.getNis();
            i++;
        }
        setListAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, dataSiswa));
    }
}