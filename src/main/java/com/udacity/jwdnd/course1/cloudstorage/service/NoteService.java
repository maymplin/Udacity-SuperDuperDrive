package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.insert(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }

    public void updateNote(Note note) {
        noteMapper.update(note);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNoteByNoteId(noteId);
    }

    public List<Note> getUserNotes(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }
}
