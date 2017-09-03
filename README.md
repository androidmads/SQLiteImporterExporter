# SQLiteImporterExporter
A light weight library for exporting and importing sqlite database in android
## Created By
[![API](https://img.shields.io/badge/AndroidMads-AJTS-brightgreen.svg?style=flat)](https://github.com/androidmads/SQLiteImporterExporter)
## How to Download
<b>Gradle:</b>
```groovy
compile 'com.ajts.androidmads.sqliteimpex:library:1.0.0'
```
<b>Maven:</b>
```groovy
<dependency>
  <groupId>com.ajts.androidmads.sqliteimpex</groupId>
  <artifactId>library</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
## How to use this Library:
This Library is used to import SQLite Database from Assets or External path and Export/Backup SQLite Database to external path.

```java
SQLiteImporterExporter sqLiteImporterExporter = new SQLiteImporterExporter(getApplicationContext(), db);

// Listeners for Import and Export DB
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
```

#### To Import SQLite from Assets 
```java
try {
	sqLiteImporterExporter.importDataBaseFromAssets();
} catch (Exception e) {
	e.printStackTrace();
}
```

#### To import from external storage
```java
try {
	sqLiteImporterExporter.importDataBase(path);
} catch (Exception e) {
	e.printStackTrace();
}
```

#### To export to external storage
```java
try {
	sqLiteImporterExporter.exportDataBase(path);
} catch (Exception e) {
	e.printStackTrace();
}
```
#### License
```
MIT License

Copyright (c) 2017 AndroidMad / Mushtaq M A

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
