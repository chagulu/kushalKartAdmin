// ===== Storage Keys =====
const TOKEN_KEY = 'auth_token';
const USER_KEY = 'auth_user';

// ===== Get stored JWT token =====
function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

// ===== Save token & user info =====
function saveAuth(token, user) {
    if (token) {
        localStorage.setItem(TOKEN_KEY, token);
    }
    if (user) {
        localStorage.setItem(USER_KEY, JSON.stringify(user));
    }
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
        const currentTimeSec = Math.floor(Date.now() / 1000);
        return decoded.exp < currentTimeSec;
    } catch (err) {
        console.error("Token decode error:", err);
        return true;
    }
}

// ===== Add token to fetch request headers and handle token expiry redirects =====
function authFetch(url, options = {}) {
    const token = getToken();
    if (!options.headers) options.headers = {};
    if (token && !isTokenExpired(token)) {
        options.headers['Authorization'] = 'Bearer ' + token;
    }

    return fetch(url, options).then(response => {
        if (response.status === 403) {
            // Token may be expired or unauthorized — redirect to login page
            clearAuth();
            window.location.href = '/admin/login';  // Adjust to your login page path
            return Promise.reject(new Error('Unauthorized - Redirecting to login'));
        }
        return response;
    });
}
