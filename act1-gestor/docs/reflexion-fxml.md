# Reflexión sobre FXML y Scene Builder

**Asignatura:** Desarrollo de Interfaces (2º DAM)  
**Proyecto:** Gestor de Tareas / Incidencias en JavaFX  
**Trabajo en pareja:** Ángel y Nicolás (2º DAM)  

---

En las primeras sesiones hicimos una primera versión de la interfaz metiendo los componentes directamente con código Java (se puede ver en la captura `capturas/version-programatica.png`). Después pasamos toda la pantalla a FXML con Scene Builder.

Aquí dejamos nuestras respuestas a las 8 preguntas que pedía la práctica:

### 1. ¿Qué problema resuelve FXML en una aplicación JavaFX?
Resuelve el lío de tener mezclado el diseño visual con el código de Java. 
En la versión inicial teníamos que crear los botones, paneles, márgenes y colores todo dentro del método Java. El código se hacía larguísimo y si querías mover un botón tenías que cambiar un montón de líneas. Con FXML el diseño se queda en su propio archivo XML y en Java solo programas la lógica y lo que pasa cuando pulsas los botones.

### 2. ¿Qué información contiene un archivo .fxml?
Básicamente contiene la estructura de la ventana en formato XML:
- Los paneles que usamos para colocar las cosas (`VBox`, `HBox`, `SplitPane`, `GridPane`).
- Los controles que ve el usuario (la tabla, los campos de texto, los combos y los botones).
- Los identificadores (`fx:id`) para poder usarlos desde Java.
- Los métodos que se ejecutan al hacer clic (por ejemplo `onAction="#handleSaveChanges"`).
- La hoja de estilos CSS que tiene asociada (`styles.css`).

### 3. ¿Qué relación existe entre FXML y las clases JavaFX?
Cada etiqueta de FXML equivale a una clase de JavaFX. Por ejemplo:
- Poner `<Button text="Guardar" />` en el FXML es exactamente lo mismo que hacer en Java `Button btn = new Button("Guardar");`.
- Los atributos del XML son como llamar a los métodos setter en Java (por ejemplo `spacing="12.0"` es como hacer `vbox.setSpacing(12.0)`).
- En la parte de arriba del archivo FXML se ponen los `<?import ...?>`, que funcionan igual que los `import` normales de Java.

### 4. ¿Qué función cumple FXMLLoader?
Es la clase de JavaFX que se encarga de leer el archivo `.fxml` del disco y convertirlo en objetos reales de Java en la memoria del ordenador. 
Cuando haces `loader.load()`, busca el archivo FXML, crea los componentes, busca la clase controladora (`fx:controller`), conecta las variables que tienen `@FXML` con sus componentes y ejecuta el método `initialize()` para que la pantalla empiece a funcionar.

### 5. ¿Qué utilidad tienen fx:id y la anotación @FXML?
- **`fx:id`:** Es el nombre o identificador que le pones a una etiqueta en el archivo FXML (por ejemplo `fx:id="tableItems"`). Sirve para que JavaFX sepa qué componente es cuál.
- **`@FXML`:** Es la etiqueta que pones en el código Java encima de la variable o del método para que `FXMLLoader` pueda conectarlos aunque sean privados, sin tener que ponerlos públicos ni hacer getters y setters para todo.

### 6. ¿Qué papel tiene el controlador?
El controlador es el que hace que la interfaz sirva para algo:
- Carga los datos de prueba al arrancar en el `initialize()`.
- Escucha lo que hace el usuario (cuando escribe en el buscador o pulsa un botón).
- Hace los filtros de la tabla y vuelve a contar las tareas de los contadores de arriba.
- Abre la ventana modal cuando le das a añadir una tarea nueva.

### 7. ¿Qué aporta Scene Builder al proceso de diseño?
Aporta mucha rapidez porque puedes diseñar la pantalla arrastrando botones, tablas y paneles con el ratón como si fuera un programa de dibujo. Ves el resultado al instante sin tener que compilar y ejecutar el proyecto cada vez que cambias un margen o un color. Además te genera el XML solo y te ahorra equivocarte escribiendo etiquetas a mano.

### 8. ¿Qué ha mejorado y qué no ha mejorado en nuestra aplicación después de la refactorización?

**Lo que sí ha mejorado:**
- El código de Java ha quedado mucho más corto, limpio y fácil de leer.
- Fue mucho más sencillo diseñar la ventana modal para añadir tareas nuevas.
- Con el archivo `styles.css` pudimos cambiar los colores y tamaños de los botones sin tocar nada de código Java.

**Lo que no ha mejorado (dificultades que tuvimos):**
- Si te equivocas en una letra al poner el `fx:id` en Java o en el FXML, el compilador no te avisa con error rojo; el programa compila bien y luego te peta al ejecutar con un `NullPointerException`.
- Tuvimos que pegarnos un rato con Maven para que encontrase bien las rutas de los archivos FXML y con VS Code para que arrancase JavaFX sin dar error de componentes de runtime.
- Ahora tenemos más archivos repartidos en carpetas en vez de tenerlo todo en una sola clase.
