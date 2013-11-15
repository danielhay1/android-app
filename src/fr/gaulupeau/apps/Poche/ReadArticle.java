package fr.gaulupeau.apps.Poche;

import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARCHIVE;
import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARTICLE_CONTENT;
import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARTICLE_ID;
import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARTICLE_TITLE;
import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARTICLE_URL;
import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARTICLE_TABLE;
import static fr.gaulupeau.apps.Poche.ArticlesSQLiteOpenHelper.ARTICLE_AUTHOR;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import fr.gaulupeau.apps.InThePoche.R;

public class ReadArticle extends Activity {
	TextView txtTitre;
	TextView txtContent;
	TextView txtAuthor;
	Button btnMarkRead;
    SQLiteDatabase database;
    String id = "";
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article);
		ArticlesSQLiteOpenHelper helper = new ArticlesSQLiteOpenHelper(getApplicationContext());
		database = helper.getWritableDatabase();
		String[] getStrColumns = new String[] {ARTICLE_URL, ARTICLE_ID, ARTICLE_TITLE, ARTICLE_CONTENT, ARCHIVE, ARTICLE_AUTHOR};
		Bundle data = getIntent().getExtras();
		if(data != null) {
			id = data.getString("id");
		}
		Cursor ac = database.query(ARTICLE_TABLE, getStrColumns, ARTICLE_ID + "=" + id, null, null, null, null);
		ac.moveToFirst();
		txtTitre = (TextView)findViewById(R.id.txtTitre);
		txtTitre.setText(ac.getString(2));
		txtContent = (TextView)findViewById(R.id.txtContent);
		txtContent.setText(ac.getString(3));
		txtAuthor = (TextView)findViewById(R.id.txtAuthor);
		txtAuthor.setText(ac.getString(5));
		btnMarkRead = (Button)findViewById(R.id.btnMarkRead);
		btnMarkRead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(ARCHIVE, 1);
				database.update(ARTICLE_TABLE, values, ARTICLE_ID + "=" + id, null);
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		database.close();
	}
	
}
