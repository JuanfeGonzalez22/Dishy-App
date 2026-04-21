# Dishy-App 🍽️

**Dishy-App** es una aplicación móvil Android desarrollada en **Kotlin** que transforma la forma en que los usuarios descubren restaurantes y espacios de trabajo (coworking), ofreciendo una experiencia visual moderna e intuitiva.

> 🚧 **Estado del proyecto:** Beta — Las funcionalidades de búsqueda y la interfaz de usuario están en proceso de optimización activa.

---

## 📋 Tabla de Contenidos

- [Requisitos Previos](#-requisitos-previos)
- [Clonar el Repositorio](#-clonar-el-repositorio)
- [Compilación e Instalación de Dependencias](#-compilación-e-instalación-de-dependencias)
- [Ejecución](#-ejecución)
- [Stack Tecnológico](#️-stack-tecnológico)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Autor](#-autor)

---

## ✅ Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente en tu máquina:

| Herramienta | Versión Requerida |
|---|---|
| Android Studio | Ladybug 2024.2.1 o superior |
| Android SDK | `compileSdk` **36** · `minSdk` **36** · `targetSdk` **36** |
| JDK | **11** (`sourceCompatibility = JavaVersion.VERSION_11`) |
| Git | Cualquier versión reciente |

> 💡 Puedes verificar tu versión de Java ejecutando `java -version` en la terminal. Asegúrate de que sea **Java 11** — versiones anteriores causarán error en el Gradle Sync.
>
> ⚠️ **Nota importante:** Como `minSdk = 36`, la app **solo corre en dispositivos con Android 16 o superior**, tanto en emulador como en dispositivo físico.

---

## 📥 Clonar el Repositorio

El desarrollo activo ocurre en la rama `Develop`. Para obtener la última versión, clona directamente esa rama:

```bash
git clone -b Develop https://github.com/JuanfeGonzalez22/Dishy-App.git
```

Luego navega al directorio del proyecto:

```bash
cd Dishy-App
```

> ⚠️ **Importante:** Siempre trabaja sobre la rama `Develop` para contribuciones o pruebas. La rama `Main` contiene únicamente versiones estables.

---

## 🔧 Compilación e Instalación de Dependencias

Tienes dos opciones para compilar el proyecto:

### Opción A — Android Studio (Recomendada)

1. Abre **Android Studio**.
2. Selecciona **File > Open** y elige la carpeta raíz del proyecto (`Dishy-App/`).
3. Espera a que el **Gradle Sync** finalice automáticamente. Este proceso descargará todas las dependencias necesarias: Jetpack Compose, Material 3, entre otras.
4. Si Android Studio solicita actualizar el Gradle plugin, acepta la sugerencia.

### Opción B — Terminal

Si prefieres compilar sin abrir el IDE, ejecuta desde la raíz del proyecto:

```bash
# En macOS / Linux
./gradlew build

# En Windows
gradlew.bat build
```

Una vez completado sin errores, el APK generado estará disponible en:

```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 Ejecución

### En un Emulador

1. Abre el **AVD Manager** en Android Studio (**Tools > Device Manager**).
2. Crea un dispositivo virtual con imagen de sistema **Android 16 (API 36)**.
3. Inicia el emulador y luego presiona el botón **Run ▶** en Android Studio.

O desde la terminal:

```bash
./gradlew installDebug
```

### En un Dispositivo Físico

1. Habilita las **Opciones de Desarrollador** en tu teléfono:
   - Ve a **Ajustes > Acerca del teléfono** y toca **Número de compilación** 7 veces.
2. Activa la **Depuración USB** dentro de las Opciones de Desarrollador.
3. Conecta el dispositivo al PC con un cable USB.
4. Verifica que Android Studio detecte el dispositivo y presiona **Run ▶**.

> 📌 El dispositivo debe tener soporte para **API 36** o superior para ejecutar la app sin problemas.

---

## 🛠️ Stack Tecnológico

| Categoría | Tecnología / Versión |
|---|---|
| Lenguaje | Kotlin |
| Plataforma | Android 16 (Baklava) — API 36 |
| `compileSdk` / `minSdk` / `targetSdk` | **36 / 36 / 36** |
| Compatibilidad Java | `JavaVersion.VERSION_11` |
| UI Framework | Jetpack Compose + Material Design 3 |
| Íconos | Material Icons Extended |
| Arquitectura | MVVM (Model-View-ViewModel) |
| Navegación | Navigation Compose `2.7.7` |
| Autenticación | Firebase Auth + Google Credentials |
| Corrutinas | `kotlinx-coroutines-play-services 1.8.1` |
| Carga de imágenes | Coil Compose `2.6.0` |
| Mapas | OSMDroid `6.1.17` |
| Control de Versiones | Git — flujo Main / Develop |

---

## 📁 Estructura del Proyecto

```
Dishy-App/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/         # Código fuente Kotlin
│   │   │   ├── res/          # Recursos (layouts, drawables, strings)
│   │   │   └── AndroidManifest.xml
│   │   └── test/             # Tests unitarios
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 👤 Autor

**Juan Felipe González** — [@JuanfeGonzalez22](https://github.com/JuanfeGonzalez22)

**Erick Garcia**

Este proyecto es parte de un desarrollo académico para la **Institución Universitaria EAM**.

---

<p align="center">Hecho con Kotlin</p>
