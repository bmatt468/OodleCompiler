/* Oodle.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: Main class for Oodle compiler project
 */

package com.bju.cps450;
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.StringReader;
import java.util.ArrayList;

import com.bju.cps450.lexer.LexerException;
import com.bju.cps450.node.Start;

public class Oodle
{
	/* printHelp
	 * Arguments:
	 * 
	 * Purpose: Writes a help statement to standard out
	 */
	public static void printHelp() {
		System.out.println("Oodle Compiler");
		System.out.println("v 0.1");
		System.out.println("Author: Ethan McGee");
		System.out.println("");
		System.out.println("Usage:");
		System.out.println(" java -jar oodle.jar [options] [files]");
		System.out.println("");
		System.out.println("Options:");
		System.out.println("-ds, --print-tokens");
		System.out.println("  display a list of tokens from the listed files");
		System.out.println("-?, --help");
		System.out.println("  display this message");
	}
	
	/* main
	 * Arguments:
	 *  @args - the list of command line arguments
	 * Purpose: main execution function for compiler
	 */
    public static void main(String[] args) throws IOException, IllegalOptionValueException, UnknownOptionException, LexerException {
    	String[] files = processCommandLineOptions(args);
    	
    	// force stdlib into files
    	try {
    		ArrayList<String> list = new ArrayList<String>();
    		list.add("./stdlib.ood");
    		for (int i = 0; i < files.length; ++i) {
    			list.add(files[i]);
    		}
    		files = list.toArray(files);
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    	}
    	
		//read and merge all files into single file    	
    	String source = "";    	
    	boolean first = true;
		for(String file: files) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int lines = 0;
			while((line = reader.readLine()) != null) {
				source += ((first ? "" : "\n") + line);
				first = false;
				++lines;
			}
			reader.close();
			Application.getFileAndLineNumbers().addFile(file, lines);
		}
		
		//begin lexing and parsing
		OodleLexer lexer = null;
		try {
			lexer = new OodleLexer(new PushbackReader(new StringReader(source)));
			OodleParser oodleParser = new OodleParser(lexer);
			//oodleParser.parse();
			
			Start node = oodleParser.parse();
			
			if (Application.getErrors().getTotalErrors() == 0) {
				SemanticChecker checker = new SemanticChecker();
				node.apply(checker);
				
				// begin code generation
				if (Application.getErrors().getTotalErrors() == 0 && !(Application.getErrors().getUnsupportedFeatures())) {
					CodeGenerator cg = new CodeGenerator();
					node.apply(cg);
					try {
						cg.writeAssembly();
					} catch (Exception e) {
						System.exit(1);
					}
					Runtime rt = Runtime.getRuntime();
					Process p = rt.exec("gcc -g %FN%.s stdlib.o -o %FN%"
							.replace("%FN%", Application.getFileAndLineNumbers().getLastFile()));
					
					try {
						int exitCode = p.waitFor(); //wait for process to finish and grab exit code
						if(exitCode != 0) { //there was some error
							BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
							String line = "";
							line = b.readLine();
							while (line != null) {
							System.out.println(line); //print the error from gcc
							line = b.readLine();
							}
						} else {
							String s = Application.getFileAndLineNumbers().getLastFile();
							if (!(Application.getOptions().shouldKeepFile())) {
								File f = new File("%FN%.s"
										.replace("%FN%", Application.getFileAndLineNumbers().getLastFile())); //delete our temporary file
								f.delete();
							}
						}
					} catch(Exception ex) {
						System.out.println(ex.getMessage());
					}
				} else if (Application.getErrors().getUnsupportedFeatures()) {
					System.err.println("WARNING: Unsupported eatures used. Code generation aborted");
				}
			}	
		} catch (LexerException ex) {
			Application.getErrors().addLexicalError();
			System.out.println(Application.getFileAndLineNumbers().getFile(lexer.peek().getLine()) + ":" + Application.getFileAndLineNumbers().getLine(lexer.peek().getLine()) + "," + lexer.peek().getPos() + ":" + ex.getMessage());
		} 
		System.out.println(Application.getErrors().getTotalErrors() + " errors detected");
		if(Application.getErrors().getTotalErrors() > 0) {
			System.exit(1);
		} else {
			System.exit(0);
		}
    }
    
    /* processCommandLineOptions
	 * Arguments:
	 *  @args - the list of command line arguments
	 * Purpose: check for command line options, print help if any are malformed, return a list of files
	 */
    private static String[] processCommandLineOptions(String[] args) throws IllegalOptionValueException, UnknownOptionException {
    	CmdLineParser parser = new CmdLineParser();
    	//command line options
		CmdLineParser.Option printToken1 = parser.addBooleanOption('d', "print-tokens");
		CmdLineParser.Option printToken2 = parser.addBooleanOption('s', "print-tokens");
		CmdLineParser.Option help = parser.addBooleanOption('?', "help");
		CmdLineParser.Option source = parser.addBooleanOption('S', "output .s");
		//parse command line arguments
		parser.parse(args);
		
		//set applicable values from options class
		if((Boolean)parser.getOptionValue(printToken1, false) && (Boolean)parser.getOptionValue(printToken2, false)) {
			Application.getOptions().printTokens();
		} else if ((Boolean)parser.getOptionValue(printToken1, false) || (Boolean)parser.getOptionValue(printToken2, false) || (Boolean)parser.getOptionValue(help, false)) {
			printHelp();
			System.exit(0);
		}
		
		if ((Boolean)parser.getOptionValue(source, false)) {
			Application.getOptions().keepFile();
		}
		
		return parser.getRemainingArgs();
    }

}

