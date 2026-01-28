package de.thm.mni.dto;

/**
 * Request-Body für Login
 */
public record LoginRequest(String email, String password) {
}
