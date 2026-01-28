package de.thm.mni.dto;

import java.util.Set;

/**
 * Request-Body für Hangaranbieter-Registrierung
 */
public record RegisterHangarProviderRequest(
        String name,
        String email,
        String password,
        String contact,
        String serviceHours,
        String city,
        String costs,
        Set<String> storageConditions
) {
}
