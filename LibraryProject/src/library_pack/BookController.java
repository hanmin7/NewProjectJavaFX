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

public class BookController implements Initializable {
	Connection conn;
	
	@FXML TableView<Book> tableView;
	@FXML TableColumn<Book, Integer> id;
	@FXML TableColumn<Book, String> title;
	@FXML TableColumn<Book, String> wr;
	@FXML TableColumn<Book, Integer> status;
	@FXML TableColumn<Book, String> content;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "hr", "hr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<Book> bookList = getBookList();
		tableView.setItems(bookList);
		
//		TableColumn<Book, String> tcTitle = new TableColumn<Book, String>();
//		tcTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
//		tcTitle.setText("책제목");
//		tableView.getColumns().add(tcTitle);
//		tableView.setItems(bookList);
		
		
		id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		wr.setCellValueFactory(new PropertyValueFactory<Book, String>("writer"));
		status.setCellValueFactory(new PropertyValueFactory<Book, Integer>("status"));
		
		
		
		
		
	}//initialize
	
	
	public ObservableList<Book> getBookList() {
		ObservableList<Book> list = FXCollections.observableArrayList();
		String sql = "select id, title, writer, status, content from book";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Book board = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("writer"), rs.getInt("status"), rs.getString("content"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} //getBookList
}




