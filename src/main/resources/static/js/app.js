const API_BASE = "http://localhost:8082/api";

// Tab Management
function showTab(tabName) {
  document
    .querySelectorAll(".tab-content")
    .forEach((el) => el.classList.add("hidden"));
  document
    .querySelectorAll(".tab")
    .forEach((el) => el.classList.remove("active"));

  document.getElementById(tabName).classList.remove("hidden");
  event.target.classList.add("active");

  if (tabName === "aircraft-owners") loadAircraftOwners();
  if (tabName === "hangar-providers") loadHangarProviders();
  if (tabName === "aircrafts") loadAircrafts();
  if (tabName === "appointments") loadAppointments();
  if (tabName === "parking") loadParking();
  if (tabName === "spare-parts") loadSpareParts();
  if (tabName === "services") loadServices();
  if (tabName === "reservations") loadReservations();
}

// Alert Management
function showAlert(elementId, message, type) {
  const alertDiv = document.getElementById(elementId);
  alertDiv.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
  setTimeout(() => (alertDiv.innerHTML = ""), 3000);
}

// Aircraft Owners Management
async function createAircraftOwner(event) {
  event.preventDefault();

  const owner = {
    name: document.getElementById("ownerName").value,
    email: document.getElementById("ownerEmail").value,
    password: document.getElementById("ownerPassword").value,
    contact: document.getElementById("ownerContact").value,
    role: "AIRCRAFT_OWNER",
  };

  try {
    const response = await fetch(`${API_BASE}/aircraft-owners`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(owner),
    });

    if (response.ok) {
      showAlert(
        "owner-alert",
        "Aircraft Owner created successfully!",
        "success",
      );
      document.getElementById("ownerForm").reset();
      loadAircraftOwners();
    }
  } catch (error) {
    showAlert("owner-alert", "Error creating aircraft owner", "error");
  }
}

async function loadAircraftOwners() {
  try {
    const response = await fetch(`${API_BASE}/aircraft-owners`);
    const owners = await response.json();

    const tbody = document.getElementById("ownersTableBody");
    tbody.innerHTML = owners
      .map(
        (owner) => `
          <tr>
              <td>${owner.id}</td>
              <td>${owner.name}</td>
              <td>${owner.email}</td>
              <td>${owner.contact || "-"}</td>
              <td>
                  <button class="btn-delete" onclick="deleteAircraftOwner(${
                    owner.id
                  })">Delete</button>
              </td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading owners:", error);
  }
}

async function deleteAircraftOwner(id) {
  if (!confirm("Are you sure?")) return;

  try {
    await fetch(`${API_BASE}/aircraft-owners/${id}`, {
      method: "DELETE",
    });
    showAlert("owner-alert", "Aircraft Owner deleted successfully!", "success");
    loadAircraftOwners();
  } catch (error) {
    showAlert("owner-alert", "Error deleting aircraft owner", "error");
  }
}

// Hangar Providers Management
async function createHangarProvider(event) {
  event.preventDefault();

  const provider = {
    name: document.getElementById("providerName").value,
    email: document.getElementById("providerEmail").value,
    password: document.getElementById("providerPassword").value,
    contact: document.getElementById("providerContact").value,
    serviceHours: document.getElementById("providerServiceHours").value,
    role: "HANGAR_PROVIDER",
  };

  try {
    const response = await fetch(`${API_BASE}/hangar-providers`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(provider),
    });

    if (response.ok) {
      showAlert(
        "provider-alert",
        "Hangar Provider created successfully!",
        "success",
      );
      document.getElementById("providerForm").reset();
      loadHangarProviders();
    }
  } catch (error) {
    showAlert("provider-alert", "Error creating hangar provider", "error");
  }
}

async function loadHangarProviders() {
  try {
    const response = await fetch(`${API_BASE}/hangar-providers`);
    const providers = await response.json();

    const tbody = document.getElementById("providersTableBody");
    tbody.innerHTML = providers
      .map(
        (provider) => `
          <tr>
              <td>${provider.id}</td>
              <td>${provider.name}</td>
              <td>${provider.email}</td>
              <td>${provider.contact || "-"}</td>
              <td>${provider.serviceHours || "-"}</td>
              <td>
                  <button class="btn-delete" onclick="deleteHangarProvider(${
                    provider.id
                  })">Delete</button>
              </td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading providers:", error);
  }
}

