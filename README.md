# IANAManagement
Servicios sobre informacion IANA

Presentación
Este es el proyecto que soluciona los requerimientos formulados para la administración de rangos IANA (Internet Assigned Numbers Authority). Los requerimientos que cubre son los siguientes: (1) Carga de rangos IANA a una base de datos, (2) Consulta de dirección IP sobre la tabla cargada. Estas funcionalidades se ejecutan desde las API de servicios RESTfull expuestos.

Estrategia de diseño
Se pretende construir la base para un sistema de administración de una lista de rangos IANA persistidos dentro de una base de datos relacional. Esta administración se diseña para que pueda ser automatizada, es decir ejecutada desde eventos de otras aplicaciones. El sistema está diseñado para que pueda operar en alta disponibilidad.

Herramientas de desarrollo
Desarrollo en J2EE usando la versión jdk1.8.0_171.
El ambiente de desarrollo usado IDE Eclipse Versión: Neon.3 Release (4.6.3).
Se usa MAVEN como manejador de dependencias y arquetipo.
Se usa como Framework Spring boot.
Se usó como motor de base de datos para pruebas de desarrollo MySQL Workbench 8. 
Se usa como manejador de versiones GIT

Insumos.
Archivo Plano tipo CSV para pruebas de carga.
Documento con requerimientos funcionales y no funcionales.

Criterios asumidos
Se asumen los siguientes criterios que no contemplo el documento:
1)	Reemplazo de campo Time Zone: En el documento se hace referencia a este campo, pero en el archivo de texto insumo no hay ninguno con esta característica. Para efecto se reemplaza este campo con el campo Company tomando el último campo de la distribución.
2)	Procesamiento del Archivo Plano: Se entiende desde el requerimiento que hay un lanzamiento del procesamiento del archivo; como se expresa, son archivos de gran volumen, se asume que el servicio expuesto solamente activa el lanzamiento. Con ello el procesamiento del archivo se limitaría a leerlo desde una localización, -parsearlo- desde una funcionalidad del aplicativo, y persistirlo en una tabla de base de datos.
3)	Transformación del Archivo Plano: En el requerimiento no se expresa transformación del archivo. Se asumen una transformación del campo IpTo a tipo numérico, con el propósito de que tanto los campos ipFrom como ipTo queden numéricos y se favorezca la velocidad de las consultas de rango que se hagan sobre estos, ya que sobre ellos se establece un índice único y a la vez un árbol de ordenamiento.
4)	Validación de los registros del Archivo Plano: Se asume como registro NO válido aquellos que cumplan los siguientes criterios:

a.	Cuando falta de rango (ipFrom, ipTo).
b.	Cuando falta de datos numéricos en el rango.
c.	Cuando la cantidad de campos del registro son menores que 9.

5)	Autodeterminaciones: Son las acciones que se hacen sobre un registro de la tabla para evitar rechazarlo por inválido. Se implementaron las siguientes:
a.	Solo se procesan los nueve primeros campos del registro del archivo plano.
b.	La longitud de los campos es truncada a la longitud de los campos configurados en la base de datos huesped para evitar desbordamientos.

Configuración del Aplicativo

1)	Configuración de la Base de datos: 
Ajustar application.properties a los datos de conexión de la base de datos. Es necesario validar esta configuración para el inicio sin problemas del aplicativo.
2)	Configurar Ruta de Ubicación del archivo plano:
Ajustar el functional.properties con la ruta de ubicación del archivo de prueba. Tener en cuenta escapar los caracteres “\\”
3)	Configurar Requerimientos para la construcción del proyecto (Build Maven Goals):
Aplica solo para ambientes de desarrollo. Si se ejecuta sobre IDE Eclipse, tener en cuenta hacerlo con el JDK en librerías y no con el Jre.

Creación de la tabla de base de datos:
Se debe ejecutar dentro de la base de datos el script MakeTable.sql que está ubicado en la raíz del proyecto. Puede requerirse ajustar el prefijo de la base de datos. No cambiar el nombre de la tabla, campos ni tipología.

Ejecución y prueba del aplicativo
Antes de ejecutarse se deben haber completado los pasos de la configuración.
Una vez desplegado el aplicativo, esto es desde IDE o desde el despliegue de su .war, el aplicativo queda listo y sirviendo a sus API de tipo GET.
Las pruebas de la API pueden hacerse desde un navegador normal (Con activación de herramientas de desarrollo), desde un SOAP-UI (Endpoint Explorer) o similar. A continuación se detallan los manuales de la API expuestas.

1)	API: Prueba de aplicativo funcionando
localhost:8080/rest/isalive
Esta prueba entrega una respuesta de Estado 200 cuando el aplicativo está listo para servir a las API.

