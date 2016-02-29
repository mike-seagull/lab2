package clients;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
//import org.json.*;

public class Main {
	
	String servletUrl = "";
	Request req;
	
	public Main(String url) {
		servletUrl = url;
		req = new Request(servletUrl);
	}
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	/* 
	 * "REQUEST" METHODS
	 */
	private String postToDo(String id, String message) throws IOException {
		/*
		 * Stores the string ‘todo message’ in the servlet with the supplied integer ‘id’ and the client’s timestamp.
		 */
		return req.post(id, message);
	}
	private String getToDo(String id) throws IOException {
		/*
		 * Retrieves and displays the todo message to the console and when it was posted.
		 */
		return req.get(id);
		
	}
	private HashMap<String, String> getAllToDos() {
		/*
		 * Retrieves a List of all todo messages as a map of <id,todo message> pairs 
		 * and prints it to the console.
		 */
		
		/*
		 *  NEED TO DO SOMETHING HERE
		 */
		
		
		
		HashMap<String,String> result = new HashMap<String,String>();
		return result;
	}
	private String deleteToDo(String id) throws MalformedURLException, IOException {
		/*
		 * Deletes the todo message at the given id from the servlet.
		 */
		return req.delete(id);

	}
	private void putToDo(String id, String message) throws IOException{
		/*
		 * Stores the string todo message in the servlet with the 
		 * supplied integer ‘id’ and the clients timestamp. Overwrite any existing value. 
		 */
		req.put(id, message);
	}
	@SuppressWarnings("resource")
	public static void main(String[] args) throws SQLException, MalformedURLException, IOException {
		Main m = new Main("http://localhost/todo");
		Scanner sc = new Scanner(System.in);
		String input = "";
		
		 // Main loop
		
		while (true) {
			System.out.println("\nEnter a command...");
			input = sc.nextLine();
			String[] cmd = input.split("\\s+");
			String method = cmd[0].toUpperCase();
			if (method.equals("POST") && cmd.length >= 3 && isInteger(cmd[1])) {
				String message = "";
				for (int i=2;i<cmd.length;i++) {
					message += cmd[i];
					if (i != cmd.length-1) {
						message += " ";
					}
				}
				m.postToDo(cmd[1], message);
			}else if (method.equals("GET") && cmd.length == 1) {
				m.getAllToDos();
			}else if (method.equals("GET") && cmd.length == 2 && isInteger(cmd[1])) {
				String message = m.getToDo(cmd[1]);
				if (message == null || message.equals("")) {
					System.out.println("NO MESSAGE TO DISPLAY");
				}else {
					System.out.println("MESSAGE: '"+message+"'");
				}
			}else if (method.equals("DELETE") && cmd.length == 2 && isInteger(cmd[1])) {
				m.deleteToDo(cmd[1]);
			}else if (method.equals("PUT") && cmd.length >= 3 && isInteger(cmd[1])) {
					String message = "";
					for (int i=2;i<cmd.length;i++) {
						message += cmd[i];
						if (i != cmd.length-1) {
							message += " ";
						}
					}
					m.putToDo(cmd[1], message);
			}else {
				System.out.println("Error: Invalid Command");
			}
		}
	}
}
