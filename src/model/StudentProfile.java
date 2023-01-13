package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


public class StudentProfile implements Iterable<Module>, Serializable {

	private String studentPnumber;
	private Name studentName;
	private String studentEmail;
	private LocalDate studentDate;
	private Course studentCourse;
	private Set<Module> selectedModules;
	private Set<Module> reservedModules;
	
	public StudentProfile() {
		studentPnumber = "";
		studentName = new Name();
		studentEmail = "";
		studentDate = null;
		studentCourse = null;
		selectedModules = new TreeSet<Module>();
		reservedModules = new TreeSet<Module>();
	}
	
	public String getStudentPnumber() {
		return studentPnumber;
	}
	
	public void setStudentPnumber(String studentPnumber) {
		this.studentPnumber = studentPnumber;
	}
	
	public Name getStudentName() {
		return studentName;
	}
	
	public void setStudentName(Name studentName) {
		this.studentName = studentName;
	}
	
	public String getStudentEmail() {
		return studentEmail;
	}
	
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	
	public LocalDate getSubmissionDate() {
		return studentDate;
	}
	
	public void setSubmissionDate(LocalDate studentDate) {
		this.studentDate = studentDate;
	}
	
	public String getSubmissionDateString() {
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
		String formattedDate = studentDate.format(formatters);
		return formattedDate;
	}
	
	public Course getStudentCourse() {
		return studentCourse;
	}
	
	public void setStudentCourse(Course studentCourse) {
		this.studentCourse = studentCourse;
	}
	
	public boolean addSelectedModule(Module m) {
		return selectedModules.add(m);
	}
	
	public Set<Module> getAllSelectedModules() {
		return selectedModules;
	}
	
	public boolean addReservedModule(Module m) {
		m.setReserved(true);
		return reservedModules.add(m);
	}
	
	public Set<Module> getAllReservedModules() {
		return reservedModules;
	}
	
	public void clearStudentProfile() {
		studentPnumber = "";
		studentName = new Name();
		studentEmail = "";
		studentDate = null;
		studentCourse = null;
	}
	
	public void clearSelectedModules() {
		selectedModules.clear();
	}
	
	public void clearReservedModules() {
		reservedModules.clear();
	}
	
	public boolean areDetailsEmpty() {
		if (studentPnumber.equals("") || studentName.getFullName().equals("")  || studentEmail.equals("") || studentDate == null || studentCourse.getAllModulesOnCourse() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getStudentDetailsOverview() {
		String profile = "Name: " + getStudentName().getFirstName() + " " + getStudentName().getFamilyName() + 
						 "\nPNo: " + getStudentPnumber() +
						 "\nEmail: " + getStudentEmail() +
						 "\nDate: " + getSubmissionDateString() +
						 "\nCourse: " + getStudentCourse();
		return profile;
	}
	
	public String getSelectedModulesOverview() {
		String selectedModules = "Selected modules\n===============";
		for(Module m : this.getAllSelectedModules()) {
			selectedModules += "\n\nModule code: " + m.getModuleCode() + 
								"\nModule name: " + m.getModuleName() +
								"\nModule credits: " + m.getModuleCredits() + 
								"\nMandatory on your course? " + m.isMandatoryString() +
								"\nDelivery: " + m.getDeliveryString(); 
 		}
		return selectedModules;
	}
	
	public String getReservedModulesOverview() {
		String reservedModules = "Reserved modules\n===============";
		for(Module m : this.getAllReservedModules()){
			reservedModules += "\n\nModule code: " + m.getModuleCode() + 
								"\nModule name: " + m.getModuleName() +
								"\nModule credits: " + m.getModuleCredits() + 
								"\nDelivery: " + m.getDeliveryString(); 
 		}
		return reservedModules;
	}
	
	@Override
	public String toString() {
		return "StudentProfile:[Pnumber=" + studentPnumber + ", studentName="
				+ studentName + ", studentEmail=" + studentEmail + ", studentDate="
				+ studentDate + ", studentCourse=" + studentCourse.actualToString() 
				+ ", selectedModules=" + selectedModules
				+ ", reservedModules=" + reservedModules + "]";
	}

	@Override
	public Iterator<Module> iterator() {
		return selectedModules.iterator();
	}
	
}
