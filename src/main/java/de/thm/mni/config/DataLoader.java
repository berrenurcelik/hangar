package de.thm.mni.config;

import de.thm.mni.model.*;
import de.thm.mni.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Lädt Test-Daten beim Anwendungsstart
 * Temporär deaktiviert wegen Jackson Serialisierung
 */
//@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AircraftOwnerRepository aircraftOwnerRepository;
    
    @Autowired
    private HangarProviderRepository hangarProviderRepository;
    
    @Autowired
    private PartsProviderRepository partsProviderRepository;
    
    @Autowired
    private SparePartRepository sparePartRepository;
    
    @Autowired
    private AircraftRepository aircraftRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Lade Test-Daten...");
        
        // 1. Aircraft Owners erstellen
        AircraftOwner owner1 = new AircraftOwner("Max Mustermann", "max@example.com", "pass123", "0176-1234567");
        AircraftOwner owner2 = new AircraftOwner("Anna Schmidt", "anna@example.com", "pass456", "0177-9876543");
        AircraftOwner owner3 = new AircraftOwner("Hans Meyer", "hans@example.com", "pass789", "0178-5555555");
        
        aircraftOwnerRepository.save(owner1);
        aircraftOwnerRepository.save(owner2);
        aircraftOwnerRepository.save(owner3);
        System.out.println("✅ 3 Aircraft Owners erstellt");
        
        // 2. Hangar Providers erstellen
        Set<String> conditions1 = new HashSet<>();
        conditions1.add("Climate controlled");
        conditions1.add("24/7 Security");
        
        Set<String> conditions2 = new HashSet<>();
        conditions2.add("Basic storage");
        conditions2.add("Fire protection");
        
        HangarProvider hp1 = new HangarProvider("Sky Hangar GmbH", "sky@hangar.de", "hp123", 
                                                 "0800-111111", "Mo-Fr 8:00-18:00", conditions1);
        HangarProvider hp2 = new HangarProvider("AeroStorage AG", "aero@storage.de", "hp456", 
                                                 "0800-222222", "Mo-So 6:00-22:00", conditions2);
        
        hangarProviderRepository.save(hp1);
        hangarProviderRepository.save(hp2);
        System.out.println("✅ 2 Hangar Providers erstellt");
        
        // 3. Parts Providers erstellen
        PartsProvider ps1 = new PartsProvider("Aviation Parts Inc", "parts@aviation.com", "ps123", "0800-333333");
        PartsProvider ps2 = new PartsProvider("Aircraft Components Ltd", "components@aircraft.com", "ps456", "0800-444444");
        PartsProvider ps3 = new PartsProvider("AeroSupply GmbH", "supply@aero.de", "ps789", "0800-555555");
        
        partsProviderRepository.save(ps1);
        partsProviderRepository.save(ps2);
        partsProviderRepository.save(ps3);
        System.out.println("✅ 3 Parts Providers erstellt");
        
        // 4. Spare Parts erstellen
        SparePart part1 = new SparePart("Propeller Blade", "High-quality aluminum propeller blade", 1500, 10);
        part1.setPartsProvider(ps1);
        
        SparePart part2 = new SparePart("Landing Gear", "Heavy-duty landing gear system", 5000, 5);
        part2.setPartsProvider(ps1);
        
        SparePart part3 = new SparePart("Engine Filter", "Oil filter for aircraft engines", 150, 50);
        part3.setPartsProvider(ps2);
        
        SparePart part4 = new SparePart("Navigation Light", "LED navigation light", 200, 25);
        part4.setPartsProvider(ps2);
        
        SparePart part5 = new SparePart("Fuel Pump", "Electric fuel pump", 800, 15);
        part5.setPartsProvider(ps3);
        
        SparePart part6 = new SparePart("Brake Pad Set", "Carbon brake pads", 600, 30);
        part6.setPartsProvider(ps3);
        
        sparePartRepository.save(part1);
        sparePartRepository.save(part2);
        sparePartRepository.save(part3);
        sparePartRepository.save(part4);
        sparePartRepository.save(part5);
        sparePartRepository.save(part6);
        System.out.println("✅ 6 Spare Parts erstellt");
        
        // 5. Aircrafts erstellen
        Aircraft aircraft1 = new Aircraft("10m x 15m", "D-ABCD", null, owner1);
        aircraft1.setSize(5);
        aircraft1.setMaintenanceStatus("Good");
        aircraft1.setFlightReadiness("Ready");
        
        Aircraft aircraft2 = new Aircraft("12m x 18m", "D-EFGH", null, owner2);
        aircraft2.setSize(8);
        aircraft2.setMaintenanceStatus("Maintenance Required");
        aircraft2.setFlightReadiness("Not Ready");
        
        Aircraft aircraft3 = new Aircraft("11m x 16m", "D-IJKL", null, owner3);
        aircraft3.setSize(6);
        aircraft3.setMaintenanceStatus("Excellent");
        aircraft3.setFlightReadiness("Ready");
        
        aircraftRepository.save(aircraft1);
        aircraftRepository.save(aircraft2);
        aircraftRepository.save(aircraft3);
        System.out.println("✅ 3 Aircrafts erstellt");
        
        // 6. Services erstellen
        Service service1 = new Service("Basic Maintenance", "Standard maintenance check", 500);
        service1.setHangarProvider(hp1);
        
        Service service2 = new Service("Full Inspection", "Complete aircraft inspection", 2000);
        service2.setHangarProvider(hp1);
        
        Service service3 = new Service("Cleaning Service", "Interior and exterior cleaning", 300);
        service3.setHangarProvider(hp2);
        
        Service service4 = new Service("Engine Overhaul", "Complete engine overhaul", 15000);
        service4.setHangarProvider(hp2);
        
        serviceRepository.save(service1);
        serviceRepository.save(service2);
        serviceRepository.save(service3);
        serviceRepository.save(service4);
        System.out.println("✅ 4 Services erstellt");
        
        // 7. Parking Spots erstellen
        Parking parking1 = new Parking("AVAILABLE", 1, "Indoor");
        parking1.setHangarProvider(hp1);
        
        Parking parking2 = new Parking("AVAILABLE", 2, "Indoor");
        parking2.setHangarProvider(hp1);
        
        Parking parking3 = new Parking("OCCUPIED", 3, "Outdoor");
        parking3.setHangarProvider(hp2);
        
        Parking parking4 = new Parking("AVAILABLE", 4, "Outdoor");
        parking4.setHangarProvider(hp2);
        
        Parking parking5 = new Parking("RESERVED", 5, "Indoor");
        parking5.setHangarProvider(hp1);
        
        parkingRepository.save(parking1);
        parkingRepository.save(parking2);
        parkingRepository.save(parking3);
        parkingRepository.save(parking4);
        parkingRepository.save(parking5);
        System.out.println("✅ 5 Parking Spots erstellt");
        
        System.out.println("🎉 Test-Daten erfolgreich geladen!");
        System.out.println("📋 Übersicht:");
        System.out.println("   - Aircraft Owners: 3 (IDs: 1, 2, 3)");
        System.out.println("   - Hangar Providers: 2 (IDs: 4, 5)");
        System.out.println("   - Parts Providers: 3 (IDs: 6, 7, 8)");
        System.out.println("   - Spare Parts: 6");
        System.out.println("   - Aircrafts: 3");
        System.out.println("   - Services: 4");
        System.out.println("   - Parking Spots: 5");
    }
}
