# Registro de Uso de IA en la Práctica (Gemini)

**Asignatura:** Desarrollo de Interfaces (2º DAM)  
**Proyecto:** Gestor de Tareas / Incidencias en JavaFX  
**Herramienta utilizada:** Google Gemini  
**Trabajo en pareja:** Ángel y Nicolás (2º DAM)  

---

## 1. Cómo hemos usado la IA
Durante la práctica hemos usado Gemini como apoyo cuando nos quedábamos atascados con algún error de JavaFX o de Maven. Siguiendo lo que nos dijo el profesor en clase, no hemos apuntado preguntas tontas (como recordar la sintaxis de un bucle), sino las dudas de verdad que nos ayudaron a sacar el proyecto adelante.

Aquí dejamos las consultas principales y los fallos que nos dio la IA:

---

## 2. Consultas principales realizadas

### Consulta 1: Conectar las columnas de la tabla con el modelo Item
- **Fecha:** 21/10/2026
- **Problema:** La tabla cargaba las filas pero las celdas salían en blanco. No sabíamos cómo enlazar las columnas con las variables `Property` de la clase `Item`.
- **Lo que le preguntamos a Gemini:**  
  > *"En JavaFX tengo una clase Item con propiedades StringProperty, DoubleProperty e IntegerProperty. ¿Cómo configuro las columnas del TableView en el controller para que muestren los datos correctamente usando setCellValueFactory?"*
- **Lo que nos respondió y qué usamos:** Nos explicó que al usar `Property` en el modelo podíamos enlazar las columnas directamente con una función lambda: `col.setCellValueFactory(cellData -> cellData.getValue().nameProperty())`. Usamos esa línea adaptándola a cada una de las 5 columnas en el método `setupTableColumns()`.
- **Qué modificamos:** Cambiamos los nombres para que coincidieran con los de nuestro `Item.java` y pusimos los tipos correctos (`String` y `Number`) en las columnas.
- **Cómo lo comprobamos:** Arrancamos el programa y vimos que la tabla ya mostraba los nombres, categorías, horas y estados perfectamente.
- **Qué aprendimos:** Aprendimos a usar `setCellValueFactory` con lambdas para que la tabla se actualice sola si cambia el modelo.

---

### Consulta 2: El filtro que busca por texto y por estado a la vez
- **Fecha:** 23/10/2026
- **Problema:** Queríamos que al escribir en el buscador y al cambiar el combo de estado la tabla se filtrara sola en tiempo real, pero sin borrar los datos originales de la lista.
- **Lo que le preguntamos a Gemini:**  
  > *"¿Cómo filtrar un TableView en JavaFX combinando un TextField y un ComboBox al mismo tiempo sin borrar los datos originales de la lista?"*
- **Lo que nos respondió y qué usamos:** Nos dijo que metiéramos la lista normal en un `FilteredList` y que le pusiéramos listeners al campo de texto y al combo para que cada vez que cambien se aplique el filtro (`setPredicate`).
- **Qué modificamos:** Gemini nos pasó un código larguísimo y lleno de cosas raras. Lo simplificamos a dos condiciones booleanas normales (`matchesSearch` y `matchesStatus`) con `toLowerCase()` para que no falle si pones mayúsculas o minúsculas.
- **Cómo lo comprobamos:** Escribimos palabras en el buscador y fuimos cambiando el combo entre "Todos", "Pendiente" y "Completada"; vimos que la tabla mostraba justo lo que tocaba.
- **Qué aprendimos:** Cómo funciona `FilteredList` como una capa por encima de la lista original sin machacar los datos.

---

### Consulta 3: Abrir la ventana modal y pasarle los datos
- **Fecha:** 26/10/2026
- **Problema:** Como teníamos el fallo de que al añadir una tarea se duplicaba la que estaba seleccionada, queríamos abrir una ventana nueva para meter los datos y que al guardar apareciera en la tabla principal.
- **Lo que le preguntamos a Gemini:**  
  > *"En JavaFX quiero que al pulsar un botón se abra otra ventana FXML en modo modal (bloqueando la anterior) y pasarle la lista de datos del primer controller al segundo controller para añadir un nuevo elemento."*
