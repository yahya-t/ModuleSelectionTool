package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Module;

public class SelectModulesPane extends GridPane {

	private ObservableList<Module> obsUnselectedTerm1, obsUnselectedTerm2, obsSelectedYearLong, obsSelectedTerm1, obsSelectedTerm2;
	private ListView<Module> lstUnselectedTerm1, lstUnselectedTerm2, lstSelectedYearLong, lstSelectedTerm1, lstSelectedTerm2;
	private Button btnAddTerm1, btnRemoveTerm1, btnAddTerm2, btnRemoveTerm2, btnReset, btnSubmit;
	private TextField txtCreditsTerm1, txtCreditsTerm2;
	
	public SelectModulesPane() {
		this.setPadding(new Insets(20,20,20,20));
		
		//set column constraints to grow horizontally
		ColumnConstraints column0 = new ColumnConstraints();
		column0.setHgrow(Priority.ALWAYS);
		ColumnConstraints column1 = new ColumnConstraints(); 
		column1.setHgrow(Priority.ALWAYS);
		this.getColumnConstraints().addAll(column0, column1);
		
		//set row constraints for only row 0 to grow vertically
		RowConstraints row0 = new RowConstraints();
		row0.setVgrow(Priority.ALWAYS);
		this.getRowConstraints().addAll(row0);
		
		//initialise ObservableLists
		obsUnselectedTerm1 = FXCollections.observableArrayList();
		obsUnselectedTerm2 = FXCollections.observableArrayList();
		obsSelectedYearLong = FXCollections.observableArrayList();
		obsSelectedTerm1 = FXCollections.observableArrayList();
		obsSelectedTerm2 = FXCollections.observableArrayList();
		
		//initialise TextFields
		lstUnselectedTerm1 = new ListView<Module>(obsUnselectedTerm1); //add modules to parameter
		lstUnselectedTerm2 = new ListView<Module>(obsUnselectedTerm2); //add modules to parameter
		
		lstSelectedYearLong = new ListView<Module>(obsSelectedYearLong);
		lstSelectedTerm1 = new ListView<Module>(obsSelectedTerm1);
		lstSelectedTerm2 = new ListView<Module>(obsSelectedTerm2);
		
		//initialise Buttons
		btnAddTerm1 = new Button("Add");
		btnRemoveTerm1 = new Button("Remove");
		
		btnAddTerm2 = new Button("Add");
		btnRemoveTerm2 = new Button("Remove");
		
		btnSubmit = new Button("Submit");
		btnReset = new Button("Reset");
		btnSubmit.setPrefSize(80, 20);
		btnReset.setPrefSize(80, 20);
		
		//initialise TextFields
		txtCreditsTerm1 = new TextField("0");
		txtCreditsTerm2 = new TextField("0");
		
		//create HBox to store the module ListViews
		HBox hbxUnselectedModules1 = new HBox(modulesBox("Unselected Term 1 modules", lstUnselectedTerm1, 300, 120));
		HBox hbxUnselectedModules2 = new HBox(modulesBox("Unselected Term 2 modules", lstUnselectedTerm2, 300, 120));
		
		HBox hbxSelectedYearLongModules = new HBox(modulesBox("Selected Year Long modules", lstSelectedYearLong, 300, 50));
		HBox hbxSelectedModules1 = new HBox(modulesBox("Selected Term 1 modules", lstSelectedTerm1, 300, 120));
		HBox hbxSelectedModules2 = new HBox(modulesBox("Selected Term 2 modules", lstSelectedTerm2, 300, 120));
		hbxSelectedModules1.setPadding(new Insets(30, 0, 0, 0));
		hbxSelectedModules2.setPadding(new Insets(30, 0, 0, 0));
		
		//create HBox to store Add and Remove buttons, which will be stored inside the VBox leftContainer
		HBox hbxModuleButtons1 = new HBox(buttonsBox("Term 1", btnAddTerm1, btnRemoveTerm1));
		hbxModuleButtons1.setAlignment(Pos.CENTER);
		hbxModuleButtons1.setPadding(new Insets(20,0,30,0));
		
		HBox hbxModuleButtons2 = new HBox(buttonsBox("Term 2", btnAddTerm2, btnRemoveTerm2));
		hbxModuleButtons2.setAlignment(Pos.CENTER);
		hbxModuleButtons2.setPadding(new Insets(20,0,30,0));
		
		//create HBox to store Term 1 and Term 2 credits
		HBox hbxCredits1 = new HBox(creditsBox("Current Term 1 credits:", txtCreditsTerm1));
		hbxCredits1.setAlignment(Pos.CENTER);
		
		HBox hbxCredits2 = new HBox(creditsBox("Current Term 2 credits:", txtCreditsTerm2));
		hbxCredits2.setAlignment(Pos.CENTER);
		hbxCredits2.setPadding(new Insets(30,0,0,0));
		
		//create VBox to store left and right fields
		VBox leftContainer = new VBox();
		leftContainer.setPadding(new Insets(20, 20, 20, 20));
		leftContainer.setAlignment(Pos.CENTER_RIGHT);
		leftContainer.getChildren().addAll(hbxUnselectedModules1, hbxModuleButtons1, hbxUnselectedModules2, hbxModuleButtons2, hbxCredits1, btnSubmit);
		
	
		VBox rightContainer = new VBox();
		rightContainer.setPadding(new Insets(20, 20, 20, 20));
		rightContainer.setAlignment(Pos.CENTER_LEFT);
		rightContainer.getChildren().addAll(hbxSelectedYearLongModules, hbxSelectedModules1, hbxSelectedModules2, hbxCredits2, btnReset);
		
		//set vertical grow properties
		VBox.setVgrow(hbxUnselectedModules1, Priority.ALWAYS);
		VBox.setVgrow(hbxUnselectedModules2, Priority.ALWAYS);
		
		VBox.setVgrow(hbxSelectedModules1, Priority.ALWAYS);
		VBox.setVgrow(hbxSelectedModules2, Priority.ALWAYS);
		
		//add containers
		this.add(leftContainer, 0, 0);
		this.add(rightContainer, 1, 0);
	}
	
	
			/* -- design methods -- */
	
