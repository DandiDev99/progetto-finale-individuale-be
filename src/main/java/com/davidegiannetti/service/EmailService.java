package com.davidegiannetti.service;

import com.davidegiannetti.entity.User;

public interface EmailService {
    public void sendConfirmationEmail(String email, String password);
}
