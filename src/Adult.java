import java.util.ArrayList;

public class Adult extends Person {

	private Person spouse;
	private ArrayList<Dependent> childlist = new ArrayList<Dependent>();
	
	public Adult(String id, String name, String photo, String status, int age) {
		super(id, name, photo, status, age);
	}
	
// ---------- Getters and Setters

	public Person getSpouse() {
		return spouse;
	}

	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}

	public ArrayList<Dependent> getChildlist() {
		return childlist;
	}

	public void setChildlist(ArrayList<Dependent> childlist) {
		this.childlist = childlist;
	}

	
	
}
