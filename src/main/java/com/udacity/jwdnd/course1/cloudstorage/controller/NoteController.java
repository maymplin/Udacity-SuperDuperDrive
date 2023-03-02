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
    public RedirectView saveNote(Note note, Principal principal, RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(principal.getName()).getUserId();
        Integer noteId;
        String errorMessage = null;

        if (note == null) {
            errorMessage = "There was an error saving your note. Please try again.";
        } else {
            noteId = note.getNoteId();
            if (noteId == null) {

                // save new note
                note.setUserId(userId);
                noteService.addNote(note);
            } else {

                // updating existing note
                noteService.updateNote(note);
            }
        }

        if (errorMessage == null) {
            redirectAttributes.addFlashAttribute("fileSuccess", "Note successfully saved.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", errorMessage);
        }

        return new RedirectView("/home");
    }

    @RequestMapping("delete/{id}")
    public RedirectView deleteNote(@PathVariable String id, RedirectAttributes redirectAttributes) {
        String deleteError = null;
        Integer noteId = Integer.parseInt(id);

        if (noteService.getNote((noteId)) == null) {
            deleteError = "There was an error deleting your note. Please try again.";
        } else {
            noteService.deleteNote(noteId);
        }

        if ( deleteError == null) {
            redirectAttributes.addFlashAttribute("fileSuccess", "Note successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", deleteError);
        }

        return new RedirectView("/home");
    }

}
