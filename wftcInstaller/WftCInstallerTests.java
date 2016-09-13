package wftcInstaller;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;

public class WftCInstallerTests {
	
	private AuxMeth am = new AuxMeth();
	
	@Test
	public void AttemptToCreateExistingDirLeavesOriginalUnmodified() throws IOException {
		File target = new File("testDir");
		Files.createDirectory(target.toPath());
		
		AuxMeth.mkDirIfNotPresent(target);
		
		assertTrue(target.exists());
		Files.delete(target.toPath());
	}

}
