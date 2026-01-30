# Benutzerhandbuch – Hangar Management System

Dieses Handbuch richtet sich an **Endnutzer** der Anwendung (Flugzeugbesitzer und Hangaranbieter). Es beschreibt, wie Sie die Oberfläche bedienen und die wichtigsten Funktionen nutzen.

---

## 1. Einstieg

### 1.1 Anwendung öffnen

- Öffnen Sie im Browser die Adresse der Anwendung (z. B. `http://localhost:8082`).
- Sie gelangen auf die **Startseite** (Dashboard). Ohne Anmeldung sehen Sie dort eine Übersicht und die Möglichkeit, sich anzumelden oder zu registrieren.

### 1.2 Registrierung

Es gibt zwei Nutzerrollen mit getrennten Registrierungswegen:

| Rolle | Seite | Beschreibung |
|-------|--------|--------------|
| **Flugzeugbesitzer (FB)** | **Registrieren** → „Flugzeugbesitzer“ | E-Mail, Name, Passwort und Kontaktdaten eingeben. Nach der Registrierung können Sie Stellplätze suchen, Serviceanfragen stellen, Angebote verwalten usw. |
| **Hangaranbieter (HA)** | **Registrieren** → „Hangaranbieter“ | E-Mail, Name, Passwort; zusätzlich Angaben zu Stadt, Servicezeiten und Lagerbedingungen. Nach der Registrierung können Sie Stellplätze, Services und Spezialisierungen verwalten. |

- Wählen Sie die passende Rolle und füllen Sie alle Pflichtfelder aus.
- Die E-Mail muss **eindeutig** sein (wird bereits von einem anderen Nutzer genutzt, erscheint eine Fehlermeldung).
- Nach erfolgreicher Registrierung werden Sie in der Regel eingeloggt und zum Dashboard weitergeleitet.

### 1.3 Anmeldung (Login)

- Klicken Sie auf **Anmelden** (bzw. nutzen Sie die Login-Seite).
- Geben Sie **E-Mail** und **Passwort** ein.
- Die Anwendung erkennt automatisch Ihre Rolle (Flugzeugbesitzer oder Hangaranbieter) und zeigt das passende **Dashboard** mit den jeweiligen Kacheln und Links.

### 1.4 Abmelden

- Über den Bereich **oben rechts** (nach dem Login) können Sie sich abmelden. Danach sind Sie wieder auf der Startseite ohne Zugriff auf rollenspezifische Funktionen.

---

## 2. Flugzeugbesitzer (FB)

Nach dem Login als Flugzeugbesitzer erscheint das **Dashboard** mit Kacheln zu den folgenden Bereichen. Jede Kachel führt zu einer eigenen Seite.

### 2.1 Profil

- **Seite:** Profil (bzw. über Dashboard „Profil“).
- Hier können Sie Ihre **Stammdaten** (Name, Kontakt, ggf. Passwort) einsehen und bearbeiten.

### 2.2 Meine Flugzeuge

- **Seite:** *Meine Flugzeuge* (`meine-flugzeuge.html`).
- **Funktion:** Flugzeuge anlegen, bearbeiten und anzeigen (z. B. Kennzeichen, Abmessungen, Wartungsstatus).
- Diese Liste wird z. B. auf der Seite **Standortdetails** genutzt: Beim Absenden einer **Serviceanfrage** (Button „Serviceanfrage senden“) müssen Sie ein Flugzeug auswählen – die Auswahl kommt aus „Meine Flugzeuge“. Ohne angelegte Flugzeuge können Sie dort keine Serviceanfrage stellen.

### 2.3 Stellplatz suchen

- **Seite:** *Stellplatz suchen* (`parking-search.html`).
- **Funktion:** Suche nach Stellplätzen, z. B. nach **Stadt**.
- Die Ergebnisliste zeigt passende Stellplätze mit Standortinformationen. Über einen Link gelangen Sie zu den **Standortdetails**.

### 2.4 Standortdetails

- **Seite:** Wird von der Stellplatzsuche aus aufgerufen (*Standortdetails* / `location-details.html`).
- **Funktion:** Anzeige von Adresse, Merkmalen, Services und Konditionen des gewählten Standorts.
- Von hier aus können Sie eine **Serviceanfrage** an den Hangaranbieter senden (Button „Serviceanfrage senden“). Nach dem Absenden erscheint eine Bestätigung (z. B. „Serviceanfrage wurde erfolgreich übermittelt.“).

### 2.5 Meine Angebote

- **Seite:** *Meine Angebote* (`meine-angebote.html`).
- **Funktion:** Alle **Angebote** zu Ihren Serviceanfragen einsehen. Sie können Angebote **annehmen** oder **ablehnen**. Der Status wird entsprechend aktualisiert.

### 2.6 Übergabe-/Rückgabetermine

- **Seite:** *Übergabe-Termine* bzw. Termine verwalten (`uebergabe-termine.html`).
- **Funktion:** Übergabe- und Rückgabetermine zwischen Ihnen und dem Hangaranbieter einsehen und verwalten.

### 2.7 Zusatzservices buchen

- **Seite:** *Zusatzservices* (`zusatzservices.html`).
- **Funktion:** Zusatzservices (z. B. Wartung, Tank-Service) buchen. Die Buchungen erscheinen unter „Meine Buchungen“.

### 2.8 Meine Buchungen

