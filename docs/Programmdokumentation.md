# Programmdokumentation – Hangar Management System

Diese Dokumentation beschreibt die **Programmstruktur und die Logik** der Anwendung. Sie dient als Entwicklungsartefakt gemäß Prozessanforderungen (Programmdokumentation).

---

## 1. Übersicht und Zweck

Die Anwendung ist ein **Hangar-Management-System** in Java (Spring Boot) mit getrennten Rollen:

- **Flugzeugbesitzer (FB):** Stellplatz suchen, Serviceanfragen stellen, Angebote annehmen/ablehnen, Flugzeuge verwalten, Zusatzservices buchen, Ersatzteile reservieren.
- **Hangaranbieter (HA):** Stellplätze verwalten, eingelagerte Flugzeuge pflegen, Services anbieten, Spezialisierung (Flugzeugtypen), Reparatur-/Wartungsanfragen und Termine verwalten, Terminanfragen bearbeiten.

**Technischer Stack:** Java 21, Spring Boot, JPA/Hibernate, PostgreSQL, statisches Frontend (HTML/CSS/JavaScript).

---

## 2. Architektur und Schichten

Die Anwendung folgt einer **mehrschichtigen Architektur**:

```
  Frontend (HTML/CSS/JS)  →  REST-API (Controller)  →  Service / Katalog  →  Repository  →  Datenbank
```

| Schicht | Paket | Aufgabe |
|--------|--------|--------|
| **Model** | `de.thm.mni.model` | JPA-Entities (Datenmodell) |
| **Repository** | `de.thm.mni.repository` | Datenzugriff (Spring Data JPA) |
| **Service / Katalog** | `de.thm.mni.service` | Geschäftslogik, Use-Case-Abläufe |
| **Controller** | `de.thm.mni.controller` | REST-Endpunkte, Validierung |
| **DTO** | `de.thm.mni.dto` | Request/Response-Objekte (z. B. Login, Registrierung) |
| **Config** | `de.thm.mni.config` | DataLoader (Initialdaten) |

---

## 3. Modell (Entities) und Beziehungen

### 3.1 Benutzer und Rollen

- **`Benutzer`** (abstrakte Basis-Entity, `@Inheritance(JOINED)`):
  - Felder: `id`, `name`, `email`, `password`, `contact`, `role` (Enum: `AIRCRAFT_OWNER`, `HANGAR_PROVIDER`, …).
  - Unterklassen: **`AircraftOwner`** (FB), **`HangarProvider`** (HA). Weitere Rollen (z. B. `MaintenanceProvider`, `PartsProvider`) sind im Modell vorhanden.

- **`AircraftOwner`**: Erbt von `Benutzer`, hat eine Liste **`aircrafts`** (OneToMany zu `Aircraft`).

- **`HangarProvider`**: Erbt von `Benutzer`, zusätzlich u. a.:
  - `serviceHours`, `city`, `costs`
  - `storageConditions` (ElementCollection)
  - `aircraftTypes` (ElementCollection) – Spezialisierung (z. B. „Cessna 172“)
  - **`selectedAircrafts`** (ManyToMany zu `Aircraft`) – „Flugzeuge im Hangar“
  - **`parkings`** (OneToMany zu `Parking`)
  - **`services`** (OneToMany zu `Service`)
  - **`repairRequests`** (OneToMany zu `RepairRequest`)

### 3.2 Flugzeuge und Standorte

- **`Aircraft`**: Gehört einem `AircraftOwner` (ManyToOne). Felder u. a.: `registrationMark`, `dimensions`, `size`, `maintenanceStatus`, `flightReadiness`, `photo`.

- **`Parking`**: Gehört einem `HangarProvider` (ManyToOne), optional einer **`Location`** (ManyToOne). Felder: `status` (AVAILABLE, OCCUPIED, …), `number`, `siteStatus` (Kategorie, z. B. Indoor/Outdoor).

