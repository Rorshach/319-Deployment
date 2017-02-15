import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class xlsReader {

	private FileInputStream fis;
	private Workbook workbook;
	private Sheet sheet;
	private ArrayList<data> dataToStore;

	public xlsReader(int sn) {
		dataToStore = new ArrayList<data>();
		try {
			fis = new FileInputStream(
					"datacc_npw.xls");
			int sheetNo = sn;
			workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheetAt(sheetNo);
			sheet.removeRow(sheet.getRow(sheetNo));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public ArrayList<data> retrieveData(String sheetName) {
		String EmployeeID, DepartmentCode, CostCentreCode, CategoryCode, CategoryDescription, CostCentreDescription, DepartmentDescription, FirstName, LastName , Email, ReportsToID, ProductDescription ,ProductCode, PurchaseID, DateCreated, CreatedBy, DateModified, ModifiedB, ApprovedBy;
		EmployeeID = DepartmentCode = CostCentreCode = CategoryCode = CategoryDescription = CostCentreDescription = DepartmentDescription = FirstName = LastName = Email = ReportsToID = ProductDescription = ProductCode = PurchaseID = DateCreated = CreatedBy = DateModified = ModifiedB = ApprovedBy = "";
		if(sheetName.equals("Approver")){
			for (Row row : sheet) {
				for (int i = 0; i < 3; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						EmployeeID = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						DepartmentCode = cell.getRichStringCellValue().getString();
					else if (i == 2 && cell != null)
						CostCentreCode = cell.getRichStringCellValue().getString();
				}

				if (row.getCell(0).getRichStringCellValue().toString().length() == 9) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, null);
					dataToStore.add(info);
				}}}
		else if(sheetName.equals("Category")){
			for (Row row : sheet) {
				for (int i = 0; i < 2; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						CategoryCode = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						CategoryDescription = cell.getRichStringCellValue().getString();
				}

				if (!row.getCell(0).getRichStringCellValue().toString().equals("CategoryCode")) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, CategoryCode);
					dataToStore.add(info);
				}}}

		else if(sheetName.equals("CostCentre")){
			for (Row row : sheet) {
				for (int i = 0; i < 2; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						CostCentreCode = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						CostCentreDescription = cell.getRichStringCellValue().getString();
				}

				if (row.getCell(0).getRichStringCellValue().toString().length() == 4) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, null);
					dataToStore.add(info);
				}}}

		else if(sheetName.equals("Department")){
			for (Row row : sheet) {
				for (int i = 0; i < 2; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						DepartmentCode = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						DepartmentDescription = cell.getRichStringCellValue().getString();
				}

				if (row.getCell(0).getRichStringCellValue().toString().length() == 9) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, null);
					dataToStore.add(info);
				}}}

		else if(sheetName.equals("Employee")){
			for (Row row : sheet) {
				for (int i = 0; i < 6; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						EmployeeID = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						FirstName = cell.getRichStringCellValue().getString();
					else if (i == 2 && cell != null)
						LastName = cell.getRichStringCellValue().getString();
					else if (i == 3 && cell != null)
						DepartmentCode = cell.getRichStringCellValue().getString();
					else if (i == 4 && cell != null)
						ReportsToID = cell.getRichStringCellValue().getString();
					else if (i == 5 && cell != null)
						DepartmentCode = cell.getRichStringCellValue().getString();
				}

				if (row.getCell(0).getRichStringCellValue().toString().length() == 9) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, null);
					dataToStore.add(info);
				}}}

		else if(sheetName.equals("Product")){
			for (Row row : sheet) {
				for (int i = 0; i < 3; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						ProductCode = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						ProductDescription = cell.getRichStringCellValue().getString();
					else if (i == 2 && cell != null)
						CategoryCode = cell.getRichStringCellValue().getString();
				}

				if (row.getCell(0).getRichStringCellValue().toString().length() == 9) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, null);
					dataToStore.add(info);
				}}}

		else if(sheetName.equals("Purchases")){
			for (Row row : sheet) {
				for (int i = 0; i < 7; i++) {
					Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
					if (i == 0 && cell != null)
						PurchaseID = cell.getRichStringCellValue().getString();
					else if (i == 1 && cell != null)
						EmployeeID = cell.getRichStringCellValue().getString();
					else if (i == 2 && cell != null)
						CostCentreCode = cell.getRichStringCellValue().getString();
					else if (i == 3 && cell != null)
						ProductCode = cell.getRichStringCellValue().getString();
					else if (i == 4 && cell != null)
						DateCreated = cell.getRichStringCellValue().getString();
					else if (i == 5 && cell != null)
						CreatedBy = cell.getRichStringCellValue().getString();
					else if (i == 6 && cell != null)
						ApprovedBy = cell.getRichStringCellValue().getString();
				}

				if (row.getCell(0).getRichStringCellValue().toString().length() == 9) {
					data info = new data(EmployeeID, DepartmentCode, CostCentreCode, null);
					dataToStore.add(info);
				}}}

		dataToStore.forEach(System.out::println);
		return dataToStore;
	}

	public static void main(String[] args) {
		xlsReader myxls = new xlsReader(2);
		myxls.retrieveData("CostCentre");
	}
}