package de.thm.mni.config;

import de.thm.mni.model.*;
import de.thm.mni.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Lädt Initial-Daten beim ersten Anwendungsstart (nur wenn Datenbank leer ist)
 * PostgreSQL-kompatibel
 */
@Component
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
    
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private HandoverReturnAppointmentRepository handoverReturnAppointmentRepository;

    @Autowired
    private ServiceBookingRepository serviceBookingRepository;

    @Autowired
    private AircraftTypeRepository aircraftTypeRepository;

    @Autowired
    private MaintenanceProviderRepository maintenanceProviderRepository;

    @Autowired
    private ArticleReservationRepository articleReservationRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Prüfe ob bereits Daten vorhanden sind
        // Prüfe mehrere Tabellen, um sicherzustellen, dass die Datenbank wirklich leer ist
        if (aircraftOwnerRepository.count() > 0 || 
            hangarProviderRepository.count() > 0 || 
            aircraftRepository.count() > 0) {
            System.out.println(" Datenbank enthält bereits Daten. Initial-Daten werden nicht geladen.");
            return;
        }
        
        System.out.println(" Lade Initial-Daten (PostgreSQL)...");
        
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

        Set<String> conditions3 = new HashSet<>();
        conditions3.add("Heated hangar");
        conditions3.add("24/7 Zugang");

        Set<String> conditions4 = new HashSet<>();
        conditions4.add("Standardlagerung");
        conditions4.add("Videoüberwachung");

        Set<String> conditions5 = new HashSet<>();
        conditions5.add("Premiumlagerung");
        conditions5.add("Enteisungsservice");

        Set<String> conditions6 = new HashSet<>();
        conditions6.add("Basic storage");
        conditions6.add("Outdoor-Plätze");
        
        HangarProvider hp1 = new HangarProvider("Sky Hangar GmbH", "sky@hangar.de", "hp123", 
                                                 "0800-111111", "Mo-Fr 8:00-18:00", "Berlin", conditions1);
        HangarProvider hp2 = new HangarProvider("AeroStorage AG", "aero@storage.de", "hp456", 
                                                 "0800-222222", "Mo-So 6:00-22:00", "München", conditions2);
        HangarProvider hp3 = new HangarProvider("Hamburg Hangar GmbH", "hamburg@hangar.de", "hp789",
                                                 "0800-333666", "Mo-Fr 7:00-19:00", "Hamburg", conditions3);
        HangarProvider hp4 = new HangarProvider("Main Airport Storage", "fra@hangar.de", "hp987",
                                                 "0800-444777", "Mo-So 6:00-22:00", "Frankfurt", conditions4);
        HangarProvider hp5 = new HangarProvider("Cologne Airpark", "cgn@hangar.de", "hp654",
                                                 "0800-555888", "Mo-Fr 9:00-18:00", "Köln", conditions5);
        HangarProvider hp6 = new HangarProvider("Stuttgart Hangar Services", "str@hangar.de", "hp321",
                                                 "0800-666999", "Mo-Sa 8:00-20:00", "Stuttgart", conditions6);
        
        hangarProviderRepository.save(hp1);
        hangarProviderRepository.save(hp2);
        hangarProviderRepository.save(hp3);
        hangarProviderRepository.save(hp4);
        hangarProviderRepository.save(hp5);
        hangarProviderRepository.save(hp6);
        System.out.println("✅ 6 Hangar Providers erstellt");
        
        // 3. Parts Providers erstellen
        PartsProvider ps1 = new PartsProvider("Aviation Parts Inc", "parts@aviation.com", "ps123", "0800-333333");
        PartsProvider ps2 = new PartsProvider("Aircraft Components Ltd", "components@aircraft.com", "ps456", "0800-444444");
        PartsProvider ps3 = new PartsProvider("AeroSupply GmbH", "supply@aero.de", "ps789", "0800-555555");
        
        partsProviderRepository.save(ps1);
        partsProviderRepository.save(ps2);
        partsProviderRepository.save(ps3);
        System.out.println("✅ 3 Parts Providers erstellt");

        // 4. Maintenance Provider (Werkstätten) erstellen 
        MaintenanceProvider mp1 = new MaintenanceProvider("AeroRepair GmbH", "repair@aero.de", "mp123", "0800-777777", "Cessna");
        MaintenanceProvider mp2 = new MaintenanceProvider("SkyMaintenance AG", "maintenance@sky.de", "mp456", "0800-888888", "Boeing");
        maintenanceProviderRepository.save(mp1);
        maintenanceProviderRepository.save(mp2);
        System.out.println("✅ 2 Maintenance Provider erstellt ");
        
        // 5. Spare Parts erstellen
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
        
        // 6. Aircrafts erstellen
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

        Aircraft aircraft4 = new Aircraft("9m x 14m", "D-MAX1", null, owner1);
        aircraft4.setSize(4);
        aircraft4.setMaintenanceStatus("Excellent");
        aircraft4.setFlightReadiness("Ready");
        
        Aircraft aircraft5 = new Aircraft("13m x 19m", "D-MAX2", null, owner1);
        aircraft5.setSize(9);
        aircraft5.setMaintenanceStatus("Good");
        aircraft5.setFlightReadiness("Ready");
        
        aircraftRepository.save(aircraft1);
        aircraftRepository.save(aircraft2);
        aircraftRepository.save(aircraft3);
        aircraftRepository.save(aircraft4);
        aircraftRepository.save(aircraft5);
        System.out.println("✅ 5 Aircrafts erstellt (3 für Max Mustermann)");

        // eingelagerte Flugzeuge – dem Hangaranbieterprofil zuordnen
        hp1.getSelectedAircrafts().add(aircraft1);
        hp1.getSelectedAircrafts().add(aircraft2);
        hp1.getSelectedAircrafts().add(aircraft4);
        hp1.getSelectedAircrafts().add(aircraft5);
        hp2.getSelectedAircrafts().add(aircraft3);
        hangarProviderRepository.save(hp2);
        // hp1 wird später zusammen mit aircraft types gespeichert (siehe unten)
        System.out.println("✅ Flugzeuge den Hangaranbietern zugeordnet (Sky Hangar: 4 Flugzeuge)");
        
        // 6. Services erstellen
        Service service1 = new Service("Basic Maintenance", "Standard maintenance check", 500);
        service1.setHangarProvider(hp1);
        
        Service service2 = new Service("Full Inspection", "Complete aircraft inspection", 2000);
        service2.setHangarProvider(hp1);
        
        Service service3 = new Service("Cleaning Service", "Interior and exterior cleaning", 300);
        service3.setHangarProvider(hp2);
        
        Service service4 = new Service("Engine Overhaul", "Complete engine overhaul", 15000);
        service4.setHangarProvider(hp2);

        Service service5 = new Service("Premium Wartung", "Umfassende Wartung mit Inspektion", 3500);
        service5.setHangarProvider(hp1);
        
        Service service6 = new Service("Tank-Service", "Betankung und Treibstoffprüfung", 250);
        service6.setHangarProvider(hp1);
        
        Service service7 = new Service("Lackierung", "Professionelle Flugzeuglackierung", 8000);
        service7.setHangarProvider(hp1);
        
        serviceRepository.save(service1);
        serviceRepository.save(service2);
        serviceRepository.save(service3);
        serviceRepository.save(service4);
        serviceRepository.save(service5);
        serviceRepository.save(service6);
        serviceRepository.save(service7);
        System.out.println("✅ 7 Services erstellt (Sky Hangar: 5 Services)");
        
        // 7. Locations erstellen (UC FB.3)
        Set<String> location1Features = new HashSet<>();
        location1Features.add("Wachschutz");
        location1Features.add("Echtzeitüberwachung");
        location1Features.add("Wetterschutz");
        location1Features.add("Flugfeld vorhanden");
        
        Set<String> location1Services = new HashSet<>();
        location1Services.add("Reinigung");
        location1Services.add("Tanken");
        location1Services.add("Einlagern");
        
        Set<String> location1Conditions = new HashSet<>();
        location1Conditions.add("Klimatisiert");
        location1Conditions.add("24/7 Sicherheit");
        
        Location location1 = new Location(
                "Berlin Flughafen, Terminal 2, Hangar A", 
                location1Features, 
                location1Services, 
                location1Conditions
        );
        location1 = locationRepository.save(location1);
        
        Set<String> location2Features = new HashSet<>();
        location2Features.add("Wachschutz");
        location2Features.add("Wetterschutz");
        
        Set<String> location2Services = new HashSet<>();
        location2Services.add("Reinigung");
        location2Services.add("Einlagern");
        
        Set<String> location2Conditions = new HashSet<>();
        location2Conditions.add("Basic storage");
        location2Conditions.add("Fire protection");
        
        Location location2 = new Location(
                "München Flughafen, Hangar B", 
                location2Features, 
                location2Services, 
                location2Conditions
        );
        location2 = locationRepository.save(location2);

        Set<String> location3Features = new HashSet<>();
        location3Features.add("Direkter Zugang zum Vorfeld");
        location3Features.add("Enteisungsfläche");

        Set<String> location3Services = new HashSet<>();
        location3Services.add("Reinigung");
        location3Services.add("Tanken");
        location3Services.add("Kurzzeit-Einlagerung");

        Set<String> location3Conditions = new HashSet<>();
        location3Conditions.add("Beheizter Hangar");
        location3Conditions.add("Videoüberwachung");

        Location location3 = new Location(
                "Hamburg Flughafen, Hangar C",
                location3Features,
                location3Services,
                location3Conditions
        );
        location3 = locationRepository.save(location3);

        Set<String> location4Features = new HashSet<>();
        location4Features.add("Großraumhangar");
        location4Features.add("Werkstatt vor Ort");

        Set<String> location4Services = new HashSet<>();
        location4Services.add("Heavy Maintenance");
        location4Services.add("Langzeit-Einlagerung");

        Set<String> location4Conditions = new HashSet<>();
        location4Conditions.add("Feuerlöschanlage");
        location4Conditions.add("24/7 Zugang");

        Location location4 = new Location(
                "Frankfurt Flughafen, Hangar D",
                location4Features,
                location4Services,
                location4Conditions
        );
        location4 = locationRepository.save(location4);

        Set<String> location5Features = new HashSet<>();
        location5Features.add("Separate GA-Zone");
        location5Features.add("Innen- und Außenstellplätze");

        Set<String> location5Services = new HashSet<>();
        location5Services.add("Reinigung");
        location5Services.add("Einlagerung");

        Set<String> location5Conditions = new HashSet<>();
        location5Conditions.add("Premiumlagerung");
        location5Conditions.add("Enteisungsservice");

        Location location5 = new Location(
                "Köln/Bonn Flughafen, Hangar E",
                location5Features,
                location5Services,
                location5Conditions
        );
        location5 = locationRepository.save(location5);

        Set<String> location6Features = new HashSet<>();
        location6Features.add("Alpen-Nähe");
        location6Features.add("Outdoor-Stellplätze");

        Set<String> location6Services = new HashSet<>();
        location6Services.add("Kurzzeit- und Langzeit-Einlagerung");

        Set<String> location6Conditions = new HashSet<>();
        location6Conditions.add("Basic storage");
        location6Conditions.add("Zaun + Zugangskontrolle");

        Location location6 = new Location(
                "Stuttgart Flughafen, Hangar F",
                location6Features,
                location6Services,
                location6Conditions
        );
        location6 = locationRepository.save(location6);
        
        System.out.println("✅ 6 Locations erstellt");
        
        // 8. Parking Spots erstellen
        Parking parking1 = new Parking("AVAILABLE", 1, "Indoor");
        parking1.setHangarProvider(hp1);
        parking1.setLocation(location1);
        
        Parking parking2 = new Parking("AVAILABLE", 2, "Indoor");
        parking2.setHangarProvider(hp1);
        parking2.setLocation(location1);
        
        Parking parking3 = new Parking("OCCUPIED", 3, "Outdoor");
        parking3.setHangarProvider(hp2);
        parking3.setLocation(location2);
        
        Parking parking4 = new Parking("AVAILABLE", 4, "Outdoor");
        parking4.setHangarProvider(hp2);
        parking4.setLocation(location2);
        
        Parking parking5 = new Parking("RESERVED", 5, "Indoor");
        parking5.setHangarProvider(hp1);
        parking5.setLocation(location1);

        Parking parking6 = new Parking("AVAILABLE", 6, "Indoor");
        parking6.setHangarProvider(hp3);
        parking6.setLocation(location3);

        Parking parking7 = new Parking("AVAILABLE", 7, "Indoor");
        parking7.setHangarProvider(hp4);
        parking7.setLocation(location4);

        Parking parking8 = new Parking("AVAILABLE", 8, "Indoor");
        parking8.setHangarProvider(hp5);
        parking8.setLocation(location5);

        Parking parking9 = new Parking("AVAILABLE", 9, "Outdoor");
        parking9.setHangarProvider(hp6);
        parking9.setLocation(location6);
        
        parkingRepository.save(parking1);
        parkingRepository.save(parking2);
        parkingRepository.save(parking3);
        parkingRepository.save(parking4);
        parkingRepository.save(parking5);
        parkingRepository.save(parking6);
        parkingRepository.save(parking7);
        parkingRepository.save(parking8);
        parkingRepository.save(parking9);
        System.out.println("✅ 9 Parking Spots erstellt");
        
        // 9. ServiceRequests erstellen (UC FB.4 – Serviceanfrage stellen)
        ServiceRequest sr1 = new ServiceRequest("1 Woche", "Wartung, Inspektion", hp1, aircraft1);
        ServiceRequest sr2 = new ServiceRequest("2 Wochen", "Reinigung, Tanken", hp2, aircraft2);
        ServiceRequest sr3 = new ServiceRequest("3 Tage", "Kurzzeit-Einlagerung", hp1, aircraft3);
        
        ServiceRequest sr4 = new ServiceRequest("Triebwerksinspektion, Fahrwerksreparatur", "Berlin Flughafen, Hangar A", aircraft1, hp1, mp1);
        ServiceRequest sr5 = new ServiceRequest("Avionik-Check, Hydraulikprüfung", "Berlin Flughafen, Hangar A", aircraft4, hp1, mp2);
        
        serviceRequestRepository.save(sr1);
        serviceRequestRepository.save(sr2);
        serviceRequestRepository.save(sr3);
        serviceRequestRepository.save(sr4);
        serviceRequestRepository.save(sr5);
        System.out.println("✅ 5 ServiceRequests erstellt (Sky Hangar: 2 HA.6-Anfragen mit MP)");
        
        // 10. Offers erstellen (UC FB.5 – Angebot annehmen/ablehnen, „Meine Angebote“)
        Offer o1 = new Offer(sr1, 850.0, Offer.STATUS_PENDING);
        Offer o2 = new Offer(sr2, 420.0, Offer.STATUS_PENDING);
        Offer o3 = new Offer(sr3, 180.0, Offer.STATUS_PENDING);
        
        offerRepository.save(o1);
        offerRepository.save(o2);
        offerRepository.save(o3);
        System.out.println("✅ 3 Offers erstellt (UC FB.5)");
        
        // 11. Übergabe-/Rückgabetermine (StR.E.7)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 5);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        HandoverReturnAppointment h1 = new HandoverReturnAppointment(cal.getTime(), "CONFIRMED", HandoverReturnAppointment.TYPE_HANDOVER, hp1, owner1);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        cal.set(Calendar.HOUR_OF_DAY, 14);
        HandoverReturnAppointment h2 = new HandoverReturnAppointment(cal.getTime(), "CONFIRMED", HandoverReturnAppointment.TYPE_RETURN, hp1, owner1);
        handoverReturnAppointmentRepository.save(h1);
        handoverReturnAppointmentRepository.save(h2);
        System.out.println("✅ 2 Übergabe-/Rückgabetermine erstellt ");
        
        // 12. Zusatzservice-Buchungen (StR.E.8)
        ServiceBooking sb1 = new ServiceBooking("Zusatzservice", "Mo–Fr 9–17", "BOOKED", 500.0, owner1, "Basic Maintenance");
        ServiceBooking sb2 = new ServiceBooking("Zusatzservice", "nächste Woche", "BOOKED", 2300.0, owner2, "Full Inspection, Cleaning Service");
        
        ServiceBooking sb3 = new ServiceBooking("Zusatzservice", "Februar 2025", "BOOKED", 3500.0, owner1, "Premium Wartung");
        ServiceBooking sb4 = new ServiceBooking("Zusatzservice", "März 2025", "PENDING", 250.0, owner1, "Tank-Service");
        
        serviceBookingRepository.save(sb1);
        serviceBookingRepository.save(sb2);
        serviceBookingRepository.save(sb3);
        serviceBookingRepository.save(sb4);
        System.out.println("✅ 4 Zusatzservice-Buchungen erstellt (Max Mustermann: 3 Buchungen)");
        
        // 13. Flugzeugtypen (StR.L.4 – Spezialisierung)
        AircraftType at1 = new AircraftType("Cessna 172", "Propeller", "Klein");
        AircraftType at2 = new AircraftType("Beechcraft Bonanza", "Propeller", "Klein");
        AircraftType at3 = new AircraftType("Pilatus PC-12", "Propeller", "Mittel");
        AircraftType at4 = new AircraftType("Boeing 737", "Düsen", "Groß");
        AircraftType at5 = new AircraftType("Airbus A320", "Düsen", "Groß");
        aircraftTypeRepository.save(at1);
        aircraftTypeRepository.save(at2);
        aircraftTypeRepository.save(at3);
        aircraftTypeRepository.save(at4);
        aircraftTypeRepository.save(at5);
        System.out.println("✅ 5 Flugzeugtypen erstellt ");

        // Sky Hangar – Spezialisierung (HA.4)
        // hp1 wurde bereits mit Aircraft versehen (siehe oben), jetzt nur noch aircraft types hinzufügen
        hp1.getAircraftTypes().add("Cessna 172");
        hp1.getAircraftTypes().add("Beechcraft Bonanza");
        hp1.getAircraftTypes().add("Pilatus PC-12");
        // Alle Änderungen (Aircraft + Aircraft Types) auf einmal speichern
        hangarProviderRepository.save(hp1);
        System.out.println("✅ Sky Hangar Spezialisierung gesetzt (3 Flugzeugtypen) und Aircraft'ler gespeichert");

        Calendar appCal = Calendar.getInstance();
        appCal.add(Calendar.DAY_OF_MONTH, 10);
        appCal.set(Calendar.HOUR_OF_DAY, 10);
        appCal.set(Calendar.MINUTE, 0);
        appCal.set(Calendar.SECOND, 0);
        appCal.set(Calendar.MILLISECOND, 0);
        Appointment app1 = new Appointment(appCal.getTime(), "CONFIRMED", sr4, hp1, mp1);
        appointmentRepository.save(app1);
        System.out.println("✅ 1 Appointment erstellt (Sky Hangar – HA.7)");

        // Max Mustermann – Ersatzteile-Reservierungen (FB.10)
        ArticleReservation ar1 = new ArticleReservation(part1, 2, "RESERVED", owner1, ps1);
        ArticleReservation ar2 = new ArticleReservation(part3, 5, "RESERVED", owner1, ps2);
        articleReservationRepository.save(ar1);
        articleReservationRepository.save(ar2);
        System.out.println("✅ 2 Ersatzteile-Reservierungen erstellt (Max Mustermann)");
        
        System.out.println(" Test-Daten erfolgreich geladen!");
        System.out.println(" Übersicht:");
        System.out.println("   - Aircraft Owners: 3");
        System.out.println("   - Hangar Providers: 6");
        System.out.println("   - Parts Providers: 3");
        System.out.println("   - Spare Parts: 6");
        System.out.println("   - Aircrafts: 5");
        System.out.println("   - Services: 7");
        System.out.println("   - Locations: 6");
        System.out.println("   - Parking Spots: 9");
        System.out.println("   - ServiceRequests: 5");
        System.out.println("   - Offers: 3");
        System.out.println("   - Handover/Return-Termine: 2");
        System.out.println("   - Zusatzservice-Buchungen: 4");
        System.out.println("   - Flugzeugtypen: 5");
        System.out.println("   - Appointments: 1");
        System.out.println("   - Ersatzteile-Reservierungen: 2");
    }
}
