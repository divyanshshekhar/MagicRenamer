/*
* Renamer
*
* v1.0.0.1
*
* --Changelog--
* v1.0.0.1	(April 8, 2017)
* Modified Credits.
* v1.0.0.0 (April 7, 2017)
* Initial.
*
*
* 2017-April-5
*
* Copyright 2017 Divyansh Shekhar Gaur
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package DivyanshShekhar.MagicRenamer;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
* 
*/
public class Renamer{
	
	/**
	* Array to store the modification options entered by user.
	* One element of array stores one modification.
	* Currently only ne modification (i.e. at the start of file) at a time is supported. However, plan is to extend the usage to 4 modificationss (2 at the start and 2 at the end) and even support regex patterns.
	*/
	static Mod mods[] = new Mod[1];
	
	/**
	* Path to folder in which the target files are.
	*/
	static String folder;
	
	/**
	* Entry point of program.
	* @param args No arguments needed.
	* @throws IOException Catch an IO Exception if occurs.
	* @since 1.0.0.0
	*/
	public static void main(String args[]) throws IOException{
		System.out.println("This program can be used to add or remove some text from start or end of a file.");
		try{
			getPath();
			renameInFolder();
		}
		catch(Exception ex){
			
			// Note: Logging not working currently
			File log = new File("./error.log");
			// if file doesnt exists, then create it
			if (!log.exists()) {
				log.createNewFile();
			}

			// true = append file
			FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			String dateTime = df.format(dateobj);
			pw.println("At ["+dateTime+"]\n"+ex.toString());
		}
		
		//Credits
		System.out.println("\n\nVisit http://www.empyreaninfotech.com/products/ to find more handy tools developed by me.\n");
		System.out.println("\n\nDeveloped by : Divyansh Shekhar Gaur"
			+"\nConnect with developer at:"
			+"\n\tTwitter : https://www.twitter.com/divsgaur"
			+"\n\tGitHub : https://github.com/divyanshshekhar"
			+"\n\tGoogle Plus : https://plus.google.com/+DivyanshShekhar");
			
		System.out.println("\nThanks...");
		System.out.println("Exiting...");
	}
	
	/** Private constructor to prevent accidental instantiation of this class.
	*/
	private Renamer(){}
	
	/**
	* Take input from user and do renaming.
	* @throws IOException InputStreamReader may throw IOException
	* @since 1.0.0.0
	*/
	private static void renameInFolder() throws IOException{
		char ch;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do{			
			getCriteria();
			op_rename();
			System.out.println("Rename more files in same folder? Y|N : ");
			ch=(char)br.read();
			System.out.println("You chose : "+ch);
		}
		while(ch=='y'||ch=='Y');
	}
	
