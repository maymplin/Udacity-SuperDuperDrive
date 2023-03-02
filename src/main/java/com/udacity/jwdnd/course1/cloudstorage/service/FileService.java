package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    // Reference Spring Doc Interface MultipartFile
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
    public int addFile(MultipartFile file, Integer userId) throws FileAlreadyExistsException {
        File newFile = new File();

        String fileName = file.getOriginalFilename();

        if (!isFileExists(fileName)) {
            try {
                newFile.setFileName(fileName);
                newFile.setContentType(file.getContentType());
                newFile.setFileSize(Long.toString(file.getSize()));
                newFile.setUserId(userId);
                newFile.setFileData(file.getBytes());
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        } else {
            throw new FileAlreadyExistsException(fileName);
        }

        return fileMapper.insert(newFile);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.delete(fileId);
    }

    public Boolean isFileExists(String fileName) {

        File retrievedFile = fileMapper.getFileByFileName(fileName);

        return retrievedFile != null;
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public List<File> getUserFiles(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }
}
