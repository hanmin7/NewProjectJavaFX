package library_pack;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private SimpleIntegerProperty id;
	private SimpleStringProperty title;
	private SimpleStringProperty writer;
	private SimpleIntegerProperty status;
	private SimpleStringProperty content;
	
	public Book (int id, String title, String writer, int status, String content) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.title = new SimpleStringProperty(title);
		this.writer = new SimpleStringProperty(writer);
		this.status = new SimpleIntegerProperty(status);
		this.content = new SimpleStringProperty(content);
	}
	
	public void setId(Integer id) {
		this.id.set(id);
	}
	public int getId() {
		return this.id.get();
	}
	public SimpleIntegerProperty idProperty() {
		return this.id;
	}
	
	public void setTitle(String title) {
		this.title.set(title);
	}
	public String getTitle() {
		return this.title.get();
	}
	public SimpleStringProperty titleProperty() {
		return this.title;
	}
	
	public void setWriter(String writer) {
		this.writer.set(writer);
	}
	public String getWriter() {
		return this.writer.get();
	}
	public SimpleStringProperty writerProperty() {
		return this.writer;
	}
	
	public void setStatus(Integer status) {
		this.status.set(status);
	}
	public int getStatus() {
		return this.status.get();
	}
	public SimpleIntegerProperty statusProperty() {
		return this.status;
	}
	
	public void setContent(String content) {
		this.content.set(content);
	}
	public String getContent() {
		return this.content.get();
	}
	public SimpleStringProperty contentProperty() {
		return this.content;
	}
	
}
