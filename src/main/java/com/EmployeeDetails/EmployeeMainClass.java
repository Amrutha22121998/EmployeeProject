package com.EmployeeDetails;

import com.DbConstants.DataBaseConstants;
import com.DbHelper.DbHelper;

public class EmployeeMainClass {

	
		public static void EmployeeDetails(){
				
			EmployeeOperation.selectOperation();
	}

}
class EmployeeOperation {
	public static void selectOperation() {
		int count=0;
		while(count==0) {
		try {
			while (DataBaseConstants.count ==1) {
				System.out.println("Enter choice");
				System.out.println("1.Insert record");
				System.out.println("2.Select record");
				System.out.println("3.Update record ");
				System.out.println("4.Delete record ");
				System.out.println("5.Fetch all record ");
				System.out.println("6.classification employee based on status");
				System.out.println("7.Exit ");
				int choice = Integer.parseInt(DataBaseConstants.scanner.nextLine());
				switch (choice) {
				case 1:
					 DbHelper.insertRecord();
					break;
				case 2:
					DbHelper.selectRecord();
					break;
				case 3:
					DbHelper.updateRecords();
					break;
				case 4:
					DbHelper.deleteRecords();
					break;
				case 5:
					DbHelper.fetchAllRecords();
					break;
				case 6:
					DbHelper.jsonformatRecord();
					break;
				case 7:
					DbHelper.exitHome();
					break;

				}count++;
			}
			}
		
		catch (Exception e) {
			System.out.println("Something went to wrong.\nPlease try again.");
			count=0;
		}

	}}

}
