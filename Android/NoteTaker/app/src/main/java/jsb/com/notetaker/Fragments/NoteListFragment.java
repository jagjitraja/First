package jsb.com.notetaker.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jsb.com.notetaker.Activities.MainActivity;
import jsb.com.notetaker.Activities.NoteDetailActivity;
import jsb.com.notetaker.AdaptersAndDataFiles.DataFile;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteAdapter;
import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteDataController;
import jsb.com.notetaker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends ListFragment {

	private NoteAdapter noteAdapter;
	private ArrayList<Note> notes = new ArrayList<>();

	public NoteListFragment() {
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		super.onActivityCreated(savedInstanceState);

		DataFile dataFile = new DataFile(getActivity());

		notes = dataFile.read_data();
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.FINANCIAL));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.PRIVATE));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.MEALS));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.WORK));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.STUDIES));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.FINANCIAL));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.PRIVATE));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.MEALS));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.WORK));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.STUDIES));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.FINANCIAL));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.PRIVATE));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.MEALS));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.WORK));
		notes.add(new Note("One","qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq", Note.Category.STUDIES));
		noteAdapter = new NoteAdapter(getActivity(),notes);
		setListAdapter(noteAdapter);

		//set list view to create context menu on long click
		registerForContextMenu(getListView());

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
		menuInflater.inflate(R.menu.long_press_context_menu,menu);
	}


	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		int menuItemID = item.getItemId();
		int rowItemSelected = info.position;

		switch (menuItemID){
			case R.id.edit:
				launchNoteDetailActivity(rowItemSelected,MainActivity.EDIT_NOTE_MOTIVE);
				return true;
			case R.id.delete:
				return true;
		}
		return super.onContextItemSelected(item);
	}

	public void launchNoteDetailActivity(int position, String noteDetailMotive){

		Intent intent = new Intent(getActivity(),NoteDetailActivity.class);
		Note note = (Note) getListAdapter().getItem(position);
		String title = note.getTitle();
		String body = note.getBody();


		Note.Category category = note.getCategory();

		intent.putExtra(MainActivity.NOTE_TITLE_KEY,title);
		intent.putExtra(MainActivity.NOTE_BODY_KEY,body);
		intent.putExtra(MainActivity.NOTE_CATEGORY_KEY,category);
		intent.putExtra(MainActivity.INTENT_MOTIVE,noteDetailMotive);

		NoteDataController.initialCategory = category;
		NoteDataController.initialNoteBody = body;
		NoteDataController.initialNoteTitle = title;

		startActivity(intent);
	}

}
