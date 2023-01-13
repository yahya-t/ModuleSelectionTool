package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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

public class ReserveModulesPane extends Accordion {

	private ObservableList<Module> obsUnselectedTerm1, obsUnselectedTerm2, obsReservedTerm1, obsReservedTerm2;
	private TitledPane ttpTerm1Modules, ttpTerm2Modules;
	private ListView<Module> lstUnselectedTerm1, lstUnselectedTerm2, lstReservedTerm1, lstReservedTerm2;
	private Button btnAddTerm1, btnRemoveTerm1, btnAddTerm2, btnRemoveTerm2, btnConfirmTerm1, btnConfirmTerm2;
	private TextField txtCreditsTerm1, txtCreditsTerm2;
	
	public ReserveModulesPane() {
		this.setPadding(new Insets(20, 20, 20, 20));
		
		//initialise ObservableLists
		obsUnselectedTerm1 = FXCollections.observableArrayList();
		obsUnselectedTerm2 = FXCollections.observableArrayList();
		obsReservedTerm1 = FXCollections.observableArrayList();
		obsReservedTerm2 = FXCollections.observableArrayList();
				
		//initialise TextFields
		lstUnselectedTerm1 = new ListView<Module>(obsUnselectedTerm1); //add modules to parameter
		lstUnselectedTerm2 = new ListView<Module>(obsUnselectedTerm2); //add modules to parameter
		lstReservedTerm1 = new ListView<Module>(obsReservedTerm1);
		lstReservedTerm2 = new ListView<Module>(obsReservedTerm2);
		
		//initialise Buttons
		btnAddTerm1 = new Button("Add");
		btnRemoveTerm1 = new Button("Remove");
		btnConfirmTerm1 = new Button("Confirm");
		
		btnAddTerm2 = new Button("Add");
		btnRemoveTerm2 = new Button("Remove");
		btnConfirmTerm2 = new Button("Confirm");

		//initialise TextField
		txtCreditsTerm1 = new TextField("0");
		txtCreditsTerm2 = new TextField("0");
		
		//create HBox to store the ListViews
		HBox hbxUnselectedModules1 = new HBox(modulesBox("Unselected Term 1 modules", lstUnselectedTerm1, 300, 300));
		HBox hbxReservedModules1 = new HBox(modulesBox("Reserved Term 1 modules", lstReservedTerm1, 300, 300));
		
		HBox hbxUnselectedModules2 = new HBox(modulesBox("Unselected Term 2 modules", lstUnselectedTerm2, 300, 300));
		HBox hbxReservedModules2 = new HBox(modulesBox("Reserved Term 2 modules", lstReservedTerm2, 300, 300));
		
		//create HBox to store the buttons
		HBox hbxButtons1 = new HBox(buttonsBox("Reserve 30 credits worth of Term 1 modules", btnAddTerm1, btnRemoveTerm1, btnConfirmTerm1));
		HBox hbxButtons2 = new HBox(buttonsBox("Reserve 30 credits worth of Term 2 modules", btnAddTerm2, btnRemoveTerm2, btnConfirmTerm2));

		//create HBox to store Term 1 and Term 2 credits
		HBox hbxCredits1 = new HBox(creditsBox("Reserved Term 1 credits:", txtCreditsTerm1));
		HBox hbxCredits2 = new HBox(creditsBox("Reserved Term 2 credits:", txtCreditsTerm2));

		//create container to store the nodes
		VBox container1 = new VBox(containerBox(hbxUnselectedModules1, hbxReservedModules1, hbxButtons1, hbxCredits1));
		VBox container2 = new VBox(containerBox(hbxUnselectedModules2, hbxReservedModules2, hbxButtons2, hbxCredits2));
		
		//initialise titled pans and add nodes
		ttpTerm1Modules = new TitledPane("Term 1 modules", container1);
		ttpTerm2Modules = new TitledPane("Term 2 modules", container2);
		
		//disabled tab 2
		ttpTerm2Modules.setDisable(true);
		
		this.getPanes().addAll(ttpTerm1Modules, ttpTerm2Modules);
		this.setExpandedPane(ttpTerm1Modules);
	}
	

			/* -- design methods -- */
	
