package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Schedule;
import model.Module;
import model.StudentProfile;
import view.ModuleSelectionToolRootPane;
import view.OverviewSelectionPane;
import view.ReserveModulesPane;
import view.SelectModulesPane;
import view.CreateStudentProfilePane;
import view.ModuleSelectionToolMenuBar;

public class ModuleSelectionToolController {

	//fields to be used throughout class
	private ModuleSelectionToolRootPane view;
	private StudentProfile model;
	
	private CreateStudentProfilePane cspp;
	private SelectModulesPane smp;
	private ReserveModulesPane rmp;
	private OverviewSelectionPane osp;
	private ModuleSelectionToolMenuBar mstmb;

	public ModuleSelectionToolController(ModuleSelectionToolRootPane view, StudentProfile model) {
		//initialise view and model fields
		this.view = view;
		this.model = model;
		
		//initialise view subcontainer fields
		cspp = view.getCreateStudentProfilePane();
		smp = view.getSelectModulesPane();
		rmp = view.getReserveModulesPane();
		mstmb = view.getModuleSelectionToolMenuBar();
		osp = view.getOverviewSelectionPane();

		//add courses to combobox in create student profile pane using the generateAndReturnCourses helper method below
		cspp.addCoursesToComboBox(generateAndReturnCourses());

		//attach event handlers to view using private helper method
		this.attachEventHandlers();	
	}

	
	//helper method - used to attach event handlers
	private void attachEventHandlers() {
		//attach an event handler to the create student profile pane
		cspp.addCreateStudentProfileHandler(new CreateStudentProfileHandler());
		smp.addAddTerm1Handler(new AddTerm1Handler());
		smp.addAddTerm2Handler(new AddTerm2Handler());
		smp.addRemoveTerm1Handler(new RemoveTerm1Handler());
		smp.addRemoveTerm2Handler(new RemoveTerm2Handler());
		smp.addSubmitHandler(new SubmitHandler());
		smp.addResetHandler(new ResetHandler());
		rmp.addAddReserved1Handler(new AddReservedTerm1Handler());
		rmp.addAddReserved2Handler(new AddReservedTerm2Handler());
		rmp.addRemoveReserved1Handler(new RemoveReservedTerm1Handler());
		rmp.addRemoveReserved2Handler(new RemoveReservedTerm2Handler());
		rmp.addConfirmReserved1Handler(new ConfirmReserved1Handler());
		rmp.addConfirmReserved2Handler(new ConfirmReserved2Handler());
		osp.addSaveOverviewHandler(new SaveOverviewHandler());	
		osp.addRestartHandler(new RestartHandler());
		mstmb.addSaveHandler(new SaveProfileHandler());
		mstmb.addLoadHandler(new LoadProfileHandler());
		mstmb.addAboutHandler(new AboutHandler());
		mstmb.addExitHandler(new ExitHandler());
	}

	
			/* -- Event Handlers -- */
	
