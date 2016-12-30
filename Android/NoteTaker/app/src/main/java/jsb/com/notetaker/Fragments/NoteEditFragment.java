package jsb.com.notetaker.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import jsb.com.notetaker.Activities.MainActivity;
import jsb.com.notetaker.Activities.NoteDetailActivity;
import jsb.com.notetaker.AdaptersAndDataFiles.Note;
import jsb.com.notetaker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

	private ImageButton imageButton;
	private EditText editTitle;
	private EditText editBody;
	private AlertDialog chooseCategoryDialogue;
	private AlertDialog saveConfirmDialogue;
	private Note.Category chosenCategory;

	private Button saveButton;

	private boolean changesMade = false;
	private static Note.Category initialCategory;


	public NoteEditFragment() {
		// Required empty public constructor
	}

	private void getUIElements(View fragmentLayout){
		imageButton = (ImageButton) fragmentLayout.findViewById(R.id.edit_note_item_icon);
		editBody = (EditText) fragmentLayout.findViewById(R.id.edit_note_body_view);
		editTitle = (EditText)fragmentLayout.findViewById(R.id.edit_note_title_view);
		saveButton = (Button) fragmentLayout.findViewById(R.id.save_button);

	}


	private void fillNoteData(Intent intent, Bundle savedInstanceState){

		final String title = intent.getExtras().getString(MainActivity.NOTE_TITLE_KEY);
		String body = intent.getExtras().getString(MainActivity.NOTE_BODY_KEY);
		Note.Category category;

		if(savedInstanceState == null) {
			category = (Note.Category) intent.getExtras().getSerializable(MainActivity.NOTE_CATEGORY_KEY);
			chosenCategory = category;
		}
		else{
			category = (Note.Category) savedInstanceState.getSerializable(MainActivity.NOTE_CATEGORY_KEY);
			chosenCategory = category;
			if(savedInstanceState.getBoolean(MainActivity.SAVE_DIALOGUE_IS_SHOWING)){
				launchSaveConfirmationDialogue();
				saveConfirmDialogue.show();
				NoteDetailActivity.isSaveDialogueShowing = true;
			}

			if(savedInstanceState.getBoolean(MainActivity.CHOICE_DIALOGUE_IS_SHOWING)){
				launchChooseCategoryDialogueBuilder();
				chooseCategoryDialogue.show();
				NoteDetailActivity.isChoiceDialogueShowing = true;
			}
		}
		imageButton.setImageResource(Note.getCategoryImageFromCategory(category));
		editTitle.setText(title);
		editBody.setText(body);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View fragmentLayout = inflater.inflate(R.layout.fragment_note_edit,container,false);
		getUIElements(fragmentLayout);
		Intent intent = getActivity().getIntent();
		fillNoteData(intent,savedInstanceState);

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchSaveConfirmationDialogue();
				saveConfirmDialogue.show();
				NoteDetailActivity.isSaveDialogueShowing=true;
			}
		});
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChooseCategoryDialogueBuilder();
                chooseCategoryDialogue.show();
	            NoteDetailActivity.isChoiceDialogueShowing = true;
            }
        });


		return fragmentLayout;
	}


	private void launchChooseCategoryDialogueBuilder(){
		AlertDialog.Builder chooseCategoryDialogueBuilder = new AlertDialog.Builder(getContext());
        final String[] categories = {"PRIVATE","MEALS","WORK","FINANCIAL","STUDIES"};

        chooseCategoryDialogueBuilder.setTitle(getResources().getString(R.string.choose_category_dialogue_title));
        chooseCategoryDialogueBuilder.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chosenCategory = Note.getCategoryFromString(categories[which]);
                imageButton.setImageResource(Note.getCategoryImageFromCategory(chosenCategory));
                dialog.dismiss();
	            NoteDetailActivity.isChoiceDialogueShowing = false;
            }
        });
        chooseCategoryDialogueBuilder.setNegativeButton(getResources().getString(R.string.cancel_button),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	            NoteDetailActivity.isChoiceDialogueShowing = false;
            }
        });

        chooseCategoryDialogue = chooseCategoryDialogueBuilder.create();
	}

	private void launchSaveConfirmationDialogue(){
		AlertDialog.Builder saveConfirmDialogueBuilder = new AlertDialog.Builder(getContext());

		saveConfirmDialogueBuilder.setTitle(R.string.save_confirm_dialogue_title);
		saveConfirmDialogueBuilder.setMessage(R.string.save_confirm_message);

		saveConfirmDialogueBuilder.setPositiveButton(R.string.save_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NoteDetailActivity.isSaveDialogueShowing = false;
			}
		});
		saveConfirmDialogueBuilder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NoteDetailActivity.isSaveDialogueShowing = false;
			}
		});
		saveConfirmDialogue = saveConfirmDialogueBuilder.create();
	}


    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putSerializable(MainActivity.NOTE_CATEGORY_KEY,chosenCategory);

	    if(NoteDetailActivity.isChoiceDialogueShowing){
		    Log.d(MainActivity.APP_ID_KEY,"SAVING CHOICE DIALOGUE INSTANCE");
		    chooseCategoryDialogue.dismiss();
		    saveInstanceState.putBoolean(MainActivity.CHOICE_DIALOGUE_IS_SHOWING,NoteDetailActivity.isChoiceDialogueShowing);
		    NoteDetailActivity.isChoiceDialogueShowing = false;
	    }
	    if(NoteDetailActivity.isSaveDialogueShowing){
		    Log.d(MainActivity.APP_ID_KEY,"SAVING SAVE DIALOGUE INSTANCE");
		    saveConfirmDialogue.dismiss();
		    saveInstanceState.putBoolean(MainActivity.SAVE_DIALOGUE_IS_SHOWING,NoteDetailActivity.isSaveDialogueShowing);
		    NoteDetailActivity.isSaveDialogueShowing = false;
	    }

    }

	@Override
	public void onDestroy() {
		super.onDestroy();

	}
}
