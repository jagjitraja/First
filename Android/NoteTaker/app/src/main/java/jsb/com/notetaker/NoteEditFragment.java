package jsb.com.notetaker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

	private ImageButton imageButton;
	private EditText editTitle;
	private EditText editBody;

	public NoteEditFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit,container,false);

		imageButton = (ImageButton) fragmentLayout.findViewById(R.id.edit_note_item_icon);
		editBody = (EditText) fragmentLayout.findViewById(R.id.edit_note_body_view);
		editTitle = (EditText)fragmentLayout.findViewById(R.id.edit_note_title_view);

		Intent intent = getActivity().getIntent();

		String title = intent.getExtras().getString(MainActivity.NOTE_TITLE_KEY);
		String body = intent.getExtras().getString(MainActivity.NOTE_BODY_KEY);
		Note.Category category = (Note.Category) intent.getExtras().getSerializable(MainActivity.NOTE_CATEGORY_KEY);

		imageButton.setImageResource(Note.getCategoryImageFromCategory(category));
		editTitle.setText(title);
		editBody.setText(body);

		return inflater.inflate(R.layout.fragment_note_edit, container, false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("DESTROYED THIS FRAGMENT","========================");
	}
}
