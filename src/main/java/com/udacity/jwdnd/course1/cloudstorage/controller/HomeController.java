package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("home")
public class HomeController {

    private CredentialService credentialService;
    private FileService fileService;
    private NoteService noteService;
    private UserService userService;

    public HomeController(CredentialService credentialService, FileService fileService, NoteService noteService, UserService userService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String displayHome(Principal principal, Model model) {

        String username = principal.getName();
        Integer userId = userService.getUser(username).getUserId();

        // Get all files for the current user
        List<File> fileList = fileService.getUserFiles(userId);
        model.addAttribute("files", fileList);

        // Get all notes for the current user
        List<Note> noteList = noteService.getUserNotes(userId);
        model.addAttribute("notes", noteList);

        // Get all credentials for current user

        return "home";
    }
}
