package jsb.com.notetaker.AdaptersAndDataFiles;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jsb.com.notetaker.Activities.MainActivity;
import jsb.com.notetaker.R;

/**
 * Created by Jagjit Singh on 12/21/2016.
 */

public class Note implements Serializable {

    public static final String NOTE_CLASS = Note.class.getSimpleName();

	private int ID;
	private String title;
	private String body;
	private Calendar date;
	private Category category;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yy, hh:mm a");

	public Note(String title, String body, String date, Category category,int id) {
		this.body = body;
		this.ID = id;
		this.title = title;
        this.date = Calendar.getInstance();
        try {
            this.date.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.category = category;
	}

	public Note(String title, String body, Category category, int i) {
		this.body = body;
		this.title = title;
		this.date = Calendar.getInstance();
        this.date.setTimeInMillis(System.currentTimeMillis());
		this.category = category;
	}

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public int getID(){
		return this.ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public static int getCategoryImageFromCategory(Category category) {

		switch (category) {
			case PRIVATE:
				return R.drawable.p;
			case FINANCIAL:
				return R.drawable.f;
			case STUDIES:
				return R.drawable.s;
			case MEALS:
				return R.drawable.m;
			case WORK:
				return R.drawable.w;
			default:
				return R.drawable.p;
		}
	}

	public static Category getCategoryFromString(String s) {
		switch (s) {

			case "PRIVATE":
				return Category.PRIVATE;
			case "FINANCIAL":
				return Category.FINANCIAL;
			case "STUDIES":
				return Category.STUDIES;
			case "MEALS":
				return Category.MEALS;
			case "WORK":
				return Category.WORK;
			default:
				return Category.PRIVATE;
		}
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDate() {
		String dateString = simpleDateFormat.format(this.date.getTime());
		return dateString;
	}

	public void setDate(String date) {
        try {
            this.date.setTime(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	int getCategoryImage() {

		switch (this.category) {

			case PRIVATE:
				return R.drawable.p;
			case FINANCIAL:
				return R.drawable.f;
			case STUDIES:
				return R.drawable.s;
			case MEALS:
				return R.drawable.m;
			case WORK:
				return R.drawable.w;
			default:
				return R.drawable.p;
		}

	}

	public String toString() {
		return "Title: " + title + " Body: " + body + " Date: " + getDate() + " Category: " + category;
	}

	public enum Category {PRIVATE, WORK, FINANCIAL, STUDIES, MEALS}

	public static Date getDateFromString(String stringDate){

		Date date = new Date();
		try {
			date = simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
			Log.e(NOTE_CLASS,"Invalid date entered to be parsed => "+stringDate);
		}
		return date;
	}

}