- **Lo que nos respondió y qué usamos:** Nos dio el código para cargar el FXML con `FXMLLoader`, coger el controlador de la ventana modal con `loader.getController()`, pasarle la lista con un método `setMasterData()` y bloquear la ventana principal con `APPLICATION_MODAL`.
- **Qué modificamos:** Le pusimos un tamaño fijo de 440x360 para que no se pudiera deformar la ventana y añadimos un aviso para que no te deje guardar si el nombre de la tarea está vacío.
- **Cómo lo comprobamos:** Abrimos la ventana con el botón verde, creamos una tarea de prueba y al pulsar Guardar vimos que se cerraba sola y salía en la tabla principal.
- **Qué aprendimos:** A pasar datos de un controlador a otro con un método setter normal y cómo abrir ventanas modales en JavaFX.

---

### Consulta 4: Error de JavaFX runtime al pulsar Run en VS Code
- **Fecha:** 21/10/2026
- **Problema:** Al darle al botón de ejecutar en VS Code sobre `App.java`, nos salía un error en la consola diciendo: `Error: faltan los componentes de JavaFX runtime y son necesarios para ejecutar esta aplicación`.
- **Lo que le preguntamos a Gemini:**  
  > *"Tengo un proyecto JavaFX con Maven en VS Code y al ejecutar la clase que extiende de Application me dice que faltan los componentes de JavaFX runtime. ¿Cómo lo arreglo de forma simple sin tener que pasar argumentos de VM por consola?"*
- **Lo que nos respondió y qué usamos:** Nos explicó que en Java moderno, si ejecutas una clase que hereda de `Application`, la máquina virtual se queja si no le pasas los módulos por comandos. La solución fácil era crear una clase secundaria `Main.java` con un `main` normal de toda la vida que llame a `App.main(args)`.
- **Qué modificamos:** Creamos la clase `Main.java` con solo 4 líneas y pusimos en `launch.json` que ejecute esa clase.
- **Cómo lo comprobamos:** Le dimos a F5 y la aplicación arrancó directa sin dar ningún error.
- **Qué aprendimos:** El truco del lanzador secundario para ahorrarnos configurar comandos raros de VM en el editor.

---

### Consulta 5: Contar tareas por estado y calcular el siguiente ID sin streams raros
- **Fecha:** 26/10/2026
- **Problema:** Al meter una tarea nueva en la ventana modal necesitábamos calcular el siguiente ID autoincremental, y en la pantalla principal queríamos contar cuántas tareas hay en cada estado (Pendiente, En Progreso, Completada) para actualizar los contadores.
- **Lo que le preguntamos a Gemini:**  
  > *"¿Cómo calcular en Java el siguiente ID autoincremental de una ObservableList y cómo contar los elementos según su estado para actualizar etiquetas en JavaFX?"*
- **Lo que nos respondió y qué usamos:** Gemini nos propuso hacerlo con programación funcional y streams: `masterData.stream().mapToInt(Item::getId).max().orElse(0) + 1` y con `masterData.stream().filter(...).count()`. Nos dimos cuenta de que eso era un lío innecesario para lo que hemos dado en clase de DAM.
- **Qué modificamos:** Descartamos los streams y lo cambiamos por un bucle `for` de toda la vida con variables contadoras (`pendientes++`, `enProgreso++`, etc.) y un `if (item.getId() >= nextId) nextId = item.getId() + 1;` que cualquiera de los dos sabe explicar en la pizarra si nos pregunta el profe.
- **Cómo lo comprobamos:** Añadimos varias tareas desde la ventana modal y comprobamos que los IDs se asignaban correlativos (1, 2, 3, 4, 5...) y que los 4 contadores de arriba subían y bajaban perfectamente.
- **Qué aprendimos:** Que no hace falta complicar el código con streams si un bucle `for` clásico hace lo mismo, se lee mejor y es más fácil de depurar.

