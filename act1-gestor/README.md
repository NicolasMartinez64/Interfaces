# Práctica 1: Gestor de Tareas / Incidencias en JavaFX

**Asignatura:** Desarrollo de Interfaces (2º DAM)  
**Trabajo en pareja:** Ángel y Nicolás (2º DAM)  
**Tecnologías usadas:** Java 21, JavaFX, FXML, Scene Builder y Maven  

---

## 1. ¿De qué va la aplicación?
Es una aplicación de escritorio sencilla para gestionar tareas e incidencias (como las que tiene una empresa de informática). 

Permite:
- Ver de un vistazo los **4 contadores arriba**: Total de tareas, Pendientes, En Proceso y Completadas.
- Buscar tareas en tiempo real escribiendo en el buscador o filtrando por estado en el desplegable.
- Ver la lista completa en una tabla a la izquierda.
- Pinchar en una tarea para ver sus detalles a la derecha, cambiarle los datos o borrarla (te pide confirmación antes para no borrarla sin querer).
- Añadir tareas nuevas pulsando el botón verde `+ Nueva Tarea...`, que abre una ventana modal limpia para que no se dupliquen datos.

---

## 2. Cómo ejecutar el proyecto

### Desde la terminal con Maven:
Entra en la carpeta `act1-gestor` y escribe:
```bash
mvn clean javafx:run
```

### Desde Visual Studio Code:
1. Abre el archivo `Main.java` (está en `src/main/java/com/proyectodi/Main.java`).
2. Dale al botón de **Run** o pulsa **F5**.
*(Usamos esta clase `Main` puente porque si ejecutas `App.java` directo en VS Code salta el error de que faltan los componentes de JavaFX runtime).*

---

## 3. Reparto de tareas y sesiones (10 horas)

### Reparto en la pareja:
- **Ángel:** Diseño del boceto en papel (wireframe), maquetación de la pantalla en Scene Builder (`main_view.fxml`), enlace del `TableView` con el formulario y la ventana modal (`new_task.fxml`).
- **Nicolás:** Configuración de Maven (`pom.xml`), hoja de estilos `styles.css`, buscador con filtros de texto y estado, y los bucles para los 4 contadores de arriba.

### Las 5 sesiones de clase:
- **Sesión 1 (2h):** Pensar la distribución, requisitos del profesor y dibujar el wireframe.
- **Sesión 2 (2h):** Primera versión básica con código Java directo (sin FXML).
- **Sesión 3 (2h):** Crear la clase `Item.java`, meter datos de prueba y programar el filtro.
- **Sesión 4 (2h):** Diseñar la vista en Scene Builder, conectar el controlador y aplicar CSS.
- **Sesión 5 (2h):** Solucionar el fallo de duplicados con la ventana modal, quitar emojis rotos, poner confirmación al borrar y revisión final.

---

## 4. Estructura del proyecto

```
act1-gestor/
    pom.xml                                <- Archivo de configuración Maven
    README.md                              <- Este archivo con la explicación general
     capturas/                              <- Las 2 capturas pedidas en la práctica
        version-programatica.png           <- Versión 1 en código Java puro
        version-final.png                  <- Versión 2 final con FXML
    docs/                                  <- Los 4 documentos pedidos por el profesor
        wireframe.png                      <- Dibujo/boceto inicial de la pantalla
        decisiones-diseno.md               <- Por qué pusimos los botones y colores así
        reflexion-fxml.md                  <- Respuestas a las 8 preguntas sobre FXML
        IA.md                              <- Las dudas que le preguntamos a Gemini
    src/
        main/
             java/com/proyectodi/           <- Solo 5 clases Java
            resources/com/proyectodi/      <- 2 vistas FXML y styles.css
```

---

## 5. Documentos de la carpeta docs/

- [**Decisiones de Diseño (docs/decisiones-diseno.md)**](file:///c:/Proyectos/Desarrollo%20Interfaces/Act1-gestor/act1-gestor/docs/decisiones-diseno.md): Las 5 decisiones de diseño (contadores, modal, colores, etc.).
- [**Reflexión sobre FXML (docs/reflexion-fxml.md)**](file:///c:/Proyectos/Desarrollo%20Interfaces/Act1-gestor/act1-gestor/docs/reflexion-fxml.md): Respuestas a las 8 preguntas de la guía.
- [**Uso de IA con Gemini (docs/IA.md)**](file:///c:/Proyectos/Desarrollo%20Interfaces/Act1-gestor/act1-gestor/docs/IA.md): Las 9 consultas a Gemini y las cosas que descartamos.
- [**Boceto Wireframe (docs/wireframe.png)**](file:///c:/Proyectos/Desarrollo%20Interfaces/Act1-gestor/act1-gestor/docs/wireframe.png): El plano inicial de la pantalla.
