package jsb.com.notetaker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteDataController;
import jsb.com.notetaker.Fragments.NoteEditFragment;
import jsb.com.notetaker.Fragments.NoteViewFragment;
import jsb.com.notetaker.R;

import static jsb.com.notetaker.AdaptersAndDataFiles.NoteDataController.*;

public class NoteDetailActivity extends AppCompatActivity {

	public static boolean isSaveDialogueShowing;
	public static boolean isChoiceDialogueShowing;
	private static ActionBar actionBar;
    public final String NOTE_DETAIL_ACTIVITY = NoteDetailActivity.class.getSimpleName();
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private NoteEditFragment noteEditFragment;
	private String newNoteTitle;
	private String newNoteBody;
	private Note.Category newNoteCategory;
	private String newNoteDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		setFragment();
	}

	private void setFragment() {
		Intent intent = getIntent();
		String action = intent.getExtras().getString(MainActivity.INTENT_MOTIVE);

		switch (action) {
			case MainActivity.VIEW_NOTE_MOTIVE:
				NoteViewFragment noteViewFragment = new NoteViewFragment();
				setTitle(getString(R.string.View_Note_Title));
				//show up button
				//actionBar.setDisplayHomeAsUpEnabled(true);
				fragmentTransaction.add(R.id.detail_container, noteViewFragment, getString(R.string.NOTE_VIEW_FRAGMENT));
				break;

			case MainActivity.EDIT_NOTE_MOTIVE:
				noteEditFragment = new NoteEditFragment();
				setTitle(getString(R.string.Edit_Note_Title));
				//hide up button
				//actionBar.setDisplayHomeAsUpEnabled(false);
				fragmentTransaction.add(R.id.detail_container, noteEditFragment, getString(R.string.NOTE_EDIT_FRAGMENT));
				break;

			case MainActivity.ADD_NOTE_MOTIVE:
				noteEditFragment = new NoteEditFragment();
				setTitle(getString(R.string.Add_Note_Title));
				//hide up button
				//actionBar.setDisplayHomeAsUpEnabled(false);
				fragmentTransaction.add(R.id.detail_container, noteEditFragment, getString(R.string.NOTE_EDIT_FRAGMENT));
				break;
		}
		fragmentTransaction.commit();
	}

	private boolean checkChanges() {
		//check changes between initial note data and current note data
		return !(newNoteTitle.equals(initialNoteTitle) &&
				newNoteBody.equals(initialNoteBody) &&
				newNoteCategory.equals(initialCategory)&&
				newNoteDate.equals(initialDate));
	}

	@Override
	public void onBackPressed() {
		noteEditFragment = (NoteEditFragment) fragmentManager.findFragmentByTag(getString(R.string.NOTE_EDIT_FRAGMENT));

        Log.d(NOTE_DETAIL_ACTIVITY,(noteEditFragment==null)+"||||||||||||||");
		if (noteEditFragment != null) {
			newNoteTitle = noteEditFragment.getnewNoteTitle();
			newNoteBody = noteEditFragment.getnewNoteBody();
			newNoteCategory = noteEditFragment.getnewNoteCategory();
			newNoteDate = noteEditFragment.getnewNoteDate();

			if (checkChanges()) {
				Log.d(NOTE_DETAIL_ACTIVITY, "Changes were made");
				noteEditFragment.launchSaveConfirmationDialogue();
				noteEditFragment.saveConfirmDialogue.show();
			} else {
				Log.d(NOTE_DETAIL_ACTIVITY, "No changes made");
				super.onBackPressed();
			}
		} else {
			super.onBackPressed();
		}

        Log.d(NOTE_DETAIL_ACTIVITY,"Back Button was pressed");
	}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            Log.d(NOTE_DETAIL_ACTIVITY, "UP WAS PRESSED");

            noteEditFragment = (NoteEditFragment) fragmentManager.findFragmentByTag(getString(R.string.NOTE_EDIT_FRAGMENT));

            if (noteEditFragment != null) {
                newNoteTitle = noteEditFragment.getnewNoteTitle();
                newNoteBody = noteEditFragment.getnewNoteBody();
                newNoteCategory = noteEditFragment.getnewNoteCategory();
                newNoteDate = noteEditFragment.getnewNoteDate();

                if (checkChanges()) {
                    Log.d(NOTE_DETAIL_ACTIVITY, "Changes were made");
                    noteEditFragment.launchSaveConfirmationDialogue();
                    noteEditFragment.saveConfirmDialogue.show();

                } else {
                    Log.d(NOTE_DETAIL_ACTIVITY, "No changes made");
                    return super.onOptionsItemSelected(item);
                }
            } else {
                return super.onOptionsItemSelected(item);
            }
        }else{

            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
