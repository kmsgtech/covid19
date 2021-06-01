package tutorial.model;

public class Book {

	private int pages;
	private String name;
	private int book_Id;
	
	public int getpageCount() {
		return pages;
	}
	public void setpageCount(int pages) {
		this.pages = pages;
	}
	public String getbookName() {
		return name;
	}
	public void setbookName(String name) {
		this.name = name;
	}
	
	public int getbookId() {
		return book_Id;
	}
	public void setbookId(int book_Id) {
		this.book_Id = book_Id;
	}
	
	
	
}
