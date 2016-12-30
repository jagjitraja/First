package jsb.com.notetaker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jsb.com.notetaker.Activities.MainActivity;
import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.R;

public class NoteViewFragment extends Fragment {

	public NoteViewFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getActivity().getIntent();
		View fragmentLayout = inflater.inflate(R.layout.fragment_note_view,container,false);

		TextView body = (TextView) fragmentLayout.findViewById(R.id.note_body_view);
		TextView title = (TextView) fragmentLayout.findViewById(R.id.note_title_view);
		ImageView icon = (ImageView) fragmentLayout.findViewById(R.id.note_item_icon);

		String noteTitle =intent.getExtras().getString(MainActivity.NOTE_TITLE_KEY);
		String noteBody = intent.getExtras().getString(MainActivity.NOTE_BODY_KEY);
		Note.Category category = (Note.Category) intent.getSerializableExtra(MainActivity.NOTE_CATEGORY_KEY);

		title.setText(noteTitle);
		body.setText(noteBody);
		icon.setImageResource(Note.getCategoryImageFromCategory(category));

		return fragmentLayout;
	}
}


