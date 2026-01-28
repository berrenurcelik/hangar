package de.thm.mni.service;

import de.thm.mni.model.HandoverReturnAppointment;
import de.thm.mni.repository.HandoverReturnAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UC FB.7 : HandoverReturnAppointmentCatalog
 */
@Component
public class HandoverReturnAppointmentCatalog {

    @Autowired
    private HandoverReturnAppointmentRepository repository;

    public void add(HandoverReturnAppointment app) {
        repository.save(app);
    }

    public HandoverReturnAppointment save(HandoverReturnAppointment app) {
        return repository.save(app);
    }

    public List<HandoverReturnAppointment> findByAircraftOwnerId(Long ownerId) {
        return repository.findByAircraftOwner_Id(ownerId);
    }

    public List<HandoverReturnAppointment> findByHangarProviderId(Long hpId) {
        return repository.findByHangarProvider_Id(hpId);
    }

    public List<HandoverReturnAppointment> findByHangarProviderIdAndStatus(Long hpId, String status) {
        return repository.findByHangarProvider_IdAndStatus(hpId, status);
    }

    public HandoverReturnAppointment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termin nicht gefunden"));
    }
}
