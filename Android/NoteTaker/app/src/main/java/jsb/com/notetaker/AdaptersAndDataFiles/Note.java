package jsb.com.notetaker.AdaptersAndDataFiles;

import java.io.Serializable;
import java.util.Date;

import jsb.com.notetaker.R;

/**
 * Created by Jagjit Singh on 12/21/2016.
 */

public class Note implements Serializable {

	private String title;
	private String body;
	private long date;
	private Category category;
	public Note(String title, String body, long date, Category category) {
		this.body = body;
		this.title = title;
		this.date = date;
		this.category = category;
	}

	public Note(String title, String body, Category category) {
		this.body = body;
		this.title = title;
		this.date = System.currentTimeMillis();
		this.category = category;
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

		Date regularDate = new Date();
		regularDate.setTime(date);

		String dateString = regularDate.toString().substring(0,16);
		return dateString;
	}

	public void setDate(long date) {
		this.date = date;
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
		return "Title: " + title + " Body: " + body + " Date: " + getDate().toString() + " Category: " + category;
	}

	public enum Category {PRIVATE, WORK, FINANCIAL, STUDIES, MEALS}
}
