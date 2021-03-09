/**
 * @author Adrian Marcellus 
 * @version 12/17/2019
 * Final Project Main Pane
 * This is the main pane used for playing connect four. 
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConnectFourPane extends BorderPane {

	private char[][] board = new char[7][6];
	private char whoseTurn;
	private char checkIfWinner;
	private boolean win = false;
	private boolean tie = false;
	private boolean first = true;
	private int moves = 0;
	Text text = new Text();	
	Music sounds;
	private double textX;
	private double textY;
	private double saveTextPosX;
	private double saveTextPosY;
	
	public ConnectFourPane() {
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				board[x][y] = 'w';
			}
		}
		whoseTurn = randomStart();
		try {
			this.setPane();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private char randomStart() {
		char turn;
		int random = (int)(Math.random() * 100) + 1;
		if(random % 2 == 0) {
			turn = 'r';
		}
		else {
			turn = 'b';
		}
		return turn;
	}
	private void setPane() throws FileNotFoundException {
		Pane center = new Pane();
		center.getChildren().add(this.getRec());
		Circle[][] pieces = this.getCircles();
		for(int x = 0; x < pieces.length; x++) {
			for(int y = 0; y < pieces[x].length; y++) {
				Circle circ = pieces[x][y];
				center.getChildren().add(circ);
			}
		}
		center.getChildren().add(this.getTurnTitle());
		this.setCenter(center);
		this.setTop(this.getButtons());
		if(this.checkTie()) {
			win = true;
			tie = true;
			this.winScreen();
		}
		else if(this.checkWin() && !win) {
			win = true;
			this.winScreen();
		}
	}
	private boolean checkTie() {
		boolean isTie = true;
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				if(board[x][y] == 'w') {
					isTie = false;
				}
			}
		}
		return isTie;
	}
	private void winScreen() throws FileNotFoundException {
		Stage stage = new Stage();
		BorderPane pane = new BorderPane();

		Text text = new Text();
		text.setScaleX(5);
		text.setScaleY(5);

		HBox top = new HBox();
		top.setPadding(new Insets(20, 20, 20, 20));
		pane.setTop(top);
		top.setAlignment(Pos.TOP_CENTER);
		Button play = new Button("Play Again?");
		play.setOnAction((ActionEvent e) -> {			
			stage.close();
			this.reset();
			sounds.stopSound();
		});
		top.getChildren().add(play);			

		HBox bot = new HBox();
		bot.setPadding(new Insets(20, 20, 20, 20));
		pane.setBottom(bot);
		bot.setAlignment(Pos.TOP_CENTER);

		Button quit = new Button("Quit.");
		quit.setOnAction((ActionEvent e) -> {
			stage.close();
			sounds.stopSound();
			System.exit(0);
		});
		bot.getChildren().add(quit);

		VBox left = new VBox();
		left.setPadding(new Insets(20, 20, 20, 20));
		pane.setLeft(left);

		VBox right = new VBox();
		right.setPadding(new Insets(20, 20, 20, 20));
		pane.setRight(right);

		if(tie) {
			PrintWriter yeet = new PrintWriter("ConnectFourGameLog");
			yeet.println("Moves Made: " + this.moves + " Winner: TIE");
			yeet.close();
			text.setText("TIE!");
			text.setStroke(Color.GRAY);
			text.setFill(Color.GRAY);
			top.setStyle("-fx-background-color: gray");
			bot.setStyle("-fx-background-color: gray");
			left.setStyle("-fx-background-color: gray");
			right.setStyle("-fx-background-color: gray");
		}
		else if(checkIfWinner == 'r') {
			PrintWriter yeet = new PrintWriter("ConnectFourGameLog");
			yeet.println("Moves Made: " + this.moves + " Winner: Red");
			yeet.close();
			text.setText("RED WINS!");
			text.setStroke(Color.RED);
			text.setFill(Color.RED);
			top.setStyle("-fx-background-color: red");
			bot.setStyle("-fx-background-color: red");
			left.setStyle("-fx-background-color: red");
			right.setStyle("-fx-background-color: red");
			this.soundEffect("SOUNDS/RedWins.wav");
		}
		else {
			PrintWriter yeet = new PrintWriter("ConnectFourGameLog");
			yeet.println("Moves Made: " + this.moves + " Winner: Black");
			yeet.close();
			text.setText("BLACK WINS!");
			text.setStroke(Color.BLACK);
			top.setStyle("-fx-background-color: black");
			bot.setStyle("-fx-background-color: black");
			left.setStyle("-fx-background-color: black");
			right.setStyle("-fx-background-color: black");
			this.soundEffect("SOUNDS/BlackWins.wav");
		}
		pane.setCenter(text);
		stage.setTitle("Winner Winner Chicken Dinner");
		stage.setScene(new Scene(pane, 450, 450));
		stage.show();
	}
	private void reset() {
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				board[x][y] = 'w';
			}
		}
		win = false;
		tie = false;
		checkIfWinner = 'a';
		try {
			this.setPane();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	private Circle[][] getCircles() {
		Circle[][] pieces = new Circle[7][6];
		Circle circ;
		for(int x = 0; x < pieces.length; x++) {
			for(int y = 0; y < pieces[x].length; y++) {
				circ = new Circle();
				circ.setRadius(40);
				circ.setStroke(Color.BLACK);
				circ.centerXProperty().bind(this.widthProperty().divide(2).subtract((x * 150) - 450));
				circ.centerYProperty().bind(this.heightProperty().divide(2).subtract((y * 100) - 250));
				if(board[x][y] == 'r') {
					circ.setFill(Color.RED);
				}
				else if (board[x][y] == 'b') {
					circ.setFill(Color.BLACK);
				}
				else {
					circ.setFill(Color.WHITE);
				}
				pieces[x][y] = circ;
			}
		}
		return pieces;
	}
	private Rectangle getRec() {
		Rectangle board = new Rectangle();
		board.setHeight(600);
		board.setWidth(1100);
		board.setFill(Color.YELLOW);
		board.setStroke(Color.BLACK);
		board.xProperty().bind(this.widthProperty().divide(2).subtract(board.getWidth() / 2));
		board.yProperty().bind(this.heightProperty().divide(2).subtract(board.getHeight() / 2));
		return board;
	}
	private Text getTurnTitle() {	
		if(!win) {
			if(this.whoseTurn == 'r') {
				text = new Text("Red Turn");
				text.setFill(Color.RED);
				text.setStroke(Color.RED);
				text.setScaleX(2);
				text.setScaleY(2);
			}
			else if(this.whoseTurn == 'b') {
				text = new Text("Black Turn");
				text.setFill(Color.BLACK);
				text.setStroke(Color.BLACK);
				text.setScaleX(2);
				text.setScaleY(2);
			}
			if(first) {
				text.setX(45);
				text.setY(100);
				saveTextPosX = 45;
				saveTextPosY = 100;
				first = false;
			}
			else {
				text.setX(saveTextPosX);
				text.setY(saveTextPosY);
			}
			text.setOnMouseDragged(e -> moveText(e, text));
			text.setOnMousePressed(e -> mousePressed(e,text) );
		}
		return text;
	}
	private HBox getButtons() {
		HBox top = new HBox(82);
		top.setPadding(new Insets(0, 0, 0, 0));
		top.setStyle("-fx-background-color: gold");
		Button col1 = new Button("Column 1");
		col1.setOnAction((ActionEvent e) -> {			
			this.playPiece(6);
			this.animation(6);
		});
		Button col2 = new Button("Column 2");
		col2.setOnAction((e) -> {
			this.playPiece(5);
			this.animation(5);
		});
		Button col3 = new Button("Column 3");
		col3.setOnAction((e) -> {
			this.playPiece(4);
			this.animation(4);
		});
		Button col4 = new Button("Column 4");
		col4.setOnAction((e) -> {
			this.playPiece(3);
			this.animation(3);
		});
		Button col5 = new Button("Column 5");
		col5.setOnAction((e) -> {
			this.playPiece(2);
			this.animation(2);
		});
		Button col6 = new Button("Column 6");
		col6.setOnAction((e) -> {
			this.playPiece(1);
			this.animation(1);
		});
		Button col7 = new Button("Column 7");
		col7.setOnAction((e) -> {
			this.playPiece(0);
			this.animation(0);
		});
		top.getChildren().addAll(col1,col2,col3,col4,col5,col6,col7);
		top.setAlignment(Pos.CENTER);
		return top;
	}
	private void animation(int row) {
		if(!win) {
			Circle[][] circle = this.getCircles();
			ABallPane animation = new ABallPane(whoseTurn, circle[row][0].getCenterX(), circle[row][this.getLastWhite(row)].getCenterY());
			this.getChildren().addAll(animation.getChildren());
		}

	}
	private int getLastWhite(int row) {
		for(int y = board[row].length-1; y >= 0; y--) {
			if(board[row][y] != 'w') {
				return y;
			}
		}
		return 0;
	}
	public void soundEffect(String filePath) {
		sounds = new Music();
		sounds.playSound(filePath);
	}
	public void soundEffect(int x) {
		sounds = new Music();
		sounds.playSound(x);
	}
	public void playPiece(int row) {
		if(!win && this.rowNotFull(row)) {
			int random = (int)(Math.random() * 3) + 1;
			this.soundEffect(random - 1);
		}
		boolean once = true;
		for(int y = 0; y < board[row].length; y++) {
			if(board[row][y] == 'w' && once && !win) {
				board[row][y] = whoseTurn;
				once = false;
				checkIfWinner = whoseTurn;
				moves++;
				if(whoseTurn == 'r') {
					whoseTurn = 'b';
				}
				else {
					whoseTurn = 'r';
				}
			}			
		}
		try {
			this.setPane();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	private boolean rowNotFull(int row) {
		for(int x = 0; x < board[row].length; x++) {
			if(board[row][x] == 'w') {
				return true;
			}
		}
		return false;
	}
	private boolean checkWin() {
		if(this.winHori()||this.winVer()||this.winDiagL()|| this.winDiagR()) {
			return true;
		}
		return false;
	}
	private boolean winDiagR() {
		for(int x = board.length-1; x > 2; x--) {
			for(int y = 0; y < board[x].length - 3; y++) {
				if(board[x][y] == checkIfWinner && board[x-1][y+1] == checkIfWinner && board[x-2][y+2] == checkIfWinner && board[x-3][y+3] == checkIfWinner) {
					return true;
				}
			}
		}
		return false;
	}
	private boolean winDiagL() {
		for(int x = 0; x < board.length - 3; x++) {
			for(int y = 0; y < board[x].length - 3; y++) {
				if(board[x][y] == checkIfWinner && board[x+1][y+1] == checkIfWinner && board[x+2][y+2] == checkIfWinner && board[x+3][y+3] == checkIfWinner) {
					return true;
				}
			}
		}	
		return false;
	}
	private boolean winVer() {	
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length - 3; y++) {
				if(board[x][y] == checkIfWinner && board[x][y + 1] == checkIfWinner && board[x][y + 2] == checkIfWinner && board[x][y + 3] == checkIfWinner) {
					return true;
				}
			}
		}
		return false;
	}
	private boolean winHori() {
		for(int x = 0; x < board.length - 3; x++) {
			for(int y = 0; y < board[x].length; y++) {
				if(board[x][y] == checkIfWinner && board[x+1][y] == checkIfWinner && board[x+2][y] == checkIfWinner && board[x+3][y] == checkIfWinner) {
					return true;
				}
			}
		}
		return false;
	}
	private void mousePressed(MouseEvent e, Text text) {
		double mouseX = e.getX();
		double mouseY = e.getY();
		textX = mouseX - text.getX();
		textY = text.getY() - mouseY; 
	}

	private void moveText(MouseEvent mouseE, Text text) {
		text.setX(mouseE.getX() - textX);
		text.setY(mouseE.getY() + textY);
		saveTextPosX = text.getX();
		saveTextPosY = text.getY();
	}
}
