/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.controller;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import taskcheck.app.config.Constants;
import taskcheck.app.service.AttachmentService;
import taskcheck.data.entity.Attachment;
import taskcheck.data.entity.Issue;
import taskcheck.data.view.Views;

/**
 * The Class AttachmentController.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/attachments")
public class AttachmentController {

	/** The env. */
	@Autowired
	private Environment env;

	/** The attachment service. */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * Upload.
	 *
	 * @param identifier the identifier
	 * @param file the file
	 * @param description the description
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping(value = "/{identifier}/upload", headers = Constants.ApiVersion.V1)
	public ResponseEntity<Void> upload(@PathVariable("identifier") String identifier,
			@RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws IOException {

		String baseDir = env.getProperty(Constants.Attachment.ATTACHMENT_PATH);
		String path = baseDir + File.separator + identifier;

		File dir = new File(path);

		if (!dir.exists()) {
			dir.mkdir();
		}

		String fileName = file.getOriginalFilename();

		File storedFile = new File(path + File.separator + fileName);

		if (storedFile.exists()) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSssmmHHddMMyyyy");

			String date = simpleDateFormat.format(new Date());
			fileName = date + "_CopyOf" + fileName;
		}

		storedFile = new File(path + File.separator + fileName);
		storedFile.createNewFile();
		FileOutputStream outputStream = new FileOutputStream(storedFile);
		outputStream.write(file.getBytes());
		outputStream.close();

		Attachment attachment = new Attachment();
		attachment.setIdentifier(identifier);
		attachment.setName(file.getOriginalFilename());
		attachment.setDescription(description);
		attachment.setFormat(file.getContentType());
		attachment.setPath(storedFile.getAbsolutePath());

		attachmentService.create(attachment);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Gets the attachments.
	 *
	 * @param identifier the identifier
	 * @return the attachments
	 */
	@GetMapping(value = "/{identifier}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Attachment.class)
	public ResponseEntity<List<Attachment>> getAttachments(@PathVariable("identifier") String identifier) {

		List<Attachment> objs = attachmentService.getAll(identifier);

		return new ResponseEntity<List<Attachment>>(objs, HttpStatus.OK);
	}

	/**
	 * Download attachment.
	 *
	 * @param identifier the identifier
	 * @param id the id
	 * @param request the request
	 * @return the response entity
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping(value = "/{identifier}/download/{id}", headers = Constants.ApiVersion.V1)
	@JsonView(Views.Attachment.class)
	public ResponseEntity<Resource> downloadAttachment(@PathVariable("identifier") String identifier,
			@PathVariable("id") Long id, HttpServletRequest request) throws IOException {

		Attachment obj = attachmentService.getById(id);

		File file = new File(obj.getPath());
		Resource resource = new UrlResource(file.toURI());
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
