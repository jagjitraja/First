package jsb.com.notetaker.AdaptersAndDataFiles;

/**
 * Created by Jagjit Singh on 12/29/2016.
 */


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jsb.com.notetaker.R;

/**
 * Created by Jagjit Singh on 12/22/2016.
 */

public class NoteAdapter extends ArrayAdapter<Note> {


	public final String NOTE_ADAPTER = NoteAdapter.class.getSimpleName();

	public NoteAdapter(Context context, ArrayList<Note> notes) {
		super(context, 0, notes);
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Note note = (Note) getItem(position);
		if (note != null) {
			ViewHolder viewHolder;

			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_item, parent, false);
				convertView.setTag(viewHolder);
				viewHolder.noteImage = (ImageView) convertView.findViewById(R.id.note_image);
				viewHolder.noteBody = (TextView) convertView.findViewById(R.id.note_body);
				viewHolder.noteTitle = (TextView) convertView.findViewById(R.id.note_title);
				viewHolder.noteDate = (TextView) convertView.findViewById(R.id.note_date);
				viewHolder.dateLabel = (TextView) convertView.findViewById(R.id.date_label);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.noteTitle.setTypeface(NoteDataController.getTitleFont());
			viewHolder.noteBody.setTypeface(NoteDataController.getBodyFont());
			viewHolder.noteDate.setTypeface(NoteDataController.getDateFont());
			viewHolder.dateLabel.setTypeface(NoteDataController.getDateFont());

			viewHolder.noteTitle.setText(note.getTitle());
			viewHolder.noteBody.setText(note.getBody());
			viewHolder.noteImage.setImageResource(note.getCategoryImage());
			viewHolder.noteDate.setText(note.getDate());
			return convertView;
		}
		return convertView;
	}

	public static class ViewHolder {
		ImageView noteImage;
		TextView noteTitle;
		TextView noteBody;
		TextView noteDate;
		TextView dateLabel;
	}
}