- **`Location`**: Standortbeschreibung mit `location` (Name/Anschrift), `features`, `services`, `conditions` (ElementCollections). Wird für die Anzeige „Standortdetails“ (UC FB.3) genutzt.

### 3.3 Services, Anfragen und Angebote

- **`Service`**: Gehört einem `HangarProvider`. Beschreibung und Preis eines Zusatzservices (z. B. Wartung, Tank-Service).

- **`ServiceRequest`**: Serviceanfrage (UC FB.4). Verknüpft `HangarProvider`, `Aircraft`, optional `MaintenanceProvider`. Felder u. a.: `duration`, `services`.

- **`Offer`**: Angebot zu einer Serviceanfrage (UC FB.5). Verknüpft mit `ServiceRequest`, enthält `price`, `status` (z. B. PENDING, ACCEPTED, REJECTED).

- **`ServiceBooking`**: Buchung eines Zusatzservices durch einen Flugzeugbesitzer. Verknüpft mit `AircraftOwner`, enthält Beschreibung, Zeitfenster, Preis, Status.

### 3.4 Termine und Ersatzteile

- **`HandoverReturnAppointment`**: Übergabe-/Rückgabetermin zwischen Flugzeugbesitzer und Hangaranbieter (FB.7 / HA).

- **`Appointment`**: Wartungstermin (HA.7), verknüpft mit `ServiceRequest`, `HangarProvider`, `MaintenanceProvider`.

- **`ArticleReservation`**: Ersatzteile-Reservierung (FB.10). Verknüpft `SparePart`, `AircraftOwner`, `PartsProvider`. Felder: `quantity`, `status`.

- **`SparePart`**: Ersatzteil, gehört einem `PartsProvider`. **`AircraftType`**: Flugzeugtyp (Name, Typ, Größe) für die Spezialisierung (HA.4).

### 3.5 Wichtige Beziehungen (Kernlogik)

- **Stellplatzsuche (FB.2):** `Parking` → `HangarProvider`, `Location`. Suche nach Stadt liefert Parkings mit zugehöriger Location.
- **Standortdetails (FB.3):** Über `parkingId` wird die zugehörige `Location` geladen (Anschrift, Merkmale, Services, Konditionen).
- **Serviceanfrage (FB.4):** FB erstellt `ServiceRequest` (Dauer, Leistungen, gewähltes Flugzeug, gewählter HA). HA sieht Anfragen; Angebote werden als `Offer` erstellt.
- **Flugzeuge im Hangar (HA.5):** Liste kommt aus `HangarProvider.getSelectedAircrafts()`. Wartungs-/Fahrbereitschaftsdaten werden in `MaintenanceStatus` und `FlightReadiness` (pro HP + Aircraft) geführt.
- **Stellplätze bearbeiten (HA.3):** `ParkingService.updateParking(hangarId, category, status, number)`: `number > 0` = Stellplätze hinzufügen, `number < 0` = Stellplätze entfernen (über `ParkingCatalog.add` bzw. `remove`).

---

## 4. Geschäftslogik (Services und Kataloge)

### 4.1 Authentifizierung und Registrierung

- **`AuthService`**
  - **Login:** E-Mail zuerst in `AircraftOwnerRepository`, dann in `HangarProviderRepository` suchen; Passwort vergleichen. Rückgabe: `AuthResponse` (id, name, email, role) – ohne Passwort.
  - **Registrierung FB:** E-Mail eindeutig prüfen, `AircraftOwner` anlegen und speichern.
  - **Registrierung HA:** E-Mail eindeutig prüfen, `HPService.saveProfile(...)` aufrufen (legt HangarProvider mit Stadt, Servicezeiten, Lagerbedingungen an).

### 4.2 Stellplätze (HA.3)

