const API_BASE = 'http://localhost:8082/api';
const USER_KEY = 'hangar_user';

function saveUser(user) {
  if (user && typeof user === 'object') {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  }
}

function getCurrentUser() {
  try {
    const data = localStorage.getItem(USER_KEY);
    return data ? JSON.parse(data) : null;
  } catch (_) {
    return null;
  }
}

function logout() {
  localStorage.removeItem(USER_KEY);
  window.location.href = 'login.html';
}

function showAuthAlert(container, message, type) {
  if (!container) return;
  container.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
  setTimeout(() => (container.innerHTML = ''), 4000);
}
