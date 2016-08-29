package wftcInstaller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class WftCInstaller {

	public static void main(String[] args) {
		try {
			/* rename non empty dir: 
			Files.move(new File("/home/asta666/Desktop/wtfc").toPath(), new File("/home/asta666/Desktop/wtfc2").toPath(), StandardCopyOption.ATOMIC_MOVE); */

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("xxxasd");
	}
	
	public class Installation {
	     String loc_path = null;
	     String os = null;
	     AuxMeth am = new AuxMeth();
	     
	     public void performInstall() throws IOException {
	     //make a copy of the americas folder
		 am.copyFolder(new File(loc_path + "/mods/americas"), new File(loc_path + "/mods/amorg" ));
		 //run americas unpacker
		 am.runUnpacker(loc_path, os);
	    }
	}
}
