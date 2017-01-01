package jsb.com.notetaker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteDataController;
import jsb.com.notetaker.Fragments.NoteEditFragment;
import jsb.com.notetaker.Fragments.NoteViewFragment;
import jsb.com.notetaker.R;

public class NoteDetailActivity extends AppCompatActivity{

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	public static boolean isSaveDialogueShowing;
	public static boolean isChoiceDialogueShowing;
	private NoteEditFragment noteEditFragment;

	private String newNoteTitle;
	private String newNoteBody;
	private Note.Category newNoteCategory;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_detail);
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
				setTitle("View Note");
				fragmentTransaction.add(R.id.detail_container, noteViewFragment, "NOTE_VIEW_FRAGMENT");
				break;

			case MainActivity.EDIT_NOTE_MOTIVE:
				noteEditFragment = new NoteEditFragment();
				setTitle("Edit Note");
				fragmentTransaction.add(R.id.detail_container,noteEditFragment,"NOTE_EDIT_FRAGMENT");
				break;
		}
		fragmentTransaction.commit();
	}


	/*@Override //get data from fragment
	public void passNoteData(String newTitle, String newBody, Note.Category newCategory){
		Log.d(MainActivity.APP_ID_KEY,"RECEIVED NEW DATA FROM FRAGMENT");
		this.newNoteBody = newBody;
		this.newNoteCategory = newCategory;
		this.newNoteTitle = newTitle;
		Log.d(MainActivity.APP_ID_KEY, newNoteTitle);
		Log.d(MainActivity.APP_ID_KEY, newNoteBody);
		Log.d(MainActivity.APP_ID_KEY, newNoteCategory.toString());
	}*/

	private boolean checkChanges(){

		Log.d(MainActivity.APP_ID_KEY, newNoteTitle);
		Log.d(MainActivity.APP_ID_KEY, newNoteBody);
		Log.d(MainActivity.APP_ID_KEY, newNoteCategory.toString());

		Log.d(MainActivity.APP_ID_KEY, NoteDataController.initialNoteTitle);
		Log.d(MainActivity.APP_ID_KEY, NoteDataController.initialNoteBody);
		Log.d(MainActivity.APP_ID_KEY, NoteDataController.initialCategory.toString());

		if(newNoteTitle.equals(NoteDataController.initialNoteTitle)&&
				newNoteBody.equals(NoteDataController.initialNoteBody)&&
				newNoteCategory.equals(NoteDataController.initialCategory)){
			return false;
		}
		else{
		return true;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		noteEditFragment = (NoteEditFragment) fragmentManager.findFragmentById(R.id.note_edit_fragment);

		if(noteEditFragment!=null && checkChanges()){




			Log.d(MainActivity.APP_ID_KEY,"CHANGES WERE MADE");

			noteEditFragment.launchSaveConfirmationDialogue();
			noteEditFragment.saveConfirmDialogue.show();
			return;
		}
		Log.d(MainActivity.APP_ID_KEY,"NO CHANGES WERE MADE");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
