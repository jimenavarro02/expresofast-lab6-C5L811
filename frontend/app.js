const API_BASE = "http://localhost:8080";

const CONDUCTOR_MAP = {
    conductor1: "Luis"
};

let allEnvios = [];
let currentFilter = "TODOS";

function decodeJwtPayload(token) {
    const payload = token.split(".")[1];
    const normalized = payload.replace(/-/g, "+").replace(/_/g, "/");
    return JSON.parse(atob(normalized));
}

function getToken() {
    return sessionStorage.getItem("jwt_token");
}

function getRoles() {
    const token = getToken();
    if (!token) {
        return [];
    }

    try {
        const payload = decodeJwtPayload(token);
        return payload.roles || JSON.parse(sessionStorage.getItem("roles") || "[]");
    } catch {
        return JSON.parse(sessionStorage.getItem("roles") || "[]");
    }
}

function getUsername() {
    const token = getToken();
    if (!token) {
        return sessionStorage.getItem("username") || "";
    }

    try {
        return decodeJwtPayload(token).sub || sessionStorage.getItem("username") || "";
    } catch {
        return sessionStorage.getItem("username") || "";
    }
}

function hasRole(role) {
    return getRoles().includes(role);
}

function redirectToLogin() {
    sessionStorage.clear();
    window.location.href = "index.html";
}

function showAlert(message, isError = true) {
    const box = document.getElementById("alertBox");
    if (!box) {
        return;
    }

    const text = Array.isArray(message) ? message.join(" • ") : String(message);
    box.textContent = text;
    box.hidden = false;
    box.classList.toggle("error", isError);

    window.setTimeout(() => {
        box.hidden = true;
    }, 5000);
}

function extractErrors(body) {
    if (!body) {
        return ["Solicitud inválida."];
    }

    if (body.errors && typeof body.errors === "object") {
        return Object.entries(body.errors).map(([field, msg]) => `${field}: ${msg}`);
    }

    if (body.messages && typeof body.messages === "object") {
        return Object.entries(body.messages).map(([field, msg]) => `${field}: ${msg}`);
    }

    if (body.detail) {
        return [body.detail];
    }

    if (body.message) {
        return [body.message];
    }

    return ["Solicitud inválida."];
}

async function fetchWithAuth(path, options = {}) {
    const token = getToken();
    const headers = {
        "Content-Type": "application/json",
        ...(options.headers || {})
    };

    if (token) {
        headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(`${API_BASE}${path}`, {
        ...options,
        headers
    });

    if (response.status === 401 || response.status === 403) {
        redirectToLogin();
        throw new Error("Sesión no autorizada.");
    }

    if (response.status === 400) {
        const body = await response.json().catch(() => ({}));
        const errors = extractErrors(body);
        showAlert(errors);
        throw new Error(errors.join(" "));
    }

    if (response.status === 404) {
        const body = await response.json().catch(() => ({}));
        const errors = extractErrors(body);
        showAlert(errors);
        throw new Error(errors.join(" "));
    }

    if (!response.ok) {
        const body = await response.json().catch(() => ({}));
        const errors = extractErrors(body);
        showAlert(errors);
        throw new Error(errors.join(" "));
    }

    return response;
}

document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", handleLogin);
    }

    if (document.getElementById("enviosContainer")) {
        initDashboard();
    }
});

