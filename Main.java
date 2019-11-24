/*
 * Author: Anda Su
 * Teacher: J. Radulovic
 * Jun. 17, 2019
 * This contains the main method, launches the app and displays the GUI
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

//TIMER USED: https://stackoverflow.com/questions/34801227/fast-counting-timer-in-javafx

public class Main extends Application {
	private Scrambler scrambler = new Scrambler();
	private static CubeDisplay cDisplay = new CubeDisplay();
	private Listz results = new Listz();
	private boolean timerPrimed = false;
	private boolean timing = false;

	public void start(Stage primaryStage) throws Exception {
		
		//PRIMARY - BORDERPANE VIEW
		BorderPane bPane = new BorderPane();
		Scene scene = new Scene(bPane, 987, 610);
		scene.setFill(Color.rgb(223, 223, 223));
		primaryStage.setTitle("Cube Timer by Anda Su");
		primaryStage.setScene(scene);
		
		//TOP ELEMENTS
		Label scram = new Label(scrambler.displayScramble());
		scram.setFont(Font.font("Arial", 16));
		
		//BOTTOM ELEMENTS
		Button remove = new Button("Remove");
		Button removeRecent = new Button ("Remove Recent");
		Button clearAll = new Button ("Clear All");
		Button nextScramble = new Button("Next Scramble");
		Button displayCube = new Button("Display Cube");
		ComboBox<String> cubeSize = new ComboBox<String>();
		cubeSize.getItems().add("2x2");
		cubeSize.getItems().add("3x3");
		cubeSize.getItems().add("4x4");
		cubeSize.getItems().add("5x5");
		cubeSize.getSelectionModel().select(1);
		HBox bottom = new HBox(remove, removeRecent, clearAll, cubeSize, nextScramble, displayCube);
		
		//CENTRE ELEMENTS
		final DoubleProperty time = new SimpleDoubleProperty();
		final Label labelTimer = new Label();
		labelTimer.setFont(Font.font("Cambria", 32));
        labelTimer.textProperty().bind(time.asString("%.3f seconds"));
		final AnimationTimer timer = new AnimationTimer() {
            private long startTime ;

            public void start() {
                startTime = System.currentTimeMillis();
                timing = true;
                super.start();
            }

            public void stop() {
                timing = false;
                super.stop();
            }

            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                time.set((now - startTime) / 1000.0);
            }
        };
		
        //LEFT ELEMENTS
		final TableView<Entry> table = new TableView();
		
		//CUBE DISPLAY ELEMENTS
		Label popupCubeDisplay = new Label();
		HBox pop = new HBox(popupCubeDisplay);
		PopOver popup = new PopOver(pop);
		popup.setAutoHide(false);
		popup.setTitle("Draw Scramble");
		popup.setDetached(true);
		
		//TABLE OF RECORDS 
		TableColumn<Entry, Integer> column0 = new TableColumn("Entry #");
		column0.setCellValueFactory(new PropertyValueFactory("entryNumber"));
		
		TableColumn<Entry, Double> column1 = new TableColumn("Time");
        column1.setCellValueFactory(new PropertyValueFactory("time"));

        TableColumn<Entry, Double> column2 = new TableColumn("Ao5");
        column2.setCellValueFactory(new PropertyValueFactory("ao5"));
        
        TableColumn<Entry, Double> column3 = new TableColumn("Ao12");
        column3.setCellValueFactory(new PropertyValueFactory("ao12"));

        table.getColumns().add(column0);
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.setFocusTraversable(false);
        
        //TABLE ACTIONS
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				remove.setOnAction(new EventHandler<ActionEvent>(){
					public void handle(ActionEvent event) {
						if(!table.getItems().isEmpty()) {
							results.remove(table.getSelectionModel().getSelectedItem().getEntryNumber()-1);
							table.getItems().remove(table.getSelectionModel().getSelectedItem());
						}
					}
				});
			}
		});
		table.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
		});
		
		//BOTTOM BUTTON ACTIONS
		//REMOVE
		remove.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
        });
		
		//REMOVE RECENT
		removeRecent.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				if(!table.getItems().isEmpty()) {
					table.getItems().remove(results.size()-1);
					results.removeLatest();
				}
			}
		});
		removeRecent.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
        });
		
		//CLEAR ALL
		clearAll.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				results.removeAll();
				table.getItems().clear();
				table.refresh();
			}
			
		});
		clearAll.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
        });
		
		//CHANGE CUBE SIZE
		cubeSize.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				cDisplay.setFaceSize(cubeSize.getSelectionModel().getSelectedIndex()+2);
				scrambler.setCubeSize(cubeSize.getSelectionModel().getSelectedIndex()+2);
				scram.setText(scrambler.displayScramble());
				cDisplay.scramble(scrambler.getScramble());
				popupCubeDisplay.setText(cDisplay.displayCube());
			}
			
		});
		cubeSize.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
        });
		
		//REQUEST NEW SCRAMBLE
		nextScramble.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				scram.setText(scrambler.displayScramble());
				cDisplay.scramble(scrambler.getScramble());
				popupCubeDisplay.setText(cDisplay.displayCube());
			}
		});
		nextScramble.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
        });
		
		//DISPLAY SCRAMBLED CUBE - 2D NET
		displayCube.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				cDisplay.scramble(scrambler.getScramble());
				popupCubeDisplay.setText(cDisplay.displayCube());
				if(!popup.isShowing()) {
					popup.show(primaryStage);
				}
				else {
					popup.hide();
				}
			}
		});
		displayCube.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent event) {
				labelTimer.requestFocus();
			}
        });
		
		//START TIMER
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE && !timerPrimed) {
					timerPrimed = true;
					labelTimer.setTextFill(Color.rgb(0, 255, 0));
				}
				else if(event.getCode() == KeyCode.SPACE && timerPrimed && timing){
					timerPrimed = false;
					timing = false;
					timer.stop();
					Entry time1 = new Entry(time.get());
					results.add(time1);
					table.getItems().addAll(time1);
					scram.setText(scrambler.displayScramble());
					cDisplay.scramble(scrambler.getScramble());
					popupCubeDisplay.setText(cDisplay.displayCube());
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE && timerPrimed) {
					timing = true;
					timer.start();
					scram.setText("");
					labelTimer.setTextFill(Color.rgb(0, 0, 0));
				}
			}
		});
		
		//ADD ELEMENTS TO THE BORDERPANE
		bPane.setTop(scram);
		bPane.setLeft(table);
		bPane.setBottom(bottom);
		bPane.setAlignment(scram, Pos.CENTER);
		bPane.setCenter(labelTimer);
		
		//DISPLAY THE SCENE
		primaryStage.show();
		}
		
	public static void main(String[] args) {
		launch(args);
	}
	
}
