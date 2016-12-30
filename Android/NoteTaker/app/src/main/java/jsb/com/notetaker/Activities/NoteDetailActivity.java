package jsb.com.notetaker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import jsb.com.notetaker.Fragments.NoteEditFragment;
import jsb.com.notetaker.Fragments.NoteViewFragment;
import jsb.com.notetaker.R;

public class NoteDetailActivity extends AppCompatActivity {

	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	public static boolean isSaveDialogueShowing;
	public static boolean isChoiceDialogueShowing;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_detail);
		setFragment();
	}

	private void setFragment() {

		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		Intent intent = getIntent();
		String action = intent.getExtras().getString(MainActivity.INTENT_MOTIVE);

		switch (action) {
			case MainActivity.VIEW_NOTE_MOTIVE:
				NoteViewFragment noteViewFragment = new NoteViewFragment();
				setTitle("View Note");
				fragmentTransaction.add(R.id.detail_container, noteViewFragment, "NOTE_VIEW_FRAGMENT");
				break;

			case MainActivity.EDIT_NOTE_MOTIVE:
				NoteEditFragment noteEditFragment = new NoteEditFragment();
				setTitle("Edit Note");
				fragmentTransaction.add(R.id.detail_container,noteEditFragment,"NOTE_EDIT_FRAGMENT");
				break;
		}

		fragmentTransaction.commit();
	}
}
