package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.input.*;
import javafx.event.*;

public class Container_server_user extends HBox{
	@FXML
	CheckBox checkuser;

	@FXML
	Label iduser;

	@FXML
	TextField usernamefield,emailuserfield,userpasswordfield;

	@FXML
	ChoiceBox<typeuser> typeuserchoice;

	@FXML
	HBox container_users;

	@FXML
	ImageView imgusercontain;

	private Server_main server;

	protected enum typeuser{
		WHITELIST,
		BLACKLIST
	}
	/**
	 * terserah lo
	 * @param id
	 * @param name
	 * @param email
	 * @param password
	 * @param tipe
	 * @param imguser = Image
	 * @param kj = Server_main
	 * @throws IOException
	 */
	Container_server_user(int id, String name, String email, String password, String tipe, Image imguser,Server_main kj) throws IOException {
		FXMLLoader ow = new FXMLLoader(getClass().getResource("user_container.fxml"));
		ow.setController(this);
		ow.setRoot(this);
		ow.load();

		typeuser po = null;
		switch(tipe) {
			case "WHITELIST":
				po = typeuser.WHITELIST;
				break;
			case "BLACKLIST":
				po = typeuser.BLACKLIST;
				break;
		}

		typeuserchoice.getItems().addAll(typeuser.WHITELIST,typeuser.BLACKLIST);
		iduser.setText(String.valueOf(id));
		usernamefield.setText(name);
		emailuserfield.setText(email);
		userpasswordfield.setText(password);
		typeuserchoice.setValue(po);
		imgusercontain.setImage(imguser);
		imgusercontain.setFitHeight(40);
		imgusercontain.setFitWidth(40);
		server = kj;
	}

	int pq = 0;
	@FXML
	private boolean checkuser_pressed(ActionEvent g) {
		boolean j = false;
		Node[] l = server.getDescriptionContainerUser();
		if(checkuser.isSelected()) {
			container_users.setStyle("-fx-background-color:green");
			j = true;
			((TextArea)l[0]).appendText(","+iduser.getText());
			pq = ((TextField)l[0]).getText().replaceFirst(",", "").split(",").length;
			((TextArea)l[1]).appendText(","+usernamefield.getText());
			((TextArea)l[2]).appendText(","+emailuserfield.getText());
			((TextArea)l[3]).appendText(","+pq+userpasswordfield.getText());
			((TextArea)l[4]).appendText(","+pq+typeuserchoice.getValue().name());	
		}
		else {
			container_users.setStyle("-fx-background-color:white");
			j = false;
			((TextArea)l[0]).setText(((TextArea)l[0]).getText().replace(","+iduser.getText(), ""));
			((TextArea)l[1]).setText(((TextArea)l[1]).getText().replace(","+usernamefield.getText(), ""));
			((TextArea)l[2]).setText(((TextArea)l[2]).getText().replace(","+emailuserfield.getText(), ""));
			((TextArea)l[3]).setText(((TextArea)l[3]).getText().replace(","+pq+userpasswordfield.getText(), ""));
			((TextArea)l[4]).setText(((TextArea)l[4]).getText().replace(","+pq+typeuserchoice.getValue().name(), ""));
			pq = 0;
		}
		return j;
	}
}

class Container_server_book extends HBox{
	@FXML 
	HBox container_book;

	@FXML
	CheckBox checkbook;

	@FXML
	Label idbook;

	@FXML
	TextField titlebook,authorbook,descriptionbook;

	@FXML
	ImageView imgbookcontain;

	Server_main server;
	Container_server_book(int id,String title, String author, String desc,Image imgbook,Server_main k) throws IOException{
		FXMLLoader kj = new FXMLLoader(getClass().getResource("book_container.fxml"));
		kj.setController(this);
		kj.setRoot(this);
		kj.load();

		idbook.setText(String.valueOf(id));
		titlebook.setText(title);
		authorbook.setText(author);
		descriptionbook.setText(desc);
		imgbookcontain.setImage(imgbook);
		imgbookcontain.setFitHeight(40);
		imgbookcontain.setFitWidth(40);
		server = k;
	}

	int ks = 0;
	@FXML
	private boolean checkbook_pressed(ActionEvent lp) {
		boolean k = false;
		Node[] la = server.getDescriptionContainerBook();
		if(checkbook.isSelected()) {
			container_book.setStyle("-fx-background-color:cyan");
			k = true;
			((TextArea)la[5]).appendText(","+descriptionbook.getText());
			ks = ((TextArea)la[5]).getText().replaceFirst(",", "").split(",").length;
			((TextArea)la[0]).appendText(","+ks+titlebook.getText());
			((TextArea)la[1]).appendText(","+ks+authorbook.getText());
			((TextArea)la[2]).appendText(","+descriptionbook.getText());
			((TextArea)la[3]).appendText(","+ks+titlebook.getText());
			((TextArea)la[4]).appendText(","+ks+authorbook.getText());
		}
		else {
			container_book.setStyle("-fx-background-color:white");
			k = false;
			((TextArea)la[0]).setText(((TextArea)la[0]).getText().replace(","+ks+titlebook.getText(), ""));
			((TextArea)la[1]).setText(((TextArea)la[1]).getText().replace(","+ks+authorbook.getText(), ""));
			((TextArea)la[2]).setText(((TextArea)la[2]).getText().replace(","+descriptionbook.getText(), ""));
			((TextArea)la[3]).setText(((TextArea)la[3]).getText().replace(","+ks+titlebook.getText(), ""));
			((TextArea)la[4]).setText(((TextArea)la[4]).getText().replace(","+ks+authorbook.getText(), ""));
			((TextArea)la[5]).setText(((TextArea)la[5]).getText().replace(","+descriptionbook.getText(), ""));
			ks = 0;
		}
		return k;
	}

	@FXML
	private void imgbookcontain_pressed(MouseEvent s) {
		FileChooser nb = new FileChooser();
		Image hf = imgbookcontain.getImage();
		nb.getExtensionFilters().add(new ExtensionFilter("Image", "*.png", "*.jpg"));
		nb.setTitle("Choose your image");
		File hye = nb.showOpenDialog(server.getScene().getWindow());
		if (hye != null) {
			try {
				hf = new Image(new FileInputStream(hye));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		imgbookcontain.setImage(hf);
		imgbookcontain.setFitHeight(40);
		imgbookcontain.setFitWidth(40);
	}
}