- **`ParkingService.updateParking(hangarId, category, status, number)`**
  - `number > 0`: Neues `Parking` mit Kategorie und Status anlegen, dem Hangar zuordnen, optional `Location` anhand der Hangar-Stadt setzen (`LocationCatalog.getDefaultLocationForCity(city)`). Dann `ParkingCatalog.add(parking, number)` – setzt die Anzahl und speichert.
  - `number < 0`: Bestehendes Parking desselben Hangars mit gleicher Kategorie und Status suchen; `ParkingCatalog.remove(parking, number)` – reduziert Anzahl oder löscht die Entity, falls Anzahl ≤ |number|.

- **`ParkingCatalog.add`:** Setzt `parking.number` und speichert. **`ParkingCatalog.remove`:** Reduziert `number` oder löscht das Parking, wenn `number <= Math.abs(removeNumber)`.

### 4.3 Standortdetails (FB.3)

- **`LocationService.getLocationByParkingId(parkingId)`:** Liefert die zum Parking gehörende `Location` (Anschrift, features, services, conditions). Wird vom Frontend für die Seite „Standortdetails“ verwendet. Fehlt die Location, zeigt die Anwendung „Details momentan nicht verfügbar“.

### 4.4 Serviceanfrage (FB.4) und Angebote (FB.5)

- **ServiceRequest:** Erstellung über `SRequestManagement` / `ServiceRequestController` (z. B. `save-aco`: Flugzeugbesitzer legt Anfrage mit Dauer, Leistungen, gewähltem Flugzeug und Hangaranbieter an).
- **Offer:** Angebote werden dem Flugzeugbesitzer zugeordnet. **`OfferService.getOffersByOwnerEmail(email)`** lädt alle Angebote des FB. **`OfferService.saveDecision(offerId, status)`** setzt Status (ACCEPTED/REJECTED) und speichert (UC FB.5 – Angebot annehmen/ablehnen).

### 4.5 Flugzeuge im Hangar (HA.5)

- **`HPService.getAircraftInHangarByEmail(email)`:** Liefert die Liste `HangarProvider.getSelectedAircrafts()` für den eingeloggten HA. Der Hangaranbieter kann Wartungs- und Fahrbereitschaftsdaten pro Flugzeug über **`StatusController`** (POST `/save-input-by-provider`) pflegen. Eine Zuordnung „Flugzeug aus Hangar entfernen“ ist im Use Case nicht vorgesehen und ist im Programm nicht implementiert.

### 4.6 Spezialisierung (HA.4)

- **Flugzeugtypen:** `SpecializationService.setTypes(hpId, aircraftTypeIds)` ersetzt die aktuelle Auswahl an Flugzeugtypen des HangarProviders (Strings aus `ACTypeCatalog`).
- **Auswahl eingelagerter Flugzeuge:** `SpecializationService.saveSelection(hpId, selectedAircraftIds)` fügt die gewählten Aircraft zur Menge `selectedAircrafts` hinzu (wird z. B. von API verwendet; Frontend „Spezialisierung“ arbeitet primär mit Typen).

### 4.7 Weitere Services (kurz)

- **`BookingService`:** Zusatzservice-Buchungen (FB.8).
- **`AppointmentService` / `AppointmentManager`:** Wartungstermine (HA.7).
- **`HandoverReturnAppointmentCatalog`:** Übergabe-/Rückgabetermine.
- **`ArticleReservationService` / `ArticleReservationCatalog`:** Ersatzteile-Reservierung (FB.10).
- **`SearchParking`:** Stellplatzsuche nach Stadt (FB.2).

---

## 5. Controller und API

Controller liegen in `de.thm.mni.controller`, Basis-URL der API: `/api`.

