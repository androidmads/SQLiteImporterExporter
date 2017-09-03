package com.ajts.androidmads.sqliteimpexsample;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter;

public class MainActivity extends AppCompatActivity {

    SQLiteImporterExporter sqLiteImporterExporter;
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

        sqLiteImporterExporter = new SQLiteImporterExporter(getApplicationContext(), db);
        sqLiteImporterExporter.setOnImportListener(new SQLiteImporterExporter.ImportListener() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        sqLiteImporterExporter.setOnExportListener(new SQLiteImporterExporter.ExportListener() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dbQueries = new DbQueries(getApplicationContext());

        edtName = (EditText) findViewById(R.id.edtName);
        listView = (ListView) findViewById(R.id.listView);

        readDB();

        findViewById(R.id.btnDBExists).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sqLiteImporterExporter.isDataBaseExists()) {
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
                    sqLiteImporterExporter.importDataBaseFromAssets();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                readDB();
            }
        });

        findViewById(R.id.btnExportToExt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sqLiteImporterExporter.exportDataBase(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                readDB();
            }
        });

        findViewById(R.id.btnImportFromExt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sqLiteImporterExporter.importDataBase(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                readDB();

            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sqLiteImporterExporter.isDataBaseExists()) {
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

    private void readDB() {
        if (sqLiteImporterExporter.isDataBaseExists()) {
            dbQueries.open();
            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, dbQueries.getDetail());
            listView.setAdapter(adapter);
            dbQueries.close();
        } else {
            Toast.makeText(getApplicationContext(), "DB Doesn't Exists", Toast.LENGTH_SHORT).show();
        }
    }
}
