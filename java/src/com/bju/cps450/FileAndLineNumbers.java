/* FileAndLineNumbers.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: Keeps track of all files and their line counts so that given a 
 *          line number from the merged file, it can produce the original
 *          filename and line number
 */
package com.bju.cps450;

import java.util.ArrayList;

public class FileAndLineNumbers {
	private ArrayList<String> files = new ArrayList<String>();
	private ArrayList<Integer> lines = new ArrayList<Integer>();
	
	public FileAndLineNumbers() {
		
	}
	
	/* addFile
	 * Arguments:
	 * 
	 * Purpose: store a file and its line count
	 */
	public void addFile(String filename, int lineCount) {
		files.add(filename);
		lines.add(lineCount);
	}
	
	/* getFile
	 * Arguments:
	 *   @line - the line number in the merged file
	 * Purpose: returns the original file containing the requested line
	 */
	public String getFile(int line) {
		int i = 0;
		while(i + 1 < files.size() && line > lines.get(i)) {
			line -= lines.get(i);
			++i;
		}
		return files.get(i);
	}
	
	/* getLine
	 * Arguments:
	 *   @line - the line number in the merged file
	 * Purpose: returns the original line number
	 */
	public int getLine(int line) {
		int i = 0;
		while(i + 1 < files.size() && line > lines.get(i)) {
			line -= lines.get(i);
			++i;
		}
		return line;
	}
	
	/* getFiles
	 * Arguments:
	 *   
	 * Purpose: returns the last file name (stripped down for naming use)
	 */
	public String getLastFile() {		
		return files.get(files.size()-1).substring(files.get(files.size()-1).lastIndexOf('/')+1, files.get(files.size()-1).indexOf(".ood"));
	}
}
