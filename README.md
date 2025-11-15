# Calesapp

**Calesapp** es una aplicación full-stack que conecta a turistas con cocheros de coches de caballos para facilitar la contratación de paseos por la ciudad. Ofrece un sistema de reservas para diferentes tipos de servicios en diferentes puntos de una ciudad con una flota de cocheros.

---

## Índice

- [Memoria del proyecto](#memoria-del-proyecto)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Requisitos del sistema](#requisitos-del-sistema)
- [Puesta en marcha](#puesta-en-marcha)
- [Desarrollo](#desarrollo)
- [Arquitectura](#arquitectura)
- [Acceso a servicios](#acceso-a-servicios)
- [Documentación de la API](#documentación-de-la-api)
- [Pruebas de autenticación](#pruebas-de-autenticación)
- [Diseño UI](#diseño-ui)
- [Diagramas](#diagramas)
- [Testing](#testing)

---

## Memoria del proyecto

[Memoria del proyecto](docs/MemoriaPDAM-ÁlvaroLorenteAlmán.pdf)

---

## Tecnologías utilizadas

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.4.2** - Framework principal
- **Spring Data JPA** - Capa de persistencia
- **Spring Security** - Autenticación y autorización
- **PostgreSQL** - Base de datos (producción y desarrollo)
- **H2** - Base de datos en memoria (testing)
- **JWT (jjwt 0.12.6)** - Tokens de autenticación
- **Lombok** - Reducción de código boilerplate
- **Springdoc OpenAPI** - Documentación automática de la API
- **JUnit 5 & Mockito** - Testing unitario
- **Maven** - Gestión de dependencias

### Frontend
- **Angular 19** - Framework JavaScript
- **TypeScript 5.7** - Lenguaje de programación
- **Tailwind CSS 4.0** - Framework CSS
- **RxJS** - Programación reactiva
- **SweetAlert2** - Alertas y modales
- **Google Maps API** - Integración de mapas
- **jwt-decode** - Decodificación de tokens JWT
- **Jasmine & Karma** - Testing

### Infraestructura
- **Docker & Docker Compose** - Containerización
- **Nginx** - Servidor web para el frontend
- **PGAdmin** - Gestión de PostgreSQL

---

## Estructura del proyecto

```
proyecto-calesapp/
├── calesapp/                           # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/.../calesapp/
│   │   │   │   ├── config/            # Configuraciones
│   │   │   │   ├── controller/        # Controladores REST
│   │   │   │   ├── dto/               # Data Transfer Objects
│   │   │   │   ├── error/             # Manejo de excepciones
│   │   │   │   ├── files/             # Sistema de archivos (local/Imgur)
│   │   │   │   ├── model/             # Entidades de dominio
│   │   │   │   ├── repository/        # Repositorios JPA
│   │   │   │   ├── security/          # Seguridad y JWT
│   │   │   │   ├── service/           # Lógica de negocio
│   │   │   │   ├── specification/     # Especificaciones JPA
│   │   │   │   ├── user/              # Módulo de usuarios
│   │   │   │   └── validation/        # Validaciones personalizadas
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                      # Tests unitarios
│   ├── pom.xml
│   └── Dockerfile
│
├── proyecto-calesapp-ng/              # Frontend (Angular)
│   ├── src/
│   │   └── app/
│   │       ├── admin/                 # Panel de administración
│   │       ├── components/            # Componentes compartidos
│   │       │   ├── hero/
│   │       │   ├── login/
│   │       │   ├── register/
│   │       │   ├── verify-account/
│   │       │   └── mapa-paradas/
│   │       ├── core/                  # Layout y estructura
│   │       ├── models/                # Interfaces TypeScript
│   │       ├── pages/                 # Páginas principales
│   │       │   ├── cocheros/
│   │       │   ├── paradas/
│   │       │   ├── servicios/
│   │       │   ├── valoraciones/
│   │       │   └── contactos/
│   │       └── services/              # Servicios HTTP
│   ├── package.json
│   └── Dockerfile
│
├── docs/                              # Documentación
│   ├── diagrama-clases-dominio.drawio.png
│   ├── diagrama-entidad-relacion.drawio.png
│   └── ProyectoCalesapp.postman_collection.json
│
├── docker-compose-dev.yml             # Compose para desarrollo
├── docker-compose-prod.yml            # Compose para producción
└── README.md
```

---

## Requisitos del sistema

Para ejecutar el proyecto localmente, asegúrate de tener instalado:

- **Java Development Kit (JDK) 17**
- **Apache Maven**
- **Docker**
- **Docker Compose**
- **Node.js** (versión 18 o superior)

---

## Puesta en marcha

Clonar el repositorio en tu máquina local:
```bash
git clone https://github.com/alvaroloal/proyecto-calesapp.git
```
---

La aplicación dispone de dos perfiles de configuración que se adaptan al entorno de ejecución:

- **Desarrollo**
  ```bash
  docker-compose -f docker-compose-dev.yml up -d
  ```
  Ejecuta la aplicación en un contenedor Docker con acceso a PGAdmin, y muestra mensajes de consultas SQL en consola.

   Ejecutar docker-compose desde la raiz del repositorio:
  ```bash
  docker-compose -f docker-compose-dev.yml up -d --build
  ```
   
- **Producción**
  ```bash
  docker-compose -f docker-compose-prod.yml up -d
  ```
  Ejecuta la aplicación en un contenedor Docker sin mostrar las consultas SQL.


Para bajar los servicios y eliminar los recursos asociados basta con ejecutar la siguiente orden:
```bash
docker-compose -f docker-compose-dev.yml down --remove-orphans
```

---

## Desarrollo

### Backend (Spring Boot)

**Navegar al directorio del backend:**
```bash
cd calesapp
```

**Compilar el proyecto:**
```bash
./mvnw clean package
```

**Ejecutar tests:**
```bash
./mvnw test
```

**Ejecutar un test específico:**
```bash
# Una clase de test completa
./mvnw test -Dtest=ParadaServiceTest

# Un método específico
./mvnw test -Dtest=ParadaServiceTest#testFindById
```

**Ejecutar sin Docker:**
```bash
./mvnw spring-boot:run
```

### Frontend (Angular)

**Navegar al directorio del frontend:**
```bash
cd proyecto-calesapp-ng
```

**Instalar dependencias:**
```bash
npm install
```

**Iniciar servidor de desarrollo:**
```bash
npm start
# La aplicación estará disponible en http://localhost:4200
```

**Compilar para producción:**
```bash
npm run build
```

**Ejecutar tests:**
```bash
npm test
```

---

## Arquitectura

### Modelo de dominio

La aplicación está construida alrededor de las siguientes entidades principales:

#### Entidades principales:

- **Usuario**: Gestión de usuarios con roles (USER, ADMIN). Incluye sistema de verificación por email.
- **Cochero**: Información de los cocheros (conductores de coches de caballos).
- **Parada**: Puntos de recogida/entrega en la ciudad con coordenadas GPS.
- **Servicio**: Servicios ofrecidos por los cocheros. Tipos disponibles:
  - `CITY_TOUR` - Paseo por la ciudad
  - `SPECIAL_EVENT` - Evento especial
  - `TRANSFER` - Traslado
  - `CUSTOM` - Personalizado
- **Contacto**: Solicitudes de reserva que vinculan usuarios con servicios, paradas o cocheros.
- **Valoracion**: Reseñas de usuarios sobre los servicios.
- **Ciudad**: Ciudades donde operan los servicios.

#### Relaciones principales:

- Un **Cochero** puede ofrecer múltiples **Servicios**
- Un **Servicio** puede tener múltiples **Valoraciones** y **Contactos**
- Una **Parada** puede tener múltiples **Contactos**
- Un **Usuario** puede crear múltiples **Contactos** y **Valoraciones**
- Una **Ciudad** contiene múltiples **Paradas** y **Cocheros**

### Arquitectura Backend

El backend sigue una arquitectura en capas con separación clara de responsabilidades:

**Capa de Presentación (Controllers)**
- Endpoints REST en `/controller`
- Validación de entrada
- Conversión entre DTOs y entidades

**Capa de Negocio (Services)**
- Lógica de negocio en `/service`
- Transacciones
- Validaciones complejas

**Capa de Persistencia (Repositories)**
- Repositorios Spring Data JPA
- Consultas personalizadas
- Especificaciones para búsquedas dinámicas

**Seguridad**
- Autenticación basada en JWT
- Tokens de acceso y refresh separados
- Sistema de verificación por email con tokens
- Control de acceso basado en roles

**Sistema de Archivos**
- Abstracción mediante interfaz `StorageService`
- Implementación local: almacenamiento en `./uploads`
- Implementación Imgur: subida a API de Imgur

### Arquitectura Frontend

La aplicación Angular está organizada por características (feature modules):

**Estructura modular:**
- **Pages**: Módulos de funcionalidades principales (cocheros, paradas, servicios, etc.)
- **Components**: Componentes reutilizables (login, registro, mapas)
- **Services**: Servicios HTTP que se comunican con el backend
- **Models**: Interfaces TypeScript para el tipado fuerte
- **Admin**: Panel de administración con dashboard
- **Core**: Componentes de layout y estructura general

**Flujo de autenticación:**
1. Usuario se registra → genera token de verificación
2. Usuario verifica cuenta mediante token
3. Usuario inicia sesión → recibe JWT access + refresh token
4. Token se envía en headers para endpoints protegidos

---

## Acceso a servicios

Una vez levantados los servicios con Docker Compose, puedes acceder a:

### Aplicación
- **Frontend**: [http://localhost](http://localhost) (puerto 80)
- **Backend API**: [http://localhost:8080](http://localhost:8080)

### Base de datos
- **PostgreSQL**:
  - Host: `localhost:5433`
  - Base de datos: `calesappdb`
  - Usuario: `<configurado en docker-compose>`
  - Contraseña: `<configurado en docker-compose>`

- **PGAdmin**: [http://localhost:5050](http://localhost:5050)
  - Email: `<configurado en docker-compose>`
  - Contraseña: `<configurado en docker-compose>`

---

## Documentación de la API
El acceso a la documentación de la API está protegido con autenticación.
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

**Credenciales de acceso**: Configuradas en el sistema de autenticación.

---

## Colección de Postman

[`ProyectoCalesapp.postman_collection.json`](docs/ProyectoCalesapp.postman_collection.json)

---

## Pruebas de autenticación

Para acceder a los endpoints protegidos (usuario y administrador), hay que seguir el siguiente flujo:

1. **Registro**: Al registrarse, se genera un token de verificación.
2. **Verificación**: Una vez verificado el registro, se habilita el acceso al inicio de sesión.
3. **Autenticación**: Al iniciar sesión, se genera un token JWT necesario para consumir los endpoints protegidos.

---

## Diseño UI

[Diseño Figma](https://www.figma.com/design/j4B1QGaIhNnBDOHB3CKBqm/Calesa?node-id=0-1&t=THystnwQQcP75NMc-1)

[Diseño Excalidraw](https://excalidraw.com/#json=j7m9msPvdRWd-6Zuf8EvS,B9joBmUKhWV5y1Oaw0Jw9g)

---

## Diagramas

![Modelo de datos](docs/diagrama-clases-dominio.drawio.png)
![Diagrama entidad - relación](docs/diagrama-entidad-relacion.drawio.png)


---

## Testing

### Backend (JUnit 5 & Mockito)

Las pruebas unitarias del backend utilizan **JUnit 5** y **Mockito** para simular dependencias y verificar la lógica de negocio.

**Ubicación de los tests**: `calesapp/src/test/java/`

**Ejemplo: ParadaServiceTest**

El servicio `ParadaService` gestiona la lógica de negocio relacionada con las paradas. Las pruebas simulan las dependencias de `ParadaRepository` y `CiudadRepository`.

**Métodos probados:**
- `findAll()`: Verifica la recuperación de todas las paradas
- `findById()`: Prueba la búsqueda por ID, tanto con resultados como sin ellos
- `create()`: Valida la creación de una nueva parada a partir de un DTO
- `update()`: Comprueba la actualización de una parada existente
- `delete()`: Verifica la eliminación correcta de una parada existente

**Ejecutar tests:**
```bash
cd calesapp
./mvnw test
```

### Frontend (Jasmine & Karma)

El frontend utiliza **Jasmine** como framework de testing y **Karma** como test runner.

**Ejecutar tests:**
```bash
cd proyecto-calesapp-ng
npm test
```

---

## Características adicionales

### Validaciones personalizadas

El proyecto incluye validaciones personalizadas implementadas mediante anotaciones:

- **@EmailBasico**: Validación de formato de email
- **@FechaPasada**: Verifica que la fecha sea anterior a la actual
- **@FechaFutura**: Verifica que la fecha sea posterior a la actual
- **@SinPalabrasProhibidas**: Filtrado de contenido inapropiado

### Búsquedas dinámicas

El sistema implementa el patrón **Specification** de Spring Data JPA para realizar búsquedas dinámicas y complejas en las entidades `Parada` y `Cochero`, permitiendo combinar múltiples criterios de búsqueda.

### Sistema de archivos

Soporta dos estrategias de almacenamiento configurables:

1. **Almacenamiento local**: Archivos guardados en `./uploads`
2. **Almacenamiento en Imgur**: Integración con API de Imgur

**Configuración** en `application.properties`:
```properties
storage.location=./uploads
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=10MB
imgur.clientid=${IMGUR_CLIENTID}
```

### Perfiles de configuración

El backend soporta diferentes perfiles de Spring:

- **dev**: Desarrollo con logging SQL activado
- **prod**: Producción sin logging SQL
- **pgsql**: Configuración para PostgreSQL

**Cambiar perfil** en `application.properties`:
```properties
spring.profiles.active=dev, pgsql
```

---

## Contenedores Docker

El stack de Docker Compose incluye 4 servicios principales:

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **PostgreSQL** | 5433 | Base de datos principal |
| **PGAdmin** | 5050 | Gestión de base de datos |
| **Spring Boot API** | 8080 | Backend REST API |
| **Nginx** | 80 | Servidor web del frontend |

Todos los servicios están conectados mediante la red `calesapp-network`.

---

## Configuración importante

### Variables de entorno

Crea un archivo `.env` en la raíz del proyecto para configurar variables sensibles:
```bash
# Ejemplo de archivo .env
IMGUR_CLIENTID=your_imgur_client_id_here

# Otras variables según necesidad
# DB_PASSWORD=your_db_password
# JWT_SECRET=your_jwt_secret
```

⚠️ **Importante**: Nunca incluyas el archivo `.env` en el control de versiones.

### Base de datos

Por defecto, la aplicación usa `spring.jpa.hibernate.ddl-auto=create-drop`, lo que **recrea el esquema en cada inicio**.

Para persistir los datos entre reinicios, cambia en `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=update
```

### Seguridad

⚠️ **IMPORTANTE - Antes de desplegar en producción:**

1. **Cambia el secreto JWT** en `application.properties`:
   ```properties
   jwt.secret=<genera_un_secreto_seguro_aleatorio>
   ```

2. **Actualiza las credenciales de base de datos** en `docker-compose.yml`

3. **Configura perfiles separados** para desarrollo y producción

4. **Revisa el archivo `.gitignore`** para asegurar que no se suban archivos sensibles

---

## Contribuciones

Este proyecto es un trabajo de fin de estudios de DAM (Desarrollo de Aplicaciones Multiplataforma).

---

## Autor

**Álvaro Lorente Almán**

---

## Licencia

Este proyecto es de uso educativo.


