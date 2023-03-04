package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    List<Credential> getCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM credentials WHERE credentialid = #{credentialId}")
    Credential getCredentialByCredentialId(Integer credentialId);

    @Insert("INSERT INTO credentials (url, username, password, userid) " +
            "VALUES(#{url}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    void delete(Integer credentialId);

    @Update("UPDATE credentials SET url = #{url}, " +
            "username = #{username}, " +
            "password = #{password}" +
            "WHERE credentialId = #{credentialId}")
    void update(Credential credential);
}
