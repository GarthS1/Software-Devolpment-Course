import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * The runnable class which handles the students requests and communicates with the student's client
 * @author Garth Slaney, Jiho Kim, Eddie Kim
 *
 */
public class RegistrationController implements Runnable{
	/**
	 * Input stream
	 */
	BufferedReader socketIn;
	/**
	 * Output stream
	 */
	PrintWriter socketOut;
	/**
	 * The course catalogue which is used 
	 */
	CourseCatalogue cat;
	/**
	 * The student currently signed in
	 */
	Student st;

	public RegistrationController(BufferedReader socketIn, PrintWriter socketOut, CourseCatalogue cat, Student st) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		this.cat = cat;
		this.st = st;
	}
	
	@Override
	public void run() {
		String action; 
		boolean exit = false;			//exit condition for the loop
		while(!exit) {
			try {
				action = socketIn.readLine();
				switch(action) {
				case "search catalogue courses":
					String course = socketIn.readLine();
					int courseId = Integer.parseInt(socketIn.readLine());
					
					Course courseSearched = cat.searchCat(course, courseId);
					socketOut.println(courseSearched);
					break;
				case "add course to student course":
				  Registration addedCourse = new Registration ();
					String course1 = socketIn.readLine();
					int courseId1 = Integer.parseInt(socketIn.readLine());
					Course courseSearched1 = cat.searchCat(course1, courseId1);
					int section = Integer.parseInt(socketIn.readLine()) - 1; 									//need to subtract one to get effective address 
				  addedCourse.completeRegistration(st, courseSearched1.getCourseOfferingAt(section));
					break;
				case "remove course from student course":
					int removeId = Integer.parseInt(socketIn.readLine()) - 1; 								// decrease by 1 to get effective address 
					st.removeRegistration(removeId);
					break;
				case "view All courses in catalog":
					socketOut.print(cat);
					break;
				case "view all courses taken by student":
					socketOut.print(st.printCourses());
					break;
				case "quit":
					exit = true;
					break;
				default:
					socketOut.print("Invalid input enetered. Please enter a different input.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
		
		


