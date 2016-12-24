package jsb.com.notetaker;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jagjit Singh on 12/22/2016.
 */

public class NoteAdapter extends ArrayAdapter<Note>{

	public static class ViewHolder{
		ImageView noteImage;
		TextView noteTitle;
		TextView noteBody;
	}

	public NoteAdapter(Context context, ArrayList<Note> notes) {
		super(context,0, notes);
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Note note =(Note) getItem(position);
		ViewHolder viewHolder;

		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_item,parent,false);
			convertView.setTag(viewHolder);
			viewHolder.noteImage = (ImageView)convertView.findViewById(R.id.note_image);
			viewHolder.noteBody = (TextView) convertView.findViewById(R.id.note_body);
			viewHolder.noteTitle = (TextView) convertView.findViewById(R.id.note_title);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.noteTitle.setText(note.getTitle());
		viewHolder.noteBody.setText(note.getBody());
		viewHolder.noteImage.setImageResource(note.getCategoryImage());

		return convertView;
	}
}
