USE ExpresoFastC5L811_II2026;

GO
INSERT INTO
    Rol(nombre_rol)
VALUES
    ('ROLE_ADMIN'),
('ROLE_OPERADOR'),
('ROLE_CONDUCTOR');

-- Password123!
DECLARE @hash VARCHAR(255) = '$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1Wn5cGBwA/7XgOymx1i86Kx5w5zK7y6';

INSERT INTO
    Usuario(
        username,
        password_hash,
        nombre_completo,
        email,
        activo
    )
VALUES
    (
        'admin',
        @hash,
        'Carlos Alvarado',
        'admin@expresofast.cr',
        1
    ),
    (
        'operador1',
        @hash,
        'Ana Operadora',
        'operador@expresofast.cr',
        1
    ),
    (
        'conductor1',
        @hash,
        'Luis Conductor',
        'conductor@expresofast.cr',
        1
    );

INSERT INTO
    UsuarioRol(usuario_id, rol_id)
VALUES
    (1, 1),
(2, 2),
(3, 3);