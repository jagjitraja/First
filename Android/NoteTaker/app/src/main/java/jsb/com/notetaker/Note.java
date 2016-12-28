package jsb.com.notetaker;

import java.io.Serializable;

/**
 * Created by Jagjit Singh on 12/21/2016.
 */

public class Note implements Serializable{

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

	public static int getCategoryImageFromCategory(Category category){

		switch (category){
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
	public static Category getCategoryFromString(String s){
		switch (s){

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
	public String toString(){
		return "Title: "+ title+" Body: "+body+" Date: "+date+" Category: "+category;
	}
}
