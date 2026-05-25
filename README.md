# Dishy - Social Vibe Discovery App 🍽️✨

**Dishy** es una plataforma social diseñada para descubrir lugares no solo por su comida, sino por su "vibra". Enfocada en nómadas digitales, estudiantes y exploradores urbanos, permite compartir fotos reales con datos técnicos como velocidad de Wi-Fi, nivel de ruido y disponibilidad de enchufes.

---

## 🚀 Guía de Instalación y Clonación

Si deseas clonar este proyecto y ejecutarlo en tu entorno local, sigue estos pasos cuidadosamente:

### 1. Clonar el Repositorio
Abre una terminal y ejecuta:
```bash
git clone https://github.com/TU_USUARIO/Dishy-App.git
```

### 2. Configuración de Firebase (Backend)
La app requiere una instancia de Firebase activa.
1.  Ve a [Firebase Console](https://console.firebase.google.com/).
2.  Crea un nuevo proyecto llamado "DishyApp".
3.  Añade una aplicación **Android**:
    *   Usa el nombre de paquete: `com.example.dishy_app`.
    *   Descarga el archivo `google-services.json` y colócalo en la carpeta `app/` de tu proyecto.
4.  **Authentication**: Activa los métodos de inicio de sesión:
    *   Correo electrónico y contraseña.
    *   Google (requiere configurar el SHA-1 en la consola de Firebase).
5.  **Firestore Database**: 
    *   Crea la base de datos en modo prueba o usa las siguientes **Reglas de Seguridad**:
    ```javascript
    service cloud.firestore {
      match /databases/{database}/documents {
        match /{document=**} {
          allow read, write: if request.auth != null;
        }
      }
    }
    ```

### 3. Configuración de Cloudinary (Imágenes)
Para la subida de fotos, usamos Cloudinary (gratuito).
1.  Crea una cuenta en [Cloudinary](https://cloudinary.com/).
2.  Busca tu `cloud_name` en el Dashboard.
3.  Ve a `Settings > Upload` y crea un **Unsigned Upload Preset** llamado `ProjectDISHYApp`.
4.  En el proyecto Android, abre `CreatePostViewModel.kt` y actualiza:
```kotlin
val config = mapOf(
    "cloud_name" to "TU_CLOUD_NAME",
    "secure" to true
)
```

### 4. Acceso de Administrador Maestro
Para probar las funciones de borrado y gestión de usuarios, regístrate con cualquier correo que termine en:
👉 **`@dishy.app`** (Ejemplo: `admin@dishy.app`)

---

## ✨ Características por Rol

| Explorador (User) | Empresa (Business) | Administrador (Admin) |
| :--- | :--- | :--- |
| Explora el feed social | Perfil verificado con badge | Borra cualquier post inapropiado |
| Guarda lugares y "vibes" | Publica fotos oficiales | Cambia roles de usuarios |
| Agita para descubrir (Shake) | Estadísticas de engagement | Gestión total de la comunidad |

## 🛠️ Stack Tecnológico

*   **UI**: Jetpack Compose, Material 3.
*   **Lenguaje**: Kotlin + Coroutines & Flow.
*   **Mapas**: OSMDroid (OpenStreetMap).
*   **Backend**: Firebase (Auth, Firestore).
*   **Imágenes**: Cloudinary (Storage), Coil (Loading).
*   **Arquitectura**: MVVM con Repositorios.

---
Desarrollado con ❤️ para exploradores de buenas vibras.
