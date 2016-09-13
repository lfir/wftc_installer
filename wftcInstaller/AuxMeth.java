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
	public static void bkFilesIfExist(String localDir, File resFolder, File destinationFolder) throws IOException {
		String q1 = "\n";
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
	
    public static void copyFile(Path sourceFile, Path destinationFile, Path parentFolder) throws IOException {
    	if (parentFolder != null) { // null will be returned if the path has no parent
    	    //Files.createDirectories(parentFolder); 
        	Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            //System.out.println("File copied :: " + destinationFolder);
    	}
	}
	
    public static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
    	//Recursively copies the contents of a directory, overwriting existing files in dest
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
    
	public static void mkDirIfNotPresent(File folder) {
        if (!folder.exists()) {
        	folder.mkdir();
            //System.out.println("Directory created :: " + destinationFolder);
        }
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
    
    public static void createDesktopShortcut(String local_path) throws IOException {
    	//working on windows and linux
	    	 File desktopDir = new File(System.getProperty("user.home"), "Desktop");
	    	 Path homeDirLnk = new File(desktopDir + "/War for the Colonies").toPath();
	    	 Path targetFile = (new File(local_path + "/mods/americas/War for the Colonies.bat")).toPath();
	    	 Files.deleteIfExists(homeDirLnk);
	    	 Files.createSymbolicLink(homeDirLnk, targetFile);
    }
    
    public static Path strToPath(String str) {
    	Path path = (new File(str)).toPath();
    	return path;
    }
    
    //infos
    public static boolean isUnixOS() {
    	return  System.getProperty("os.name").equals("Linux") ||
    			System.getProperty("os.name").equals("Mac OS X");
    }

}

