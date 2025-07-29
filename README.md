![Logo de la universidad](Logo.png)
# Universidad Politécnica Salesiana

## Carrera de Computación
**Asignatura:** Estructura de Datos

**Proyecto Final: Resolución de Laberintos con Algoritmos de Búsqueda**

**Integrantes:** Keyra Carvajal / Diana Borja / Bryan Barros / Erika Collaguazo

**Correo institucional:**  kcarvajalc5@est.ups.edu.ec /  ----@est.ups.edu.ec /  ----@est.ups.edu.ec / ecollaguazo@est.ups.edu.ec

---

## 1. Descripción del problema

Este proyecto tiene como objetivo resolver de manera eficiente el problema de encontrar un camino óptimo dentro de un laberinto representado como una matriz bidimensional. El desafío consiste en determinar, desde un punto de inicio hasta un punto de destino, una ruta válida que evite obstáculos (muros) y minimice la cantidad de pasos o el tiempo computacional necesario.

Este tipo de problema se relaciona directamente con la programación dinámica, ya que en muchos casos el algoritmo debe tomar decisiones basadas en resultados previamente calculados para evitar repeticiones innecesarias. La programación dinámica permite optimizar los algoritmos recursivos que exploran múltiples caminos mediante técnicas como la memoización o la tabulación, mejorando significativamente el rendimiento.

A través de una interfaz visual construida en Java con Swing, el usuario puede diseñar laberintos personalizados, seleccionar entre diferentes algoritmos de búsqueda (DFS, BFS, recursivo simple, recursivo completo, backtracking, recursivo + backtracking) y observar en tiempo real cómo se ejecuta cada uno sobre la matriz. Esto permite comparar el rendimiento de los métodos tanto desde el punto de vista visual como estadístico, ya que se registran métricas como el número de pasos y el tiempo de ejecución.

El enfoque del proyecto no solo se centra en resolver el laberinto, sino en comprender cómo diferentes estrategias algorítmicas abordan un mismo problema, y cómo la programación dinámica puede optimizar estos métodos al reducir la redundancia y aprovechar subsoluciones ya exploradas.

---

## 2. Propuesta de solución

### - Marco teórico

#### 1.- DFS (Depth-First Search)
Algoritmo que explora caminos profundizando antes de retroceder. Usa una pila y puede encontrar una solución rápidamente, pero no garantiza que sea la más corta.

#### 2.- BFS (Breadth-First Search)
Explora el laberinto por niveles utilizando una cola. Asegura la ruta más corta, pero consume más memoria y puede ser más lento.

#### 3.- Recursivo (2D)
Versión simple que explora el laberinto hacia la derecha y abajo. Es útil para ver cómo funciona la recursión, pero es limitado en laberintos reales.

#### 4.- Recursivo 4D
Amplía el Recursivo 2D, permitiendo movimiento en las 4 direcciones. Tiene mayor cobertura y mejora la exploración del laberinto.

#### 5.- Recursivo 4D + Backtracking
Si un camino no lleva a la meta, retrocede y prueba otros caminos. Usa recursión con retroceso para garantizar solución. Más completo.

#### 6.- Backtracking Visual
Algoritmo de exploración exhaustiva que prueba todas las rutas posibles. Marca visualmente cada paso, retroceso y solución final. Ideal para observar el proceso paso a paso.

---

### Tecnologías utilizadas

- Lenguaje: **Java**
- Interfaz Gráfica: **Java Swing (AWT)**
- IDE: **IntelliJ IDEA**
- Control de versiones: **Git**
- Biblioteca de gráficas: **JFreeChart**
- Librerias: ** --- **
### Diagrama UML
-
-
-
-


**Explicación:**

-
-
-
### Capturas de interfaz
> aquí 2 capturas de pantalla del funcionamiento.

- **Laberinto 1
- **Laberinto 2


## Ejemplo de código:
### Algoritmo Backtracking
![Imagen 1](Backtracking1.png)
![Imagen 2](Backtracking2.png)
![Imagen 3](Backtracking3.png)
![Imagen 4](Backtracking4.png)

### Explicación

El algoritmo Backtracking es una técnica de búsqueda que explora todas las posibles rutas 
dentro de un laberinto, avanzando celda por celda desde un punto de inicio hasta un punto de fin. 
Si encuentra una pared, un límite o un camino sin salida, retrocede y prueba otra alternativa. 
En esta implementación se optimiza el recorrido mediante programación dinámica (memoización), 
lo que permite guardar el número mínimo de pasos necesarios desde cada celda hasta el destino, 
evitando recalcular trayectorias ya procesadas. El algoritmo construye el camino óptimo desde el 
inicio hasta el final y también registra el recorrido completo para fines de visualización. 
Es útil tanto para encontrar soluciones eficientes como para representar visualmente cómo el 
sistema explora el laberinto, mostrando primero el recorrido en gris y luego el camino final en azul.

---
### Algoritmo BFS (Breadth-First Search)
![BFS paso 1](BFS1.png)
![BFS paso 2](BFS2.png)
![BFS paso 3](BFS3.png)
![BFS paso 4](BFS4.png)
### Explicación
BFS (Breadth-First Search) o Búsqueda en Anchura es un algoritmo que recorre el laberinto 
expandiendo primero todas las celdas cercanas al punto de inicio antes de avanzar más lejos. 
Explora el laberinto por niveles, utilizando una cola (FIFO) para registrar los caminos por visitar.
A diferencia de Backtracking, BFS siempre encuentra el camino más corto en términos de cantidad de 
celdas visitadas, aunque no sigue una ruta visualmente directa. Es ideal para representar soluciones
rápidas y eficientes, especialmente cuando se necesita minimizar la distancia desde el origen al destino.

## 4. Conclusiones por estudiantes

### ✨ Erika Collaguazo


### ✨ Bryan Barros


### ✨ Diana Borja


### ✨ Keyra Carvajal


## 5. Recomendaciones


## 6. Aplicaciones futuras

