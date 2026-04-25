# Backend y Orquestación - Sistema de Gestión Escolar

Este repositorio contiene la API REST en Spring Boot, la base de datos PostgreSQL y la orquestación completa con Docker Compose para levantar todo el ecosistema (Frontend y Backend).

---

## ⚠️ Instrucciones Críticas de Instalación

Para que la orquestación con Docker funcione correctamente, **este repositorio y el repositorio del frontend deben estar clonados en la misma carpeta padre**, ya que el archivo `docker-compose.yml` busca el código del frontend en una ruta relativa (`../frontend`).

Sigue estos pasos exactos para clonar y levantar el proyecto:

**1. Crea una carpeta principal y entra en ella:**

```bash
mkdir prueba-tecnica-fullstack
cd prueba-tecnica-fullstack
```

**2. Clona el repositorio del Frontend** (Asegúrate de que la carpeta se llame `frontend`):

```bash
git clone <https://github.com/lauvale029/prueba-tecnica-frontend.git> frontend
```

**3. Clona este repositorio del Backend** (Asegúrate de que la carpeta se llame `backend`):

```bash
git clone <https://github.com/lauvale029/prueba-tecnica-backend.git> backend
```

Tu estructura final debe verse exactamente así:

```
📁 prueba-tecnica-fullstack/
├── 📁 backend/   <-- (Estás aquí. Contiene el docker-compose.yml)
└── 📁 frontend/  <-- (Contiene la app React)
```

---

## 🚀 Despliegue con Docker

1. Abre una terminal y ubícate dentro de la carpeta `backend` que acabas de clonar.
2. Ejecuta el siguiente comando para construir y levantar todos los contenedores:

```bash
docker-compose up -d --build
```

### Accesos de la Aplicación

| Servicio | URL | Puerto |
|---|---|---|
| Frontend (Interfaz de Usuario) | http://localhost | 80 |
| Backend materias | http://localhost:8082/api/materias | 8082 |
| Backend alumnos | http://localhost:8082/api/alumnos | 8082 |
| Backend notas | http://localhost:8082/api/notas | 8082 |
| Base de Datos (host) | localhost | 5433 |

> El puerto interno de PostgreSQL es `5432`; se expone como `5433` en la máquina host.

---

## 🗄️ Restauración de Datos de Prueba

El proyecto incluye un archivo `.dump` con datos de prueba reales (Alumnos, Materias y Notas). Para cargarlos en la base de datos levantada por Docker, ejecuta este comando desde la carpeta `backend`:

```bash
docker exec -i db_prueba_tecnica pg_restore -U admin -d escuela_db -1 < ./db_dump/datos_prueba.dump
```

> **Nota:** No mostrará ningún mensaje de éxito en la consola si funciona correctamente. Simplemente recarga el frontend y verás los datos.

---

## 🛠️ Tecnologías del Backend

- **Java 17** + Spring Boot 3
- **Spring Data JPA** / Hibernate
- **PostgreSQL 15**
- **Docker** & Docker Compose