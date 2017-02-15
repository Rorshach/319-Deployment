import java.io.Serializable;

public class data implements Serializable {

	private static final long serialVersionUID = 1L;

	private String DepartmentCode;

	private String EmployeeID;

	private String CostCentreCode;

	private String CategoryCode;

	public data() {
		this("", "", "", "");
	}

	public data(String EmployeeID, String DepartmentCode, String CostCentreCode, String CategoryCode) {
		this.EmployeeID = EmployeeID;
		this.DepartmentCode = DepartmentCode;
		this.CostCentreCode = CostCentreCode;
		this.CategoryCode = CategoryCode;
	}

	public void setDepartmentCode(String DepartmentCode) {
		this.DepartmentCode = DepartmentCode;
	}

	public String getDepartmentCode() {
		return DepartmentCode;
	}

	public void setCategoryCode(String CategoryCode) {
		this.CategoryCode = CategoryCode;
	}

	public String getCategoryCode() {
		return CategoryCode;
	}

	public void setEmployeeID(String EmployeeID) {
		this.EmployeeID = EmployeeID;
	}

	public String getEmployeeID() {
		return EmployeeID;
	}

	public void setCostCentreCode(String CostCentreCode){
		this.CostCentreCode = CostCentreCode;
	}

	public String getCostCentreCode() {
		return CostCentreCode;
	}

	@Override
	public String toString() {
		//return "data [EmployeeID=" + EmployeeID + ", DepartmentCode=" + DepartmentCode + ", CostCentreCode=" + CostCentreCode + "]";
		// check output; testing
		return "data["+CostCentreCode+"]";
	}

}