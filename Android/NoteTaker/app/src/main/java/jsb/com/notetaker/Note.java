package jsb.com.notetaker;

import java.util.Date;

/**
 * Created by Jagjit Singh on 12/21/2016.
 */

public class Note {

	public enum Category{PRIVATE,WORK,FINANCIAL,STUDIES,MEALS}
	private String title;
	private String body;
	private Date date;
	private Category category;

	public Note(String title, String body, Date date, Category category){
		this.body=body;
		this.title = title;
		this.date = date;
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public Date getDate() {
		return date;
	}

	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}
	public String toString(){
		return "Title: "+ title+" Body: "+body+" Date: "+date+" Category: "+category;
	}
}
