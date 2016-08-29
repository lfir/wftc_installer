package wftcInstaller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class AuxMeth
{
    public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
    	//Recursively copies a directory and its contents overwriting existing ones
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory()) 
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
                //System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) 
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                 
                //Recursive function call
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another 
        	Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
            //System.out.println("File copied :: " + destinationFolder);
        }
    }
    
    public static void runUnpacker(String path, String os) throws IOException {
    	//Run m2tw unpacker for americas mod
    	List<String> cmdAndArgs = Arrays.asList("cmd", "/c", "unpack_americas.bat");
    	if(os == "unix") {
    		cmdAndArgs.add(0, "wine");
    	}
    	File dir = new File(path + "/tools/unpacker");
    	ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
    	pb.directory(dir);
    	Process p = pb.start();	
    }
    
    
}