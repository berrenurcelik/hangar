package de.thm.mni.service;

import de.thm.mni.dto.AuthResponse;
import de.thm.mni.model.AircraftOwner;
import de.thm.mni.model.Benutzer;
import de.thm.mni.model.HangarProvider;
import de.thm.mni.repository.AircraftOwnerRepository;
import de.thm.mni.repository.HangarProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service für Registrierung und Login (Flugzeugbesitzer & Hangaranbieter)
 */
@Service
public class AuthService {

    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;

    @Autowired
    private HangarProviderRepository hangarProviderRepository;

    @Autowired
    private HPService hpService;

    /**
     * Login: Prüft E-Mail in Flugzeugbesitzer, dann Hangaranbieter. Passwort muss übereinstimmen.
     */
    public AuthResponse login(String email, String password) {
        var owner = aircraftOwnerRepository.findByEmail(email);
        if (owner.isPresent()) {
            AircraftOwner o = owner.get();
            if (o.getPassword() != null && o.getPassword().equals(password))
                return AuthResponse.from(o);
            throw new IllegalArgumentException("Ungültiges Passwort");
        }
        var provider = hangarProviderRepository.findByEmail(email);
        if (provider.isPresent()) {
            HangarProvider p = provider.get();
            if (p.getPassword() != null && p.getPassword().equals(password))
                return AuthResponse.from(p);
            throw new IllegalArgumentException("Ungültiges Passwort");
        }
        throw new IllegalArgumentException("Kein Benutzer mit dieser E-Mail gefunden");
    }

    /**
     * Registrierung als Flugzeugbesitzer. E-Mail muss einzigartig sein.
     */
    @Transactional
    public AuthResponse registerAircraftOwner(String name, String email, String password, String contact) {
        if (aircraftOwnerRepository.findByEmail(email).isPresent()
                || hangarProviderRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("E-Mail bereits registriert");
        AircraftOwner owner = new AircraftOwner(name, email, password, contact != null ? contact : "");
        owner = aircraftOwnerRepository.save(owner);
        return AuthResponse.from(owner);
    }

    /**
     * Registrierung als Hangaranbieter. E-Mail muss einzigartig sein.
     * HA.1 5a1: Bei vorhandenem Profil "Benutzer existiert bereits".
     */
    @Transactional
    public AuthResponse registerHangarProvider(String name, String email, String password, String contact,
                                               String serviceHours, String city, String costs, Set<String> storageConditions) {
        if (aircraftOwnerRepository.findByEmail(email).isPresent()
                || hangarProviderRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Benutzer existiert bereits");
        HangarProvider provider = hpService.saveProfile(
                name, email, password,
                contact != null ? contact : "",
                serviceHours != null ? serviceHours : "",
                city != null ? city : "",
                costs != null ? costs : "",
                storageConditions != null ? storageConditions : Set.of()
        );
        return AuthResponse.from(provider);
    }
}
