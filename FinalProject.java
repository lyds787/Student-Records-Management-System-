package finalproject;
/* Final Project
 * Names: Raoul Ahrendts Nelson, Lydianne A. Rivera Cordero
 * Date: 11/26/2023 
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Comparator;
import java.io.*;
import java.time.LocalDate;


public class  FinalProject {
	// Global Scanner to allow for access outside of main
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// Creates a new ArrayList of people objects
		UniversityCommunityList people = new UniversityCommunityList();
		String fullName, universityID;
		int choice = 0;
		// Display welcome message
		System.out.println("Welcome to my Personal Management Program\n");
		System.out.println("Choose one of the options:\n");
		// Display menu options
		while (true) {
			boolean validInput;
			do {
				try {
				System.out.println("1- Enter the information of a faculty");
				System.out.println("2- Enter the information of a student");
				System.out.println("3- Print tuition invoice for a student");
				System.out.println("4- Print faculty information");
				System.out.println("5- Enter the information of a staff member");
				System.out.println("6- Print the information of a staff member");
				System.out.println("7- Delete a person");
				System.out.println("8- Exit Program");
				System.out.print("\n\tEnter your selection: ");
				// Read user's choice
				// Scans for choice
				choice = scanner.nextInt();
				scanner.nextLine();
				
				System.out.println("\n\n");
				
				if(!(choice >= 1 && choice <= 8)) {
					System.out.println("Invalid entry- please try again\n\n");
					validInput = false;
				}
				else validInput = true;
				//Catches an InputMismatchException, indicating an invalid entry
				} catch (InputMismatchException e) {
					// Marks the input as invalid
					System.out.println("Invalid entry- please try again\n\n");
					scanner.nextLine();
					validInput = false;
				}
			} while(!validInput);


			//Switch based on user's choice
			switch (choice) {
			case 1:
				// Code for entering faculty information
				System.out.println("Enter the faculty info:");
				System.out.print("\tName of the faculty member: ");
				String facultyFullName = scanner.nextLine();
				
				System.out.print("\t");
				// Gets a valid ID in the correct format from user
				universityID = getValidUniversityID();

				String facultyRank = getValidRank();
				String facultyDepartment = getValidDepartment();
				

				Faculty faculty = new Faculty(facultyFullName, universityID, facultyDepartment, facultyRank);

				// Creates and adds the person to the list while checking for duplicates
				if(people.addPerson(faculty)) {
					System.out.println("\nFaculty member added!\n\n");
				}
				else System.out.println("ID already taken!\n\n");
				break;

			case 2:
				// Code for entering student information
				System.out.println("Enter the student's info:");
				System.out.print("\tName of Student: ");
				String studentName = scanner.nextLine();

				// Gets a valid ID in the correct format from the user
				System.out.print("\t");

				universityID = getValidUniversityID();
				//Gets GPA
				System.out.print("\tGpa: ");
				double gpa = scanner.nextDouble();
				scanner.nextLine();
				//Gets credit hours taken
				System.out.print("\tCredit hours: ");
				int creditHours = scanner.nextInt();
				scanner.nextLine();
				// Create a new Student object with entered information
				Student student = new Student(studentName, universityID, gpa, creditHours);

				// Adds the person to the list while checking for duplicates
				if(people.addPerson(student)) {
					System.out.println("\nStudent added!\n\n");
				}
				else System.out.println("ID already taken!");
				break;

			case 3:
				// Code for printing tuition invoice for a student
				//Gets students ID
				System.out.print("Enter the Student's ");
				universityID = getValidUniversityID();

				// Only returns false if there was no person found to print
				if(!people.printInfo(universityID)) {
					System.out.println("No Student matched!\n\n");
				}
				break;
			case 4:
				// Code for printing faculty information
				// Gets the Faculty univ ID from user
				System.out.print("Enter the Faculty's ");
				universityID = getValidUniversityID();

				// Only returns false if there was no person found to print
				if(!people.printInfo(universityID)) {
					System.out.println("No Faculty member matched!\n\n");
				}
				break;
			case 5: 
				//Code for entering staff member information
				System.out.println("Enter the staff member info:");
				System.out.print("\tName of staff Member: ");

				fullName = scanner.nextLine();

				System.out.print("\t");
				// Gets a valid ID in the correct format from user
				universityID = getValidUniversityID();

				// Gets department
				String department = getValidDepartment();

				// Gets the status
				String status = getValidStatus();				

				// Changes status to Part for P, and Full for F
				if (status.equals("P")) status = "Part";
				else status = "Full";

				// Creates and adds the person to the list while checking for duplicates
				if(people.addPerson(new Staff(fullName, universityID, department, status))) {
					System.out.println("Staff member added!\n\n");
				}
				else System.out.println("ID already taken!");
				break;

			case 6:
				// Code for printing staff member information
				// Gets the staffs ID from user
				System.out.print("Enter the Staff's ");
				universityID = getValidUniversityID();

				// Only returns false if there was no person found to print
				if(!people.printInfo(universityID)) {
					System.out.println("No Staff member matched!\n\n");
				}
				break;
			case 7:
				// Code for deleting a person
				System.out.print("Enter the ID of the person to delete: ");
				String deleteId = scanner.nextLine();

				//Calls the method to delete the ID
				people.deletePerson(deleteId);

				break;
			case 8:
				// Code for exiting program and creating report
				// Gets a valid choice of yes or no (Y or N)
				String createReportChoice = getValidYesOrNo();
				// Check if the user wants to create a report
				if (createReportChoice.equalsIgnoreCase("Y")) {
					do {
						try {
							// Prompt user for sorting preference
							System.out.print("Would like to sort your students by descending gpa or name (1 for gpa, 2 for name): ");

							// Scans for an int and consumes newLine
							choice = scanner.nextInt();
							scanner.nextLine();

						if(!(choice == 1 || choice == 2)) {
							System.out.println("Invalid entry- please try again\n\n");
							validInput = false;
						}
						else validInput = true;
						// Catches an InputMismatchException, indicating an invalid entry
						} catch (InputMismatchException e) {
							// Marks the input as invalid
							System.out.println("Invalid entry- please try again\n\n");
							scanner.nextLine();
							validInput = false;
						}
					} while(!validInput);
					
					try {
						switch (choice) {
						case 1:
							// If it created the report successfully
							if(people.generateReport(new GpaComparator())) {
								System.out.println("\nReport created and saved on your hard drive!");
							}
							// Otherwise, it will print that it didn't work within the method
							break;
						case 2:
							if(people.generateReport(new NameComparator())) {
								System.out.println("\nReport created and saved on your hard drive!");
							}
							// Otherwise, it will print that it didn't work within the method
							break;
						}
					} catch(Exception e) {
						// Print if an error occurs while writing to the report file
						System.out.println("An error occurred while writing to the report file!");
						System.out.println("Error: " + e.getMessage());
					}
				}

				// Display goodbye message
				System.out.println("Goodbye!");
				// Closes the scanner
				scanner.close();
				// Ends the program
				return;
			}      
		}
	}

	public static boolean isValidIDFormat(String universityID) {
		// The .match method itself returns true or false as to whether it matched or not
		return universityID.matches("[a-zA-Z]{2}\\d{4}");
	}
	public static String getValidUniversityID() {
		String universityID;
		// A loop to make sure the ID is the right format
		do{
			System.out.print("ID: ");
			universityID = scanner.nextLine();
			// If the format isn't valid, print a reminder message
			if(!isValidIDFormat(universityID)) {
				System.out.print("\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit" + "\n\n\t");
			}
		}while(!isValidIDFormat(universityID));
		// Returns the valid UniversityID
		return universityID;
	}
/*
 * Gets a valid department from the user.
 * @return The valid department entered by the user.
 */
	public static String getValidDepartment() {
		String facultyDepartment = "";
		boolean isValidDepartment = false;
		 // Keep prompting until a valid department is entered
		while (!isValidDepartment) {
			System.out.print("\tDepartment: ");
			facultyDepartment = scanner.nextLine(); 

			// Convert the first letter to uppercase and the rest to lowercase
			facultyDepartment = facultyDepartment.substring(0, 1).toUpperCase() +
              		      facultyDepartment.substring(1).toLowerCase();
			
			// Check if the entered department is valid
			if (facultyDepartment.equalsIgnoreCase("Mathematics") ||
					facultyDepartment.equalsIgnoreCase("Engineering") ||
					facultyDepartment.equalsIgnoreCase("English")) 
			{
				isValidDepartment = true;
			} else {
				System.out.println("\t\t\"" + facultyDepartment + "\"" +" is invalid. Please enter a valid department.");
			}
		}
		return facultyDepartment;
	}
	/*
	 * Gets a valid rank from the user.
	 * @return The valid rank entered by the user.
	 */
	public static String getValidRank() {
		String rank = "";
		boolean isValidRank = false;
		// Keep prompting until a valid rank is entered
		while (!isValidRank) {
			System.out.print("\tRank: ");
			rank = scanner.nextLine(); 

			// Convert the first letter to uppercase and the rest to lowercase
			rank = rank .substring(0, 1).toUpperCase() +
					rank .substring(1).toLowerCase();
			
			// Check if the entered rank is valid
			if (rank.equalsIgnoreCase("Professor") || rank.equalsIgnoreCase("Adjunct")) {
				isValidRank = true;
			} else {
				System.out.println("\t\t\"" + rank + "\"" + " is invalid");
			}
		}
		return rank;
	}

	/*
	 * Gets a valid status from the user.
	 * @return The valid status entered by the user.
	 */
	public static String getValidStatus() {
		String status;

		// Makes sure that the status is a valid one
		do{

			// Gets the status from user
			System.out.print("\tStatus, Enter P for Part Time, or Enter F for Full Time: ");
			status = scanner.nextLine().toUpperCase();

			// If the status doesn't equal P or F
			if (!(status.equals("P") || status.equals("F"))) {
				System.out.println("Invalid input. Please enter 'P' for Part Time or 'F' for Full Time.\n");
			}

		} while(!(status.equals("P") || status.equals("F")));
		// Returns the valid status
		return status;
	}
	/*
	 * Gets a valid report choice from the user.
	 * @return The valid choice entered by the user.
	 */
	public static String getValidYesOrNo() {
		String choice;

		// Makes sure that the choice is a valid one
		do{
			// Prompt user to create a report
			System.out.print("Would you like to create the report? (Y/N): ");
			choice = scanner.nextLine().toUpperCase();

			// If the status doesn't equal Y or N
			if (!(choice.equals("Y") || choice.equals("N"))) {
				System.out.println("Invalid input. Please enter 'Y' for yes or 'N' for no\n");
			}

		} while(!(choice.equals("Y") || choice.equals("N")));
		// Returns the valid choice
		return choice;
	}
	// Overrides the compare method to be able to compare two person's gpas
	private static class GpaComparator implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			// Makes sure these 2 people are students
			if (p1 instanceof Student && p2 instanceof Student) {
				// Compares the 2 gpas
				// Returns -1 if 1st < 2nd, and 1 if 1st > 2nd
				return Double.compare(((Student)p2).getGpa(), ((Student)p1).getGpa());
			}
			return 0;
		}
	}

	/*
	 * Comparator class to compare two persons' GPAs.
	 */

	//Overrides the compare method to be able to compare two person's names
	private static class NameComparator implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			// Makes sure these 2 people are students
			if (p1 instanceof Student && p2 instanceof Student) {
				// Compares the 2 gpas
				// Returns negative # if 1st < 2nd, and positive # if 1st > 2nd
				return p2.getFullName().compareToIgnoreCase(p1.getFullName());
			}
			return 0;
		}
	}
}