async function deleteHangarProvider(id) {
  if (!confirm("Are you sure?")) return;

  try {
    await fetch(`${API_BASE}/hangar-providers/${id}`, {
      method: "DELETE",
    });
    showAlert(
      "provider-alert",
      "Hangar Provider deleted successfully!",
      "success",
    );
    loadHangarProviders();
  } catch (error) {
    showAlert("provider-alert", "Error deleting hangar provider", "error");
  }
}

// Aircrafts Management
async function createAircraft(event) {
  event.preventDefault();

  const aircraft = {
    registrationMark: document.getElementById("aircraftRegistrationMark").value,
    size: parseInt(document.getElementById("aircraftSize").value) || null,
    dimensions: document.getElementById("aircraftDimensions").value,
    maintenanceStatus: document.getElementById("aircraftMaintenanceStatus")
      .value,
    flightReadiness: document.getElementById("aircraftFlightReadiness").value,
  };

  try {
    const response = await fetch(`${API_BASE}/aircrafts`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(aircraft),
    });

    if (response.ok) {
      showAlert("aircraft-alert", "Aircraft created successfully!", "success");
      document.getElementById("aircraftForm").reset();
      loadAircrafts();
    }
  } catch (error) {
    showAlert("aircraft-alert", "Error creating aircraft", "error");
  }
}

