package strategy;

import java.io.File;

public class ManagerFile implements SaveOpenFile{
	
	private SaveOpenFile saveOpenFile;

	public ManagerFile(SaveOpenFile saveOpenFile) {
		this.saveOpenFile = saveOpenFile;
	}

	@Override
	public void save(File file) {
		saveOpenFile.save(file);
		
	}

	@Override
	public void open(File file) {
		saveOpenFile.open(file);
	}

}
