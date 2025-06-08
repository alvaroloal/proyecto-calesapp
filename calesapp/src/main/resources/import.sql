INSERT INTO ciudad (id, nombre, descripcion) VALUES
(1, 'Sevilla', 'Ciudad en el sur de España conocida por su cultura, historia y gastronomia.');

INSERT INTO parada (nombre, ubicacion, descripcion, ciudad_id) VALUES
('Plaza de Toros', '37.3893, -5.9961', 'Parada junto a la emblematica Plaza de Toros de la Maestranza.', 1),
('Torre del Oro', '37.3829, -5.9966', 'Parada al lado del histórico monumento junto al río Guadalquivir.', 1),
('Plaza Virgen de los Reyes', '37.3862, -5.9927', 'Parada cerca de la Catedral y la Giralda.', 1),
('Parque Maria Luisa', '37.3764, -5.9869', 'Parada en el pulmon verde de Sevilla, cerca de la Plaza de America.', 1),
('Plaza de España', '37.3772, -5.9869', 'Parada en el icónico conjunto arquitectónico de Sevilla.', 1),
('Plaza de America', 'Parque Maria Luisa', 'Parada ubicada en el Parque María Luisa, cerca del Museo Arqueológico.', 1),
('Giralda', '37.3860, -5.9924', 'Parada junto al famoso campanario de la Catedral de Sevilla.', 1),
('Altozano', '37.3910, -5.9998', 'Parada en la Plaza del Altozano, en pleno barrio de Triana.', 1),
('Todogoma', '37.3860, -5.9924', 'Parada en la esquina de la calle Adriano con Paseo Colon', 1),
('Estatua Becquer', 'Parque Maria Luisa', 'Parada en la entrada del parque junto al restaurante La Raza', 1),
('Monte Gurugu', 'Parque Maria Luisa', 'Parada en el interior del parque Maria Luisa', 1),
('Archivo General de Indias', '37.3860, -5.9924', 'Parada en la Plaza del Altozano, junto al Archivo General de Indias', 1),
('Plaza de la Encarnacion', '37.3880, -5.9930', 'Parada en la Plaza de la Encarnacion, junto a las Setas de Sevilla', 1),
('Puente de Triana', '37.3893, -5.9961', 'Parada en el Puente de Isabel II, conocido como Puente de Triana', 1),
('Calle Sierpes', '37.3860, -5.9924', 'Parada en la famosa calle comercial del centro de Sevilla', 1)
;

INSERT INTO cochero (nombre, apellidos, experiencia) VALUES
('Jesus', 'Lorente Gaviño',7),
('Carlos', 'Lorente Gaviño',8),
('Jose', 'Lorente Cala',9),
('Manuel', 'Lorente Cala',10),
('Carlos', 'Lorente Cala',10),
('David', 'Heredia Fernandez',9),
('Antonio', 'Heredia Fernandez',7),
('Jose Claudio', 'Gamez Lorente',8),
('Gonzalo', 'Garcia Ferrero',6),
('Antonio', 'Muñoz Pedrosa',5);


INSERT INTO servicio (tipo_servicio, tarifa, duracion, descripcion, disponibilidad, cochero_id) VALUES
('TRANSPORTE', 25.50, 60, 'Servicio de transporte a cualquier punto de la ciudad', true, 1),
('EVENTO', 40.00, 120, 'Alquiler para eventos especiales', false, 2),
('PASEO', 30.00, 90, 'Paseo guiado por el centro historico de Sevilla y sus monumentos mas emblematicos', true, 3);

INSERT INTO user_entity (id, username, password, enabled, verification_token, rol) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'juanperez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'abc123', 0),
('7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 'marialopez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'def456', 1),
('a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 'carlossanchez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'ghi789', 1),
('a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 'anagomez', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'jkl012', 1),
('3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 'luistorres', '$2a$10$X9z3D.q/sFqf5J8eEyo8O.8Z5j5Gk8r1J7K9P1gOQ8F1E4zY9B5Wy', true, 'mno345',1);

INSERT INTO valoracion (puntuacion, comentario, fecha, usuario_id, servicio_id) VALUES
(5, 'Excelente servicio, muy recomendado.', '2025-02-15', '550e8400-e29b-41d4-a716-446655440000',1),
(4, 'Buena atención, aunque podría mejorar.', '2025-02-10', '7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 1),
(3, 'Servicio aceptable, pero hubo algunos inconvenientes.', '2025-01-28', 'a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 1),
(5, 'Todo perfecto, repetiré sin duda.', '2025-02-05', 'a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 3),
(2, 'No cumplio con mis expectativas.', '2025-01-20', '3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 3),
(4, 'Buen servicio, aunque el tiempo de espera fue largo.', '2025-02-12', '550e8400-e29b-41d4-a716-446655440000', 2),
(5, 'Excelente experiencia, muy profesional.', '2025-02-01', '7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 2),
(3, 'Servicio regular, podría mejorar.', '2025-01-30', 'a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 1),
(4, 'Muy buen servicio, lo recomiendo.', '2025-02-03', 'a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 1),
(1, 'Muy mala experiencia, no volveré a usar este servicio.', '2025-01-15', '3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 2);

INSERT INTO contacto (mensaje, fecha, usuario_id, servicio_id) VALUES
('Consulta sobre disponibilidad de plazas', '2025-02-01', '550e8400-e29b-41d4-a716-446655440000', 2),
('Solicitud de información adicional', '2025-02-05', '7f26fbb5-dbb7-4127-b4c9-85e3d64e08f0', 1),
('Problemas con la plataforma', '2025-02-10', 'a58e25bb-4c08-4f91-b45f-3cb8fd50c6a9', 3),
('Revisión de documentos enviados', '2025-02-12', 'a8a31f2b-7dd6-4b56-86d8-5b49730a2cb1', 2),
('Cambio en los datos de inscripción', '2025-02-15', '3b6e27bc-8fcd-41c3-a6f2-265e677aa9f6', 1),
('Consulta sobre disponibilidad en la época de feria', '2025-02-01', '550e8400-e29b-41d4-a716-446655440000', 2);













