package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.*;

public class Server_main extends BorderPane {
	BorderPane kas;

	@FXML
	TextField admininit,userssearchfields;

	@FXML
	TextField booksearchfields;

	@FXML
	TextArea iduserfields,nameuserfields,emailuserfields,passworduserfields,typeuserfields,
		titlebookdescfields,authorbookdescfields,descbookdescareas, descbookaddareas, descbookdescareas1,
		titlebookaddfields,authorbookaddfields,titlebookdescfields1,authorbookdescfields1;

	@FXML 
	PasswordField passwordadmininit;

	boolean checked = false;

	@FXML
	ScrollPane scrolluser,scrollbook;

	@FXML
	Button deleteusersbtn,deletebookdescbtn,updatebookdescbtn,imgbookaddbtn;

	@FXML
	VBox containermainuser,containermainbook;

	HashMap<String,Object> mainsources;
	Database lko;
	Server_main(Database lko) throws IOException{
		kas = new BorderPane();
		this.lko = lko;
		try {
			mainsources = lko.LoadData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FXMLLoader ga = new FXMLLoader(getClass().getResource("Loginmenu.fxml"));
		ga.setController(this);
		ga.setRoot(kas);
		ga.load();
		if((LinkedList<Object>)mainsources.get("user") != null) {
		Iterator<Object> po = ((LinkedList<Object>)mainsources.get("user")).iterator();
		while(po.hasNext()) {
			Object[] lk =(Object[])po.next(); 
			Image rw = new Image((InputStream)lk[5]);
			Container_server_user lq = new Container_server_user((int)lk[0],(String)lk[1],(String)lk[2],(String)lk[3],(String)lk[4],rw,this);
			containermainuser.getChildren().add(lq);
		}}
		if((LinkedList<Object>)mainsources.get("book") != null) {
		Iterator<Object> po1 = ((LinkedList<Object>)mainsources.get("book")).iterator();
		int rw = 1;
		while(po1.hasNext()) {
			Object[] ka =(Object[])po1.next();
			Image g = new Image((InputStream)ka[3]);
			Container_server_book mx = new Container_server_book(rw,(String)ka[0],(String)ka[1],(String)ka[2],g,this);
			containermainbook.getChildren().add(mx);
			rw++;
		}}

		setCenter(kas);
	}

	@FXML
	private void init_admin(KeyEvent h) {
		if(h.getCode() == KeyCode.ENTER) {
			String lq = admininit.getText().replaceAll("\\s","");
			String lqw = passwordadmininit.getText().replaceAll("\\s","");
			boolean jq = lko.getAuthenticationServer(lq, lqw);
			if(jq) {
				scrolluser.setVisible(true);
				scrollbook.setVisible(true);
			}
		}
	}

	protected Node[] getDescriptionContainerUser() {
		Node[] p = {iduserfields,nameuserfields,emailuserfields,passworduserfields,typeuserfields};
		return p;
	}

	protected Node[] getDescriptionContainerBook() {
		Node[] l = {titlebookdescfields,authorbookdescfields,descbookdescareas,titlebookdescfields1,authorbookdescfields1,descbookdescareas1};
		return l;
	}

	@FXML
	private void userssearchfields_keypressed(KeyEvent o) throws SQLException, IOException {
		if(o.getCode().equals(KeyCode.ENTER)) {
		String hg = userssearchfields.getText();
		if(hg != "") {
			try {
				int p = Integer.parseInt(hg);
				Iterator<Object> poi = lko.searchUser(p).iterator();
				containermainuser.getChildren().removeAll(containermainuser.getChildren());
				while(poi.hasNext()) {
					Object[] lk =(Object[])poi.next(); 
					Image rw = new Image((InputStream)lk[5]);
					Container_server_user lq = new Container_server_user((int)lk[0],(String)lk[1],(String)lk[2],(String)lk[3],(String)lk[4],rw,this);
					containermainuser.getChildren().add(lq);
				}
			}
			catch(NumberFormatException u) {
				Iterator<Object> poi = lko.searchUser(hg).iterator();
				containermainuser.getChildren().removeAll(containermainuser.getChildren());
				while(poi.hasNext()) {
					Object[] lk =(Object[])poi.next(); 
					Image rw = new Image((InputStream)lk[5]);
					Container_server_user lq = new Container_server_user((int)lk[0],(String)lk[1],(String)lk[2],(String)lk[3],(String)lk[4],rw,this);
					containermainuser.getChildren().add(lq);
				}
			}}}
	}

	@FXML
	private void deleteusersbtn_pressed(ActionEvent j) throws SQLException, IOException {
		String[] l = iduserfields.getText().split(",");
		int[] ja = new int[l.length-1];
		int k=0;
		for(int y=0;y<l.length;y++) {
			if(l[y] == "") {
				continue;
			}
			else {
				ja[k] = Integer.parseInt(l[y]);
				k++;
			}
		}
		lko.deleteUser(ja);
		userrefresh_keypressed();
		iduserfields.clear();nameuserfields.clear();emailuserfields.clear();passworduserfields.clear();typeuserfields.clear();
	}

	@FXML
	private void booksearchfields_keypressed(KeyEvent s) throws SQLException, IOException {
		if(s.getCode().equals(KeyCode.ENTER)) {
		String hg = booksearchfields.getText();
		if(hg != "") {
			Iterator<Object> poi = lko.searchBook(hg).iterator();
			containermainbook.getChildren().removeAll(containermainbook.getChildren());
			int us = 1;
			while(poi.hasNext()) {
				Object[] lk =(Object[])poi.next(); 
				Image rw = new Image((InputStream)lk[3]);
				Container_server_book lq = new Container_server_book(us,(String)lk[0],(String)lk[1],(String)lk[2],rw,this);
				containermainbook.getChildren().add(lq);
				us++;
			}
		}}
	}

	@FXML
	private void deletebookdescbtn_pressed(ActionEvent ya) throws SQLException, IOException {
		String[] l = titlebookdescfields.getText().replaceFirst(",", "").split(",");
		String[] w = authorbookdescfields.getText().replaceFirst(",", "").split(",");
		lko.deleteBook(l,w);
		bookrefresh_keypressed();
		titlebookdescfields.clear();authorbookdescfields.clear();descbookdescareas.clear();titlebookdescfields1.clear();
		authorbookdescfields1.clear();descbookdescareas1.clear();
	}

	@FXML
	private void updatebookdescbtn_pressed(ActionEvent s) throws SQLException, IOException {
		String[] l = titlebookdescfields.getText().replaceFirst(",", "").split(",");
		String[] w = authorbookdescfields.getText().replaceFirst(",", "").split(",");
		String[] la = titlebookdescfields1.getText().replaceFirst(",", "").split(",");
		String[] la1 = authorbookdescfields1.getText().replaceFirst(",", "").split(",");
		String[] la2 = descbookdescareas1.getText().replaceFirst(",", "").split(",");
		try {
			lko.updateBook(l, w, la, la1, la2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bookrefresh_keypressed();
		titlebookdescfields.clear();authorbookdescfields.clear();descbookdescareas.clear();titlebookdescfields1.clear();
		authorbookdescfields1.clear();descbookdescareas1.clear();
	}
	private File hye = null;
	@FXML
	private void imgbookaddbtn_pressed(ActionEvent s) {
		FileChooser nb = new FileChooser();
		ImageView imgbookcontain = new ImageView();
		nb.getExtensionFilters().add(new ExtensionFilter("Image", "*.png", "*.jpg"));
		nb.setTitle("Choose your image");
		hye = nb.showOpenDialog(getScene().getWindow());
		if (hye != null) {
			try {
				imgbookcontain.setImage(new Image(new FileInputStream(hye)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imgbookcontain.setFitHeight(100);
			imgbookcontain.setFitWidth(100);
			imgbookaddbtn.setStyle("-fx-background-color:rgba(1,1,1,0)");
			imgbookaddbtn.setText("");
			imgbookaddbtn.setGraphic(imgbookcontain);
		}
	}

	@FXML
	private void addbookaddbtn_pressed(ActionEvent s) throws SQLException, IOException {
		if (hye == null) {
			hye =  new File(getClass().getResource("No_Image.png").toExternalForm());
		}
		hye.setReadable(true);
		hye.setWritable(true);
		String ka = titlebookaddfields.getText();
		String ka1 = authorbookaddfields.getText();
		String ka2 = descbookaddareas.getText();
		BufferedImage po = ImageIO.read(hye);
		ByteArrayOutputStream mz = new ByteArrayOutputStream();
		ImageIO.write(po, "png", mz);
		ByteArrayInputStream la = new ByteArrayInputStream(mz.toByteArray());
		lko.addBook(ka,ka1,ka2,la);
		bookrefresh_keypressed();
		descbookaddareas.clear();titlebookaddfields.clear();authorbookaddfields.clear();
	}

	private void bookrefresh_keypressed() throws SQLException, IOException {
		containermainbook.getChildren().removeAll(containermainbook.getChildren());
		Iterator<Object> po1 = lko.refreshBook().iterator();
		int gs = 1;
		while(po1.hasNext()) {
			Object[] ka =(Object[])po1.next();
			Image g = new Image((InputStream)ka[3]);
			Container_server_book mx = new Container_server_book(gs,(String)ka[0],(String)ka[1],(String)ka[2],g,this);
			containermainbook.getChildren().add(mx);
			gs++;
		}
	}

	@FXML
	private void userrefresh(ActionEvent s) throws SQLException, IOException {
		userrefresh_keypressed();
	}

	private void userrefresh_keypressed() throws SQLException, IOException {
		containermainuser.getChildren().removeAll(containermainuser.getChildren());
		Iterator<Object> po = lko.refreshUser().iterator();
		while(po.hasNext()) {
			Object[] lk =(Object[])po.next(); 
			Image rw = new Image((InputStream)lk[5]);
			Container_server_user lq = new Container_server_user((int)lk[0],(String)lk[1],(String)lk[2],(String)lk[3],(String)lk[4],rw,this);
			containermainuser.getChildren().add(lq);
		}
	}
}