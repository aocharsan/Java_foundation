package utilsClasses;

public class HighSalariedEmployees {
	
	    private int id;
	    private String name;
	    private String department;
	    
		public HighSalariedEmployees(int id, String name, String department) {
			super();
			this.id = id;
			this.name = name;
			this.department = department;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getDepartment() {
			return department;
		}

		@Override
		public String toString() {
			return "HighSalariedEmployees [id=" + id + ", name=" + name + ", department=" + department + "]";
		}
	    
		
	    
}
