package library_pack;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LoginController implements Initializable{
	Connection conn;
	@FXML 
	TableView<Book> tableViewTest;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hr", "hr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<Book> bookList = getBookList();
		tableViewTest.setItems(bookList);
		
		TableColumn<Book, ?> tcId = (TableColumn<Book, ?>) tableViewTest.getColumns().get(0); //칼럼첫번째꺼 가져옴
		tcId.setCellValueFactory(new PropertyValueFactory("id"));
		TableColumn<Book, ?> tcTitle = (TableColumn<Book, ?>) tableViewTest.getColumns().get(1);
		tcTitle.setCellValueFactory(new PropertyValueFactory("title"));
	}
	
	
	
	public ObservableList<Book> getBookList() {
		ObservableList<Book> list = FXCollections.observableArrayList();
		String sql = "select id, title, writer, status, content, image from book";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Book board = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("writer"), rs.getInt("status"), rs.getString("content"), rs.getString("image"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} //getBookList
}
