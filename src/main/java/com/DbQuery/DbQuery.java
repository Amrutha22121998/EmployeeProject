package com.DbQuery;

import java.util.Scanner;

public class DbQuery {
	static Scanner sc = new Scanner(System.in);
	public static String insertQuery = "insert into Employee(Name,Designation, Qualification,Status, Salary) values(?,?,?,?,?)";
	public static String selectQuery = "select * from Employee  where Emp_code=?";
	public static String updateQuery1 = "update Employee set ";
	public static String updateQuery2=" = ? where Emp_code =?";
	public static String updateQueryDesignation = "update Employee set ? = ? where Emp_code =?";
	public static String updateQueryQualification = "update Employee set ? = ? where Emp_code =?";
	public static String updateQueryStatus = "update Employee set ? = ? where Emp_code =?";
	public static String updateQuerySalary = "update Employee set ? = ? where Emp_code =?";
	public static String deleteQuery = "delete from Employee  where Emp_code=?";
	public static String fetchAllRecord = " select Emp_code , Name from Employee ";
	public static String StatusBasedFetchQuery1 = "select Name,Designation,Qualification,Status,Salary from Employee where Status like ?";
}
