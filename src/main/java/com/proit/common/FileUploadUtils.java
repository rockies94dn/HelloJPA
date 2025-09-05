package com.proit.common;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.jakartaee.commons.compress.utils.FileNameUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

public class FileUploadUtils {

	static final String STORED_IMAGE_FOLDER = "images";

	public static String processImage(String fileName, Part part, ServletContext context) throws IOException {
		String uploadPath = context.getRealPath("") + STORED_IMAGE_FOLDER;

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		String uploadFileName = part.getSubmittedFileName();
		String storedFileName = fileName + "." + FileNameUtils.getExtension(uploadFileName);
		String storedFile = uploadPath + File.separator + storedFileName;
		System.out.println(storedFile);
		part.write(storedFile);

		return storedFileName;
	}

	public static void deleteFile(String imageUrl, ServletContext context) {
		if (imageUrl == null || imageUrl.isEmpty()) {
			return;
		}

		String uploadPath = context.getRealPath("") + STORED_IMAGE_FOLDER;
		String storedFileName = uploadPath + File.separator + imageUrl;

		File file = new File(storedFileName);
		if (file.exists()) {
			file.delete();
		}
	}
}