| Bereich | Controller | Wichtige Endpunkte (Auswahl) |
|--------|------------|------------------------------|
| Auth | `AuthController` | POST `/auth/login`, `/auth/register-owner`, `/auth/register-provider` |
| Flugzeuge | `AircraftController` | CRUD für Aircraft; Flugzeuge pro FB z. B. nach E-Mail |
| Stellplätze | `ParkingController` | GET by ID; POST `/update-by-provider` (HA.3: Anzahl/Kategorie/Status) |
| Standort | `LocationController` | GET `/by-parking/{parkingId}` (FB.3) |
| Suche | `SearchParkingController` | GET Suchparameter (z. B. Stadt) für Stellplatzsuche |
| Serviceanfrage | `ServiceRequestController` | POST `/save-aco` (FB.4: Anfrage anlegen) |
| Angebote | `OfferController` | GET nach FB-E-Mail; POST Entscheidung (annehmen/ablehnen) |
| Status (HA.5) | `StatusController` | GET `/aircraft-in-hangar`, `/current`; POST `/save-input-by-provider` |
| Spezialisierung | `SpecializationController` | GET `/aircraft-types-list`, `/current`; POST `/confirm-by-provider` |
| Services (HA) | `ServiceController` | Services pro HangarProvider; Anlegen/Bearbeiten |
| Buchungen | `BookingController` | Zusatzservice-Buchungen (FB) |
| Ersatzteile | `ArticleReservationController`, `PartsController` | Reservierung, Teileabfragen |

Die **Validierung** erfolgt in den Controller-Methoden (z. B. Pflichtfelder, E-Mail-Format) bzw. in den Services (fachliche Regeln). Fehler werden als HTTP 4xx mit optionalem JSON-Body (message, missingFields) zurückgegeben.

---

## 6. Frontend-Struktur

- **Ort:** `src/main/resources/static`
- **Gemeinsam:** `js/auth.js` (API_BASE, getCurrentUser, logout, showAuthAlert), `css/styles.css`, `css/auth.css`
- **Start:** `index.html` – Dashboard; je nach Rolle (FB/HA) werden unterschiedliche Karten und Links angezeigt.
- **Flugzeugbesitzer:** u. a. `login.html`, `register.html`, `profile.html`, `meine-flugzeuge.html`, `parking-search.html`, `location-details.html`, `meine-angebote.html`, `zusatzservices.html`, `meine-buchungen.html`, `ersatzteile.html`, `uebergabe-termine.html`
- **Hangaranbieter:** u. a. `hp-profile.html`, `stellplaetze.html`, `einlagerungsservices.html`, `spezialisierung.html`, `flugzeuge-im-hangar.html`, `reparatur-wartungsanfrage.html`, `wartungstermine.html`, `terminanfragen.html`

**Logik im Frontend:** Rollenprüfung nach Login (localStorage); Aufruf der REST-API per `fetch`; Anzeige von Listen und Formularen; Fehler- und Erfolgsmeldungen über `showAuthAlert` bzw. modale Hinweise (z. B. „Gesendet“ nach Serviceanfrage).

---

## 7. Konfiguration und Initialdaten

- **`application.properties`:** Datenbank (PostgreSQL, URL, Benutzer, Passwort), JPA (`ddl-auto=update`), Server-Port (8082).
- **`DataLoader`** (CommandLineRunner): Läuft beim Start; prüft, ob bereits Daten vorhanden sind (`aircraftOwnerRepository.count() > 0` bzw. hangar/provider/aircraft). Ist die Datenbank leer, werden u. a. angelegt:
  - AircraftOwner, HangarProvider, PartsProvider, MaintenanceProvider
  - Aircraft, Parking, Location, Service
  - ServiceRequest, Offer, HandoverReturnAppointment, ServiceBooking, ArticleReservation
  - AircraftType
  - Zuordnung Flugzeuge zu Hangar (selectedAircrafts), Spezialisierung (aircraftTypes), Standortzuordnung zu Parkings

Damit stehen sofort Testbenutzer (z. B. FB: max@example.com, HA: sky@hangar.de) und konsistente Beispieldaten zur Verfügung.

---

## 8. Wichtige Abläufe (Use-Case-Zuordnung)

