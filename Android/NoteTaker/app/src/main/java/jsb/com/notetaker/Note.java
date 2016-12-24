package jsb.com.notetaker;

import java.util.Date;

/**
 * Created by Jagjit Singh on 12/21/2016.
 */

public class Note {

	public enum Category{PRIVATE,WORK,FINANCIAL,STUDIES,MEALS}
	private String title;
	private String body;
	private long date;
	private Category category;

	public Note(String title, String body, long date, Category category){
		this.body=body;
		this.title = title;
		this.date = date;
		this.category = category;
	}


	public Note(String title, String body, Category category){
		this.body=body;
		this.title = title;
		this.date = System.currentTimeMillis();
		this.category = category;
	}
	public Category getCategory() {
		return category;
	}
	public long getDate() {
		return date;
	}
	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	public int getCategoryImage(){

		switch (this.category){

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


	public String toString(){
		return "Title: "+ title+" Body: "+body+" Date: "+date+" Category: "+category;
	}
}
