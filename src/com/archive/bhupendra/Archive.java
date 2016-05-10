package com.archive.bhupendra;

import java.io.File;

import com.ui.bhupendra.FileSize;
import com.ui.bhupendra.MainWindow;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class Archive {

	private MainWindow mainWindow;
	private long processed = 0;

	public Archive(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void archive(String destDir, String sourceDir, String secret) throws Exception {

		File fSource = new File(sourceDir);
		if (fSource.isFile()) {
			new ArchiveFile(destDir, sourceDir, secret);
			processed += fSource.length();
			mainWindow.addProgress(processed);
		} else {
			File[] files = fSource.listFiles();
			if (files.length == 0) {
				new ArchiveFolder(destDir, sourceDir, secret);
			} else {
				File destFolder = new File(destDir, fSource.getName());
				if (!destFolder.exists()) {
					destFolder.mkdirs();
				}
				for (File file : files) {
					archive(destFolder.getAbsolutePath(), file.getAbsolutePath(), secret);
				}

				FileSize size = new FileSize(mainWindow, destFolder);
				new ArchiveFolder(destDir, destFolder.getAbsolutePath(), secret);
				processed += size.getSize();
				mainWindow.addProgress(processed);
				delete(destFolder);
			}
		}
	}

	public void extract(String destDir, String sourceDir, String secret, boolean isRoot) throws Exception {
		try {
			File source = new File(sourceDir);
			if (source.isDirectory()) {
				File[] files = source.listFiles();
				for (File file : files) {
					extract(source.getAbsolutePath(), file.getAbsolutePath(), secret, false);
				}
			} else {
				ZipFile zipFile = new ZipFile(sourceDir);
				if (source.getName().endsWith(".d") || source.getName().endsWith(".f")) {
					if (zipFile.isEncrypted()) {
						if (secret == null || secret.isEmpty()) {
							throw new Exception("Password is null.");
						}
						zipFile.setPassword(secret);
					}
					zipFile.extractAll(destDir);
					if (!isRoot)
						delete(source);

					File dir = new File(destDir);
					File[] files = dir.listFiles();
					for (File file : files) {
						if (file.isDirectory())
							extract(destDir + File.separator + file.getName(), file.getAbsolutePath(), secret, false);
					}
				}
			}

		} catch (ZipException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void delete(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] entries = file.listFiles();
				for (File currentFile : entries)
					currentFile.delete();
			}
			file.delete();
		}
	}

}
