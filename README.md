# proyecto-calesapp
Aplicación que pone en contacto  a turistas con cocheros de coches de caballo para contratar sus servicios.

## Requisitos
- Jdk17
- Maven
- Docker
- Docker-compose

## Inicializando el proyecto

1. Clona el repositorio en un directorio local.
2. Levanta la base de datos: `docker-compose --build -d`
3. Ejecuta el proyecto con Maven: `mvn spring-boot:run`

## Perfiles
`dev`: perfil para desarrollo con mensajes de consultas SQL.  
`prod`: perfil de producción.

## Documentación
Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
OpenAPI: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## Colección de Postman
[ProyectoCalesapp.postman_collection.json](docs/ProyectoCalesapp.postman_collection.json)

## Descripción del problema
A dia de hoy, cuando alguien quiere alquilar un coche de caballo para un paseo por la cuidad el proceso es el siguiente: ir a la parada donde se encuentra el coche de caballo y contratar el servicio. 
Este modelo presenta varios problemas: 
generalmente un turista desconoce la ubicacion de las paradas
son muy pocos los cocheros que ofrecen sus servicios de manera particular por internet.
no se conocen los detalles de antemano sobre el servicio. (tarifa, duración, disponibilidad)
no hay valoraciones sobre como son los paseos en los coches de caballos.


## Motivacion del proyecto:
Aplicacion que pone en contacto  a turistas con cocheros de coches de caballo para contratar sus servicios:

## Aplicación que:
- Ofrecer la ubicacion de las paradas
- Permite a los cocheros ofrecer sus servicios.
- Informa a los usuarios sobre los detalles de los servicios que ofrecen los cocheros.
- Permite a los usuarios valorar el servicio ofrecido por cada uno de los cocheros.
- Permite a los usuarios contactar con el cochero.

## Requisitos funcionales
- Vamos a definir 3 perfiles de usuarios: no autenticado (visitante), autenticado, administrador.

## API:
- Visitante puede ver las paradas que existen.
- Usuario autenticado puede ver la informacion de los servicios que te ofrecen los cocheros.
- Usuario autenticado debe ser capaz de buscar el servicio que desee y contactar con el cochero.
- Un usuario autenticado debe ser capaz de valorar un servico de un cochero determinado.
- El administrador es el unico que puede modificar paradas, servicios y ciudades.




