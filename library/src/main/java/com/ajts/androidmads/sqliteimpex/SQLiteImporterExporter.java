package com.ajts.androidmads.sqliteimpex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteImporterExporter extends SQLiteOpenHelper {
    private Context context;
    private String DB_PATH;
    private String DB_NAME;
    public ImportListener importListener;
    public ExportListener exportListener;

    @SuppressLint("SdCardPath")
    public SQLiteImporterExporter(Context context, String DB_NAME) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        this.DB_NAME = DB_NAME;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    public void setOnImportListener(ImportListener importListener) {
        this.importListener = importListener;
    }

    public void setOnExportListener(ExportListener exportListener) {
        this.exportListener = exportListener;
    }

    public boolean isDataBaseExists() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void importDataBaseFromAssets() {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            this.getReadableDatabase();
            myInput = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            if (importListener != null)
                importListener.onSuccess("Successfully Imported");
        } catch (Exception e) {
            if (importListener != null)
                importListener.onFailure(e);
        } finally {
            // Close the streams
            try {
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException e) {
                if (importListener != null)
                    importListener.onFailure(e);
            }
        }
    }

    public void importDataBase(String path) {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            this.getReadableDatabase();
            String inFileName = path + DB_NAME;
            myInput = new FileInputStream(inFileName);
            String outFileName = DB_PATH + DB_NAME;
            myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            if (importListener != null)
                importListener.onSuccess("Successfully Imported");
        } catch (Exception e) {
            if (importListener != null)
                importListener.onFailure(e);
        } finally {
            try {
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException ioe) {
                if (importListener != null)
                    importListener.onFailure(ioe);
            }
        }

    }

    public void exportDataBase(String path) {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            String inFileName = DB_PATH + DB_NAME;
            myInput = new FileInputStream(inFileName);
            String outFileName = path + DB_NAME;
            myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            if (exportListener != null)
                exportListener.onSuccess("Successfully Exported");
        } catch (Exception e) {
            if (exportListener != null)
                exportListener.onFailure(e);
        } finally {
            try {
                // Close the streams
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (Exception ex) {
                if (exportListener != null)
                    exportListener.onFailure(ex);
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    public interface ImportListener {
        void onSuccess(String message);

        void onFailure(Exception exception);
    }

    public interface ExportListener {
        void onSuccess(String message);

        void onFailure(Exception exception);
    }

}