class UniversityCommunityList {
	private ArrayList<Person> list;

	// Getters and Setters
	public ArrayList<Person> getList() {
		return list;
	}

	public void setList(ArrayList<Person> list) {
		this.list = list;
	}

	// Constructors

	public UniversityCommunityList() {
		this.list = new ArrayList<Person>();
	}

	// Methods

	// A helper Lookup function 
	// Returns the index of the Person that matches the universityID
	private int lookUpPerson(String universiyID) {
		int personIndex = -1;
		// Iterate through the array
		for(int i = 0; i < list.size(); i++) {
			// At each element, check if the ID matches
			if(list.get(i).getUniversityID().equals(universiyID)) {
				// Save this person's index (location) in the list
				personIndex = i;
			}
		}
		// If the an ID match was not found (personIndex never changed)
		if(personIndex == -1) {
			return personIndex;
		}
		// Returns the actual index of the found person
		return personIndex;
	}


	// A helper duplicate checker
	// Returns a boolean that represents whether the ID entered is a duplicate or not
	public boolean duplicateChecker(String universityID) {
		// Iterates through length of list
		for(int i = 0; i < list.size(); i++) {
			// Checks for any matches
			if(list.get(i).getUniversityID().equals(universityID)) {
				// Returns true as the ID already exists in the list
				return true;
			}
		}
		// Otherwise, no existing ID was found
		return false;	
	}



