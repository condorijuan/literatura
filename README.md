# 📚 Literatura - Catálogo de Libros

Aplicación de consola desarrollada con **Spring Boot** que permite gestionar un catálogo de libros y autores mediante la integración con la API de Gutendex.

## 🚀 Características

- **Búsqueda de libros**: Busca libros por título desde la API de Gutendex
- **Gestión de base de datos**: Almacena libros y autores en una base de datos relacional
- **Consultas avanzadas**: 
  - Listar todos los libros registrados
  - Listar todos los autores registrados
  - Buscar autores vivos en un año específico
  - Filtrar libros por idioma

## 🛠️ Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **Jackson** (para procesamiento JSON)
- **Jakarta Persistence API**
- **Base de datos relacional** (H2/PostgreSQL/MySQL)

## 📋 Requisitos Previos

- JDK 17 o superior
- Maven 4+
- IDE compatible (IntelliJ IDEA recomendado)

## 🔧 Instalación

1. Clona el repositorio:
```bash
git clone <url-del-repositorio>
```

2. Navega al directorio del proyecto:
```bash
cd literatura
```

3. Configura la base de datos en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

4. Compila el proyecto:
```bash
mvn clean install
```

5. Ejecuta la aplicación:
```bash
mvn spring-boot:run
```

## 📖 Uso

Al ejecutar la aplicación, se mostrará un menú interactivo con las siguientes opciones:

```
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma
0. Salir
```

### Ejemplos de uso:

**Buscar un libro:**
- Selecciona opción `1`
- Ingresa el título del libro (ej: "Don Quijote")
- La aplicación buscará en Gutendex y luego mostrará y guardará los resultados

**Consultar autores vivos en un año:**
- Selecciona opción `4`
- Ingresa el año (ej: 1850)
- Se mostrarán los autores que estaban vivos en ese año

**Filtrar por idioma:**
- Selecciona opción `5`
- Ingresa el código de idioma (ej: `en` para inglés, `es` para español)

## 📁 Estructura del Proyecto

```
com.juan.literatura
├── Model
│   ├── Autor.java
│   ├── Libro.java
│   ├── DatosAutor.java
│   ├── DatosLibro.java
│   └── Datos.java
├── Repositorio
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── Service
│   ├── ConsumoAPI.java
│   ├── ConvierteDatos.java
│   └── IConvierteDatos.java
├── Principal
│   └── Principal.java
└── LiteraturaApplication.java
```

## 🗄️ Modelo de Datos

### Entidad Libro
- ID (generado automáticamente)
- Título
- Resumen
- Idioma
- Número de descargas
- Autores (relación muchos a muchos)

### Entidad Autor
- ID (generado automáticamente)
- Nombre
- Fecha de nacimiento
- Fecha de fallecimiento
- Libros (relación muchos a muchos)

## 🌐 API Externa

La aplicación consume datos de **Gutendex API**:
- URL base: `https://gutendex.com/books/`
- Endpoint de búsqueda: `https://gutendex.com/books/?search={título}`

## 📝 Licencia

Este proyecto está bajo la Licencia MIT.

## ✉️ Contacto
**GitHub**: [@condorijuan](https://github.com/condorijuan)
