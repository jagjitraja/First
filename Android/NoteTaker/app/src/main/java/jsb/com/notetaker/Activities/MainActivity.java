package jsb.com.notetaker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.R;

public class MainActivity extends AppCompatActivity {

	public final static String APP_ID_KEY = "loggingID";

	public final static String LAUNCH_NOTE_DETAIL = "LAUNCH_NOTE_DETAIL_ACTIVITY";
	public final static String NOTE_TITLE_KEY = "NOTE TITLE";
	public final static String NOTE_BODY_KEY = "NOTE BODY";
	public final static String NOTE_DATE_KEY = "NOTE DATE";
	public final static String NOTE_CATEGORY_KEY = "NOTE CATEGORY";
	public final static String NOTE_ID_KEY = "NOTE ID KEY";
	public final static String CHANGES_MADE = "CHANGES MADE";
	public final static String DELETE_CALL = "DELETE NOTE";
	public final static String SAVE_NOTES = "CHANGES MADE";

	public final static String INTENT_MOTIVE = "FRAGMENT TO ADD";
	public final static String VIEW_NOTE_MOTIVE = "1";
	public final static String EDIT_NOTE_MOTIVE = "2";
	public final static String ADD_NOTE_MOTIVE = "3";
	public final static String SAVE_DIALOGUE_IS_SHOWING = "SAVE_DIALOGUE IS DISPLAYED ON SCREEN";
	public final static String CHOICE_DIALOGUE_IS_SHOWING = "CHOICE DIALOGUE IS DISPLAYED ON SCREEN";

//TODO when app comes form background to launch on activity it was on

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		switch (id) {
			case R.id.action_settings:
				return true;
			case R.id.add_note_button:
				Intent intent = new Intent(this, NoteDetailActivity.class);
				intent.putExtra(INTENT_MOTIVE, "3");
				intent.putExtra(NOTE_CATEGORY_KEY, Note.Category.PRIVATE);
				intent.putExtra(NOTE_BODY_KEY, " ");
				intent.putExtra(NOTE_TITLE_KEY, " ");
				intent.putExtra(NOTE_DATE_KEY," ");
				intent.putExtra(NOTE_ID_KEY, -1);
				startActivity(intent);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		System.gc();
		finish();
	}

}