	// Prints the information of the respective person in the list
	public boolean printInfo(String UniversityID) {
		// Finds the person in the list
		int personIndex = lookUpPerson(UniversityID);
		// Checks if this is a valid personIndex (there were no duplicates and they were found) 
		if(personIndex != -1) {
			list.get(personIndex).printInfo();
			return true;
		}
		// Otherwise, the person wasn't actually found
		return false;
	}

	// Adds a person
	public boolean addPerson(Person person) {
		// Checks if the ID of this person is already taken
		// If no duplicate is found, go ahead and add them to the list
		if(!duplicateChecker(person.getUniversityID())) {
			list.add(person);
			return true;
		}
		return false;
	}

	// Deletes a person from the University Roster
	public void deletePerson(String universityID) {
		// Looks up for the person in the list by ID, and if they exist (no flag of -1), removes them
		int personIndex = lookUpPerson(universityID);
		if(personIndex != -1) {
			// Gets the name of which instance of person (Student, faculty or staff) and removes them
			String personToDelete = list.get(personIndex).getClass().getName();
			list.remove(personIndex);
			System.out.println("The " + personToDelete + " has been removed from the list");
		}
		else {
		// If the person was not found, either return false or print the not found statement
		// return false;
		System.out.println("Sorry, no such person exists.\n\n");
		}
	}

