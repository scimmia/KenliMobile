package com.jiayusoft.mobile.kenli.utils.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.jiayusoft.mobile.kenli.R;
import com.jiayusoft.mobile.kenli.utils.io.FileUtil;

import java.io.*;

/**
 * Created by ASUS on 14-4-4.
 */
public class DBHelper{

    private static final String DB_NAME = "kenli.db";
    private final Context mContext;
    private SQLiteDatabase mDatabase = null;
    private boolean mIsInitializing = false;
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DBHelper(Context context) {
        this.mContext = context;
    }
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mDatabase != null) {
            if (!mDatabase.isOpen()) {
                // darn! the user closed the database by calling mDatabase.close()
                mDatabase = null;
            } else {
                return mDatabase;  // The database is already open for business
            }
        }

        if (mIsInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }

        SQLiteDatabase db = null;
        try {
            mIsInitializing = true;
            String path = mContext.getDatabasePath(DB_NAME).getPath();
            if (!(new File(path).exists())) {
                FileUtil.createFile(path);
                InputStream is = this.mContext.getResources().openRawResource(
                        R.raw.kenli);
                FileOutputStream fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY| SQLiteDatabase.NO_LOCALIZED_COLLATORS);

            mDatabase = db;
            return mDatabase;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mIsInitializing = false;
            if (db != null && db != mDatabase) db.close();
        }
        return null;
    }

    public synchronized void close() {
        if(mDatabase != null)
            mDatabase.close();
    }
}