2)	API: Carga de Archivo plano (Lanzamiento de procesamiento)
Esta prueba requiere que exista un archivo plano en la ruta configurada en functional.properties. 
En esta ruta pueden existir varios archivos. Es necesario conocer el nombre de cual se va a procesar.
El escenario funcional de esta operación es que el archivo llega a esta ruta, producto de un programador de tareas (Control-M), o el resultado de una descarga desde otro sistema. Puede llegar historificado (con Nombre aleatorio), el sistema consumidor de la API ya sabe con anterioridad que archivo procesar.
localhost:8080/rest/processfile/iana_20210509.txt
Ante la inexistencia del archivo, entrega un estado 406, un JSON extendiendo la respuesta, registra en logger la petición inefectiva.
Cuando el archivo existe, se envía a proceso asincrónico, esto es, en una tarea en background controlada por hilos, luego se libera la respuesta de la API. La API retorna una respuesta de estado 200, un JSON extendiendo la respuesta, aclarando que no hay resultados en esta API ya que el trabajo se procesa en Background, registra en looger los sucesos del proceso, para este caso, los registros que no se procesaron y su razón y el número de registros que fueron procesados.

3)	API: Consulta de rangos donde se encuentra una IP
Esta API entrega información de rangos en los cuales se encuentra la IP. Tiene tres respuestas en un JSON con estado y extensión de respuesta. Las respuestas se detallan a continuación:
La dirección IP esta fuera de un formato válido, 	estado 406. Significa que se dio un formato no válido para una IP, sea porque tiene mala anatomía (diferente de 4 segmentos), o porque tiene valores mayores a 255 en sus segmentos, o porque tiene malformaciones de caracteres o signos dentro de su composición.
No hubo resultados para la consulta. Estado 412. Significa que la dirección enviada como parámetro no se encuentra dentro de los rangos inscritos en la base de datos.
Consulta satisfactoria, estado 200. Significa que hay resultados que se detallan en un JSON de respuesta.

Patrones y estrategias usadas para esta aplicación

Para el desarrollo estandarizado de este proyecto se hizo uso de patrones y herramientas. Se mencionan los más destacados.
ORM: A través de Hibernate/JPA proporcionados por el framework Spring, con el cual se independiza la capa de persistencia del motor de Base de datos relacional. Esto favorece la conmutación de la base de datos y la ejecución de patrones de consulta y gestión derivadas del JPA.
Inyección de dependencias: Derivadas del framework Spring usado para la construcción del aplicativo, optimizando la creación de objetos de mayor uso.
Multihilo:  Se usa la utilidad TaskExecutor proporcionada por Spring para ejecutar tareas en segundo plano. Esto permite el procesamiento asincrónico de la carga del archivo. El procesamiento asincrónico favorece la optimización del performace del aplicativo y su carga equilibrada dentro de la JVM.
Loggin (org.slf4j.Logger): Uso de trazas de auditoría.  Se deja registrado en logger las operaciones inefectivas y las respuestas no esperadas del aplicativo.
Properties: Se proporciona la forma de configurar el aplicativo para personalizar variables, como por ejemplo la ruta desde donde se toman los archivos a procesar.
Tokenización: Usado para el –Parseo- de cadenas y validación. Favorece el intercambio de datos entre sistemas.
DTO: Diseño de modelos de datos de trasferencia, para entrega de resultados. Es una estandarización de los formatos de información entregados por la API.
TDD: Se implementan test automáticos de pruebas unitarias con la utilidad SpringBootTest, para favorecer procesos de construcción e integración continua del aplicativo.  

Conclusiones
El desarrollo de este aplicativo, deja como experiencia el aprovechamiento de un ecosistema compuesto por un tecnology stack, que favorece la construcción inicial y futuras modificaciones del aplicativo dentro de técnicas de desarrollo ágil.
Los tiempos invertidos inicialmente mientras se instala el ecosistema pueden ser altos, pero compensa con el bajo tiempo en el desarrollo de las modificaciones al aplicativo.
Es importante destacar que, si bien este ejercicio no representa todas las funcionalidades que debe tener un escenario de administración real de un control IANA, es la base para construir a partir de este modelo todo un plano funcional.

Web-grafía
Los siguientes portales fueron muy útiles en la elaboración de este proyecto. Gracias a internet y sus colaboradores continuamos creciendo en la profesión
https://www.iana.org/
https://www.ecodeup.com/
https://docs.github.com
https://www.amitph.com
https://reflectoring.io
https://spring.io
https://dzone.com

Fin del documento





