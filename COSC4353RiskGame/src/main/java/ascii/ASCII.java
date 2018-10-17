package ascii;

import java.io.*;

/**
 * ASCII class reads in a text file and prints it out, allows for multiple uses of ASCII art
 * @author Grant Williams
 * 
 * @date 9/12/18
 **/
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
	public void readASCII(String filepath){
		try{
			File currentDir = new File(".");
	        File parentDir = currentDir.getAbsoluteFile();
	        File newFile = new File(parentDir + filepath);
	        fr = new FileReader(newFile);
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
