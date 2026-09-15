const API = "http://localhost:8080";
const token = () => localStorage.getItem("jwt_token");
const roles = () => JSON.parse(localStorage.getItem("roles") || "[]");
function goLogin() { localStorage.clear(); location.href = "login.html"; }
async function fetchWithAuth(url, options = {}) {
    const headers = { "Content-Type": "application/json", ...(options.headers || {}) };
    if (token()) headers.Authorization = `Bearer ${token()}`;
    const r = await fetch(API + url, { ...options, headers });
    if (r.status === 401 || r.status === 403) { goLogin(); throw new Error("Sesión expirada o sin permisos"); }
    return r;
}
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");
    if (form) form.addEventListener("submit", login);
    if (document.getElementById("envios")) initDashboard();
});
async function login(e) {
    e.preventDefault(); const error = document.getElementById("loginError");
    try {
        const r = await fetch(API + "/api/auth/login", {
            method: "POST", headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username: username.value, password: password.value })
        });
        if (!r.ok) throw new Error("Usuario o contraseña incorrectos");
        const d = await r.json(); localStorage.setItem("jwt_token", d.token); localStorage.setItem("username", d.username); localStorage.setItem("roles", JSON.stringify(d.roles)); location.href = "index.html";
    } catch (x) { error.textContent = x.message }
}
async function initDashboard() {
    if (!token()) return goLogin();
    document.getElementById("userInfo").textContent = `${localStorage.getItem("username")} (${roles().join(", ")})`;
    document.getElementById("logoutBtn").onclick = goLogin;
    const canCreate = roles().some(x => ["ROLE_ADMIN", "ROLE_OPERADOR"].includes(x));
    document.getElementById("adminPanel").style.display = canCreate ? "block" : "none";
    if (canCreate) document.getElementById("envioForm").addEventListener("submit", createEnvio);
    await loadEnvios();
    document.getElementById("closeModal").onclick = () => document.getElementById("modal").classList.remove("show");
    document.getElementById("desde").onchange = renderFiltered;
    document.getElementById("hasta").onchange = renderFiltered;
}
let currentBitacora = [];
async function loadEnvios() {
    const r = await fetchWithAuth("/api/envios/optimizados"); const data = await r.json();
    const box = document.getElementById("envios"); box.innerHTML = "";
    data.forEach(e => {
        const c = document.createElement("article"); c.className = "card";
        c.innerHTML = `<h3>${e.codigoRastreo}</h3><p>${e.direccionDestino}</p><p>Peso: ${e.pesoKg} kg | Costo: ${e.costo}</p><p>Estado: <span class="status">${e.estadoEnvio}</span></p><p>Vehículo: ${e.placaVehiculo || "-"} | Conductor: ${e.nombreConductor || "-"}</p>`;
        if (roles().some(x => ["ROLE_ADMIN", "ROLE_OPERADOR"].includes(x))) { const b = document.createElement("button"); b.textContent = "Ver Bitácora"; b.onclick = () => openBitacora(e.id); c.appendChild(b); }
        if (roles().some(x => ["ROLE_ADMIN", "ROLE_CONDUCTOR"].includes(x))) { const s = document.createElement("button"); s.textContent = "Cambiar estado"; s.onclick = () => changeState(e.id); c.appendChild(s); }
        box.appendChild(c);
    });
}
async function createEnvio(e) {
    e.preventDefault();
    const body = { codigoRastreo: codigo.value, direccionDestino: direccion.value, pesoKg: Number(peso.value), costo: Number(costo.value), vehiculoId: Number(vehiculoId.value), conductorId: Number(conductorId.value) };
    const r = await fetchWithAuth("/api/envios", { method: "POST", body: JSON.stringify(body) }); if (!r.ok) { alert((await r.json()).message || "Error"); return; } e.target.reset(); loadEnvios();
}
async function changeState(id) {
    const nuevo = prompt("Nuevo estado (PENDIENTE, EN_TRANSITO, ENTREGADO, CANCELADO):"); if (!nuevo) return;
    const obs = prompt("Observaciones:") || "";
    const r = await fetchWithAuth(`/api/envios/${id}/estado`, { method: "PATCH", body: JSON.stringify({ nuevoEstado: nuevo, observaciones: obs }) });
    if (!r.ok) alert((await r.json()).message || "Error"); else loadEnvios();
}
async function openBitacora(id) {
    const r = await fetchWithAuth(`/api/envios/${id}/bitacora`); currentBitacora = await r.json(); renderFiltered();
    document.getElementById("modal").classList.add("show");
}
function renderFiltered() {
    const a = document.getElementById("desde").value, b = document.getElementById("hasta").value;
    const rows = currentBitacora.filter(x => { const d = x.fechaCambio.substring(0, 10); return (!a || d >= a) && (!b || d <= b) });
    document.getElementById("bitacora").innerHTML = rows.length ? rows.map(x => `<div class="card"><b>${x.estadoAnterior} → ${x.estadoNuevo}</b><p>${new Date(x.fechaCambio).toLocaleString()}</p><p>Usuario: ${x.usuario}</p><p>Observaciones: ${x.observaciones || "-"}</p></div>`).join("") : "<p>No hay registros.</p>";
}
