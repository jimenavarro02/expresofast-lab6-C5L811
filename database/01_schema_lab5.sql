CREATE DATABASE ExpresoFastC5L811_II2026;

GO
    USE ExpresoFastC5L811_II2026;

GO
    CREATE TABLE EmpresaLogistica (
        empresa_id INT IDENTITY(1, 1) PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL UNIQUE,
        cedula_juridica VARCHAR(20) NOT NULL UNIQUE,
        telefono VARCHAR(20) NOT NULL,
        fecha_registro DATETIME NOT NULL
    );

GO
    CREATE TABLE Vehiculo (
        vehiculo_id INT IDENTITY(1, 1) PRIMARY KEY,
        placa VARCHAR(15) NOT NULL UNIQUE,
        capacidad_kg DECIMAL(10, 2) NOT NULL,
        estado VARCHAR(20) NOT NULL,
        empresa_id INT NOT NULL,
        CONSTRAINT CK_Vehiculo_Estado CHECK (
            estado IN (
                'DISPONIBLE',
                'EN_RUTA',
                'MANTENIMIENTO'
            )
        ),
        CONSTRAINT FK_Vehiculo_Empresa FOREIGN KEY (empresa_id) REFERENCES EmpresaLogistica(empresa_id)
    );

GO
    CREATE TABLE Conductor (
        conductor_id INT IDENTITY(1, 1) PRIMARY KEY,
        nombre VARCHAR(50) NOT NULL,
        apellidos VARCHAR(50) NOT NULL,
        licencia VARCHAR(20) NOT NULL UNIQUE,
        telefono VARCHAR(20) NOT NULL
    );

GO
    CREATE TABLE Envio (
        envio_id INT IDENTITY(1, 1) PRIMARY KEY,
        codigo_rastreo VARCHAR(30) NOT NULL UNIQUE,
        direccion_destino VARCHAR(200) NOT NULL,
        peso_kg DECIMAL(10, 2) NOT NULL,
        costo DECIMAL(10, 2) NOT NULL,
        estado_envio VARCHAR(20) NOT NULL,
        vehiculo_id INT NOT NULL,
        conductor_id INT NOT NULL,
        fecha_creacion DATETIME NULL,
        fecha_modificacion DATETIME NULL,
        CONSTRAINT CK_Envio_Estado CHECK (
            estado_envio IN (
                'PENDIENTE',
                'EN_TRANSITO',
                'ENTREGADO',
                'CANCELADO'
            )
        ),
        CONSTRAINT CK_Envio_Peso CHECK (peso_kg > 0),
        CONSTRAINT CK_Envio_Costo CHECK (costo >= 0),
        CONSTRAINT FK_Envio_Vehiculo FOREIGN KEY (vehiculo_id) REFERENCES Vehiculo(vehiculo_id),
        CONSTRAINT FK_Envio_Conductor FOREIGN KEY (conductor_id) REFERENCES Conductor(conductor_id)
    );

GO
INSERT INTO
    EmpresaLogistica (
        nombre,
        cedula_juridica,
        telefono,
        fecha_registro
    )
VALUES
    (
        'ExpresoFast Transporte S.A.',
        '3-101-999999',
        '8888-1111',
        GETDATE()
    ),
    (
        'Carga Tica S.A.',
        '3-101-888888',
        '8888-2222',
        GETDATE()
    );

GO
INSERT INTO
    Vehiculo (placa, capacidad_kg, estado, empresa_id)
VALUES
    (
        'EF-001',
        1000.00,
        'DISPONIBLE',
        1
    ),
    (
        'EF-002',
        750.00,
        'DISPONIBLE',
        1
    ),
    (
        'CT-101',
        1500.00,
        'EN_RUTA',
        2
    );

GO
INSERT INTO
    Conductor (nombre, apellidos, licencia, telefono)
VALUES
    (
        'Carlos',
        'Ramírez Mora',
        'B2-10001',
        '8888-3001'
    ),
    (
        'Andrea',
        'Solano Vega',
        'B2-10002',
        '8888-3002'
    ),
    (
        'Luis',
        'Jiménez Rojas',
        'B2-10003',
        '8888-3003'
    );

GO
INSERT INTO
    Envio (
        codigo_rastreo,
        direccion_destino,
        peso_kg,
        costo,
        estado_envio,
        vehiculo_id,
        conductor_id,
        fecha_creacion,
        fecha_modificacion
    )
VALUES
    (
        'EXP-9901',
        'Paraíso, Cartago',
        12.50,
        3500.00,
        'PENDIENTE',
        1,
        1,
        GETDATE(),
        GETDATE()
    ),
    (
        'EXP-9902',
        'San José Centro',
        25.00,
        5200.00,
        'EN_TRANSITO',
        2,
        2,
        GETDATE(),
        GETDATE()
    ),
    (
        'EXP-9903',
        'Heredia Centro',
        8.75,
        2800.00,
        'ENTREGADO',
        3,
        3,
        GETDATE(),
        GETDATE()
    );

GO
SELECT
    *
FROM
    EmpresaLogistica;

SELECT
    *
FROM
    Vehiculo;

SELECT
    *
FROM
    Conductor;

SELECT
    *
FROM
    Envio;