# Decisiones de Diseño de la Interfaz

**Asignatura:** Desarrollo de Interfaces (2º DAM)  
**Proyecto:** Gestor de Tareas / Incidencias en JavaFX  
**Trabajo en pareja:** Ángel y Nicolás (2º DAM)  

---

Para esta práctica hemos tomado varias decisiones de diseño para que el programa sea fácil de usar, no resulte confuso y se vea ordenado:

### 1. Poner los contadores (KPIs) arriba del todo
- **Por qué lo hicimos:** Nada más abrir la aplicación, queríamos que se viera de un vistazo cómo va el trabajo sin tener que contar las filas de la tabla a mano.
- **Cómo quedó:** Pusimos cuatro tarjetas arriba en horizontal con los 4 datos que pedía la práctica: **Total de tareas**, **Pendientes** (en naranja para que llame la atención), **En Proceso** (en azul) y **Completadas** (en verde). Además, en la barra del buscador pusimos un texto que te dice cuántas tareas hay visibles según lo que escribas.

### 2. Separar la creación de tareas en una ventana modal
- **Por qué lo hicimos:** En la primera versión teníamos el botón de "Añadir Tarea" en el mismo formulario de la derecha. El problema era que si tenías una tarea seleccionada en la tabla y le dabas a añadir, se duplicaba la tarea o pisaba los datos de la que ya estaba.
- **Cómo quedó:** Quitamos ese botón del formulario y pusimos un botón verde grande arriba (`+ Nueva Tarea...`). Al pulsarlo se abre una ventana emergente (modal) que bloquea la de atrás. Así metes los datos desde cero y no hay ningún fallo de duplicados.

### 3. Usar Maestro-Detalle con SplitPane
- **Por qué lo hicimos:** Si metes todos los datos y botones dentro de la propia tabla, queda todo apretado y es incómodo de leer.
- **Cómo quedó:** Dividimos la pantalla en dos con un `SplitPane`: a la izquierda la tabla con las columnas justas (ID, Tarea, Categoría, Horas y Estado), y a la derecha el formulario que se rellena solo al pinchar en cualquier fila para poder ver o cambiar los datos.

### 4. Colores sencillos con significado claro
- **Por qué lo hicimos:** No queríamos una pantalla sosa en blanco y negro, pero tampoco llenarla de colores chillones que mareen.
- **Cómo quedó:** Usamos una paleta básica en el archivo `styles.css`: fondo gris suave para que no canse la vista, azul para guardar cambios, verde para cosas nuevas (como añadir tarea), rojo para eliminar y naranja para avisar de tareas pendientes. Además, todos los botones llevan texto descriptivo para que no dependa solo del color.

### 5. Quitar emojis rotos y pedir confirmación antes de borrar
- **Por qué lo hicimos:** 
  1. En una versión de prueba pusimos iconos de emojis en los botones (`💾`, `🗑️`), pero al probar el proyecto en los ordenadores de clase con Windows salían como rectángulos vacíos (`▯`). Decidimos quitarlos y dejar texto normal bien claro.
  2. También nos dimos cuenta de que si le dabas al botón de borrar se borraba la tarea al instante sin preguntar. Por si el usuario le da sin querer, pusimos una ventanita de confirmación que te pregunta si de verdad la quieres eliminar.
  3. En la ventana de añadir tarea pusimos que pulsando `Enter` se guarde y con `Escape` se cierre, que es mucho más cómodo que usar siempre el ratón.
