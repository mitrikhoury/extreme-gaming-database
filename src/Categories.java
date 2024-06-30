
public class Categories {

	private int ID;
	private String name;
	private boolean activeStates;

	public Categories(int ID, String name, boolean activeStates) {
		this.ID = ID;
		this.name = name;
		this.activeStates = activeStates;
	}
	
	public Categories(int ID, String name) {
		this.ID = ID;
		this.name = name;
		
	}

	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActiveStates() {
		return activeStates;
	}

	public void setActiveStates(boolean activeStates) {
		this.activeStates = activeStates;
	}

}
