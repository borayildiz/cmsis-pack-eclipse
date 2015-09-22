package com.bora.logger.file;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Logs the executed methods, constructors or stack info
 */
public class Log {
	
	private static String path = "log.txt";
	private static boolean append_to_file = true;
	private static int logID = 0;
	
	/**
	 * Log the current executed method in detail
	 */
	public static void writeCurrentMethodDetail(){
		String name = Thread.currentThread().getStackTrace()[2].getMethodName();	
		String fileName = Thread.currentThread().getStackTrace()[2].getFileName();
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		writeToFile("Method Call:		" + "Method Name: " + name + ", " + 
			    	"Class Name: " + className + ", " + "File Name: " + fileName);
	}
	
	/**
	 * Log the current executed method name
	 */
	public static void writeCurrentMethod(){
		String name = Thread.currentThread().getStackTrace()[2].getMethodName();	
		writeToFile("Method Call:		" + name + "()");
	}	
	
	/**
	 * @param arguments
	 * Log the current executed method name with parameters
	 */
	public static void writeCurrentMethod(Object...arguments){
		String name = Thread.currentThread().getStackTrace()[2].getMethodName();	
		String parameters = "";
		
		for(int i = 0; i < arguments.length ; i++){
			parameters += arguments[i]; 
			if( i != (arguments.length - 1)) parameters += ", ";
		}
		writeToFile("Method Call:		" + name + "("  + parameters + ")");
	}	
	
	/**
	 * @param constructorName
	 * Log the current executed constructor name
	 */
	public static void writeCurrentConstructor(String constructorName){	
		writeToFile("Constructor Call:	" + constructorName);
	}	
	
	/**
	 * Log the stack info of the current method
	 */
	public static void writeStackInfo(){
		int stackCount = Thread.currentThread().getStackTrace().length;
		String name = null;
		String stackInfo = null;
		
		writeToFile(("Stack Count: " + stackCount));
		
		for(int i = 0; i < stackCount; i++){
			name = Thread.currentThread().getStackTrace()[i].getMethodName();
			stackInfo = "Stack" + i + ": " + name; 
			writeToFile(stackInfo);
		}	
	}
	
	
	/**
	 * @param textLine
	 * Writes to the log file
	 */
	public static void writeToFile(String textLine){
		logID++;
		FileWriter write = null;
		
		try {
			write = new FileWriter(path, append_to_file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter print_line = new PrintWriter(write);
		print_line.printf("%s" + "%n", (logID + "#	" + textLine));
		print_line.close();
	}
	
	
	/*
	 * Clear the log file
	 */
	public static void clear(){
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.print("");
		writer.close();
	}

}
