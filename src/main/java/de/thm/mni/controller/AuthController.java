package de.thm.mni.controller;

import de.thm.mni.dto.AuthResponse;
import de.thm.mni.dto.ErrorMessage;
import de.thm.mni.dto.LoginRequest;
import de.thm.mni.dto.RegisterAircraftOwnerRequest;
import de.thm.mni.dto.RegisterHangarProviderRequest;
import de.thm.mni.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request.email(), request.password());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("/register/aircraft-owner")
    public ResponseEntity<?> registerAircraftOwner(@RequestBody RegisterAircraftOwnerRequest req) {
        try {
            AuthResponse response = authService.registerAircraftOwner(
                    req.name(), req.email(), req.password(), req.contact());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("/register/hangar-provider")
    public ResponseEntity<?> registerHangarProvider(@RequestBody RegisterHangarProviderRequest req) {
        try {
            AuthResponse response = authService.registerHangarProvider(
                    req.name(), req.email(), req.password(), req.contact(),
                    req.serviceHours(), req.city(), req.costs(), req.storageConditions());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }
}