	// Creates a report with everyone's info
	public boolean generateReport(Comparator<Person> comparator) throws Exception {	
		// Creates a new report.txt
		PrintWriter writer = new PrintWriter("report.txt");

		try {
			// Writes the current date
			writer.println("\t\tReport created on " + LocalDate.now());
			writer.println("\t\t***********************\n\n");

			// Print faculty members
			writeFacultyInfo(writer);

			// Print staff members
			writeStaffInfo(writer);

			// Print students
			writeStudentInfo(writer, comparator);
			
			// It was generated successfully
			return true;
			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		} finally {
			writer.close();
		}

	}
	// Prints all info of each faculty member
	private void writeFacultyInfo(PrintWriter writer) {
		int currentFacultyNum = 0;
		// Prints out each faculty member in the list
		writer.println("Faculty Members");
		writer.println("-------------------------");
		for(int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Faculty) {
				currentFacultyNum++;
				writer.print("\t" + (currentFacultyNum) + ". ");
				writer.println(list.get(i).getReportInfo());
			}
		}
	}
	// Prints all info of each staff member
	private void writeStaffInfo(PrintWriter writer) {
		int currentStaffNum = 0;
		// Prints out each Staff member in the list
		writer.println("Staff Members");
		writer.println("-------------------");
		for(int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Staff) {
				currentStaffNum++;
				writer.print("\t" + (currentStaffNum) + ". ");
				writer.println(list.get(i).getReportInfo());
			}
		}
	}
	// Prints all info of each student sorted in descending order (either gpa or name)
	private void writeStudentInfo(PrintWriter writer, Comparator<Person> comparator) {
		// Temp arraylist to hold the students in order to sort themtry
		ArrayList<Person> students = new ArrayList<>();

		// Prints out each Student member in the list
		writer.println("Students");
		writer.println("-----------");
		// Adds each student to the temp list
		for(Person person : list) {
			if (person instanceof Student) {
				students.add(person);
			}
		}
		// Sorts the students by the specified comparator (either gpa or names)
		Collections.sort(students, comparator);
		// Writes each students info to the .txt
		for(int i = 0; i < students.size(); i++) {
			writer.print("\t" + (i + 1) + ". ");
			writer.println(students.get(i).getReportInfo());
		}
	}
}


//---------------------------
abstract class Person {
	private String fullName;
	private String universityID;

	// Getters and Setters
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUniversityID() {
		return universityID;
	}
	public void setUniversityID(String universityID) {
		this.universityID = universityID;
	}


	// Constructors

	// Default Constructor
	public Person() {
		this.fullName = "";
		this.universityID = "";		
	}
	// If the user has the name and ID
	public Person(String fullName, String universityID) {
		this.fullName = fullName;
		this.universityID = universityID;
	}

	// If user only has name
	public Person(String fullName) {
		this.fullName = fullName;
		this.universityID = "";
	}

	// Methods
	// This method will be used to print the person's relevant info
	public abstract void printInfo();

	// This method will be used to printInfo for the report
	public abstract String getReportInfo();

	// Returns the starting info / header of a person at the University
	@Override
	public String toString() {
		return "---------------------------------------------------------------------------" +
				"\n" + fullName + "\t\t" + universityID + "\n";
	}
}

