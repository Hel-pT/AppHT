# AppHT - Aplicación Móvil para Apoyo a Terapias de Comunicación y Conducta

[![GitHub license](https://img.shields.io/github/license/Hel-pT/AppHT)](https://github.com/Hel-pT/AppHT/blob/main/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/Hel-pT/AppHT)](https://github.com/Hel-pT/AppHT/stargazers)
[![GitHub issues](https://img.shields.io/github/issues/Hel-pT/AppHT)](https://github.com/Hel-pT/AppHT/issues)

## 📋 Descripción
**AppHT** es una aplicación móvil desarrollada para brindar apoyo a terapias de comunicación y conducta para niños con **Trastorno del Espectro Autista (TEA)**. La aplicación tiene como objetivo reducir la brecha de comunicación, mejorar la calidad de vida de los niños con TEA y facilitar el trabajo de los profesionales de la salud y cuidadores.

## 🚀 Características Principales
- **Asignación de Retos**: Los profesionales pueden asignar retos personalizados para apoyar la mejora de habilidades de comunicación y comportamiento.
- **Comunicación Alternativa**: Implementación de **Sistemas Aumentativos y Alternativos de Comunicación (SAAC)**.
- **Seguimiento del Progreso**: Terapeutas y cuidadores pueden monitorear el progreso del paciente.
- **Interfaz Adaptada**: Diseño amigable y adaptable para niños con TEA.

## 📱 Tecnologías Utilizadas
- **Frontend:**
  - Kotlin Multiplatform
  - Jetpack Compose (Android)
  - Material Design Components
  - Kotlin Android Extensions
  - XML
- **Backend:**
  - Firebase Firestore para la base de datos en la nube y autenticación.
- **IDE:**
  - Android Studio

## 📦 Instalación
Para instalar y correr el proyecto localmente, sigue estos pasos:

1. **Clona el Repositorio**:
    ```bash
    git clone https://github.com/Hel-pT/AppHT.git
    ```
2. **Abre Android Studio** y selecciona el proyecto clonado.
3. **Configura Firebase**:
    - Agrega el archivo `google-services.json` en la carpeta `app/`.

## 🧐 Requisitos del Sistema
- **Android Studio** 4.1 o superior.
- **Java JDK** 8 o superior.
- **Conexión a Internet** para la integración con Firebase.

## 💡 Uso
1. **Registro de Pacientes**: Los terapeutas pueden registrar nuevos pacientes y asociarles responsables.
2. **Asignación de Retos**: Desde la pantalla del doctor, los profesionales pueden asignar retos personalizados para cada niño.
3. **Seguimiento del Progreso**: Los retos asignados y el progreso se pueden visualizar y actualizar.

## ✨ Arquitectura del Proyecto
El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** para mantener la separación de responsabilidades y asegurar la escalabilidad del código:

- **Model**: Encargado de la lógica de negocio y datos.
- **ViewModel**: Administra la lógica de la interfaz de usuario.
- **View**: Componente de la interfaz de usuario desarrollado con Jetpack Compose y XML.

## 📁 Estructura del Proyecto
- **/app**: Contiene el núcleo de la aplicación.
- **/data**: Módulos relacionados con Firebase para la comunicación y persistencia de datos.
- **/ui**: Componentes de la interfaz de usuario.
- **/viewmodel**: Lógica para manejar la interacción con la UI.

## 🚒 Funcionalidades Futuras
- **Soporte para iOS** utilizando Kotlin Multiplatform para compartir la lógica.
- **Chat en Tiempo Real** para la comunicación entre cuidadores y terapeutas.
- **Análisis de Progreso** con estadísticas detalladas.

## 🤝 Cómo Contribuir
Si estás interesado en contribuir, sigue estos pasos:

1. **Haz un Fork del Proyecto**.
2. **Crea una Nueva Rama** (`git checkout -b feature/NuevaCaracteristica`).
3. **Realiza los Cambios** y **Haz Commit** (`git commit -m 'Añadir NuevaCaracteristica'`).
4. **Empuja a la Rama** (`git push origin feature/NuevaCaracteristica`).
5. **Abre un Pull Request**.

También puedes revisar los [issues](https://github.com/Hel-pT/AppHT/issues) existentes para ver dónde puedes colaborar.

## 📄 Licencia
Este proyecto está bajo la Licencia MIT. Mira el archivo [LICENSE](https://github.com/Hel-pT/AppHT/blob/main/LICENSE) para más detalles.

## 📝 Contacto
Si tienes alguna pregunta o sugerencia, por favor no dudes en contactarnos:

- **Correo Electrónico**: odcontreras.07.1007@hotmail.com o al steb1808espi@gmail.com
- **GitHub**: [https://github.com/Hel-pT/AppHT](https://github.com/Hel-pT/AppHT)

---

