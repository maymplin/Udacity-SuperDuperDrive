package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;

    @PostMapping("add")
    public RedirectView addNote(Note note, Principal principal, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(principal.getName()).getUserId();

        note.setUserId(userId);

        if (noteService.addNote(note) > 0) {
            redirectAttributes.addFlashAttribute("fileSuccess", "Note successfully saved.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", "There was an error saving your note. Please try again.");
        }

        return new RedirectView("/home");
    }

    @PostMapping("delete/{id}")
    public RedirectView deleteNote(@PathVariable String id, RedirectAttributes redirectAttributes) {

        return new RedirectView("/home");
    }

}