async function handleLogin(event) {
    event.preventDefault();

    const errorBox = document.getElementById("loginError");
    errorBox.textContent = "";

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;

    try {
        const response = await fetch(`${API_BASE}/api/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        if (response.status === 400) {
            const body = await response.json().catch(() => ({}));
            errorBox.textContent = extractErrors(body).join(" ");
            return;
        }

        if (response.status === 401) {
            errorBox.textContent = "Usuario o contraseña incorrectos.";
            return;
        }

        if (!response.ok) {
            errorBox.textContent = "No fue posible iniciar sesión.";
            return;
        }

        const data = await response.json();
        sessionStorage.setItem("jwt_token", data.token);
        sessionStorage.setItem("username", data.username);
        sessionStorage.setItem("roles", JSON.stringify(data.roles));

        window.location.href = "dashboard.html";
    } catch {
        errorBox.textContent = "Error de conexión con el servidor.";
    }
}

async function initDashboard() {
    if (!getToken()) {
        redirectToLogin();
        return;
    }

    const roles = getRoles();
    const username = getUsername();

    document.getElementById("userDisplay").textContent =
        `${username} (${roles.join(", ")})`;

    document.getElementById("logoutBtn").addEventListener("click", redirectToLogin);

    document.querySelectorAll(".filter-btn").forEach((button) => {
        button.addEventListener("click", () => {
            document.querySelectorAll(".filter-btn").forEach((btn) => btn.classList.remove("active"));
            button.classList.add("active");
            currentFilter = button.dataset.filter;
            renderEnvios();
        });
    });

    if (hasRole("ROLE_ADMIN")) {
        document.getElementById("adminTools").hidden = false;
        document.getElementById("auditAside").hidden = false;
        document.getElementById("vehiculoForm").addEventListener("submit", registerVehicle);
        await loadAuditLog();
    } else {
        document.querySelector(".dashboard-main").classList.add("no-aside");
    }

    if (hasRole("ROLE_ADMIN") || hasRole("ROLE_OPERADOR")) {
        document.getElementById("vehiculosSection").hidden = false;
    }

    await refreshDashboard();
}

async function refreshDashboard() {
    const tasks = [loadEnvios(), loadKpis()];
    if (hasRole("ROLE_ADMIN") || hasRole("ROLE_OPERADOR")) {
        tasks.push(loadVehiculos());
    }
    await Promise.all(tasks);
}

async function loadEnvios() {
    try {
        const response = await fetchWithAuth("/api/envios");
        allEnvios = await response.json();
        renderEnvios();
    } catch (error) {
        document.getElementById("enviosContainer").innerHTML =
            `<p class="empty-state">${error.message}</p>`;
    }
}

function filterEnvios(envios) {
    let filtered = envios;

    if (hasRole("ROLE_CONDUCTOR")) {
        const conductorName = CONDUCTOR_MAP[getUsername()];
        filtered = filtered.filter((envio) => envio.nombreConductor === conductorName);
    }

    if (currentFilter !== "TODOS") {
        filtered = filtered.filter((envio) => envio.estadoEnvio === currentFilter);
    }

    return filtered;
}

function renderEnvios() {
    const container = document.getElementById("enviosContainer");
    const envios = filterEnvios(allEnvios);

    if (!envios.length) {
        container.innerHTML = `<p class="empty-state">No hay envíos para mostrar.</p>`;
        return;
    }

    container.innerHTML = envios.map((envio) => createEnvioCard(envio)).join("");

    envios.forEach((envio) => {
        const transitoBtn = document.getElementById(`transito-${envio.id}`);
        const entregadoBtn = document.getElementById(`entregado-${envio.id}`);
        const assignBtn = document.getElementById(`assign-${envio.id}`);
        const adminStateBtn = document.getElementById(`admin-state-${envio.id}`);

        if (transitoBtn) {
            transitoBtn.addEventListener("click", () => updateEstado(envio.id, "EN_TRANSITO"));
        }

        if (entregadoBtn) {
            entregadoBtn.addEventListener("click", () => updateEstado(envio.id, "ENTREGADO"));
        }

        if (assignBtn) {
            assignBtn.addEventListener("click", () => assignVehicle(envio.id));
        }

        if (adminStateBtn) {
            adminStateBtn.addEventListener("click", () => updateEstado(envio.id));
        }
    });
}

function createEnvioCard(envio) {
    const statusClass = {
        PENDIENTE: "status-pendiente",
        EN_TRANSITO: "status-transito",
        ENTREGADO: "status-entregado",
        CANCELADO: "status-cancelado"
    }[envio.estadoEnvio] || "status-pendiente";

    let actions = "";

    if (hasRole("ROLE_OPERADOR") && envio.estadoEnvio === "PENDIENTE") {
        actions += `<button type="button" id="assign-${envio.id}">Asignar vehículo</button>`;
        actions += `<button type="button" id="transito-${envio.id}">Marcar EN_TRANSITO</button>`;
    }

    if (hasRole("ROLE_CONDUCTOR") && envio.estadoEnvio === "EN_TRANSITO") {
        actions += `<button type="button" id="entregado-${envio.id}">Marcar ENTREGADO</button>`;
    }

    if (hasRole("ROLE_ADMIN") && envio.estadoEnvio !== "ENTREGADO") {
        actions += `<button type="button" id="admin-state-${envio.id}">Actualizar estado</button>`;
    }

    return `
        <article class="envio-card">
            <header>
                <h3>${envio.codigoRastreo}</h3>
                <span class="status-badge ${statusClass}">${envio.estadoEnvio}</span>
            </header>
            <p class="envio-meta">${envio.direccionDestino}</p>
            <p class="envio-meta">Peso: ${envio.pesoKg} kg | Costo: ₡${envio.costo}</p>
            <p class="envio-meta">Vehículo: ${envio.placaVehiculo || "Sin asignar"}</p>
            <p class="envio-meta">Conductor: ${envio.nombreConductor || "Sin asignar"}</p>
            <div class="envio-actions">${actions}</div>
        </article>
    `;
}

async function loadKpis() {
    const visibleEnvios = filterEnvios(allEnvios.length ? allEnvios : await fetchEnviosData());

    document.getElementById("kpiTotal").textContent = visibleEnvios.length;
    document.getElementById("kpiEntregados").textContent =
        visibleEnvios.filter((envio) => envio.estadoEnvio === "ENTREGADO").length;

    try {
        const vehiculos = await fetchVehiculosData();
        const activos = vehiculos.filter((vehiculo) => vehiculo.estado !== "MANTENIMIENTO");
        document.getElementById("kpiVehiculos").textContent = activos.length;
    } catch {
        document.getElementById("kpiVehiculos").textContent = "0";
    }
}

async function fetchVehiculosData() {
    const response = await fetchWithAuth("/api/vehiculos");
    return response.json();
}

async function loadVehiculos() {
    const container = document.getElementById("vehiculosContainer");
    if (!container) {
        return;
    }

    try {
        const vehiculos = await fetchVehiculosData();
        renderVehiculos(vehiculos);
    } catch (error) {
        container.innerHTML = `<p class="empty-state">${error.message}</p>`;
    }
}

function renderVehiculos(vehiculos) {
    const container = document.getElementById("vehiculosContainer");

    if (!vehiculos.length) {
        container.innerHTML = `<p class="empty-state">Aún no hay vehículos registrados.</p>`;
        return;
    }

    container.innerHTML = vehiculos.map((vehiculo) => {
        const statusClass = vehiculo.estado === "DISPONIBLE"
            ? "status-entregado"
            : vehiculo.estado === "EN_RUTA"
                ? "status-transito"
                : "status-pendiente";

        return `
            <article class="envio-card">
                <header>
                    <h3>${vehiculo.placa}</h3>
                    <span class="status-badge ${statusClass}">${vehiculo.estado}</span>
                </header>
                <p class="envio-meta">Capacidad: ${vehiculo.capacidadKg} kg</p>
                <p class="envio-meta">Empresa ID: ${vehiculo.empresaId ?? "-"}</p>
            </article>
        `;
    }).join("");
}

async function fetchEnviosData() {
    const response = await fetchWithAuth("/api/envios");
    allEnvios = await response.json();
    return allEnvios;
}

async function updateEstado(id, nuevoEstado) {
    let estado = nuevoEstado;

    if (hasRole("ROLE_ADMIN") && !nuevoEstado) {
        estado = window.prompt("Nuevo estado (PENDIENTE, EN_TRANSITO, ENTREGADO, CANCELADO):");
        if (!estado) {
            return;
        }
    }

    const observaciones = window.prompt("Observaciones (opcional):") || "";

    try {
        await fetchWithAuth(`/api/envios/${id}/estado`, {
            method: "PATCH",
            body: JSON.stringify({ nuevoEstado: estado, observaciones })
        });

        showAlert(`Estado actualizado a ${estado}.`, false);
        await refreshDashboard();

        if (hasRole("ROLE_ADMIN")) {
            await loadAuditLog();
        }
    } catch (error) {
        showAlert(error.message);
    }
}

async function assignVehicle(envioId) {
    try {
        const response = await fetchWithAuth("/api/vehiculos");
        const vehiculos = await response.json();
        const disponibles = vehiculos.filter((vehiculo) => vehiculo.estado === "DISPONIBLE");

        if (!disponibles.length) {
            showAlert("No hay vehículos disponibles.");
            return;
        }

        const options = disponibles.map((vehiculo) => `${vehiculo.id}: ${vehiculo.placa}`).join("\n");
        const selected = window.prompt(`Seleccione ID de vehículo:\n${options}`);
        if (!selected) {
            return;
        }

        showAlert(`Vehículo ${selected} asignado al envío ${envioId}.`, false);
        await updateEstado(envioId, "EN_TRANSITO");
    } catch (error) {
        showAlert(error.message);
    }
}

async function registerVehicle(event) {
    event.preventDefault();

    const body = {
        placa: document.getElementById("placa").value.trim(),
        capacidadKg: Number(document.getElementById("capacidad").value),
        empresaId: Number(document.getElementById("empresaId").value)
    };

    try {
        const response = await fetchWithAuth("/api/vehiculos", {
            method: "POST",
            body: JSON.stringify(body)
        });

        const vehiculo = await response.json();

        event.target.reset();
        document.getElementById("empresaId").value = "1";
        showAlert(`Vehículo ${vehiculo.placa} registrado correctamente.`, false);
        await loadKpis();
        await loadVehiculos();
    } catch (error) {
        if (error.message !== "Sesión no autorizada.") {
            showAlert(error.message);
        }
    }
}

async function loadAuditLog() {
    const container = document.getElementById("auditLog");
    if (!container) {
        return;
    }

    try {
        const response = await fetchWithAuth("/api/envios/bitacora/historial");
        const entries = await response.json();

        if (!entries.length) {
            container.innerHTML = `<p class="empty-state">Sin registros de auditoría.</p>`;
            return;
        }

        container.innerHTML = entries.map((entry) => `
            <article class="audit-entry">
                <p><strong>${entry.estadoAnterior} → ${entry.estadoNuevo}</strong></p>
                <p>${new Date(entry.fechaCambio).toLocaleString()}</p>
                <p>Usuario: ${entry.usuario}</p>
                <p>${entry.observaciones || "Sin observaciones"}</p>
            </article>
        `).join("");
    } catch (error) {
        container.innerHTML = `<p class="empty-state">${error.message}</p>`;
    }
}