async function loadAircrafts() {
  try {
    const response = await fetch(`${API_BASE}/aircrafts`);
    const aircrafts = await response.json();

    const tbody = document.getElementById("aircraftsTableBody");
    tbody.innerHTML = aircrafts
      .map(
        (aircraft) => `
          <tr>
              <td>${aircraft.id}</td>
              <td>${aircraft.registrationMark}</td>
              <td>${aircraft.size || "-"}</td>
              <td>${aircraft.dimensions || "-"}</td>
              <td>${aircraft.maintenanceStatus || "-"}</td>
              <td>${aircraft.flightReadiness || "-"}</td>
              <td>
                  <button class="btn-delete" onclick="deleteAircraft(${
                    aircraft.id
                  })">Delete</button>
              </td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading aircrafts:", error);
  }
}

async function deleteAircraft(id) {
  if (!confirm("Are you sure?")) return;

  try {
    await fetch(`${API_BASE}/aircrafts/${id}`, { method: "DELETE" });
    showAlert("aircraft-alert", "Aircraft deleted successfully!", "success");
    loadAircrafts();
  } catch (error) {
    showAlert("aircraft-alert", "Error deleting aircraft", "error");
  }
}

// Initialize on page load
document.addEventListener("DOMContentLoaded", function () {
  loadAircraftOwners();
});

// ==================== APPOINTMENTS MANAGEMENT ====================
async function createAppointment(event) {
  event.preventDefault();

  const dateInput = document.getElementById("apptDate").value;
  const timeInput = document.getElementById("apptTime").value;
  const dateTime = new Date(`${dateInput}T${timeInput}`);

  const appointment = {
    hangarProviderId: parseInt(
      document.getElementById("apptHangarProviderId").value,
    ),
    date: dateTime.getTime(),
    time: dateTime.getTime(),
    type: document.getElementById("apptType").value,
  };

  try {
    const response = await fetch(`${API_BASE}/appointments/make`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(appointment),
    });

    if (response.ok) {
      showAlert(
        "appointment-alert",
        "Appointment created successfully!",
        "success",
      );
      document.getElementById("appointmentForm").reset();
      loadAppointments();
    } else {
      showAlert("appointment-alert", "Error creating appointment", "error");
    }
  } catch (error) {
    showAlert("appointment-alert", "Error creating appointment", "error");
  }
}

async function loadAppointments() {
  try {
    // Tüm provider'ların appointment'larını yüklemek için örnek bir provider ID kullanıyoruz
    // Gerçek uygulamada tüm appointment'lar için ayrı bir endpoint gerekebilir
    const tbody = document.getElementById("appointmentsTableBody");
    tbody.innerHTML =
      '<tr><td colspan="5" style="text-align: center;">Select a provider to view appointments</td></tr>';
  } catch (error) {
    console.error("Error loading appointments:", error);
  }
}

// ==================== PARKING MANAGEMENT ====================
async function createParking(event) {
  event.preventDefault();

  const parking = {
    number: parseInt(document.getElementById("parkingNumber").value),
    status: document.getElementById("parkingStatus").value,
    siteStatus: document.getElementById("parkingSiteStatus").value,
  };

  try {
    const response = await fetch(`${API_BASE}/parkings`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(parking),
    });

    if (response.ok) {
      showAlert(
        "parking-alert",
        "Parking spot created successfully!",
        "success",
      );
      document.getElementById("parkingForm").reset();
      loadParking();
    } else {
      showAlert("parking-alert", "Error creating parking spot", "error");
    }
  } catch (error) {
    showAlert("parking-alert", "Error creating parking spot", "error");
  }
}

async function loadParking() {
  try {
    const response = await fetch(`${API_BASE}/parkings`);
    const parkings = await response.json();

    const tbody = document.getElementById("parkingTableBody");
    tbody.innerHTML = parkings
      .map(
        (parking) => `
          <tr>
              <td>${parking.id}</td>
              <td>${parking.number}</td>
              <td><span class="badge badge-${getStatusBadge(parking.status)}">${parking.status}</span></td>
              <td>${parking.siteStatus || "-"}</td>
              <td>
                  <button class="btn-delete" onclick="deleteParking(${parking.id})">Delete</button>
              </td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading parking:", error);
    document.getElementById("parkingTableBody").innerHTML =
      '<tr><td colspan="5" style="text-align: center;">No parking spots found</td></tr>';
  }
}

async function deleteParking(id) {
  if (!confirm("Are you sure?")) return;

  try {
    await fetch(`${API_BASE}/parkings/${id}`, { method: "DELETE" });
    showAlert("parking-alert", "Parking spot deleted successfully!", "success");
    loadParking();
  } catch (error) {
    showAlert("parking-alert", "Error deleting parking spot", "error");
  }
}

// ==================== SPARE PARTS MANAGEMENT ====================
async function loadSpareParts() {
  try {
    const response = await fetch(`${API_BASE}/parts`);
    const parts = await response.json();

    const tbody = document.getElementById("partsTableBody");
    tbody.innerHTML = parts
      .map(
        (part) => `
          <tr>
              <td>${part.id}</td>
              <td>${part.name}</td>
              <td>${part.description || "-"}</td>
              <td>$${part.price || 0}</td>
              <td>${part.quantity || 0}</td>
              <td><span class="badge badge-${part.availability ? "success" : "danger"}">${part.availability ? "Yes" : "No"}</span></td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading spare parts:", error);
    document.getElementById("partsTableBody").innerHTML =
      '<tr><td colspan="6" style="text-align: center;">No spare parts found</td></tr>';
  }
}

async function searchSpareParts() {
  const criteria = document.getElementById("partsSearch").value;

  if (!criteria || criteria.length < 2) {
    loadSpareParts();
    return;
  }

  try {
    const response = await fetch(
      `${API_BASE}/parts/search?criteria=${encodeURIComponent(criteria)}`,
    );
    const parts = await response.json();

    const tbody = document.getElementById("partsTableBody");
    if (parts.length === 0) {
      tbody.innerHTML =
        '<tr><td colspan="6" style="text-align: center;">No parts found</td></tr>';
      return;
    }

    tbody.innerHTML = parts
      .map(
        (part) => `
          <tr>
              <td>${part.id}</td>
              <td>${part.name}</td>
              <td>${part.description || "-"}</td>
              <td>$${part.price || 0}</td>
              <td>${part.quantity || 0}</td>
              <td><span class="badge badge-${part.availability ? "success" : "danger"}">${part.availability ? "Yes" : "No"}</span></td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error searching spare parts:", error);
  }
}

// ==================== SERVICES MANAGEMENT ====================
async function createService(event) {
  event.preventDefault();

  const service = {
    name: document.getElementById("serviceName").value,
    description: document.getElementById("serviceDescription").value,
    price: parseInt(document.getElementById("servicePrice").value),
  };

  try {
    const response = await fetch(`${API_BASE}/services`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(service),
    });

    if (response.ok) {
      showAlert("service-alert", "Service created successfully!", "success");
      document.getElementById("serviceForm").reset();
      loadServices();
    } else {
      showAlert("service-alert", "Error creating service", "error");
    }
  } catch (error) {
    showAlert("service-alert", "Error creating service", "error");
  }
}

async function loadServices() {
  try {
    const response = await fetch(`${API_BASE}/services`);
    const services = await response.json();

    const tbody = document.getElementById("servicesTableBody");
    tbody.innerHTML = services
      .map(
        (service) => `
          <tr>
              <td>${service.id}</td>
              <td>${service.name}</td>
              <td>${service.description || "-"}</td>
              <td>$${service.price || 0}</td>
              <td>
                  <button class="btn-delete" onclick="deleteService(${service.id})">Delete</button>
              </td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading services:", error);
    document.getElementById("servicesTableBody").innerHTML =
      '<tr><td colspan="5" style="text-align: center;">No services found</td></tr>';
  }
}

async function deleteService(id) {
  if (!confirm("Are you sure?")) return;

  try {
    await fetch(`${API_BASE}/services/${id}`, { method: "DELETE" });
    showAlert("service-alert", "Service deleted successfully!", "success");
    loadServices();
  } catch (error) {
    showAlert("service-alert", "Error deleting service", "error");
  }
}

// ==================== HELPER FUNCTIONS ====================
function getStatusBadge(status) {
  const statusMap = {
    AVAILABLE: "success",
    OCCUPIED: "danger",
    RESERVED: "warning",
    MAINTENANCE: "info",
  };
  return statusMap[status] || "info";
}

// ==================== ARTICLE RESERVATIONS (UC FB.10) ====================
async function createReservation(event) {
  event.preventDefault();

  const reservation = {
    acoId: parseInt(document.getElementById("resAcoId").value),
    psId: parseInt(document.getElementById("resPsId").value),
    sparePartID: parseInt(document.getElementById("resSparePartId").value),
    quantity: parseInt(document.getElementById("resQuantity").value),
    status: document.getElementById("resStatus").value,
  };

  try {
    const response = await fetch(`${API_BASE}/article-reservation/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(reservation),
    });

    if (response.ok) {
      showAlert(
        "reservation-alert",
        "Article Reservation created successfully!",
        "success",
      );
      document.getElementById("reservationForm").reset();
      loadReservations();
    } else {
      showAlert(
        "reservation-alert",
        "Error creating reservation. Check IDs exist.",
        "error",
      );
    }
  } catch (error) {
    showAlert(
      "reservation-alert",
      "Error creating reservation: " + error.message,
      "error",
    );
  }
}

