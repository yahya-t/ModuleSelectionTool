package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OverviewSelectionPane extends BorderPane {

	private TextArea txtProfile, txtSelectedModules, txtReservedModules;
	private Button btnSaveOverview, btnRestart;
	
	public OverviewSelectionPane() {
		this.setPadding(new Insets(20, 20, 40, 20));
		
		//initialise TextAreas
		txtProfile = new TextArea("Profile will appear here....");
		txtSelectedModules = new TextArea("Selected modules will appear here....");
		txtReservedModules = new TextArea("Reserved modules will appear here....");
		
		//initialise Button
		btnSaveOverview = new Button("Save Overview");
		btnRestart = new Button("Restart");
		//create containers
		HBox centerContainer = new HBox(overviewBox(txtProfile, txtSelectedModules, txtReservedModules));
		HBox bottomContainer = new HBox(buttonsBox(btnSaveOverview, btnRestart));
		
		//align buttons
		bottomContainer.setAlignment(Pos.CENTER);
		
		this.setCenter(centerContainer);
		this.setBottom(bottomContainer);
	}
	
	
			/* -- design methods -- */
	
	private VBox overviewBox(TextArea profile, TextArea selected, TextArea reserved) {
		profile.setPrefSize(620, 150);
		selected.setPrefSize(300, 300);
		reserved.setPrefSize(300, 300);
		profile.setEditable(false);
		selected.setEditable(false);
		reserved.setEditable(false);
		//inner container to store TextFields for selected and reserved modules
		HBox hbxContainerInner = new HBox(selected, reserved);
		hbxContainerInner.setSpacing(30);
		//create outer container to store all nodes
		VBox vbxContainerOuter = new VBox(profile, hbxContainerInner);
		vbxContainerOuter.setSpacing(40);
		vbxContainerOuter.setPadding(new Insets(40, 40, 40, 40));
		//set grow properties
		VBox.setVgrow(hbxContainerInner, Priority.ALWAYS);
		HBox.setHgrow(selected, Priority.ALWAYS);
		HBox.setHgrow(reserved, Priority.ALWAYS);
		HBox.setHgrow(vbxContainerOuter, Priority.ALWAYS);
		return vbxContainerOuter;
	}
	
	private HBox buttonsBox(Button btn1, Button btn2) {
		HBox hbxContainer = new HBox(btn1, btn2);
		hbxContainer.setSpacing(15);
		return hbxContainer;
	}
	
	
			/* -- access methods -- */
	
	public void setProfileOverview(String overview) {
		txtProfile.setText(overview);
	}
	
	public void setSelectedModulesOverview(String overview) {
		txtSelectedModules.setText(overview);
	}
	
	public void setReservedModulesOverview(String overview) {
		txtReservedModules.setText(overview);
	}
	
	public boolean isOverviewEmpty() {
		if(txtProfile.getText().equals("Profile will appear here....") &&
				txtSelectedModules.getText().equals("Selected modules will appear here....") &&
						txtReservedModules.getText().equals("Reserved modules will appear here....")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void clearOverview() {
		txtProfile.clear();
		txtSelectedModules.clear();
		txtReservedModules.clear();
	}

	
			/* -- event handler access methods -- */
	
	public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
		btnSaveOverview.setOnAction(handler);
	}
	
	public void addRestartHandler(EventHandler<ActionEvent> handler) {
		btnRestart.setOnAction(handler);
	}
}