---

### Consulta 6: Diálogo de confirmación antes de borrar una tarea (Alert CONFIRMATION)
- **Fecha:** 27/10/2026
- **Problema:** Si pulsabas por error el botón rojo de eliminar, la tarea desaparecía al momento sin dar opción a cancelar. En clase nos avisaron de que en interfaces borrar datos sin pedir confirmación es una mala práctica.
- **Lo que le preguntamos a Gemini:**  
  > *"¿Cómo mostrar un cuadro de diálogo emergente de confirmación en JavaFX con botones de Sí y No antes de borrar un elemento de una lista?"*
- **Lo que nos respondió y qué usamos:** Nos explicó cómo instanciar `Alert` con el tipo `Alert.AlertType.CONFIRMATION`, pasándole los botones `ButtonType.YES` y `ButtonType.NO`, y cómo comprobar con `confirm.getResult() == ButtonType.YES` si el usuario aceptó.
- **Qué modificamos:** Le quitamos la cabecera gris por defecto con `confirm.setHeaderText(null)` para que se vea más limpio, y en el mensaje le metimos el nombre de la tarea entre comillas (`"¿Seguro que quieres eliminar la tarea \"" + selectedItem.getName() + "\"?"`) para que el usuario esté 100% seguro de qué va a borrar.
- **Cómo lo comprobamos:** Seleccionamos una tarea, le dimos a eliminar y pulsamos "No": la tarea se mantuvo intacta. Volvimos a darle y pulsamos "Sí": la tarea se borró de la tabla y los contadores se actualizaron.
- **Qué aprendimos:** A usar los cuadros de diálogo estándar de JavaFX (`Alert`) y a capturar la respuesta del usuario con `ButtonType`.

---

### Consulta 7: Cerrar la ventana modal desde el botón Cancelar (Stage.close())
- **Fecha:** 26/10/2026
- **Problema:** En el controlador de la ventana modal (`NewTaskController.java`) teníamos el método `@FXML private void handleCancel()`, pero no sabíamos cómo cerrar la ventana porque en ese controlador no teníamos guardada ninguna variable `Stage`.
- **Lo que le preguntamos a Gemini:**  
  > *"En el controlador de una ventana secundaria FXML en JavaFX, ¿cómo puedo cerrar la ventana actual desde un método del controlador si no tengo la variable Stage?"*
- **Lo que nos respondió y qué usamos:** Nos explicó que en JavaFX cualquier componente visual (como un `TextField` o un `Button`) conoce la escena (`Scene`) en la que está metido, y la escena conoce su ventana (`Window` / `Stage`). Así que podíamos obtenerla con `Stage stage = (Stage) txtName.getScene().getWindow(); stage.close();`.
- **Qué modificamos:** Creamos un método privado `closeWindow()` para no repetir esas líneas de código y lo llamamos tanto en el botón de Cancelar como al terminar de guardar la tarea con éxito.
- **Cómo lo comprobamos:** Abrimos la ventana con el botón verde, pulsamos "Cancelar" y la ventana se cerró al instante sin dar excepciones ni tocar los datos de la tabla principal.
- **Qué aprendimos:** Cómo navegar por la jerarquía visual de JavaFX para llegar desde un control cualquiera hasta la ventana principal o modal.

---

### Consulta 8: Atajos de teclado en el formulario modal (Enter para Guardar y Escape para Cancelar)
- **Fecha:** 27/10/2026
- **Problema:** En la ventana modal de meter tareas era un rollo tener que usar siempre el ratón para pinchar en "Guardar" o en "Cancelar". Queríamos que al pulsar `Enter` se guardara y con `Escape` se cancelara directamente.
- **Lo que le preguntamos a Gemini:**  
  > *"¿Cómo hacer que en una ventana de formulario en JavaFX se pueda guardar pulsando Enter y cancelar pulsando Escape sin tener que ponerle listeners de teclado a cada TextField?"*
