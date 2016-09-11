package com.androidmads.sqliteSample;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import androidmads.dbimporterexporter.DBImporterExporter;

public class MainActivity extends AppCompatActivity {

    DBImporterExporter dbImporterExporter;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    public static String db = "external_db_android.sqlite";

    DbQueries dbQueries;

    EditText edtName;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbImporterExporter = new DBImporterExporter(getApplicationContext(), db);

        dbQueries = new DbQueries(getApplicationContext());

        edtName = (EditText) findViewById(R.id.edtName);
        listView = (ListView) findViewById(R.id.listView);

        readDB();

        findViewById(R.id.btnDBExists).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbImporterExporter.isDataBaseExists()) {
                    Toast.makeText(getApplicationContext(), "DB Exists", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "DB Doesn't Exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnImportFromAssets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbImporterExporter.importDataBaseFromAssets();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                readDB();
            }
        });

        findViewById(R.id.btnExportToExt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbImporterExporter.exportDataBase(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                readDB();
            }
        });

        findViewById(R.id.btnImportFromExt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbImporterExporter.importDataBase(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                readDB();

            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbImporterExporter.isDataBaseExists()) {
                    if (edtName.getText().toString().trim().length() > 0) {
                        dbQueries.open();
                        long success = dbQueries.insertDetail(edtName.getText().toString().trim());
                        if (success > -1) {
                            edtName.setText(null);
                            edtName.clearFocus();
                            Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Insertion Failed", Toast.LENGTH_LONG).show();
                        }
                        dbQueries.close();
                        readDB();
                    } else {
                        edtName.setError("Enter Name");
                    }
                } else {
                    edtName.setError("Import DB First");
                }
            }
        });

    }

    private void readDB(){
        if (dbImporterExporter.isDataBaseExists()) {
            dbQueries.open();
            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, dbQueries.getDetail());
            listView.setAdapter(adapter);
            dbQueries.close();
        } else {
            Toast.makeText(getApplicationContext(), "DB Doesn't Exists", Toast.LENGTH_SHORT).show();
        }
    }
}
