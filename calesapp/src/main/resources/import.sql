INSERT INTO ciudad (id, nombre, descripcion) VALUES
(1, 'Sevilla', 'Ciudad en el sur de España conocida por su cultura, historia y gastronomia.');

INSERT INTO parada (id, nombre, ubicacion, descripcion, ciudad_id) VALUES
(1, 'Plaza de Toros', '37.3893, -5.9961', 'Parada junto a la emblemática Plaza de Toros de la Maestranza.', 1),
(2, 'Torre del Oro', '37.3829, -5.9966', 'Parada al lado del histórico monumento junto al río Guadalquivir.', 1),
(3, 'Plaza Virgen de los Reyes', '37.3862, -5.9927', 'Parada cerca de la Catedral y la Giralda.', 1),
(4, 'Parque Maria Luisa', '37.3764, -5.9869', 'Parada en el pulmon verde de Sevilla, cerca de la Plaza de America.', 1),
(5, 'Plaza de España', '37.3772, -5.9869', 'Parada en el icónico conjunto arquitectónico de Sevilla.', 1),
(6, 'Plaza de America', '37.3738, -5.9870', 'Parada ubicada en el Parque María Luisa, cerca del Museo Arqueológico.', 1),
(7, 'Giralda', '37.3860, -5.9924', 'Parada junto al famoso campanario de la Catedral de Sevilla.', 1),
(8, 'Altozano', '37.3910, -5.9998', 'Parada en la Plaza del Altozano, en pleno barrio de Triana.', 1),
(9, 'Todogoma', '37.3860, -5.9924', 'Parada en la esquina de la calle Adriano con Paseo Colon', 1);

INSERT INTO cochero (id, nombre, apellidos, experiencia) VALUES
(1, 'Jesus', 'Lorente Gaviño',7.5),
(2, 'Carlos', 'Lorente Gaviño',8.5),
(3, 'Jose', 'Lorente Cala',9.0),
(4, 'Manuel', 'Lorente Cala',10.0),
(5, 'Carlos', 'Lorente Cala',10.0),
(6, 'David', 'Heredia Fernandez',9.5),
(7, 'Antonio', 'Heredia Fernandez',7.5),
(8, 'Jose Claudio', 'Gamez Lorente',7.5);

INSERT INTO servicio (id, tipo_servicio, tarifa, duracion, descripcion, disponibilidad, cochero_id) VALUES
(1, 'TRANSPORTE', 25.50, 60, 'Servicio de transporte a cualquier punto de la ciudad', true, 1),
(2, 'EVENTO', 40.00, 120, 'Alquiler para eventos especiales', false, 2),
(3, 'PASEO', 30.00, 90, 'Paseo guiado por el centro historico de Sevilla y sus monumentos mas emblematicos', true, 3);


INSERT INTO user_entity (id, username, password, enabled, verification_token) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'juanperez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'abc123'),
('7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 'marialopez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'def456'),
('a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 'carlossanchez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'ghi789'),
('a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 'anagomez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'jkl012'),
('3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 'luistorres', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'mno345');


INSERT INTO valoracion (puntuacion, comentario, fecha, usuario_id, servicio_id) VALUES
(5, 'Excelente servicio, muy recomendado.', '2025-02-15', '550e8400-e29b-41d4-a716-446655440000',1),
(4, 'Buena atención, aunque podría mejorar.', '2025-02-10', '7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 1),
(3, 'Servicio aceptable, pero hubo algunos inconvenientes.', '2025-01-28', 'a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 1),
(5, 'Todo perfecto, repetiré sin duda.', '2025-02-05', 'a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 3),
(2, 'No cumplió con mis expectativas.', '2025-01-20', '3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 3);

INSERT INTO contacto (id, mensaje, fecha, usuario_id, servicio_id) VALUES
(1, 'Consulta sobre disponibilidad de plazas', '2025-02-01', '550e8400-e29b-41d4-a716-446655440000', 2),
(2, 'Solicitud de información adicional', '2025-02-05', '7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 1),
(3, 'Problemas con la plataforma', '2025-02-10', 'a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 3),
(4, 'Revisión de documentos enviados', '2025-02-12', 'a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 2),
(5, 'Cambio en los datos de inscripción', '2025-02-15', '3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 1),
(6, 'Consulta sobre disponibilidad en la época de feria', '2025-02-01', '550e8400-e29b-41d4-a716-446655440000', 2);













