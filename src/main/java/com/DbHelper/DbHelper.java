package com.DbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.DbConstants.DataBaseConstants;
//import com.DbQueries.DbQuery;
import com.DbQuery.*;
import com.ResponseConstants.ResponseConstants;

public class DbHelper {
	public static Connection instance = null;

	public static Connection getConnection() {
		if (instance != null)
			return instance;
		else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				instance = DriverManager.getConnection(DataBaseConstants.dbURL, DataBaseConstants.username,
						DataBaseConstants.password);
			} catch (Exception e) {
				throw new RuntimeException("Something went wrong");
			}

		}
		return instance;
	}

	public DbHelper() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			instance = DriverManager.getConnection(DataBaseConstants.dbURL, DataBaseConstants.username,
					DataBaseConstants.password);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Something went wrong");
		}

	}

	
	@SuppressWarnings("unused")
	public static void insertRecord() throws SQLException {
		int rows = 0;
		while (rows == 0) {
			try{
				ArrayList<String> list = new ArrayList<String>();
				list.add(ResponseConstants.insertName);
				list.add(ResponseConstants.insertDesignation);
				list.add(ResponseConstants.insertQualification);
				list.add(ResponseConstants.insertStatus);
				list.add(ResponseConstants.insertSalary);
				PreparedStatement preparedStatement = DbHelper.getConnection().prepareCall(DbQuery.insertQuery);
				int index = 0; 
				for (index = 0; index < 4; index++) {
					System.out.println(list.get(index));

					preparedStatement.setString(index + 1, DataBaseConstants.scanner.nextLine());
				}
				System.out.println(list.get(4));
				String salary = DataBaseConstants.scanner.nextLine();
				int check = 0;

				while (check == 0) {
					
					try {
				int checkSalary = Integer.parseInt(salary);
						check++;

					} catch (Exception e) {

						System.out.println("Something went wrong please enter salary again");
						salary = DataBaseConstants.scanner.nextLine();
						check = 0;
					}
				}

				preparedStatement.setString(5, salary);
				rows = preparedStatement.executeUpdate();
				if (rows > 0) {
					System.out.println("Record inserted sucessfully.");
				}
			} catch (Exception e) {
				System.out.println("Something went to wrong!\nPlease try again");
				rows = 0;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void selectRecord() throws SQLException {
try(PreparedStatement preparedStatement = DbHelper.getConnection().prepareCall(DbQuery.selectQuery);){
		System.out.println("Enter the Emp_code : ");
		preparedStatement.setInt(1, Integer.parseInt(DataBaseConstants.scanner.nextLine()));
		ResultSet result = preparedStatement.executeQuery();
		if (result.next()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ResponseConstants.emp_code, result.getInt(ResponseConstants.dbCode));
			jsonObject.put(ResponseConstants.name, result.getString(ResponseConstants.dbName));
			jsonObject.put(ResponseConstants.designation, result.getString(ResponseConstants.dbDesignation));
			jsonObject.put(ResponseConstants.qualification, result.getString(ResponseConstants.dbQualification));
			jsonObject.put(ResponseConstants.status, result.getString(ResponseConstants.dbStatus));
			jsonObject.put(ResponseConstants.salary, result.getInt(ResponseConstants.dbSalary));
			System.out.println(jsonObject);

		} else {
			System.out.println("No records found..");
		}
		}catch(Exception e) {
			System.out.println("Selection failed!!");
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static void updateRecords() throws SQLException {
		try(PreparedStatement preparedStatement = DbHelper.getConnection().prepareCall(DbQuery.selectQuery);) {
			System.out.println("Enter Emp_code : ");
			int code = Integer.parseInt(DataBaseConstants.scanner.nextLine());
			preparedStatement.setInt(1, code);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(ResponseConstants.emp_code, result.getInt(ResponseConstants.dbCode));
				jsonObject.put(ResponseConstants.name, result.getString(ResponseConstants.dbName));
				jsonObject.put(ResponseConstants.designation, result.getString(ResponseConstants.dbDesignation));
				jsonObject.put(ResponseConstants.qualification, result.getString(ResponseConstants.dbQualification));
				jsonObject.put(ResponseConstants.status, result.getString(ResponseConstants.dbStatus));
				jsonObject.put(ResponseConstants.salary, result.getInt(ResponseConstants.dbSalary));
				System.out.println(jsonObject);
			}
			System.out.println("Enter new value");
			String status = UserChoiceSelection();
			String sql = DbQuery.updateQuery1 + status + DbQuery.updateQuery2;
			PreparedStatement preparedStatement1 = DbHelper.getConnection().prepareCall(sql);
			System.out.println("Enter new " + status);
			preparedStatement1.setString(1, DataBaseConstants.scanner.nextLine());
			preparedStatement1.setInt(2, code);
			int rows = preparedStatement1.executeUpdate();
			if (rows > 0) {
				System.out.println("Record updated successfully.");
			}
		} catch (Exception e) {
			System.out.println("Something went to wrong.");
		}
	}

	static String UserChoiceSelection() {
		System.out.println("What do you want to update?");
		System.out.println("1.Name");
		System.out.println("2.Designation");
		System.out.println("3.Qualification");
		System.out.println("4.Status");
		System.out.println("5.Salary");
		String status = null;
		int choice = Integer.parseInt(DataBaseConstants.scanner.nextLine());
		while (choice > 5 || choice < 1) {
			System.out.println("Invalid choice.Please enter your choice");
			choice = Integer.parseInt(DataBaseConstants.scanner.nextLine());
		}
		switch (choice) {
		case 1:
			status = ResponseConstants.dbName;
			break;
		case 2:
			status = ResponseConstants.dbDesignation;
			break;
		case 3:
			status = ResponseConstants.dbQualification;
			break;
		case 4:
			status = ResponseConstants.dbStatus;
			break;
		case 5:
			status = ResponseConstants.dbSalary;
			break;
		}
		return status;
	}

	public static void deleteRecords() throws SQLException {
		int rows = 0;
		while (rows == 0) {
			try(PreparedStatement preparedStatement1 = DbHelper.getConnection().prepareCall(DbQuery.deleteQuery);) {
				System.out.println("Enter Emp_code : ");
				int code = Integer.parseInt(DataBaseConstants.scanner.nextLine());
				preparedStatement1.setInt(1, code);
				rows = preparedStatement1.executeUpdate();
				if (rows > 0) {
					System.out.println("Record deleted successfully...");
				} else {
					System.out.println("Something went to wrong!\nPlease try again");
					rows = 0;
				}

			} catch (Exception e) {
				System.out.println("Something went to wrong!\nPlease try again");
				rows = 0;
			}

		}

	}

	public static void fetchAllRecords() throws SQLException {
		try(PreparedStatement preparedStatement = DbHelper.getConnection().prepareStatement(DbQuery.fetchAllRecord);
		ResultSet resultSet = preparedStatement.executeQuery();){
		ResultSetMetaData metaData = resultSet.getMetaData();
		String colname1 = metaData.getColumnName(1);
		String colname2 = metaData.getColumnName(2);
		String header = String.format("%-10s%s", colname1, colname2);
		System.out.println(header);

		while (resultSet.next()) {

			String row = String.format("%-10s", resultSet.getString(1));
			System.out.print(row);
			System.out.println(resultSet.getString(2));
		}
		}
	}

	@SuppressWarnings("unchecked")
	public static void jsonformatRecord() throws SQLException {
		System.out.println("Classification based on status");
	try(	PreparedStatement preparedStatement = DbHelper.getConnection().prepareCall(DbQuery.StatusBasedFetchQuery1);){
		int count=1;
		JSONObject jsonObject = new JSONObject();
		while(count<=3) {
		String status = Classification(count);
		preparedStatement.setString(1, status);
		ResultSet result = preparedStatement.executeQuery();
		JSONObject jsonObjectNew = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		while (result.next()) {

			jsonObjectNew.put(ResponseConstants.dbName, result.getString(ResponseConstants.dbName));
			jsonObjectNew.put(ResponseConstants.dbDesignation, result.getString(ResponseConstants.dbDesignation));
			jsonObjectNew.put(ResponseConstants.dbQualification, result.getString(ResponseConstants.dbQualification));
			jsonObjectNew.put(ResponseConstants.dbSalary, result.getString(ResponseConstants.dbSalary));
			jsonArray.add(jsonObjectNew);

		}
		jsonObject.put(status, jsonArray);

		count++;
		}System.out.println(jsonObject);
	}
	}

	public static String Classification(int a) {
		String selected=null;
		int status = a;
		switch (status) {
		case 1:
			selected = ResponseConstants.status1;
			break;
		case 2:
			selected = ResponseConstants.status2;
			break;
		case 3:
			selected = ResponseConstants.status3;
			break;
		}
		return selected;

	}

	public static void exitHome() throws SQLException {
		System.out.println("Exit from Home Page..");
		DataBaseConstants.count = 0;
	}
}
