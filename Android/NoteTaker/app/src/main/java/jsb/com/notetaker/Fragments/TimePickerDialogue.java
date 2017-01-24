package jsb.com.notetaker.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Date;
import java.util.GregorianCalendar;

import jsb.com.notetaker.R;

public class TimePickerDialogue extends DialogFragment {

	private Date date;
	private DatePicker datePicker;
	private TimePicker timePicker;
	private Button button;
	private int year;
	private int month;
	private int day;
	private int minute;
	private int hour;

	public TimePickerDialogue() {
		// Required empty public constructor

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View fragmentLayout = inflater.inflate(R.layout.fragment_time_picker_dialogue, container, false);
		datePicker = (DatePicker) fragmentLayout.findViewById(R.id.datePicker);
		timePicker = (TimePicker)fragmentLayout.findViewById(R.id.timePicker);
		button = (Button)fragmentLayout.findViewById(R.id.changeButton);
		datePicker.setMinDate(System.currentTimeMillis()-1000);


		//TODO Figure out the code to make sure that time picker cant set time less than current time
		//TODO HANDLE THE YEAR

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				button.setText("Done");
				datePicker.setVisibility(View.INVISIBLE);
				timePicker.setVisibility(View.VISIBLE);
				setDate(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());

				timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
					@RequiresApi(api = Build.VERSION_CODES.M)
					@Override
					public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
						setTime(hourOfDay,minute);
					}
				});

				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
						getExitTransition();
					}
				});
			}
		});

		return fragmentLayout;
	}

	private void setTime(int hour, int minute){
		this.hour = hour;
		this.minute = minute;
	}

	private void setDate(int year, int month,int day){
		this.day = day;
		this.year = year;
		this.month = month;
	}


	public Date getDate(){
		return new GregorianCalendar(year,month,day,hour,minute).getTime();
	}
}
