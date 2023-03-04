package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(Credential credential) {
        Integer credentialId = credential.getCredentialId();
        String encodedKey = generateEncodedKey();
        String encryptedPassword = encryptString(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

        if (credentialId == null) {
            credentialId = addCredential(credential);
        } else {
            updateCredential(credential);
        }

        return credentialId;
    }

    public int addCredential(Credential credential) {
        return credentialMapper.insert(credential);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.update(credential);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredentialByCredentialId(credentialId);
    }

    public List<Credential> getUserCredentials(Integer userId) {
        return credentialMapper.getCredentialsByUserId(userId);
    }

    public String generateEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public String encryptString(String password, String encodedKey) {
        return encryptionService.encryptValue(password, encodedKey);
    }

    public String decryptString(String encryptedPassword, String encodedKey) {
        return encryptionService.decryptValue(encryptedPassword, encodedKey);
    }
}
