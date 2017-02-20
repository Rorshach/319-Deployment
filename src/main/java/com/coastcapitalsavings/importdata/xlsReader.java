package com.coastcapitalsavings.importdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row; 

public class xlsReader {
	public static void importData() throws IOException{
		String databaseURL = "jdbc:hsqldb:hsql://localhost";
		String user = "sa";
		String password = "";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(databaseURL, user, password);
			System.out.println("connected");
			PreparedStatement pstm2 = null ;
			FileInputStream input = new FileInputStream("datacc_npw.xls");
			POIFSFileSystem fs = new POIFSFileSystem( input );
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			for (int k = 0; k <= 0; k++) {
				HSSFSheet sheet = wb.getSheetAt(k);
				Row row;
				if (k == 0) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String cid = row.getCell(0).getStringCellValue();
						String name = row.getCell(1).getStringCellValue();
						String sql2 = "INSERT IGNORE INTO category (cid, name) VALUES('" + cid + "','" + name + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
				else if (k == 1) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String hobby = row.getCell(0).getStringCellValue();
						String interests = row.getCell(1).getStringCellValue();
						String sql2 = "INSERT IGNORE INTO details (hobby, interests) VALUES('" + hobby + "','" + interests + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
				else if (k == 2) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String name = row.getCell(0).getStringCellValue();
						String add = row.getCell(1).getStringCellValue();
						String  contact = row.getCell(2).getStringCellValue();
						String email = row.getCell(3).getStringCellValue();
						String sql2 = "INSERT IGNORE INTO employeee (name, address, contactNo, email) VALUES('" + name + "','" + add + "','" + contact + "','" + email + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
				else if (k == 3) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String name = row.getCell(0).getStringCellValue();
						String add = row.getCell(1).getStringCellValue();
						String  contact = row.getCell(2).getStringCellValue();
						String email = row.getCell(3).getStringCellValue();
						String sql2 = "INSERT IGNORE INTO employeee (name, address, contactNo, email) VALUES('" + name + "','" + add + "','" + contact + "','" + email + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
				else if (k == 4) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String name = row.getCell(0).getStringCellValue();
						String add = row.getCell(1).getStringCellValue();
						String  contact = row.getCell(2).getStringCellValue();
						String email = row.getCell(3).getStringCellValue();
						String sql2 = "INSERT IGNORE INTO employeee (name, address, contactNo, email) VALUES('" + name + "','" + add + "','" + contact + "','" + email + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
				else if (k == 5) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String name = row.getCell(0).getStringCellValue();
						String add = row.getCell(1).getStringCellValue();
						String  contact = row.getCell(2).getStringCellValue();
						String email = row.getCell(3).getStringCellValue();
						String sql2 = "INSERT IGNORE INTO employeee (name, address, contactNo, email) VALUES('" + name + "','" + add + "','" + contact + "','" + email + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
				else if (k == 6) {
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						row = (Row) sheet.getRow(i);
						String name = row.getCell(0).getStringCellValue();
						String add = row.getCell(1).getStringCellValue();
						String  contact = row.getCell(2).getStringCellValue();
						String email = row.getCell(3).getStringCellValue();
						String sql2 = "INSERT INTO IGNORE employeee (name, address, contactNo, email) VALUES('" + name + "','" + add + "','" + contact + "','" + email + "')";
						pstm2 = (PreparedStatement) conn.prepareStatement(sql2);
						pstm2.execute();
						System.out.println("Import rows " + i);
					}}
			}
			conn.commit();
			pstm2.close();
			conn.close();
			input.close();
			System.out.println("Successfully imported xls to hsqldb");
			if (conn != null) {
				System.out.println("Connected to the database");
			}
		} catch (SQLException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		importData();
	}
}