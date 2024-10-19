# Caso Práctico Tema 2:Implementación de un Sistema de Análisis de Datos Concurrente en Umbrella Corporation

[Repositorio](https://github.com/ConcurrenteCasoPracticoTema2/demo.git)

Proyecto realizado por Jose Daniel Martín, Hugo Sanchez, Fernando Santamaría y Jose Antonio Oyono

## Introducción

Este proyecto es una aplicación web construida con Spring Boot, que se centra en el procesamiento y visualización de datos de IQ de diferentes países. La aplicación utiliza concurrencia mediante la implementación de varios `ExecutorService` para realizar tareas en paralelo, tales como el procesamiento de datos y la transmisión de eventos del servidor (SSE) a la interfaz de usuario.

Además, la aplicación cuenta con varios componentes que gestionan el procesamiento de hilos, la limpieza de base de datos y la autenticación de usuarios. La interfaz gráfica está diseñada para visualizar de manera interactiva gráficos basados en los datos procesados. Estos gráficos incluyen diagramas de dispersión, gráficos de barras y líneas, que permiten analizar la relación entre el IQ, el rango de los países, el gasto en educación y la temperatura promedio.

La estructura del proyecto se divide en varios paquetes clave que se encargan de diferentes aspectos funcionales de la aplicación, como la gestión de hilos (`hilo`), el manejo de datos de IQ (`IQ`), la limpieza de base de datos (`LimpiezaBase`), y la administración de usuarios (`Usuario`).

Cada paquete tiene una funcionalidad bien definida y utiliza tecnologías como JPA para el mapeo de entidades a la base de datos, REST para el manejo de solicitudes HTTP y SSE para la transmisión de datos en tiempo real.

## Paquete hilo

### `ConsoleMenuLauncher`

Esta clase es un componente de Spring que se encarga de abrir el navegador web en una URL específica cuando la aplicación se inicia.

#### Métodos

- **`openBrowser()`**: Método anotado con `@PostConstruct` que se ejecuta después de que el bean ha sido inicializado. Utiliza un `ExecutorService` para abrir el navegador web en la URL especificada dependiendo del sistema operativo.

### `CustomThreadFactory`

Esta clase implementa la interfaz `ThreadFactory` y se utiliza para crear hilos personalizados con nombres específicos.

#### Métodos

- **`newThread(Runnable r)`**: Crea un nuevo hilo con un nombre personalizado que incluye un contador incremental.

### `ExecutorServiceConfig`

Esta clase es una configuración de Spring que define varios `ExecutorService` como beans.

#### Métodos

- **`fixedThreadPool()`**: Crea un `ExecutorService` con un pool fijo de 8 hilos.
- **`fixedThreadPool2()`**: Crea un `ExecutorService` con un pool fijo de 8 hilos.
- **`singleThreadExecutor()`**: Crea un `ExecutorService` con un solo hilo.
- **`customThreadPool()`**: Crea un `ExecutorService` con un pool de hilos según demanda.
- **`fixedThreadPool3()`**: Crea un `ExecutorService` con un pool fijo de 8 hilos.
- **`fixedThreadPool4()`**: Crea un `ExecutorService` con un pool fijo de 8 hilos.

### `ExecutorServiceFactory`

Esta clase es un componente de Spring que proporciona métodos para crear diferentes tipos de `ExecutorService`.

#### Métodos

- **`createFixedThreadPool(int threadCount)`**: Crea un `ExecutorService` con un número fijo de hilos.
- **`createSingleThreadExecutor()`**: Crea un `ExecutorService` con un solo hilo.
- **`createCachedThreadPool()`**: Crea un `ExecutorService` que maneja tareas de manera asíncrona.
- **`createCustomThreadPool(int threadCount)`**: Crea un `ExecutorService` con un número fijo de hilos utilizando un `CustomThreadFactory`.

### `ExecutorServiceTask`

Esta clase es un servicio de Spring que utiliza varios `ExecutorService` para procesar datos de manera concurrente y enviar información a través de SSE (Server-Sent Events).

#### Métodos

- **`printIQDataWithEmitter(SseEmitter emitter)`**: Procesa una lista de `IQData` y envía la información de los hilos que están procesando los datos mediante SSE.
- **`printCountriesAndRanksWithEmitter(SseEmitter emitter)`**: Procesa una lista de `IQData` y envía información sobre los países y sus rangos mediante SSE.
- **`printCountriesIQAndTempWithEmitter(SseEmitter emitter)`**: Procesa una lista de `IQData` y envía información sobre los países, sus IQ y temperatura promedio mediante SSE.
- **`printCountriesIQAndAdditionalDataWithEmitter(SseEmitter emitter)`**: Procesa una lista de `IQData` y envía información adicional sobre los países mediante SSE.
- **`shutdownExecutors()`**: Cierra los `ExecutorService` para liberar recursos.

## Paquete IQ

### `IQData`

Esta clase representa la tabla `iq_data` en la base de datos y está anotada con anotaciones JPA para el mapeo ORM.

#### Campos

- **`id`**: La clave primaria de la tabla.
- **`rank`**: El rango del país basado en el IQ.
- **`country`**: El nombre del país.
- **`IQ`**: El nivel de IQ del país.
- **`educationExpenditure`**: El gasto en educación del país.
- **`avgIncome`**: El ingreso promedio del país.
- **`avgTemp`**: La temperatura promedio del país.

### `IQDataController`

Esta clase es un controlador REST de Spring que maneja las solicitudes HTTP relacionadas con `IQData`.

#### Métodos

- **`uploadCSV()`**: Maneja la solicitud POST a `/IQ_level_limpio.csv`. Elimina todos los registros existentes y guarda nuevos datos desde un archivo CSV.

### `IQDataRepository`

Esta interfaz extiende `JpaRepository` y proporciona métodos para interactuar con la tabla `iq_data`.

#### Métodos

- **`truncateTable()`**: Trunca la tabla `iq_data`.
- **`resetAutoIncrement()`**: Restablece el valor de auto-incremento de la tabla `iq_data`.

### `IQDataService`

Esta clase es un servicio de Spring que proporciona métodos para gestionar `IQData`.

#### Métodos

- **`deleteAll()`**: Elimina todos los registros de la tabla `iq_data`.
- **`saveCSVData()`**: Lee datos de un archivo CSV y los guarda en la tabla `iq_data`.

## Paquete LimpiezaBase

### `CleanupListener`

Esta clase es un componente de Spring que escucha el evento `ContextClosedEvent` y desencadena la limpieza de la base de datos cuando la aplicación se detiene.

#### Métodos

- **`onApplicationEvent(ContextClosedEvent event)`**: Este método se activa cuando el contexto de la aplicación se cierra. Llama al método `clearDatabase()` del `DatabaseCleanupService` para vaciar la base de datos y registra la acción.

### `DatabaseCleanupService`

Esta clase es un servicio de Spring que proporciona métodos para limpiar la base de datos.

#### Métodos

- **`clearDatabase()`**: Este método ejecuta un comando SQL para truncar la tabla `usuario`, vaciando efectivamente todos sus datos.

## Paquete Usuario

### `Usuario`

Esta clase representa la entidad `Usuario` en la base de datos y está anotada con anotaciones JPA para el mapeo ORM.

#### Campos

- **`id`**: La clave primaria de la tabla.
- **`nombre`**: El nombre del usuario.
- **`contraseña`**: La contraseña del usuario.
- **`isAdmin`**: Indica si el usuario es administrador.

### `UsuarioController`

Esta clase es un controlador REST de Spring que maneja las solicitudes HTTP relacionadas con `Usuario`.

#### Métodos

- **`vaciarTabla()`**: Maneja la solicitud POST a `/usuarios/vaciar`. Vacía la tabla de usuarios.
- **`getAllUsuarios()`**: Maneja la solicitud GET a `/usuarios`. Devuelve una lista de todos los usuarios.
- **`getUsuarioById(Integer id)`**: Maneja la solicitud GET a `/usuarios/{id}`. Devuelve un usuario por su ID.
- **`createUsuario(Usuario usuario)`**: Maneja la solicitud POST a `/usuarios`. Crea un nuevo usuario.
- **`deleteUsuario(Integer id)`**: Maneja la solicitud DELETE a `/usuarios/{id}`. Elimina un usuario por su ID.
- **`login(String nombre, String contraseña)`**: Maneja la solicitud POST a `/usuarios/login`. Autentica a un usuario.
- **`register(String nombre, String contraseña)`**: Maneja la solicitud POST a `/usuarios/register`. Registra un nuevo usuario.

### `UsuarioRepository`

Esta interfaz extiende `JpaRepository` y proporciona métodos para interactuar con la tabla `Usuario`.

#### Métodos

- **`findByNombreAndContraseña(String nombre, String contraseña)`**: Encuentra un usuario por su nombre y contraseña.

### `UsuarioService`

Esta clase es un servicio de Spring que proporciona métodos para gestionar `Usuario`.

#### Métodos

- **`login(String nombre, String contraseña)`**: Autentica a un usuario.
- **`init()`**: Método anotado con `@PostConstruct` que se ejecuta después de la inicialización del bean. Vacía la base de datos, elimina todos los datos de IQ, guarda datos de IQ desde un archivo CSV y crea un usuario administrador.
- **`clearDatabase()`**: Vacía la tabla de usuarios.
- **`createAdminUser()`**: Crea un usuario administrador.
- **`getAllUsuarios()`**: Devuelve una lista de todos los usuarios.
- **`getUsuarioById(Integer id)`**: Devuelve un usuario por su ID.
- **`createUsuario(Usuario usuario)`**: Crea un nuevo usuario.
- **`deleteUsuario(Integer id)`**: Elimina un usuario por su ID.
- **`registerUsuario(String nombre, String contraseña)`**: Registra un nuevo usuario.

### `DataController`

Esta clase es un controlador REST de Spring que maneja las solicitudes HTTP relacionadas con la transmisión de datos IQ y la gestión de ejecutores.

#### Métodos

- **`streamIQData()`**: Maneja la solicitud GET a `/iqdata/stream`. Devuelve un `SseEmitter` que transmite datos de IQ durante 30 minutos.
- **`streamCountriesAndRanks()`**: Maneja la solicitud GET a `/countries-ranks/stream`. Devuelve un `SseEmitter` que transmite datos de países y sus rangos durante 30 minutos.
- **`streamCountriesIQAndTemp()`**: Maneja la solicitud GET a `/countries-iq-temp/stream`. Devuelve un `SseEmitter` que transmite datos de países, IQ y temperatura durante 30 minutos.
- **`streamCountriesIQAndAdditionalData()`**: Maneja la solicitud GET a `/countries-iq-additional/stream`. Devuelve un `SseEmitter` que transmite datos de países, IQ y datos adicionales durante 30 minutos.
- **`shutdownExecutors()`**: Maneja la solicitud POST a `/shutdown-executors`. Apaga los ejecutores.

### `DemoApplication`

Esta clase es la clase principal de la aplicación Spring Boot que inicia la aplicación.

#### Métodos

- **`main(String[] args)`**: Método principal que inicia la aplicación Spring Boot llamando a `SpringApplication.run(DemoApplication.class, args)`.

## Directorio static

### `countries-iq-additional.html`

Este archivo HTML muestra gráficos de líneas con datos adicionales de IQ de diferentes países.

#### Elementos

- **`<video>`**: Muestra un video de fondo.
- **`<h1 class="title">`**: Título de la página.
- **`<div class="button-container">`**: Contiene botones para mostrar diferentes tipos de datos.
- **`<div id="chart" class="chart">`**: Contenedor para el gráfico.
- **`<div id="tooltip" class="tooltip">`**: Contenedor para el tooltip.

#### Scripts

- **`DOMContentLoaded`**: Inicializa la conexión SSE y configura los botones para cambiar el tipo de gráfico.
- **`createLineChart(data, chartType)`**: Crea un gráfico de líneas basado en los datos y el tipo de gráfico seleccionado.

### `countries-ranks.html`

Este archivo HTML muestra un gráfico de barras con datos de países y sus rangos.

#### Elementos

- **`<video>`**: Muestra un video de fondo.
- **`<h1 class="title">`**: Título de la página.
- **`<div id="chart">`**: Contenedor para el gráfico.
- **`<div id="tooltip" class="tooltip">`**: Contenedor para el tooltip.

#### Scripts

- **`DOMContentLoaded`**: Inicializa la conexión SSE y actualiza el gráfico de barras con los datos recibidos.
- **`createBarChart(data)`**: Crea un gráfico de barras basado en los datos recibidos.

### `login.html`

Este archivo HTML proporciona una interfaz de inicio de sesión para los usuarios.

#### Elementos

- **`<div class="signupSection">`**: Contenedor principal que incluye la sección de información y el formulario de inicio de sesión.
- **`<div class="info">`**: Muestra un mensaje de bienvenida.
- **`<div class="signupForm">`**: Contiene el formulario de inicio de sesión.

#### Scripts

- **`redirectToMenu(event)`**: Redirige al usuario al menú principal después de un inicio de sesión exitoso.

### `Menu.html`

Este archivo HTML proporciona un menú con opciones para ver diferentes gráficos y detener hilos.

#### Elementos

- **`<video>`**: Muestra un video de fondo.
- **`<div class="buttonContainer">`**: Contiene botones para iniciar/detener el stream de datos de IQ y navegar a diferentes gráficos.

#### Scripts

- **`startIQDataStream()`**: Inicia o detiene el stream de datos de IQ.
- **`stopThreads()`**: Envía una solicitud para detener los hilos.

### `registro.html`

Este archivo HTML proporciona una interfaz de registro para nuevos usuarios.

#### Elementos

- **`<div class="signupSection">`**: Contenedor principal que incluye la sección de información y el formulario de registro.
- **`<div class="info">`**: Muestra un mensaje de bienvenida.
- **`<div class="signupForm">`**: Contiene el formulario de registro.

#### Scripts

- **`redirectToLogin(event)`**: Redirige al usuario a la página de inicio de sesión después de un registro exitoso.

### `scatter_plot.html`

Este archivo HTML muestra un gráfico de dispersión con datos de IQ y temperatura promedio de diferentes países.

#### Elementos

- **`<video>`**: Muestra un video de fondo.
- **`<h1 class="title">`**: Título de la página.
- **`<div id="chart">`**: Contenedor para el gráfico.
- **`<div id="tooltip" class="tooltip">`**: Contenedor para el tooltip.

#### Scripts

- **`DOMContentLoaded`**: Inicializa la conexión SSE y actualiza el gráfico de dispersión con los datos recibidos.
- **`createScatterPlot(data)`**: Crea un gráfico de dispersión basado en los datos recibidos.