	/**
	* Carry out the low level task of renaming.
	* @since 1.0.0.0
	*/
	private static void op_rename(){
		int nFiles=0,nRenamed=0,nFailed=0;
		
		List<String> files = new ArrayList<String>();
		File[] files_raw = new File(folder).listFiles();
		//If this pathname does not denote a directory, then listFiles() returns null. 
		if(files_raw==null){
			System.out.println("Error : Couldn't find folder.");
			System.exit(3);
		}
		for (File file : files_raw) {
			if (file.isFile()) {
				boolean rename,nameChanged,extChanged;
				rename=nameChanged=extChanged=false;
				String fname = file.getName().toString();	//get name of file including extension
				String ext = fname.substring(fname.lastIndexOf('.')+1);	//get extension
				String name = fname.substring(0,fname.lastIndexOf('.'));	//get main name
				String newname,newext;
				newname=newext="";
				
				nFiles++;
				////m1////
				try{
					if(mods[0].type==ModType.REMOVE){
						if(name.indexOf(mods[0].text)==0){
							newname+=name.substring(mods[0].text.length());	
							nameChanged=true;					
						}
					}
					else if(mods[0].type==ModType.ADD){
						newname+=mods[0].text + name;	
						nameChanged=true;					
					}
				}
				catch(Exception ex){}
				////m2////
				try{
					
					if(mods[1].type==ModType.REMOVE){
						if(name.lastIndexOf(mods[1].text)+mods[1].text.length()==name.length())	//ensure that the phrase is at the last of file name
						{
							newname+=name.substring(name.length()-mods[1].text.length());						
							nameChanged=true;
						}
					}
					else if(mods[1].type==ModType.ADD){
						newname+=mods[1].text + name;						
						nameChanged=true;
					}	
				}
				catch(Exception ex){
					//System.out.println(ex.toString());
				}
				////m3////
				try{
					if(mods[2].text!=""){
						
						if(mods[2].type==ModType.REMOVE)
							if(ext.indexOf(mods[2].text)==0){
								newext=ext.substring(mods[2].text.length());						
								extChanged=true;
							}
						else if(mods[2].type==ModType.ADD){
							newext+=mods[2].text + ext;						
							extChanged=true;
						}
						
					}
				}
				catch(Exception ex){}
				////m4////
				try{
					if(mods[3].type==ModType.REMOVE)
						if(ext.lastIndexOf(mods[3].text)+mods[3].text.length()==ext.length())	//ensure that the phrase is at the last of file name
						{
							newext+=ext.substring(ext.length()-mods[3].text.length());				
							extChanged=true;		
						}
					else if(mods[3].type==ModType.ADD){
						newext+=mods[3].text + ext;
						extChanged=true;
					}
					
				}
				catch(Exception ex){}
				if(nameChanged||extChanged)
				{
					if(!nameChanged)
						newname=name;
					if(!extChanged)
						newext=ext;
					
					String newfname = newname+"."+newext;
					newfname=newfname.trim();
					//System.out.println("Old file name = "+fname);
					//System.out.println("New file name = "+newfname);
					//System.out.print("\r");
					System.out.print("Renaming file \""+fname+"\" to \""+newfname+"\"\r");
					
					File newFile = new File(folder+"\\"+newfname);
					try{
						Files.move(file.toPath(), newFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
						nRenamed++;
					}
					catch(Exception ex){
						System.out.println("\nError occured while renaming - "+file.toPath()+"\n"+ex.toString());
						nFailed++;
					}
						
				}
				else{
					//No need of renaming.
				}
			}
		}
		System.out.println("\nOperation complete.");
		System.out.println("Total files found : "+nFiles+"\nTotal renamed : "+nRenamed+"\nTotal failed : "+nFailed);
	}
	
	/**
	* Get path of the target folder.
	* @since 1.0.0.0
	*/
	private static void getPath(){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n\nEnter path to folder in which to look for files.");
		folder = sc.nextLine();
		
	}
	
	/**
	* Get the criteria for renaming the files. Currently supports renaming only the start of the file. Help in extending it to end as well as middle of the file.
	* @since 1.0.0.0
	*/
	public static void getCriteria(){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("\nEnter the text to be added or removed (Use symbol '-' or '+' at the start of text to denote REMOVAL or ADDITION of the text to filename).\nNote:You can leave it blank if nothing is to be changed.");
		System.out.println("\nEnter the text to be added to or removed from start of name of file.");
		mods[0] = new Mod(sc.nextLine());
		/*
		--Disabled--
		
		System.out.println("Enter the text to be added to or removed from end of name of file.");
		System.out.println("Function hasn't been checked for errors and is disabled for now. Sorry for the inconvenience caused.");
		//disabled
		//mods[1] = new Mod(sc.nextLine());	
		System.out.println("Enter the text to be added to or removed from start of extension.");
		System.out.println("Function hasn't been checked for errors and is disabled for now. Sorry for the inconvenience caused.");
		//disabled
		//mods[2] = new Mod(sc.nextLine());
		System.out.println("Enter the text to be added to or removed from end of extension.");
		System.out.println("Function hasn't been checked for errors and is disabled for now. Sorry for the inconvenience caused.");
		//disabled
		//mods[3] = new Mod(sc.nextLine());*/
		
	}
	
	
}