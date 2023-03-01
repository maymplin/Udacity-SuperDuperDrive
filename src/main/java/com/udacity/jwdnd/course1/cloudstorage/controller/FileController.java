package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.file.FileAlreadyExistsException;
import java.security.Principal;

@Controller
public class FileController {

    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    // Referenced https://www.baeldung.com/spring-redirect-and-forward on redirect
    @PostMapping("/upload")
    public RedirectView uploadFile(MultipartFile fileUpload, Principal principal, RedirectAttributes redirectAttributes) throws FileAlreadyExistsException {
        Integer userId = userService.getUser(principal.getName()).getUserId();
        String uploadError = null;

        if (fileUpload == null) {
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
}
