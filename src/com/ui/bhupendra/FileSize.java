package com.ui.bhupendra;

import java.io.File;

public class FileSize {

	private long size;

	public FileSize(MainWindow mainWindow, File file) {
		mainWindow.updateStatus("Calculating file size: " + file.getAbsolutePath());
		size = getSize(file);
		mainWindow.updateStatus("File size: " + size + " Bytes");
	}

	private long getSize(File file) {
		long size = 0;
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				size = size + (f.isFile() ? f.length() : getSize(f) * 2);
			}
		} else {
			size = file.length();
		}
		return size;
	}

	public long getSize() {
		return size;
	}
}
