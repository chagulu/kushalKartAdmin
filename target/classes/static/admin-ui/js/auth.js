// ===== Storage Keys =====
const TOKEN_KEY = 'auth_token';
const USER_KEY = 'auth_user';

// ===== Get stored JWT token =====
function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

// ===== Save token & user info =====
function saveAuth(token, user) {
    if (token) localStorage.setItem(TOKEN_KEY, token);
    if (user) localStorage.setItem(USER_KEY, JSON.stringify(user));
}

// ===== Get user info from localStorage =====
function getUser() {
    const userStr = localStorage.getItem(USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
}

// ===== Clear auth info (logout) =====
function clearAuth() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
}

// ===== Check if JWT token is expired =====
function isTokenExpired(token) {
    if (!token) return true;
    try {
        const payloadBase64 = token.split('.')[1];
        const decoded = JSON.parse(atob(payloadBase64));
        if (!decoded.exp) return true;
        return decoded.exp < Math.floor(Date.now() / 1000);
    } catch {
        return true;
    }
}

// ===== Fetch wrapper with JWT =====
function authFetch(url, options = {}) {
    const token = getToken();
    if (!options.headers) options.headers = {};
    if (token && !isTokenExpired(token)) {
        options.headers['Authorization'] = `Bearer ${token}`;
    }
    return fetch(url, options).then(response => {
        if (response.status === 403) {
            clearAuth();
            window.location.href = '/login.html';
            return Promise.reject(new Error('Unauthorized'));
        }
        return response;
    });
}

// ===== Global logout function available as window.logout =====

document.addEventListener('DOMContentLoaded', function () {
    var logoutBtn = document.getElementById('logoutLink');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', function(event) {
        event.preventDefault();
        window.logout();
      });
    } else {
      console.log('No element with id="logoutLink" exists');
    }
  });

  window.logout = function() {
    alert('Logging out...');
    const token = getToken();

    if (!token) {
        clearAuth();
        window.location.href = '/login.html';
        return;
    }

    // Remove Bearer prefix if stored with one (defensive)
    let bareToken = token.startsWith('Bearer ') ? token.slice(7) : token;

    // Call your logout endpoint if required, then always clear and redirect
    fetch(`/admin/logout?token=${encodeURIComponent(bareToken)}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    }).catch(err => {
        console.warn('Logout API failed:', err);
    }).finally(() => {
        clearAuth();
        window.location.href = '/admin/login';
    });
};