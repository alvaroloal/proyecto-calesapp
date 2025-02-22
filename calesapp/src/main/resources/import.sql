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
(8, 'Altozano', '37.3910, -5.9998', 'Parada en la Plaza del Altozano, en pleno barrio de Triana.', 1);

INSERT INTO cochero (id, nombre, apellidos, experiencia) VALUES
(1, 'Jesus', 'Lorente Gaviño',7.5),
(2, 'Carlos', 'Lorente Gaviño',8.5),
(3, 'Jose', 'Lorente Cala',9.0),
(4, 'Manuel', 'Lorente Cala',10.0),
(5, 'Carlos', 'Lorente Cala',10.0),
(6, 'David', 'Lopez Gutierrez',7.5);

INSERT INTO servicio (id, tipo_servicio, tarifa, duracion, descripcion, disponibilidad, cochero_id) VALUES
(1, 'TRANSPORTE', 25.50, 60, 'Servicio de transporte turistico', true, 1),
(2, 'EVENTO', 40.00, 120, 'Alquiler para eventos especiales', false, 2),
(3, 'PASEO', 30.00, 90, 'Paseo guiado por el centro historico', true, 3);








