package jsb.com.notetaker;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
	private AlertDialog.Builder chooseCategoryDialogue;

    private Note.Category chosenCategory;

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
        Note.Category category;
        if(savedInstanceState ==null) {
             category = (Note.Category) intent.getExtras().getSerializable(MainActivity.NOTE_CATEGORY_KEY);
            chosenCategory = category;
        }
        else{
            category = (Note.Category) savedInstanceState.getSerializable(MainActivity.NOTE_CATEGORY_KEY);
            chosenCategory = category;
        }
		imageButton.setImageResource(Note.getCategoryImageFromCategory(category));


            editTitle.setText(title);
		editBody.setText(body);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChooseCategoryDialogueBuilder();
                chooseCategoryDialogue.show();
            }
        });

		return fragmentLayout;
	}


	private void launchChooseCategoryDialogueBuilder(){

		chooseCategoryDialogue = new AlertDialog.Builder(getContext());

        final String[] categories = {"PRIVATE","MEALS","WORK","FINANCIAL","STUDIES"};

        chooseCategoryDialogue.setTitle(getResources().getString(R.string.choose_category_dialogue_title));

        chooseCategoryDialogue.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chosenCategory = Note.getCategoryFromString(categories[which]);
                imageButton.setImageResource(Note.getCategoryImageFromCategory(chosenCategory));
                dialog.dismiss();
            }
        });

        chooseCategoryDialogue.setNegativeButton(getResources().getString(R.string.cancel_button),
                new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        chooseCategoryDialogue.create();

	}


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){

        saveInstanceState.putSerializable(MainActivity.NOTE_CATEGORY_KEY,chosenCategory);

    }

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("DESTROYED THIS FRAGMENT","========================");
	}
}
