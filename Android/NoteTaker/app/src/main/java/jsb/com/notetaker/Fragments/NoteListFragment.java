package jsb.com.notetaker.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jsb.com.notetaker.Activities.MainActivity;
import jsb.com.notetaker.Activities.NoteDetailActivity;
import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteAdapter;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteDataController;
import jsb.com.notetaker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends ListFragment {

	private NoteAdapter noteAdapter;
	private ArrayList<Note> notes;

	public NoteListFragment() {
	}


	public void onActivityCreated(Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		super.onActivityCreated(savedInstanceState);

		if(savedInstanceState!=null){
			Log.d(MainActivity.APP_ID_KEY,"SAVED INSTANCE IS NOT NULL");
			 notes = (ArrayList) savedInstanceState.getSerializable(MainActivity.SAVE_NOTES);
		}
		else if (notes == null) {
			Log.d(MainActivity.APP_ID_KEY, "THE ARRAY LIST IS NULL");
			notes = NoteDataController.getReadNotes();

		}
		Intent intent = getActivity().getIntent();

		if (intent.getBooleanExtra(MainActivity.CHANGES_MADE, false)) {
			upDateChangedItem(intent);
		}
		noteAdapter = new NoteAdapter(getActivity(), notes);
		setListAdapter(noteAdapter);

		//set list view to create context menu on long click
		registerForContextMenu(getListView());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d(MainActivity.APP_ID_KEY,"SAVING INSTANCE----------"+(notes==null));
		outState.putSerializable(MainActivity.SAVE_NOTES,notes);
	}

	public void upDateChangedItem(Intent intent) {

		String title = intent.getExtras().getString(MainActivity.NOTE_TITLE_KEY);
		String body = intent.getExtras().getString(MainActivity.NOTE_BODY_KEY);
		Note.Category category = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_KEY);
		int position = intent.getExtras().getInt(MainActivity.NOTE_ID_KEY);

		if(intent.getExtras().getBoolean(MainActivity.DELETE_CALL)==false) {
			if (position != -1) {
				Note currentNote = (Note) notes.get(position);
				currentNote.setBody(body);
				currentNote.setTitle(title);
				currentNote.setCategory(category);
			} else {
				Note newNote = new Note(title, body, category);
				notes.add(newNote);
			}
		}
		else{
			notes.remove(position);
		}

		getActivity().getIntent().removeExtra(MainActivity.CHANGES_MADE);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		launchNoteDetailActivity(position, MainActivity.VIEW_NOTE_MOTIVE);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater menuInflater = getActivity().getMenuInflater();
		menuInflater.inflate(R.menu.long_press_context_menu, menu);
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		int menuItemID = item.getItemId();
		int rowItemSelected = info.position;
		switch (menuItemID) {
			case R.id.edit:
				launchNoteDetailActivity(rowItemSelected, MainActivity.EDIT_NOTE_MOTIVE);
				return true;
			case R.id.delete:
				notes.remove(rowItemSelected);
				noteAdapter.notifyDataSetChanged();
				return true;
		}
		return super.onContextItemSelected(item);
	}

	public void launchNoteDetailActivity(int position, String noteDetailMotive) {

		Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
		Note note = (Note) getListAdapter().getItem(position);
		String title = note.getTitle();
		String body = note.getBody();


		Note.Category category = note.getCategory();

		intent.putExtra(MainActivity.NOTE_TITLE_KEY, title);
		intent.putExtra(MainActivity.NOTE_BODY_KEY, body);
		intent.putExtra(MainActivity.NOTE_CATEGORY_KEY, category);
		intent.putExtra(MainActivity.INTENT_MOTIVE, noteDetailMotive);
		intent.putExtra(MainActivity.NOTE_ID_KEY, position);

		NoteDataController.initialCategory = category;
		NoteDataController.initialNoteBody = body;
		NoteDataController.initialNoteTitle = title;
		NoteDataController.chosenNoteID = position;

		startActivity(intent);
	}

	@Override
	public void onPause() {
		super.onPause();

		Log.d(MainActivity.APP_ID_KEY,"on pause----------"+(notes==null));
		Log.d(MainActivity.APP_ID_KEY,notes.size()+"Writing notes size  -------------On PAUSE" );
		NoteDataController.setReadNotesOnTerminate(notes);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		Log.d(MainActivity.APP_ID_KEY,"DESTRYIN----------"+(notes==null));
		Log.d(MainActivity.APP_ID_KEY,notes.size()+"Writing notes size" );
		NoteDataController.setReadNotesOnTerminate(notes);

	}
}
