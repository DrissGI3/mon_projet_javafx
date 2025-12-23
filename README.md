# 🏥 CareLink - Application de Gestion de Clinique

Application JavaFX pour la gestion complète d'une clinique médicale avec base de données MySQL.

## 📋 Fonctionnalités

- ✅ **Gestion des patients** - Ajout, modification, recherche, suppression
- ✅ **Gestion des rendez-vous** - Planification et suivi
- ✅ **Consultations médicales** - Diagnostics, prescriptions, notes
- ✅ **Traitements** - Suivi thérapeutique
- ✅ **Dossiers médicaux** - Vue Patient 360
- ✅ **Interfaces multi-rôles** - Admin, Médecin, Secrétaire

## 🛠️ Technologies Utilisées

- **JavaFX 21** - Interface graphique
- **Maven** - Gestion des dépendances
- **MariaDB/MySQL** - Base de données
- **JDBC** - Connexion base de données

## 📦 Installation

### Prérequis
- Java JDK 21+
- Maven
- MariaDB/MySQL

### Étapes

1. Cloner le projet
```bash
git clone https://github.com/VOTRE_USERNAME/carelink.git
cd carelink
```

2. Créer la base de données
```sql
CREATE DATABASE clinique;
```

3. Exécuter l'application
```bash
mvn javafx:run
```

L'application créera automatiquement les tables au premier lancement.

## 🗄️ Base de Données

### Tables principales
- `patients` - Informations des patients
- `appointments` - Rendez-vous
- `consultations` - Consultations médicales
- `treatments` - Traitements
- `prescribed_exams` - Examens prescrits

## 👥 Rôles

- **Admin** - Gestion système
- **Médecin** - Soins médicaux et consultations
- **Secrétaire** - Accueil et rendez-vous

## 📸 Captures d'écran

_À ajouter après présentation_

## 👨‍💻 Auteur

Projet développé dans le cadre de [préciser votre formation/cours]

## 📄 Licence

Ce projet est à usage éducatif.
