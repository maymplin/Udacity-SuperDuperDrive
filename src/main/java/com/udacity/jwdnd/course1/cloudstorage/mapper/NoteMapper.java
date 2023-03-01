package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    List<Note> getNotesByUserId(Integer userId);

    @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
    Note getNoteByNoteId(Integer noteId);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Delete("DELETE FROM notes WHERE noteId = #{noteId}")
    void delete(Integer noteId);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, " +
            "notedescription = #{noteDescription} " +
            "WHERE noteId = #{noteId}")
    void update(Integer noteId);

}