- **Seite:** *Meine Buchungen* (`meine-buchungen.html`).
- **Funktion:** Übersicht über Ihre Zusatzservice-Buchungen (Beschreibung, Zeitfenster, Status).

### 2.9 Ersatzteile

- **Seite:** *Ersatzteile* (`ersatzteile.html`).
- **Funktion:** Ersatzteile suchen und **Reservierungen** anlegen (Teileanbieter, Menge, Status).

---

## 3. Hangaranbieter (HA)

Nach dem Login als Hangaranbieter erscheint das **Dashboard** mit Kacheln für die Hangar-Verwaltung. Jede Kachel führt zu einer eigenen Seite.

### 3.1 Profil (Hangaranbieter)

- **Seite:** *Hangaranbieter-Profil* (`hp-profile.html`).
- **Funktion:** Stammdaten des Hangars pflegen (Name, Stadt, Servicezeiten, Lagerbedingungen, Kosten usw.).

### 3.2 Stellplätze

- **Seite:** *Stellplätze* (`stellplaetze.html`).
- **Funktion:** **Anzahl** und **Status** der Stellplätze verwalten.
  - **Positive Zahl** + Kategorie + Status: Stellplätze **hinzufügen**.
  - **Negative Zahl** + Kategorie + Status: Stellplätze **reduzieren** bzw. entfernen (es werden bestehende Stellplätze mit gleicher Kategorie/Status abgebaut).

### 3.3 Einlagerungs-Services

- **Seite:** *Einlagerungsservices* (`einlagerungsservices.html`).
- **Funktion:** **Services für die Einlagerung** anbieten – z. B. Beschreibung und Preis von Zusatzleistungen (Wartung, Tank-Service) anlegen und bearbeiten.

### 3.4 Spezialisierung

- **Seite:** *Spezialisierung* (`spezialisierung.html`).
- **Funktion:** **Flugzeugtypen** festlegen, die Ihr Hangar bedient (z. B. „Cessna 172“). Optional können Sie zugeordnete Flugzeuge („eingelagerte Flugzeuge“) auswählen und speichern.

### 3.5 Flugzeuge im Hangar

- **Seite:** *Flugzeuge im Hangar* (`flugzeuge-im-hangar.html`).
- **Funktion:** Liste aller bei Ihnen eingelagerten Flugzeuge. Pro Flugzeug können Sie **Wartungs-** und **Fahrbereitschaftsdaten** pflegen (über die angezeigten Formulare bzw. „Speichern“). Eine Funktion „Flugzeug aus Hangar entfernen“ ist in der aktuellen Version nicht vorgesehen.

### 3.6 Reparatur-/Wartungsanfrage

- **Seite:** *Reparatur-/Wartungsanfrage* (`reparatur-wartungsanfrage.html`).
- **Funktion:** Anfragen an **Werkstätten** (Maintenance-Provider) stellen. Die Anfragen erscheinen in der Wartungsanfragen-Verwaltung.

### 3.7 Wartungstermine

- **Seite:** *Wartungstermine* (`wartungstermine.html`).
- **Funktion:** Bestätigte Wartungsanfragen in **Termine** überführen und verwalten (u. a. Verknüpfung mit HangarProvider und MaintenanceProvider).

### 3.8 Terminanfragen

- **Seite:** *Terminanfragen* (`terminanfragen.html`).
- **Funktion:** **Übergabe- und Rückgabeterminanfragen** der Flugzeugbesitzer einsehen und bearbeiten.

---

## 4. Allgemeine Hinweise

### 4.1 Fehlermeldungen

- Bei ungültigen Eingaben (z. B. fehlende Pflichtfelder, doppelte E-Mail) erscheinen **Hinweise** oder **Fehlermeldungen** auf der Seite bzw. in einem Alert.
- Bei Netzwerkfehlern (z. B. Backend nicht erreichbar) kann eine Meldung wie „Fehler bei der Anfrage“ erscheinen – prüfen Sie die Verbindung und ob die Anwendung läuft.

### 4.2 Rollenwechsel

- Ein Wechsel zwischen Flugzeugbesitzer und Hangaranbieter ist **nicht** vorgesehen: Ein Konto gehört entweder zur Rolle FB oder HA. Für die andere Rolle ist eine **separate Registrierung** mit anderer E-Mail nötig.

### 4.3 Browser und Geräte

- Die Anwendung wird im **Browser** genutzt (empfohlene aktuelle Versionen von Chrome, Firefox, Edge oder Safari).
- Statische Oberfläche: Keine Installation einer App nötig; alle Aktionen laufen über die Webseiten.

---

## 5. Kurzübersicht: Seiten je Rolle

| Flugzeugbesitzer (FB) | Hangaranbieter (HA) |
|----------------------|---------------------|
| Login, Registrierung, Dashboard | Login, Registrierung, Dashboard |
| Profil | Hangaranbieter-Profil |
| Meine Flugzeuge | Stellplätze |
| Stellplatz suchen | Einlagerungsservices |
| Standortdetails (+ Serviceanfrage) | Spezialisierung |
| Meine Angebote | Flugzeuge im Hangar |
| Übergabe-Termine | Reparatur-/Wartungsanfrage |
| Zusatzservices, Meine Buchungen | Wartungstermine |
| Ersatzteile | Terminanfragen |

---

*Stand: Januar 2025 – Hangar Management System, Benutzerhandbuch für Endnutzer.*
