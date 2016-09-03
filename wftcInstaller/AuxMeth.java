package wftcInstaller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class AuxMeth
{
	
	//actions
    public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
    	//Recursively copies a directory and its contents overwriting existing files
    	FileSystem fs = FileSystems.getDefault();
    	final Path tmp = fs.getPath(destinationFolder.getParent());
        //Check if sourceFolder is a directory or file
        if (sourceFolder.isDirectory()) {
            //Verify if destinationFolder is already present; if not then create it
        	mkDirIfNotPresent(destinationFolder);
             
            //Get all files from source directory
            String files[] = sourceFolder.list();
             
            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyFolder(srcFile, destFile); //Recursive function call
            }
        } else {
            //If sourceFolder is file; then copy the file directly to new location
        	copyFile(sourceFolder.toPath(), destinationFolder.toPath(), tmp);
        }
    }
    
    public static void copyFile(Path sourceFile, Path destinationFile, Path parentFolder) throws IOException {
    	if (parentFolder != null) { // null will be returned if the path has no parent
    	    Files.createDirectories(parentFolder); 
        	Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            //System.out.println("File copied :: " + destinationFolder);
    	}
	}

	public static void mkDirIfNotPresent(File folder) {
        if (!folder.exists()) {
        	folder.mkdir();
            //System.out.println("Directory created :: " + destinationFolder);
        }
    }
	
	public static void bkFilesIfExist(String localDir, File resFolder, File destinationFolder) throws IOException {
		String q1 = "";
		Scanner scanFile = new Scanner(new File(resFolder + "/wftc-modified-file-list.txt"));
		while (scanFile.hasNextLine()) {
			File nf = new File(localDir + q1);
			if(nf.exists()) {
				Path df = destinationFolder.toPath();
				Path np = nf.toPath();
				Files.copy(np, df.resolve(np.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			
			}
			q1 = scanFile.nextLine();
		}
		scanFile.close();
	}
	
	public static void renameDir(File origDir, String desName) throws IOException {
	//rename non empty dir too
		Files.move(origDir.toPath(), (new File(desName).toPath()), StandardCopyOption.ATOMIC_MOVE);
	}
    
    public static void executeCommand(List<String> cmdAndArgs, String dirPath) throws IOException, InterruptedException {
	    	File dir = new File(dirPath);
	    	ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
	    	pb.directory(dir);
	    	Process p = pb.start();
	    	p.waitFor();
    }
        
    public static void executeShCommands(List<String> commands) throws IOException, InterruptedException {
        File tempScript = createTempScript(commands);
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", tempScript.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } finally {
            tempScript.delete();
        }
    }
        
    public static File createTempScript(List<String> commands) throws IOException {
        File tempScript = File.createTempFile("script", null);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("#!/bin/bash");
        for(String command : commands) {
        	printWriter.println(command);
        }
        printWriter.close();
        return tempScript;
    }
    
    public static int showTextMessage()	{
		return JOptionPane.showConfirmDialog(null,
				"War for the Colonies installer.\n" +
				"Copyright (C) 2016 Asta666.\n" +
				"This program is free software: you can redistribute it and/or modify it under the terms of the GNU\n" +
				"General Public License as published by the Free Software Foundation, either version 3 of the\n" +
				"License, or (at your option) any later version.\n" +
				"This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;\n" +
				"without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR\n" +
				"PURPOSE.  See the GNU General Public License for more details.\n" +
				"You should have received a copy of the GNU General Public License along with this program. If\n" +
				"not, see <http://www.gnu.org/licenses/>.\n\n" +
				"Do you accept these terms and conditions?",
				"License information", JOptionPane.YES_NO_OPTION);
	}
    
    //infos
    public static boolean isUnixOS() {
    	return  System.getProperty("os.name").equals("Linux") ||
    			System.getProperty("os.name").equals("Mac OS X");
    }

}