	//event handler (currently empty), which can be used for creating a profile
	private class CreateStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			
			if(cspp.hasEmptyFields() == true) {
				alertDialogBuilder(AlertType.WARNING, "Create Student Profile", null, "Please ensure all student details are entered");
			} else if (cspp.isDateInPast() == true) {
				alertDialogBuilder(AlertType.WARNING, "Create Student Profile", null, "The date cannot be in the past");
			} else {
				//get inputs from TextFields and place into the StudentProfile model class
				model.setStudentCourse(cspp.getSelectedCourse());
				model.setStudentPnumber(cspp.getStudentPnumber());
				model.setStudentName(cspp.getStudentName());
				model.setStudentEmail(cspp.getStudentEmail());
				model.setSubmissionDate(cspp.getStudentDate());
				//disable button to prevent 
				cspp.disableSubmitButton();				
				//redirect to SelecteModulesPane tab and populate the modules ListViews with the corresponding modules	
				view.changeTab(1);
				smp.clearAllData();
				//populate the module selection
				for(Module m : model.getStudentCourse().getAllModulesOnCourse()) {
					if(m.getDelivery().equals(Schedule.TERM_1)) {
						if(m.isMandatory()) {
							smp.getSelectedTerm1().addAll(m);
							smp.addCredits1(m.getModuleCredits());
						} else {
							smp.getUnselectedTerm1().addAll(m);
						}
					} else if(m.getDelivery().equals(Schedule.TERM_2)) {
						if(m.isMandatory()) {
							smp.getSelectedTerm2().addAll(m);
							smp.addCredits2(m.getModuleCredits());
						} else {
							smp.getUnselectedTerm2().addAll(m);
						}
					} else if(m.getDelivery().equals(Schedule.YEAR_LONG)) {
						smp.getSelectedYearLong().addAll(m);
						//credits of the 'year long module' is divided by two for the first and second term
						smp.addCredits1(m.getModuleCredits() / 2); 
						smp.addCredits2(m.getModuleCredits() / 2);
					} 
				}
			}
		}
	}

	private class AddTerm1Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(smp.getCredits1() < 60) {
				smp.addFromUnselectedTerm1();
			}
		}
	}
	
	private class AddTerm2Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(smp.getCredits2() < 60) {
				smp.addFromUnselectedTerm2();
			}
		}
	}
	
	private class RemoveTerm1Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(smp.getCredits1() > 0) {
				smp.removeFromSelectedTerm1();
			}
		}
	}
	
	private class RemoveTerm2Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(smp.getCredits2() > 0) {
				smp.removeFromSelectedTerm2();
			}
		}
	}
	
	private class SubmitHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(smp.getCredits1() == 60 && smp.getCredits2() == 60) {
				//clears the modules in the model before adding 
				model.getAllSelectedModules().clear();
				//add the modules to the model
				smp.getSelectedTerm1().forEach(m -> model.addSelectedModule(m));
				smp.getSelectedTerm2().forEach(m -> model.addSelectedModule(m));
				//add unselected modules from the selection pane to the reserved modules pane
				rmp.getUnselectedTerm1().clear();
				rmp.getUnselectedTerm2().clear();
				smp.getUnselectedTerm1().forEach(m -> rmp.getUnselectedTerm1().add(m));
				smp.getUnselectedTerm2().forEach(m -> rmp.getUnselectedTerm2().add(m));

				alertDialogBuilder(AlertType.INFORMATION, "Module Selection", null, "Your Modules have been submitted");
				smp.disableAllButtons();
				view.changeTab(2);
			} else if(smp.getCredits1() < 60 && smp.getCredits2() == 60) {
				alertDialogBuilder(AlertType.WARNING, "Module Selection", null, "Please select 60 credits worth of Term 1 Modules");
			} else if(smp.getCredits1() == 60 && smp.getCredits2() < 60) {
				alertDialogBuilder(AlertType.WARNING, "Module Selection", null, "Please select 60 credits worth of Term 2 Modules");
			} else {
				alertDialogBuilder(AlertType.WARNING, "Module Selection", null, "Please select 60 credits worth of Term 1 and Term 2 Modules each");
			}
		}
	}
	
	private class ResetHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			smp.clearAllData();
			//re-populate the select modules pane with modules from the selected course
			for(Module m : model.getStudentCourse().getAllModulesOnCourse()) {
				if(m.getDelivery().equals(Schedule.TERM_1)) {
					if(m.isMandatory()) {
						smp.getSelectedTerm1().addAll(m);
						smp.addCredits1(m.getModuleCredits());
					} else {
						smp.getUnselectedTerm1().addAll(m);
					}
				} else if(m.getDelivery().equals(Schedule.TERM_2)) {
					if(m.isMandatory()) {
						smp.getSelectedTerm2().addAll(m);
						smp.addCredits2(m.getModuleCredits());
					} else {
						smp.getUnselectedTerm2().addAll(m);
					}
				} else if(m.getDelivery().equals(Schedule.YEAR_LONG)) {
					smp.getSelectedYearLong().addAll(m);
					//credits of the 'year long module' is divided by two for the first and second term
					smp.addCredits1(m.getModuleCredits() / 2); 
					smp.addCredits2(m.getModuleCredits() / 2);
				} 
			}
		}
	}
	
	private class AddReservedTerm1Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(rmp.getCredits1() < 30) {
				rmp.addFromUnselectedTerm1();
			}
		}
	}
	
	private class AddReservedTerm2Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(rmp.getCredits2() < 30) {
				rmp.addFromUnselectedTerm2();
			}
		}
	}
	
	private class RemoveReservedTerm1Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(rmp.getCredits1() > 0) {
				rmp.removeFromSelectedTerm1();
			}
		}
	}
	
	private class RemoveReservedTerm2Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(rmp.getCredits2() > 0) {
				rmp.removeFromSelectedTerm2();
			}
		}
	}
	
	private class ConfirmReserved1Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(rmp.getCredits1() == 30) {
				//clear any reserve modules from the student profile
				model.getAllReservedModules().clear();
				//adds the reserved term 1 modules
				rmp.getReservedTerm1().forEach(m -> model.addReservedModule(m));
				rmp.changePane();
				rmp.disableTab1();
				rmp.enableTab2();
			} else if(rmp.getCredits1() < 30) {
				alertDialogBuilder(AlertType.WARNING, "Reserve Module Selection", null, "Please select 30 credits worth of Term 1 Reserve Modules");
			}			
		}
	}
	
	private class ConfirmReserved2Handler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			if(rmp.getCredits2() == 30) {
				//adds the reserved term 2 modules
				rmp.getReservedTerm2().forEach(module -> model.addReservedModule(module));
				alertDialogBuilder(AlertType.INFORMATION, "Reserve Module Selection", null, "Your Reserve Modules have been submitted");
				rmp.enableTab1();
				rmp.disableAllButtons();
				
				//changes to the OverviewSelectionPane and outputs the respective overviews
				view.changeTab(3);
				osp.setProfileOverview(model.getStudentDetailsOverview());
				osp.setSelectedModulesOverview(model.getSelectedModulesOverview());
				osp.setReservedModulesOverview(model.getReservedModulesOverview());
			} else if(rmp.getCredits2() < 30) {
				alertDialogBuilder(AlertType.WARNING, "Reserve Module Selection", null, "Please select 30 credits worth of Term 2 Reserve Modules");	
			}
		}
	}
	
	private class SaveOverviewHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			PrintWriter out = null;
			if(osp.isOverviewEmpty() == false) {
				try {
					out = new PrintWriter (new File("StudentDetails.txt"));
					out.println("Student Details\n===============\n\n" + 
							model.getStudentDetailsOverview() + "\n\n" +
							model.getSelectedModulesOverview() + "\n\n" +
							model.getReservedModulesOverview());
					alertDialogBuilder(AlertType.INFORMATION, "Save Overview", null, "Successfully saved overview");
				} catch (FileNotFoundException e1) {
					alertDialogBuilder(AlertType.ERROR, "Save Overview", null, "Failed to save overview");
				}
				out.close();
			} else {
				alertDialogBuilder(AlertType.ERROR, "Save Overview", null, "Please complete all the module selections");
			}

		}
	}

	private class RestartHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//clear data on all panes
			osp.clearOverview();
			model.clearStudentProfile();
			model.clearSelectedModules();
			model.clearReservedModules();
			cspp.clearAllData();
			smp.clearAllData();
			rmp.clearAllData();
			//enable all buttons
			cspp.enableSubmitButton();
			smp.enableAllButtons();
			rmp.enableAllButtons();
			//initialise panes of the ReserveModulesPne
			rmp.enableTab1();
			rmp.disableTab2();
			//change to the first tab
			view.changeTab(0);
		}
	}
	
	private class SaveProfileHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//save the data model
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentProfileData.dat"));) {
				//write selected modules individually as cannot serialize the observable list in register
				for(Module m : model.getAllSelectedModules()) {
					oos.writeObject(m);
				}
				//write reserved modules
				for(Module m : model.getAllReservedModules()) {
					oos.writeObject(m);
				}
				
				oos.writeObject(null);
				oos.flush();
				//dialog appears if save is successful
				alertDialogBuilder(AlertType.INFORMATION, "Save Student Profile", "Save success", "Student Profile saved as \"StudentProfileData.dat\"");
			}
			catch (IOException ioExcep) {
				System.out.print(ioExcep.getCause());
				alertDialogBuilder(AlertType.ERROR, "Save Student Profile", "Save error", "There was an error whilst saving the data");
			}
		}
	}
	
	private class LoadProfileHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//if-statement used to ensure that the details are entered on the CreateStudentProfilePane
			if(model.areDetailsEmpty() == false) {
				//load in the data model
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentProfileData.dat"));) {
					//clear overview
					osp.clearOverview(); 
					//create a Module instance which will be used to add individual mdoules
					Module m = null;
					//read module objects individually
					while ((m = (Module) ois.readObject()) != null) {
						if (m.isReserved() == false) {
							model.addSelectedModule(m);
						} else {
							model.addReservedModule(m);
						}
					}	
					//clear the data from the other panes and disable their buttons
					smp.clearAllData();
					smp.disableAllButtons();
					rmp.enableTab1();
					rmp.disableAllButtons();
					view.changeTab(3);
					//outputs the data on the OverviewSelectionPane
					osp.setProfileOverview(model.getStudentDetailsOverview());
					osp.setSelectedModulesOverview(model.getSelectedModulesOverview());
					osp.setReservedModulesOverview(model.getReservedModulesOverview());
					//close the ObjectInputStream
					ois.close(); 
				}
				catch (IOException ioExcep){
					alertDialogBuilder(AlertType.ERROR, "Load Student Profile", "Load error", "There was an error whilst loading the data");
				}
				catch (ClassNotFoundException c) {
					alertDialogBuilder(AlertType.ERROR, "Load Student Profile", "Error", "Class not found");
				}
				//load successful dialog
				alertDialogBuilder(AlertType.INFORMATION, "Load Student Profile", "Load success", "Student Profile loaded from \"StudentProfileData.dat\"");
			} else {
				//dialog to prompt user to enter their details
				alertDialogBuilder(AlertType.WARNING, "Load Student Profile", "Insufficient data", "Please enter all the necessary details on the 'Create Profile' page");
			}
		}	
	}		
		
	private class AboutHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			alertDialogBuilder(AlertType.INFORMATION, "About", null, "Module Selection Tool 1.0");
		}
	}

	private class ExitHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			System.exit(0);
		}
	}
	
	
			/* -- methods -- */
	
	//helper method - generates course and module data and returns courses within an array
	private Course[] generateAndReturnCourses() {
		Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Schedule.TERM_1);
		Module ctec3451 = new Module("CTEC3451", "Development Project", 30, true, Schedule.YEAR_LONG);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigorous Systems", 15, true, Schedule.TERM_2);	
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigorous Systems", 15, false, Schedule.TERM_2);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Schedule.TERM_1);
		Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Schedule.TERM_1);	
		Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Schedule.TERM_2);	
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Schedule.TERM_2);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Schedule.TERM_2);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Schedule.TERM_2);
		Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Schedule.TERM_1);
		Module ctec3911 = new Module("CTEC3911", "Mobile Application Development", 15, false, Schedule.TERM_1);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Schedule.TERM_2);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false, Schedule.TERM_1);
		Module imat3611 = new Module("IMAT3611", "Computer Ethics and Privacy", 15, false, Schedule.TERM_1);
		Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Schedule.TERM_1);
		Module imat3614 = new Module("IMAT3614", "Big Data and Business Models", 15, false, Schedule.TERM_2);
		Module imat3428_CompSci = new Module("IMAT3428", "Information Technology Services Practice", 15, false, Schedule.TERM_2);


		Course compSci = new Course("Computer Science");
		compSci.addModuleToCourse(imat3423);
		compSci.addModuleToCourse(ctec3451);
		compSci.addModuleToCourse(ctec3902_CompSci);
		compSci.addModuleToCourse(ctec3110);
		compSci.addModuleToCourse(ctec3605);
		compSci.addModuleToCourse(ctec3606);
		compSci.addModuleToCourse(ctec3410);
		compSci.addModuleToCourse(ctec3904);
		compSci.addModuleToCourse(ctec3905);
		compSci.addModuleToCourse(ctec3906);
		compSci.addModuleToCourse(ctec3911);
		compSci.addModuleToCourse(imat3410);
		compSci.addModuleToCourse(imat3406);
		compSci.addModuleToCourse(imat3611);
		compSci.addModuleToCourse(imat3613);
		compSci.addModuleToCourse(imat3614);
		compSci.addModuleToCourse(imat3428_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModuleToCourse(imat3423);
		softEng.addModuleToCourse(ctec3451);
		softEng.addModuleToCourse(ctec3902_SoftEng);
		softEng.addModuleToCourse(ctec3110);
		softEng.addModuleToCourse(ctec3605);
		softEng.addModuleToCourse(ctec3606);
		softEng.addModuleToCourse(ctec3410);
		softEng.addModuleToCourse(ctec3904);
		softEng.addModuleToCourse(ctec3905);
		softEng.addModuleToCourse(ctec3906);
		softEng.addModuleToCourse(ctec3911);
		softEng.addModuleToCourse(imat3410);
		softEng.addModuleToCourse(imat3406);
		softEng.addModuleToCourse(imat3611);
		softEng.addModuleToCourse(imat3613);
		softEng.addModuleToCourse(imat3614);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;

		return courses;
	}
	
	//helper method to build dialogs - you may wish to use this during certain event handlers
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
