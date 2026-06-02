PREGUNTA 1

Explica cómo funciona la relación 1:N entre CentroForense y MuestraForense tanto en SQL como en Java.
En SQL: La relación 1:N se implementa con una clave foránea (FK) en la tabla hija. MUESTRAS tiene la columna CENTRO_ID INT NOT NULL que referencia a CENTRO(ID). Una centro puede tener muchas muestras, pero cada muestra pertenece a un solo centro.

En Java: En lugar de guardar un int centroId, la clase muestra contiene un objeto private CentroForense centro. Esto permite navegar la relación directamente sin hacer consultas adicionales, respetando la orientación a objetos.


PREGUNTA 2

Explica por qué en Java utilizamos:

private CentroForense centro;

y no:
private int centroId;

Porque el DAO no devuelve filas SQL, devuelve objetos Java completamente relacionados.
Con private int centroId estaríamos filtrando la base de datos en nuestros objetos Java, lo cual rompe la encapsulación y obliga al código cliente a hacer consultas adicionales para obtener los datos de la agencia.
Con private CentroForense centro el objeto está completo: podemos acceder a todos sus datos directamente, el código es más limpio y respeta el principio de orientación a objetos.





PREGUNTA 3

Explica qué ventaja aporta PreparedStatement frente a concatenar SQL manualmente.
Seguridad: PreparedStatement evita SQL Injection. Con concatenación, un usuario malicioso podría inyectar SQL arbitrario. Los ? separan el código SQL de los datos.

Rendimiento: La query se precompila una vez en el servidor, lo que mejora el rendimiento en ejecuciones repetidas.

Legibilidad: El código es más limpio y menos propenso a errores con comillas o espacios.