- **Lo que nos respondió y qué usamos:** Nos dijo que JavaFX ya tiene eso pensado de serie en los propios botones mediante los atributos `defaultButton="true"` y `cancelButton="true"`.
- **Qué modificamos:** En vez de meter código Java en el controlador con eventos `KeyEvent`, añadimos directamente esos dos atributos en las etiquetas `<Button>` de `new_task.fxml`.
- **Cómo lo comprobamos:** Abrimos la modal, escribimos el nombre de una tarea, pulsamos la tecla Enter y se guardó sola. Luego abrimos otra, pulsamos Escape y se cerró sin guardar.
- **Qué aprendimos:** Que FXML y JavaFX tienen atajos de accesibilidad integrados en los botones para mejorar la experiencia de usuario sin escribir una sola línea de código en Java.

---

### Consulta 9: Estilo visual para campos deshabilitados o de solo lectura en CSS
- **Fecha:** 27/10/2026
- **Problema:** En el panel de la derecha pusimos el campo del ID con `editable="false"` para que no se pueda modificar a mano. Pero como por defecto en JavaFX se veía con el mismo fondo blanco y borde que los demás campos, parecía que estaba roto o que no respondía al escribir.
- **Lo que le preguntamos a Gemini:**  
  > *"En JavaFX CSS, ¿cómo cambiar el estilo de un TextField cuando está en modo editable=false o disabled para que se note claramente que es de solo lectura?"*
- **Lo que nos respondió y qué usamos:** Nos explicó que JavaFX tiene pseudo-clases en CSS para los estados de los controles. Nos dio la regla `.text-field:disabled, .text-field:read-only` para cambiarle el color de fondo y el color del texto.
- **Qué modificamos:** Lo añadimos en `styles.css` usando el color gris suave de nuestra paleta (`-fx-background-color: #f1f5f9; -fx-text-fill: #64748b; -fx-border-color: #cbd5e1;`) para que no rompiera con el resto de la interfaz.
- **Cómo lo comprobamos:** Abrimos la aplicación y comprobamos que al seleccionar cualquier tarea, el campo ID aparece sombreado en gris claro dejando claro que es un dato informativo que no se toca.
- **Qué aprendimos:** A usar pseudo-clases en JavaFX CSS para dar feedback visual claro sobre qué campos se pueden editar y cuáles no.

---

## 3. Cosas que Gemini nos dijo mal o que tuvimos que cambiar

No nos fiamos al 100% de lo que decía la IA porque nos dio varios fallos:

1. **Emojis que salían rotos (`▯`):** Gemini nos dijo de poner emojis de disquetes y papeleras en los botones para que quedara más bonito. Al probarlo en los PCs de clase con Windows salían como rectángulos vacíos rotos. Los borramos todos y pusimos texto normal en español.
2. **Dependencias viejas en el `pom.xml`:** Al principio nos dio un `pom.xml` con versiones viejas de JavaFX 17 y librerías de JUnit que no usábamos para nada. Lo limpiamos a mano y dejamos solo lo justo para Java 21.
3. **El fallo del signo `$` en el FXML:** Nos sugirió poner `text="$0.00"` en una etiqueta. Al compilar nos dio un error rarísimo (`IllegalArgumentException: Invalid path`) porque en FXML el dólar sirve para enlazar datos. Tuvimos que buscar cómo escaparlo con `\$`.
4. **Streams demasiado complicados:** Para contar las tareas nos quería meter streams con `.filter(...).count()`. Decidimos cambiarlo por bucles `for` normales de clase con contadores, que se entienden mucho mejor y no parecen sacados de StackOverflow.

---

## 4. Conclusión sobre el uso de IA
La IA viene muy bien para desatascarte cuando te sale un error raro de Maven o de JavaFX y no sabes por qué falla. Pero si copias y pegas sin mirar, te mete cosas que no funcionan o código demasiado raro que luego no sabes explicar en clase.
