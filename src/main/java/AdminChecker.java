package src.main.java;

public class AdminChecker {
	
	public Boolean checkAdmin(Long id) {
		String[] listOfAdmins = System.getenv("ADMINS").split(":");
		for (int i = 0; i < listOfAdmins.length; i++) {
			if (listOfAdmins[i].equals(id.toString())) {
				return true;				
			}
		}
		return false;
	}
}