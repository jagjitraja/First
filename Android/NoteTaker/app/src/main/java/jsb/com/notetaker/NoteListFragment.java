package jsb.com.notetaker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends ListFragment {

	private NoteAdapter noteAdapter;
	private ArrayList<Note> notes;

	public static class ViewHolder{
		ImageView noteImage;
		TextView noteTitle;
		TextView noteBody;
	}

	public NoteListFragment() {
		// Required empty public constructor
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		super.onActivityCreated(savedInstanceState);

		notes = new ArrayList<Note>();
		noteAdapter = new NoteAdapter(getActivity(),notes);
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.FINANCIAL));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));
		notes.add(new Note("Acac","acac",Note.Category.PRIVATE));

		setListAdapter(noteAdapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}
}
