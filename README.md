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
