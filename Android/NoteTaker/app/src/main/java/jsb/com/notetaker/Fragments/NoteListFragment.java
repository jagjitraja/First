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
import jsb.com.notetaker.AdaptersAndDataFiles.DataBaseController;
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
			 notes = (ArrayList<Note>) savedInstanceState.getSerializable(MainActivity.SAVE_NOTES);
		}
		else if (notes == null) {
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
		outState.putSerializable(MainActivity.SAVE_NOTES,notes);
	}

	public void upDateChangedItem(Intent intent) {

		String title = intent.getExtras().getString(MainActivity.NOTE_TITLE_KEY);
		String body = intent.getExtras().getString(MainActivity.NOTE_BODY_KEY);
        String date = intent.getStringExtra(MainActivity.NOTE_DATE_KEY);
		Note.Category category = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_KEY);
		int position = intent.getExtras().getInt(MainActivity.NOTE_ID_KEY);

		if(!intent.getExtras().getBoolean(MainActivity.DELETE_CALL)) {
			if (position != -1) {
				Note currentNote = (Note) notes.get(position);
				currentNote.setBody(body);
				currentNote.setTitle(title);
				currentNote.setCategory(category);
                currentNote.setDate(date);
				DataBaseController.getInstance().updateNote(currentNote);
			} else {
                Note newNote;
                newNote = new Note(title, body,date, category, notes.size() + 1);
				DataBaseController.getInstance().write_Note(newNote);
				//TODO - Temporary because it wont be written to database.
				//Just for purpose while app is running therefore database wont be frequently opened
				notes.add(newNote);
			}
		}
		else{
			Note deleted = notes.get(position);
			DataBaseController.getInstance().deleteNote(deleted);
			notes.remove(deleted);
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
				Note deleted = notes.get(rowItemSelected);
				notes.remove(deleted);
				noteAdapter.notifyDataSetChanged();
				DataBaseController.getInstance().deleteNote(deleted);
				return true;
		}
		return super.onContextItemSelected(item);
	}

	public void launchNoteDetailActivity(int position, String noteDetailMotive) {

		Intent intent = new Intent(getActivity(), NoteDetailActivity.class);
		Note note = (Note) getListAdapter().getItem(position);
		String title = note.getTitle();
		String body = note.getBody();
		String date = note.getDate();

		Note.Category category = note.getCategory();

		intent.putExtra(MainActivity.NOTE_TITLE_KEY, title);
		intent.putExtra(MainActivity.NOTE_BODY_KEY, body);
		intent.putExtra(MainActivity.NOTE_CATEGORY_KEY, category);
		intent.putExtra(MainActivity.NOTE_DATE_KEY,date);
		intent.putExtra(MainActivity.INTENT_MOTIVE, noteDetailMotive);
		intent.putExtra(MainActivity.NOTE_ID_KEY, position);

		NoteDataController.initialCategory = category;
		NoteDataController.initialNoteBody = body;
		NoteDataController.initialNoteTitle = title;
		NoteDataController.chosenNoteID = position;
		NoteDataController.initialDate = date;
		startActivity(intent);
	}
}