	/** Creates a VBox with a Label and a ListView
	 * @param label description of the module ListView
	 * @param list the ListView of modules
	 * @param width width of the ListView
	 * @param height height of the ListView
	 */
	private VBox modulesBox(String label, ListView<Module> list, int width, int height) {
		Label descriptionLabel = new Label(label);
		VBox vbxContainer = new VBox(descriptionLabel, list);
		list.setPrefSize(width, height);
		VBox.setVgrow(list, Priority.ALWAYS);
		HBox.setHgrow(vbxContainer, Priority.ALWAYS);
		return vbxContainer;
	}
	
	/** Creates HBox with a Label and two Buttons
	 * @param label description of the button functions
	 * @param btn1 first Button
	 * @param btn2 second Button
	 * @param btn3 third Button
	 */
	private HBox buttonsBox(String label, Button btn1, Button btn2, Button btn3) {
		btn1.setPrefSize(80, 20);
		btn2.setPrefSize(80, 20);
		btn3.setPrefSize(80, 20);
		Label lblDescription = new Label(label);
		HBox hbxContainer = new HBox(lblDescription, btn1, btn2, btn3);
		hbxContainer.setAlignment(Pos.CENTER);
		hbxContainer.setSpacing(15);
		return hbxContainer;
	}
	
	/** Creates a stylised HBox with a Label and a TextField
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
		hbxContainerInner.setPadding(new Insets(5,5,5, 5));
		hbxContainerInner.setStyle("-fx-background-color: #F0F8FF;");
		hbxContainerInner.setBorder(new Border(new BorderStroke(Color.LIGHTBLUE, BorderStrokeStyle.SOLID, new CornerRadii(10), BorderStroke.MEDIUM)));
		hbxContainerOuter.setAlignment(Pos.CENTER);
		return hbxContainerOuter;
	}
	
	/** Creates a VBox with two modulesBoxes and a buttonsBox
	 * @param modulesBox1 the modulesBox to add
	 * @param modulesBox2 he modulesBox to add
	 * @param buttonsBox he buttonsBox to add
	 */
	public VBox containerBox(HBox modulesBox1, HBox modulesBox2, HBox buttonsBox, HBox creditsBox) {
		//GridPane to store the moduleBoxes
		GridPane gpnContainerInner = new GridPane();
		gpnContainerInner.add(modulesBox1, 0, 0);
		gpnContainerInner.add(modulesBox2, 1, 0);
		gpnContainerInner.setHgap(20);
		gpnContainerInner.setVgap(20);
		//set column and row constraints to GridPane
		ColumnConstraints column0 = new ColumnConstraints();
		ColumnConstraints column1 = new ColumnConstraints(); 
		column0.setHgrow(Priority.ALWAYS);
		column1.setHgrow(Priority.ALWAYS);
		gpnContainerInner.getColumnConstraints().addAll(column0, column1);
		RowConstraints row0 = new RowConstraints();
		row0.setVgrow(Priority.ALWAYS);
		gpnContainerInner.getRowConstraints().addAll(row0);
		//HBox to store the buttobsBox
		HBox hbxContainerButtons = new HBox(buttonsBox, creditsBox);
		hbxContainerButtons.setPadding(new Insets(20, 20, 20, 20));
		hbxContainerButtons.setSpacing(50);
		hbxContainerButtons.setAlignment(Pos.CENTER);
		//store inner container in outer container
		VBox vbxContainerOuter = new VBox(gpnContainerInner, hbxContainerButtons);
		vbxContainerOuter.setPadding(new Insets(40, 30, 40, 30));
		//setVgrow() properties for inner and outer containers
		VBox.setVgrow(gpnContainerInner, Priority.ALWAYS);
		VBox.setVgrow(vbxContainerOuter, Priority.ALWAYS);
		return vbxContainerOuter;
	}
	
	
			/* -- access methods -- */
	
	public ObservableList<Module> getUnselectedTerm1() {
		return obsUnselectedTerm1;
	}
	
	public ObservableList<Module> getUnselectedTerm2() {
		return obsUnselectedTerm2;
	}
	
	public ObservableList<Module> getReservedTerm1() {
		return obsReservedTerm1;
	}
	
	public ObservableList<Module> getReservedTerm2() {
		return obsReservedTerm2;
	}
	
	public void addFromUnselectedTerm1() {
		if(lstUnselectedTerm1.getSelectionModel().getSelectedItem() != null) {
			//adds module to the reserved modules list
			obsReservedTerm1.add(lstUnselectedTerm1.getSelectionModel().getSelectedItem());
			//adds credits
			addCredits1(lstUnselectedTerm1.getSelectionModel().getSelectedItem().getModuleCredits());
			//removes module from the unselected modules list
			obsUnselectedTerm1.remove(lstUnselectedTerm1.getSelectionModel().getSelectedItem());
		}
}

