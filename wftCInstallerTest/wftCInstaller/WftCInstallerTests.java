package wftCInstaller;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

import wftcInstaller.AuxMeth;

public class WftCInstallerTests {
	
	private AuxMeth am;
	
	@Before
	public void setUp() {
		am = new AuxMeth();
	} 
	
	@Test
	public void attemptToCreateExistingDirLeavesOriginalUnmodified() throws IOException {
		File target = new File("testDir");
		Files.createDirectory(target.toPath());
		
		AuxMeth.mkDirIfNotPresent(target);
		
		assertTrue(target.exists());
		
		Files.delete(target.toPath());
	}
	
	@Test
	public void creatingNonExistentDirectoryCreatesIt() throws IOException {
		Path ptd = AuxMeth.strToPath("testDir");
		boolean pre = Files.exists(ptd);
		
		AuxMeth.mkDirIfNotPresent(new File("testDir"));
		boolean post = Files.exists(ptd);
		
		assertFalse(pre);
		assertTrue(post);
		
		Files.delete(ptd);
	}
	

	
	
}