| Use Case | Kurzbeschreibung der Programmlogik |
|----------|-------------------------------------|
| **FB.1** | Flugzeugbesitzer registrieren: AuthController POST `/auth/register-owner` → AuthService; E-Mail eindeutig prüfen, AircraftOwner anlegen und speichern. |
| **FB.2** | Stellplatzsuche: Suchparameter (Stadt) → SearchParking / ParkingController → Parkings mit Location-Info → Anzeige in Suchliste. |
| **FB.3** | Standortdetails: parkingId → LocationService.getLocationByParkingId → Location (features, services, conditions) → Anzeige; fehlt Location → „Details momentan nicht verfügbar“. |
| **FB.4** | Serviceanfrage: Formular (Flugzeug, Dauer, Leistungen) + hpId → ServiceRequestController (save-aco) → SRequestManagement → ServiceRequest gespeichert; HA sieht Anfragen in entsprechender Maske. |
| **FB.5** | Meine Angebote: Angebote nach FB-E-Mail laden → OfferController/OfferService; Entscheidung (Annehmen/Ablehnen) → saveDecision → Offer.status aktualisiert. |
| **FB.7** | Übergabe-/Rückgabetermine: HandoverReturnAppointment-Entities, Abruf/Bearbeitung über entsprechende Controller und Kataloge. |
| **FB.8** | Zusatzservices: Buchung über BookingService/BookingController; Anzeige in „Meine Buchungen“. |
| **FB.10** | Ersatzteile: ArticleReservation anlegen (PartsProvider, SparePart, AircraftOwner). |
| **HA.1** | Hangaranbieter registrieren: AuthController POST `/auth/register-provider` → AuthService; E-Mail eindeutig prüfen, HPService.saveProfile(...) → HangarProvider mit Stadt, Servicezeiten, Lagerbedingungen anlegen. |
| **HA.2** | Services für die Einlagerung anbieten: Service-Entity (OneToMany zu HangarProvider); ServiceController – Services pro HangarProvider anlegen/bearbeiten; Frontend einlagerungsservices.html. |
| **HA.3** | Stellplätze bearbeiten: Anzahl positiv/negativ + Kategorie + Status → ParkingController update-by-provider → ParkingService.updateParking → ParkingCatalog.add/remove. |
| **HA.4** | Spezialisierung: Flugzeugtypen (Strings) am HangarProvider setzen; optional Aircraft-Auswahl (saveSelection) über API. |
| **HA.5** | Flugzeuge im Hangar: Liste aus getSelectedAircrafts; Wartungs-/Fahrbereitschaft über StatusController (save-input-by-provider) → MaintenanceStatus/FlightReadiness. |
| **HA.6** | Reparatur-/Wartungsanfragen: ServiceRequest mit MaintenanceProvider; Anzeige/Bearbeitung in reparatur-wartungsanfrage.html / wartungstermine.html. |
| **HA.7** | Wartungstermine: Appointment-Entity, verknüpft mit ServiceRequest, HangarProvider, MaintenanceProvider. |

---

## 9. Hinweise für Entwickler

- **Transaktionen:** Geschäftslogik in Services mit `@Transactional` (Lesen/Schreiben konsistent).
- **Fehlerbehandlung:** Services werfen bei Verletzungen `RuntimeException` oder `IllegalArgumentException`; Controller fangen ab und liefern HTTP 400/404 mit optionalem JSON-Body.
- **Rollen:** Zugriff auf FB- bzw. HA-spezifische Endpunkte wird serverseitig über die Zuordnung der E-Mail zu AircraftOwner bzw. HangarProvider realisiert.
- **Frontend:** Kein separates Build; statische Dateien werden von Spring Boot unter `/` ausgeliefert; API unter `/api`.

---

*Stand: Januar 2025 – Hangar Management System, Programmdokumentation gemäß Prozessanforderungen.*
