
public class Dependent extends Person {

	private Person father;
	private Person mother;
	
	public Dependent(String id, String name, String photo, String status, int age) {
		super(id, name, photo, status, age);
	}
	
// ---------- Getters and Setters	

	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		this.father = father;
	}

	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		this.mother = mother;
	}

}
