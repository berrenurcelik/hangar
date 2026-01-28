package de.thm.mni.dto;

/**
 * Request-Body für Flugzeugbesitzer-Registrierung
 */
public record RegisterAircraftOwnerRequest(String name, String email, String password, String contact) {
}
