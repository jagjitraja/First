package jsb.com.notetaker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jsb.com.notetaker.Activities.MainActivity;
import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.AdaptersAndDataFiles.NoteDataController;
import jsb.com.notetaker.R;

public class NoteViewFragment extends Fragment {

	private String noteTitle ;
	private String noteBody;
	private Note.Category category;

	public NoteViewFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		inflater.inflate(R.menu.long_press_context_menu,menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int itemPosition = item.getItemId();

		switch (itemPosition){
			case R.id.edit:
				FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.detail_container,new NoteEditFragment(),"NOTE_EDIT_FRAGMENT");
				fragmentTransaction.commit();
				break;

			case R.id.delete:
				Log.d(MainActivity.APP_ID_KEY,"DELETE SELETED");
				Intent intent = new Intent(getActivity(), MainActivity.class);
				intent.putExtra(MainActivity.NOTE_TITLE_KEY, noteTitle);
				intent.putExtra(MainActivity.NOTE_BODY_KEY, noteBody);
				intent.putExtra(MainActivity.NOTE_CATEGORY_KEY, category);
				intent.putExtra(MainActivity.CHANGES_MADE, true);
				intent.putExtra(MainActivity.DELETE_CALL,true);
				intent.putExtra(MainActivity.NOTE_ID_KEY, NoteDataController.chosenNoteID);
				//clears back stack so that main activity does not relaunch on back pressed
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				getActivity().finish();
				startActivity(intent);
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		setMenuVisibility(true);

		Intent intent = getActivity().getIntent();
		View fragmentLayout = inflater.inflate(R.layout.fragment_note_view, container, false);

		TextView body = (TextView) fragmentLayout.findViewById(R.id.note_body_view);
		TextView title = (TextView) fragmentLayout.findViewById(R.id.note_title_view);
		ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.note_item_icon);

		 noteTitle = intent.getExtras().getString(MainActivity.NOTE_TITLE_KEY);
		 noteBody = intent.getExtras().getString(MainActivity.NOTE_BODY_KEY);
		 category = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_KEY);

		title.setText(noteTitle);
		body.setText(noteBody);
		icon.setImageResource(Note.getCategoryImageFromCategory(category));

		return fragmentLayout;
	}
}


