package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
@RequestMapping("credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    // save a new credential or update an existing credential
    @PostMapping("save")
    public RedirectView saveCredential(Credential credential, Principal principal,
                                       RedirectAttributes redirectAttributes) {
        Integer userId = userService.getUser(principal.getName()).getUserId();
        Integer credentialId;
        String errorMessage = null;

        if (credential == null) {
            errorMessage = "There was an error saving your credential. Please try again.";
        } else {
            credentialId = credential.getCredentialId();

            if (credentialId == null) {
                // save new credential
                credential.setUserId(userId);
                credentialService.addCredential(credential);
            } else {
                // update existing credential
                credentialService.updateCredential(credential);
            }
        }

        if (errorMessage == null) {
            redirectAttributes.addFlashAttribute("fileSuccess", "Credential successfully saved.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", errorMessage);
        }

        return new RedirectView("/home");
    }

    @RequestMapping("delete/{id}")
    public RedirectView deleteCredential(@PathVariable String id, RedirectAttributes redirectAttributes) {
        String deleteError = null;
        Integer credentialId = Integer.parseInt(id);

        if (credentialService.getCredential(credentialId) == null) {
            deleteError = "There was an error deleting your credential. Please try again.";
        } else {
            credentialService.deleteCredential(credentialId);
        }

        if ( deleteError == null) {
            redirectAttributes.addFlashAttribute("fileSuccess", "Credential successfully deleted.");
        } else {
            redirectAttributes.addFlashAttribute("fileError", deleteError);
        }

        return new RedirectView("/home");
    }
}
