package de.thm.mni.dto;

import de.thm.mni.model.Benutzer;

/**
 * Response nach Login/Registrierung (ohne Passwort)
 */
public record AuthResponse(Long id, String name, String email, Benutzer.Role role) {

    public static AuthResponse from(Benutzer user) {
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