//---------------------------
class Student extends Person {
	private double gpa;
	private int currentCreditHours;
	// Constructors
	public Student(String fullName, String id, double gpa) {
		super(fullName, id);
		this.gpa = gpa;
	}

	public Student(String fullName, String id, int currentCreditHours) 
	{
		super(fullName, id);
		this.currentCreditHours = currentCreditHours;
	}
	public Student(String fullName, String id, double gpa, int currentCreditHours) 
	{
		super(fullName, id);
		this.gpa = gpa;
		this.currentCreditHours = currentCreditHours;
	}
	//getters and setters
	public double getGpa() 
	{
		return gpa;
	}

	public void setGpa(double gpa) 
	{
		this.gpa = gpa;
	}

	public int getcurrentCreditHours() 
	{
		return currentCreditHours;
	}

	public void setcurrentCreditHours(int currentCreditHours) 
	{
		this.currentCreditHours = currentCreditHours;
	}

	@Override
	public void printInfo() 
 // Prints information about the student, including tuition, fees, and total payment.
	{
		double tuition = currentCreditHours * 236.45;
		double discount = (gpa >= 3.85) ? 0.25 * tuition : 0;
		double totalPayment = tuition + 52 - discount;

		System.out.println("---------------------------------------------------------------------------");
		System.out.println(getFullName() + "\t\t" + getUniversityID());
		System.out.println("Credit Hours: " + currentCreditHours + " ($236.45/credit hour)");
		System.out.println("Fees: $52");
		System.out.printf("Total payment (after discount): $%.2f \t ($%.2f discount applied)\n", totalPayment, discount);
		System.out.println("---------------------------------------------------------------------------\n\n");	
	}
	@Override
	public String getReportInfo() {
		//Provides report information about the student, including name, ID, GPA, and credit hours.
		return getFullName() + "\n\t" + "ID: " + getUniversityID() + "\n\t" + "Gpa: " + gpa + "\n\t" + "Credit hours: " + currentCreditHours + "\n";
	}
}

//---------------------------
abstract class Employee extends Person {
	private String department;

	// Getters and Setters
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	// Constructors

	// Default Constructor
	public Employee() {
		// Calls the constructor of the parent class
		super();
		this.department = "";		
	}
	// If the user has the name, ID, and department
	public Employee(String fullName, String universityID, String department) {
		// Calls the constructor of the parent class
		super(fullName, universityID);
		this.department = department;
	}
	// If the user has the name and department
	public Employee(String fullName, String department) {
		// Calls the constructor of the parent class
		super(fullName);
		this.department = department;
	}

	// Methods
	@Override
	public void printInfo() {
		System.out.println(this);
	}

	// Returns the toString of the parent class Person plus the department info
	@Override
	public String toString() {
		return super.toString() + department + " Department, ";
	}

}

//-------------------------------
class Faculty extends Employee {
	private String rank;

	// Getters and Setters
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}

	// Constructors

	// Default Constructor
	public Faculty() {
		super();
		this.rank = "";
	}

	public Faculty(String fullName, String universityID, String department, String rank) {
		super(fullName, universityID, department);
		this.rank = rank;
	}

	public Faculty(String fullName, String department, String rank) {
		super(fullName, department);
		this.rank = rank;
	}

	@Override
	public void printInfo() {
		System.out.println(this);
	}

	@Override
	public String getReportInfo() {
		return getFullName() + "\n\t" + "ID: " + getUniversityID() + "\n\t" + rank + "," + getDepartment() + "\n";
	}
	// Returns the info of an employee that is a person along plus the rank of this faculty member
	@Override
	public String toString() {
		return super.toString() + rank + "\n" +
				"---------------------------------------------------------------------------" + "\n\n";
	}	
}
//----------------------------------
class Staff extends Employee {
	private String status;
	// Constructors
	public Staff(String fullName, String id, String department, String status) {
		super(fullName, id, department);
		this.status = status;
	}
	//Getters and Setters
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void printInfo() {
		System.out.println(this);
	}
	@Override
	public String getReportInfo() {
		//Provides Report
		return getFullName() + "\n\t" + "ID: " + getUniversityID() + "\n\t" + getDepartment() + "," + status + " Time" + "\n";
	}
	// Returns the info of an employee that is a person plus the status of this staff member
	@Override
	public String toString() {	
		return super.toString() + status + " Time" + "\n" +
				"---------------------------------------------------------------------------" + "\n\n";
	} 
}