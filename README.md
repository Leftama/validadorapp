# Validador App

Proyecto Java 17 + Maven para practicar análisis estático de código y detección de malas prácticas utilizando **SonarQube Community Edition**. Incluye buenas prácticas de control de versiones con Git y un flujo de trabajo profesional con ramas y commits limpios.

---

## Objetivos

- Simular y detectar malas prácticas de codificación.
- Configurar y ejecutar análisis estático con SonarQube.
- Corregir errores y vulnerabilidades reportadas.
- Aplicar buenas prácticas de uso de Git y GitHub.
- Preparar el proyecto para flujos CI/CD básicos.

---

## Tecnologías

- Java 17
- Apache Maven
- SonarQube (Community Edition)
- Git & GitHub

---

## Estructura del Proyecto

```../validadorapp/
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── equipo
│   │               └── validador
│   │                   └── App.java
│   └── test
│       └── java
│           └── com
│               └── equipo
│                   └── validador
│                       └── AppTest.java
└── target

```

---

## Flujo de trabajo profesional con Git

### 1. Inicializar y conectar repositorio

```bash
git init
echo "# ValidadorApp" > README.md
touch .gitignore
git add .
git commit -m "Inicializa proyecto validadorapp con Maven y Java 17"
git branch -M main
git remote add origin https://github.com/tuusuario/validadorapp.git
git push -u origin main

```
### 2. Crear ramas por tareas
```bash
git checkout -b feature/codigo-inseguro

```
Agrega código inseguro en App.java (por ejemplo, credenciales hardcodeadas) y haz commit.

```bash
git add src/
git commit -m "Agrega código con malas prácticas para análisis SonarQube"
git push origin feature/codigo-inseguro

```
### 3. Configurar Java 17 y SonarQube
Crea una rama:

```bash
git checkout -b feature/configuracion-sonar
pom.xml:

<properties>
  <maven.compiler.source>17</maven.compiler.source>
  <maven.compiler.target>17</maven.compiler.target>
</properties>

<build>
  <plugins>
    <plugin>
      <groupId>org.sonarsource.scanner.maven</groupId>
      <artifactId>sonar-maven-plugin</artifactId>
      <version>3.9.1.2184</version>
    </plugin>
  </plugins>
</build>

```
Haz commit:
```bash
git add pom.xml
git commit -m "Configura Java 17 y plugin SonarQube"
git push origin feature/configuracion-sonar

```
### 4. Ejecutar SonarQube
```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
Crea un token en http://localhost:9000 → My Account → Security

```

Ejecuta el análisis:
```bash
mvn clean verify sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=MI_TOKEN \
  -Dsonar.scm.provider=git \
  -Dsonar.projectKey=validadorapp-cras-corregido \
  -Dsonar.projectName="Validador-App-corregido" \
  -Dsonar.projectVersion=1.0

```
### 5. Crear rama para correcciones
```bash
git checkout -b fix/codigo-seguro

```

Corrige App.java (por ejemplo, usando System.getenv() en lugar de credenciales hardcoded). Luego:

```bash
git add src/
git commit -m "Corrige vulnerabilidad de credenciales hardcodeadas"
git push origin fix/codigo-seguro

```

### 6. Merge de ramas a main
```bash
git checkout main
git merge feature/codigo-inseguro
git merge feature/configuracion-sonar
git merge fix/codigo-seguro
git push origin main

```

Alternativamente, puedes crear Pull Requests en GitHub.

---

## 📷 Evidencias para entregar o presentar

- ✅ Captura de pantalla del dashboard SonarQube antes de las correcciones.
- ✅ Captura después de aplicar las mejoras.
- ✅ Comparación del resultado del Quality Gate.
- ✅ Explicación: ¿qué detectaste? ¿qué corregiste? ¿cómo mejoró?

---

## Buenas prácticas DevOps aplicadas

- Uso de ramas por funcionalidad (feature/, fix/, etc.)
- Commits pequeños, limpios y descriptivos.
- Exclusión de archivos innecesarios (.gitignore).
- Flujo de trabajo Git profesional (local + remoto).
- Integración con SonarQube local.
- Preparación para CI/CD (compatible con GitHub Actions).

---

## Repositorio remoto
https://github.com/Leftama/validadorapp.git

---

## Preguntas finales

### ✅ ¿Qué tipo de errores detectó SonarQube que podrían haber pasado desapercibidos?

Durante el análisis estático de `validadorapp`, SonarQube identificó:

- **Vulnerabilidades de seguridad**, como credenciales hardcodeadas (`"123456"`), difíciles de detectar en tiempo de ejecución, pero peligrosas en producción.
- **Code smells** (malas prácticas), como estructuras inseguras, condiciones innecesarias o comparaciones incorrectas.
- **Bugs potenciales** que podrían ocasionar errores lógicos futuros.
- **Duplicación de código** (si aplica), que afecta la mantenibilidad.

> SonarQube destaca riesgos aún cuando el código compila y ejecuta correctamente.

---

### ¿Qué ventajas tiene el análisis estático respecto al dinámico?

| Análisis Estático (SonarQube)          | Análisis Dinámico (Pruebas / Ejecución)   |
|----------------------------------------|--------------------------------------------|
| No requiere ejecutar el código         | Requiere ejecutar el programa              |
| Detecta problemas de diseño, bugs y seguridad | Detecta errores en tiempo de ejecución |
| Cobertura total del código analizado   | Depende de los datos de prueba             |
| Preventivo: evita problemas futuros    | Correctivo: detecta errores existentes     |
| Automatizable en CI/CD                 | Más costoso en tiempo y recursos           |

El análisis estático permite identificar y corregir vulnerabilidades **antes de desplegar o probar**, fortaleciendo la calidad desde la etapa de desarrollo.

---

### ¿Cómo impacta SonarQube en la calidad del software antes del despliegue?

SonarQube mejora la calidad antes del despliegue al:

- ✅ Detectar código inseguro, duplicado o mal diseñado.
- ✅ Ayudar al equipo a escribir mejor código de forma continua.
- ✅ Evitar que malas prácticas lleguen a producción.
- ✅ Servir como filtro de calidad mediante **Quality Gates**.
- ✅ Integrarse fácilmente en flujos DevOps y pipelines CI/CD.

> En entornos profesionales, SonarQube se convierte en un aliado clave para mantener estándares de calidad sostenibles.

---

### 🛠️ ¿Qué políticas o reglas personalizarías según el tipo de proyecto?

Dependerá del dominio y criticidad del software:

#### Proyectos críticos (salud, finanzas):
- Reglas de seguridad estrictas (sin credenciales hardcodeadas).
- Tolerancia cero a bugs de alta severidad.
- Reglas obligatorias de cobertura mínima y calidad estructural.
- Integración con pipelines bloqueantes si falla el Quality Gate.

#### Proyectos web:
- Enfoque en validación de entrada/salida (XSS, SQLi).
- Buenas prácticas REST y separación de capas.
- Prevención de duplicación y estructuras confusas.

#### 📚 Proyectos educativos (como `validadorapp`):
- Reglas centradas en **seguridad básica y mantenibilidad**.
- Flexibilidad para explorar y aprender de los errores.
- Uso de SonarQube como herramienta formativa y de retroalimentación.

---