async function loadReservations() {
  try {
    const response = await fetch(`${API_BASE}/article-reservation`);
    const reservations = await response.json();

    const tbody = document.getElementById("reservationsTableBody");
    if (reservations.length === 0) {
      tbody.innerHTML =
        '<tr><td colspan="7" style="text-align: center;">No reservations found</td></tr>';
      return;
    }

    tbody.innerHTML = reservations
      .map(
        (res) => `
          <tr>
              <td>${res.id}</td>
              <td>${res.sparePart ? res.sparePart.id : "-"}</td>
              <td>${res.aircraftOwner ? res.aircraftOwner.id : "-"}</td>
              <td>${res.partsProvider ? res.partsProvider.id : "-"}</td>
              <td>${res.quantity}</td>
              <td><span class="badge badge-${getReservationBadge(res.status)}">${res.status}</span></td>
              <td>
                  <button class="btn-delete" onclick="deleteReservation(${res.id})">Delete</button>
              </td>
          </tr>
      `,
      )
      .join("");
  } catch (error) {
    console.error("Error loading reservations:", error);
    document.getElementById("reservationsTableBody").innerHTML =
      '<tr><td colspan="7" style="text-align: center;">Error loading reservations</td></tr>';
  }
}

async function deleteReservation(id) {
  if (!confirm("Are you sure you want to delete this reservation?")) return;

  try {
    await fetch(`${API_BASE}/article-reservation/${id}`, { method: "DELETE" });
    showAlert(
      "reservation-alert",
      "Reservation deleted successfully!",
      "success",
    );
    loadReservations();
  } catch (error) {
    showAlert("reservation-alert", "Error deleting reservation", "error");
  }
}

function getReservationBadge(status) {
  const statusMap = {
    PENDING: "warning",
    CONFIRMED: "info",
    DELIVERED: "success",
    CANCELLED: "danger",
  };
  return statusMap[status] || "info";
}
