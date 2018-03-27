import java.util.ArrayList;
import java.util.HashMap;

public abstract class Person {

	private String id;								// Id of the Person (4)
	private String name;								// Name of the Person (35)
	private String photo;							// Person Photo - Filename (40) 
	private String status;							// e.g. “working at KFC”, “student at RMIT”, “Freelance”, “looking for jobs” (50)
	private int age;									// 27 (2)
	
	static HashMap<String, Person> _mapFriendList;
	private ArrayList<Person> friendslist = new ArrayList<Person>(); // List of Friends
	
	public Person(String id, String name, String photo, String status, int age) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.status = status;
		this.age = age;
	}

	public void test() {
		System.out.println("Blah");
	}
	
	
// ---------- Getters and Setters
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public ArrayList<Person> getFriendslist() {
		return friendslist;
	}

	public void setFriendslist(ArrayList<Person> friendslist) {
		this.friendslist = friendslist;
	}

	
	
	
}
