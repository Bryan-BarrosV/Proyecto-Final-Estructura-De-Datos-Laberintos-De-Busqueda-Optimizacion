![Logo de la universidad](Logo.png)
# Universidad Politécnica Salesiana

## Carrera de Computación
**Asignatura:** Estructura de Datos

**Proyecto Final: Resolución de Laberintos con Algoritmos de Búsqueda**

**Integrantes:** Keyra Carvajal / Diana Borja / Bryan Barros / Erika Collaguazo

**Correo institucional:**  ----@est.ups.edu.ec /  ----@est.ups.edu.ec /  ----@est.ups.edu.ec / ecollaguazo@est.ups.edu.ec

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


### Ejemplo de código:

-
-
-
---

## 4. Conclusiones por estudiantes

### ✨ Erika Collaguazo


### ✨ Bryan Barros


### ✨ Diana Borja


### ✨ Keyra Carvajal


## 5. Recomendaciones


## 6. Aplicaciones futuras

