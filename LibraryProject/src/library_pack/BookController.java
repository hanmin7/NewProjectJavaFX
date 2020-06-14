package library_pack;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookController implements Initializable {
	Connection conn;

	@FXML
	TableView<Book> tableView;
	@FXML
	TableColumn<Book, Integer> id;
	@FXML
	TableColumn<Book, String> title;
	@FXML
	TableColumn<Book, String> wr;
	@FXML
	TableColumn<Book, Integer> status;
	@FXML
	TableColumn<Book, String> content;
	@FXML
	TableColumn<Book, String> image;
	@FXML
	Button btnList;

	Book book;

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
		
		
		id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		wr.setCellValueFactory(new PropertyValueFactory<Book, String>("writer"));
		status.setCellValueFactory(new PropertyValueFactory<Book, Integer>("status"));
		image.setCellValueFactory(new PropertyValueFactory<Book, String>("image"));
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {
			@Override
			public void changed(ObservableValue<? extends Book> arg0, Book oldVal, Book newVal) {
				book=newVal;
				
			}
		});
		
		
		btnList.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleBtnListAction(event);
			}
		});
		
//		tableView.setOnMouseClicked((new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent event) {
//				if (event.getClickCount() == 2) {
//					
//					Stage addStage = new Stage(StageStyle.UTILITY);
//					addStage.initModality(Modality.WINDOW_MODAL);
//					addStage.initOwner(btnList.getScene().getWindow());
//					
//					try {
//						Parent cont = FXMLLoader.load(getClass().getResource("Root.fxml"));
//						
//						Scene scene = new Scene(cont);
//						addStage.setScene(scene);
//						addStage.show();
//
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//			} else {
//				return;
//			}
//			}
//		});
		
		
	}// initialize

	public ObservableList<Book> getBookList() {
		ObservableList<Book> list = FXCollections.observableArrayList();
		String sql = "select id, title, writer, status, content, image from book";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Book board = new Book(rs.getInt("id"), rs.getString("title"), rs.getString("writer"),
						rs.getInt("status"), rs.getString("content"), rs.getString("image"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	} // getBookList

	public void handleBtnListAction(ActionEvent ae) {
		Stage addStage = new Stage(StageStyle.UTILITY);
		addStage.initModality(Modality.WINDOW_MODAL);
		addStage.initOwner(btnList.getScene().getWindow());

		try {
						
			Parent parent = FXMLLoader.load(getClass().getResource("List.fxml"));
			Scene scene = new Scene(parent);

			addStage.setScene(scene);
			addStage.setResizable(false);
			addStage.show();

			ImageView imageView = (ImageView) parent.lookup("#imageView");
			imageView.setImage(new Image("/images/" + book.getImage() + ".jpg"));

			Button listBorrow = (Button) parent.lookup("#listBorrow");
			listBorrow.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {

					addStage.close();
				}
			});

			Button listCancel = (Button) parent.lookup("#listCancel");
			listCancel.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					addStage.close();
				}
			});

			Label listTitle = (Label) parent.lookup("#listTitle");
			listTitle.setText(book.getTitle());

			TextArea listContent = (TextArea) parent.lookup("#listContent");
			listContent.setWrapText(true);
			listContent.setText(book.getContent());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void handleBtnCancelAction(ActionEvent e) {
		Platform.exit();
	}

}