	public void addFromUnselectedTerm2() {
		if(lstUnselectedTerm2.getSelectionModel().getSelectedItem() != null) {
			//adds module to the reserved modules list
			obsReservedTerm2.add(lstUnselectedTerm2.getSelectionModel().getSelectedItem());
			//adds credits
			addCredits2(lstUnselectedTerm2.getSelectionModel().getSelectedItem().getModuleCredits());
			//removes module from the unselected modules list
			obsUnselectedTerm2.remove(lstUnselectedTerm2.getSelectionModel().getSelectedItem());
		}	
	}
	
	public void removeFromSelectedTerm1() {
		if(lstReservedTerm1.getSelectionModel().getSelectedItem() != null) {
			if(!(lstReservedTerm1.getSelectionModel().getSelectedItem().isMandatory())) {
				//adds module to the unselected modules list
				obsUnselectedTerm1.add(lstReservedTerm1.getSelectionModel().getSelectedItem());
				//removes credits
				removeCredits1(lstReservedTerm1.getSelectionModel().getSelectedItem().getModuleCredits());
				//removes module from the selected modules list
				obsReservedTerm1.remove(lstReservedTerm1.getSelectionModel().getSelectedItem());
			}
		}
	}
	
	public void removeFromSelectedTerm2() {
		if(lstReservedTerm2.getSelectionModel().getSelectedItem() != null) {
			if(!(lstReservedTerm2.getSelectionModel().getSelectedItem().isMandatory())) {
				//adds module to the unselected modules list
				obsUnselectedTerm2.add(lstReservedTerm2.getSelectionModel().getSelectedItem());
				//removes credits
				removeCredits2(lstReservedTerm2.getSelectionModel().getSelectedItem().getModuleCredits());
				//removes module from the selected modules list
				obsReservedTerm2.remove(lstReservedTerm2.getSelectionModel().getSelectedItem());
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
	
	public void changePane() {
		this.setExpandedPane(ttpTerm2Modules);
	}
	
	public void enableTab1() {
		ttpTerm1Modules.setDisable(false);
	}
	
	public void enableTab2() {
		ttpTerm2Modules.setDisable(false);
	}
	
	public void disableTab1() {
		ttpTerm1Modules.setDisable(true);
	}
	
	public void disableTab2() {
		ttpTerm2Modules.setDisable(true);
	}
	
	public void enableAllButtons() {
		btnAddTerm1.setDisable(false);
		btnAddTerm2.setDisable(false);
		btnRemoveTerm1.setDisable(false);
		btnRemoveTerm2.setDisable(false);
		btnConfirmTerm1.setDisable(false);
		btnConfirmTerm2.setDisable(false);
	}
	
	public void disableAllButtons() {
		btnAddTerm1.setDisable(true);
		btnAddTerm2.setDisable(true);
		btnRemoveTerm1.setDisable(true);
		btnRemoveTerm2.setDisable(true);
		btnConfirmTerm1.setDisable(true);
		btnConfirmTerm2.setDisable(true);
	}
	
	public void clearAllData() {
		obsUnselectedTerm1.clear();
		obsUnselectedTerm2.clear();
		obsReservedTerm1.clear();
		obsReservedTerm2.clear();
		txtCreditsTerm1.setText("0");
		txtCreditsTerm2.setText("0");
		this.setExpandedPane(ttpTerm1Modules);
	}
	
			/* -- event handler access methods -- */
	
	public void addAddReserved1Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm1.setOnAction(handler);
	}
	
	public void addRemoveReserved1Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm1.setOnAction(handler);
	}
	
	public void addAddReserved2Handler(EventHandler<ActionEvent> handler) {
		btnAddTerm2.setOnAction(handler);
	}
	
	public void addRemoveReserved2Handler(EventHandler<ActionEvent> handler) {
		btnRemoveTerm2.setOnAction(handler);
	}
	
	public void addConfirmReserved1Handler(EventHandler<ActionEvent> handler) {
		btnConfirmTerm1.setOnAction(handler);
	}
	
	public void addConfirmReserved2Handler(EventHandler<ActionEvent> handler) {
		btnConfirmTerm2.setOnAction(handler);
	}
	
}
