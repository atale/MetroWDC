package ca.atale.metrowdc;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DataProvider extends ContentProvider {


    private MainDatabaseHelper dbHelper;

    private static final String DBNAME = "railLines";

    // Holds the database object
    private SQLiteDatabase db;
    
	@Override
	public boolean onCreate() {
		
		Context context = getContext();
		dbHelper = new MainDatabaseHelper(context);
	    return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

	     // Insert code here to determine which table to open, handle error-checking, and so forth


        /*
         * Gets a writeable database. This will trigger its creation if it doesn't already exist.
         *
         */
        db = dbHelper.getWritableDatabase();
		
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private static final String SQL_RAIL_TABLE = "CREATE TABLE " +
		    DBNAME +                       
		    "(" +                           
		    " _ID INTEGER PRIMARY KEY, " +
		    " NAME" +
		    " ENDSTATCODE " +
		    " INTDESTCODE1 " +
		    " INTDESTCODE2 " +
		    " LINECODE " +
		    " STARTSTATCODE )";
    
	protected static final class MainDatabaseHelper extends SQLiteOpenHelper {


	    MainDatabaseHelper(Context context) {
	        super(context, DBNAME, null, 1);
	    }


	    public void onCreateRail(SQLiteDatabase db) {

	        // Creates the main table
	        db.execSQL(SQL_RAIL_TABLE);
	    }


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub			
		}
	}

}