	/** Creates a VBox with a Label and a ListView
	 * @param label description of the module ListView
	 * @param list the ListView of modules
	 * @param width width of the ListView
	 * @param height height of the ListView
	 */
	private VBox modulesBox(String label, ListView<Module> list, int width, int height) {
		Label lblDescription = new Label(label);
		VBox vbxContainer = new VBox(lblDescription, list);
		list.setPrefSize(width, height);
		VBox.setVgrow(list, Priority.ALWAYS);
		HBox.setHgrow(vbxContainer, Priority.ALWAYS);
		return vbxContainer;
	}
	
	/** Creates HBox with a Label and two Buttons
	 * @param label description of the button functions
	 * @param btn1 first button
	 * @param btn2 second button
	 */
	private HBox buttonsBox(String label, Button btn1, Button btn2) {
		btn1.setPrefSize(80, 20);
		btn2.setPrefSize(80, 20);
		Label lblDescription = new Label(label);
		HBox hbxContainer = new HBox(lblDescription, btn1, btn2);
		hbxContainer.setAlignment(Pos.CENTER);
		hbxContainer.setSpacing(15);
		return hbxContainer;
	}
	
	/** Creates a stylised HBox with a label and a TextField
	 * @param label description of the credit TextField
	 * @param textField the TextField used to show the credits
	 */
	private HBox creditsBox(String label, TextField textField) {
		textField.setEditable(false);
		textField.setPrefSize(50, 5);
		textField.setAlignment(Pos.CENTER);
		Label lblDescription = new Label(label);
		HBox hbxContainerOuter = new HBox();
		HBox hbxContainerInner;
		hbxContainerOuter.getChildren().addAll(hbxContainerInner = new HBox(lblDescription, textField));
		hbxContainerInner.setAlignment(Pos.CENTER);
		hbxContainerInner.setSpacing(15);
		hbxContainerInner.setPadding(new Insets(5, 5, 5, 5));
		hbxContainerInner.setStyle("-fx-background-color: #F0F8FF;");
		hbxContainerInner.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.MEDIUM)));
		hbxContainerOuter.setAlignment(Pos.CENTER);
		hbxContainerOuter.setPadding(new Insets(0, 0, 20, 0));
		return hbxContainerOuter;
	}
	
	
			/* -- access methods -- */
	
	public ObservableList<Module> getUnselectedTerm1() {
		return obsUnselectedTerm1;
	}
	
	public ObservableList<Module> getUnselectedTerm2() {
		return obsUnselectedTerm2;
	}
	
	public ObservableList<Module> getSelectedTerm1() {
		return obsSelectedTerm1;
	}
	
	public ObservableList<Module> getSelectedTerm2() {
		return obsSelectedTerm2;
	}
	
	public ObservableList<Module> getSelectedYearLong() {
		return obsSelectedYearLong;
	}
	
	public void addModules(ObservableList<Module> list, Module modulesToAdd) {
		list.addAll(modulesToAdd);
	}
	
	public void clearAllData() {
		obsUnselectedTerm1.clear();
		obsUnselectedTerm2.clear();
		obsSelectedYearLong.clear();
		obsSelectedTerm1.clear();
		obsSelectedTerm2.clear();
		txtCreditsTerm1.setText("0");
		txtCreditsTerm2.setText("0");
	}
	
	public void addFromUnselectedTerm1() {
			if(lstUnselectedTerm1.getSelectionModel().getSelectedItem() != null) {
				//adds module to the selected modules list
				obsSelectedTerm1.add(lstUnselectedTerm1.getSelectionModel().getSelectedItem());
				//adds credits
				addCredits1(lstUnselectedTerm1.getSelectionModel().getSelectedItem().getModuleCredits());
				//removes module from the unselected modules list
				obsUnselectedTerm1.remove(lstUnselectedTerm1.getSelectionModel().getSelectedItem());
			}
	}
	
	public void addFromUnselectedTerm2() {
		if(lstUnselectedTerm2.getSelectionModel().getSelectedItem() != null) {
			//adds module to the selected modules list
			obsSelectedTerm2.add(lstUnselectedTerm2.getSelectionModel().getSelectedItem());
			//adds credits
			addCredits2(lstUnselectedTerm2.getSelectionModel().getSelectedItem().getModuleCredits());
			//removes module from the unselected modules list
			obsUnselectedTerm2.remove(lstUnselectedTerm2.getSelectionModel().getSelectedItem());
		}	
	}
	
	public void removeFromSelectedTerm1() {
		if(lstSelectedTerm1.getSelectionModel().getSelectedItem() != null) {
			if(!(lstSelectedTerm1.getSelectionModel().getSelectedItem().isMandatory())) {
				//adds module to the unselected modules list
				obsUnselectedTerm1.add(lstSelectedTerm1.getSelectionModel().getSelectedItem());
				//removes credits
				removeCredits1(lstSelectedTerm1.getSelectionModel().getSelectedItem().getModuleCredits());
				//removes module from the selected modules list
				obsSelectedTerm1.remove(lstSelectedTerm1.getSelectionModel().getSelectedItem());
			}
		}
	}
	
	public void removeFromSelectedTerm2() {
		if(lstSelectedTerm2.getSelectionModel().getSelectedItem() != null) {
			if(!(lstSelectedTerm2.getSelectionModel().getSelectedItem().isMandatory())) {
				//adds module to the unselected modules list
				obsUnselectedTerm2.add(lstSelectedTerm2.getSelectionModel().getSelectedItem());
				//removes credits
				removeCredits2(lstSelectedTerm2.getSelectionModel().getSelectedItem().getModuleCredits());
				//removes module from the selected modules list
				obsSelectedTerm2.remove(lstSelectedTerm2.getSelectionModel().getSelectedItem());
			}
		}	
	}
	
	public int getCredits1() {
		int count = Integer.parseInt(txtCreditsTerm1.getText());
		return count;
	}
	
	public int getCredits2() {
		int count = Integer.parseInt(txtCreditsTerm2.getText());
		return count;
	}

	public void addCredits1(int credits) {
		int c = Integer.parseInt(txtCreditsTerm1.getText()) + credits;
		txtCreditsTerm1.setText(String.valueOf(c));
	}
	
	public void addCredits2(int credits) {
		int c = Integer.parseInt(txtCreditsTerm2.getText()) + credits;
		txtCreditsTerm2.setText(String.valueOf(c));
	}
	
	public void removeCredits1(int credits) {
		int c = Integer.parseInt(txtCreditsTerm1.getText()) - credits;
		txtCreditsTerm1.setText(String.valueOf(c));
	}
	
	public void removeCredits2(int credits) {
		int c = Integer.parseInt(txtCreditsTerm2.getText()) - credits;
		txtCreditsTerm2.setText(String.valueOf(c));
	}
	
	public void enableAllButtons() {
		btnAddTerm1.setDisable(false);
		btnAddTerm2.setDisable(false);
		btnRemoveTerm1.setDisable(false);
		btnRemoveTerm2.setDisable(false);
		btnReset.setDisable(false);
		btnSubmit.setDisable(false);
	}
	
	public void disableAllButtons() {
		btnAddTerm1.setDisable(true);
		btnAddTerm2.setDisable(true);
		btnRemoveTerm1.setDisable(true);
		btnRemoveTerm2.setDisable(true);
		btnReset.setDisable(true);
		btnSubmit.setDisable(true);
	}
	
	
			/* -- event handler access methods -- */
	
	public void addAddTerm1Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm1.setOnAction(handler);
	}
	
	public void addRemoveTerm1Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm1.setOnAction(handler);
	}
	
	public void addAddTerm2Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm2.setOnAction(handler);
	}
	
	public void addRemoveTerm2Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm2.setOnAction(handler);
	}
	
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		btnReset.setOnAction(handler);
	}
	
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		btnSubmit.setOnAction(handler);
	}
}
