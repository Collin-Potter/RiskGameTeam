package ascii;

import java.io.*;

/**
 * ASCII class reads in a text file and prints it out, allows for multiple uses of ASCII art
 * @author Grant Williams
 * 
 * @date 9/12/18
 **/

/**
 * +---------------------------------------------------------------------------------+
|	      ___       __   __         ___    ___  __      __     __       	  |
|	|  | |__  |    /  ` /  \  |\/| |__      |  /  \    |__) | /__` |__/ 	  |
|	|/\| |___ |___ \__, \__/  |  | |___     |  \__/    |  \ | .__/ |  \ 	  |
|	                                                                          |
+---------------------------------------------------------------------------------+  
 * 
 * 
 * @author grant
 *
 */
public class ASCII {
	
	private BufferedReader br;
	private FileReader fr;
	String line;
	
	public ASCII(){
		br = null;
		fr = null;
		line = "";
	}
	//Reads in text file and prints verbatim
	public void readASCII(String filename){
		try{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br != null){
					br.close();
				}
				if(fr != null){
					fr.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
