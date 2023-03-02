package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.security.Principal;

@Controller
@RequestMapping("files")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    // Referenced https://www.baeldung.com/spring-redirect-and-forward on redirect
    @PostMapping("upload")
    public RedirectView uploadFile(MultipartFile fileUpload, Principal principal, RedirectAttributes redirectAttributes) throws FileAlreadyExistsException {
        Integer userId = userService.getUser(principal.getName()).getUserId();
        String uploadError = null;

        if (fileUpload == null || fileUpload.isEmpty()) {
            uploadError = "Please select a file and try again.";
        } else {
            if (fileService.isFileExists(fileUpload.getOriginalFilename())) {
                uploadError = "This file already exists.";
            }

            if (uploadError == null) {
                int fileUploaded = fileService.addFile(fileUpload, userId);

                if (fileUploaded < 0) {
                    uploadError = "There was an error uploading your file. Please try again.";
                }
            }
        }

        if (uploadError == null) {
            redirectAttributes.addFlashAttribute("fileSuccess", "File successfully uploaded.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", uploadError);
        }

        return new RedirectView("/home");
    }

    @RequestMapping("delete/{id}")
    public RedirectView deleteFile(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Integer fileId = Integer.parseInt(id);
        String deleteError = null;

        if(fileService.getFile(fileId) == null) {
            deleteError = "This file does not exist.";
        } else {
         fileService.deleteFile(fileId);
        }

        if (deleteError == null) {
            redirectAttributes.addFlashAttribute("fileSuccess", "File successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", deleteError);
        }

        return new RedirectView("/home");
    }

    // Referenced https://www.bezkoder.com/spring-boot-upload-file-database/ and
    // https://stackoverflow.com/questions/63126181/how-to-download-a-file-as-a-blob-byte-in-java-spring
    @RequestMapping("view/{id}")
    public ResponseEntity downloadFile(@PathVariable String id) throws Exception {
        Integer fileId = Integer.parseInt(id);
        try {
            File file = this.fileService.getFile(fileId);
            String contentType = file.getContentType();
            String fileName = file.getFileName();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(file.getFileData());
        } catch (Exception ex){
            throw new Exception("Error downloading file.");
        }
    }

}
