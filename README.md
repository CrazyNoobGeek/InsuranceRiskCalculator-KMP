# 🛡️ Insurance Risk Simulator (KMP POC)

Un module de tarification d'assurance cross-platform développé avec **Kotlin Multiplatform (KMP)**.
Ce projet démontre une architecture robuste permettant de partager 100% de la logique métier entre Android et iOS.

## 🚀 Stack Technique

* **Langage :** Kotlin
* **Architecture :** Clean Architecture (Domain / Presentation / UI)
* **UI :** Compose Multiplatform (Material 3)
* **Qualité :** Tests Unitaires : Kotlin Test (Common)

## 🏗️ Architecture & Choix Techniques

### 1. KMP & Clean Architecture
J'ai choisi d'isoler la logique de tarification dans le module `Shared` (`commonMain`).
* **Avantage :** La règle de calcul (3 ans de permis = Bonus) est codée une seule fois.
* **Fiabilité :** Si la règle change, elle change pour iOS ET Android en même temps. 0 risque de disparité.

### 2. Tests Unitaires (Effectués ✅)
La logique métier est couverte par des tests unitaires stricts (voir `CalculatePremiumUseCaseTest.kt`).
* Validation du cas "Jeune Conducteur" (< 3 ans).
* Validation du cas "Expérimenté".
* Validation des limites (Edge cases).

---

## 🤖 Stratégie d'Automatisation (Appium)

Bien que ce POC se concentre sur les Tests Unitaires, voici la stratégie **End-to-End (E2E)** que je mettrais en place avec **Appium** pour l'industrialisation :

### Approche : Page Object Model (POM)
Pour garantir la maintenabilité des tests UI, j'utiliserais le pattern POM :

1.  **Screen Definition :** Création d'une classe `RiskSimulatorScreen` qui contient les sélecteurs (IDs) des champs :
    * `input_age` (Accessibility ID)
    * `input_license_years` (Accessibility ID)
    * `btn_calculate` (Accessibility ID)

2.  **Scénario de Test (Gherkin / Cucumber style) :**
    * **GIVEN** l'application est lancée
    * **WHEN** je saisis "20" dans le champ Âge
    * **AND** je saisis "1" dans le champ Permis
    * **AND** je clique sur "Calculer"
    * **THEN** je vérifie que le texte "Surprime Appliquée" est affiché en orange.

Cette approche permet de séparer la structure de l'interface des scénarios de tests.

---

## 📱 Comment lancer le projet

**Prérequis :** Android Studio (Koala/Ladybug) & Xcode (pour iOS).

1.  Cloner le repo.
2.  Ouvrir dans Android Studio.
3.  Lancer `composeApp` sur Android Emulator ou `iosApp` via Xcode.

---
*Développé par Yahya Bahloul - POC Candidature Incubateur*