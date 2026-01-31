# 🎯 Système Expert d'Aide au Choix d'Architecture Applicative et d'Infrastructure

> Système expert intelligent basé sur l'IA symbolique pour recommander l'architecture logicielle et l'infrastructure optimales selon vos besoins

[![Python](https://img.shields.io/badge/Python-3.8+-blue.svg)](https://www.python.org/)
[![FastAPI](https://img.shields.io/badge/FastAPI-0.109-green.svg)](https://fastapi.tiangolo.com/)
[![React](https://img.shields.io/badge/React-19.2-61dafb.svg)](https://reactjs.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📖 Description

Ce projet implémente un **système expert** complet utilisant l'IA symbolique et le raisonnement par règles pour aider les développeurs, architectes et décideurs techniques à choisir la meilleure architecture applicative et infrastructure pour leurs projets.

Le système analyse vos besoins à travers un questionnaire interactif et fournit des **recommandations personnalisées et détaillées** sur :

- 🏗️ **Architecture applicative** (Microservices, Monolithique, Serverless, Event-Driven)
- 📈 **Stratégie de scalabilité** (Horizontale, Verticale, Hybride)
- 💻 **Choix technologique** (Java/Spring Boot, JavaScript/Node.js, Python)
- ☁️ **Infrastructure** (Cloud Public, On-Premise, Hybride)
- 🖥️ **Dimensionnement serveur** (avec estimations de coûts)
- 🗄️ **Base de données** (PostgreSQL, MySQL, MongoDB, Redis)
- ⚡ **Système de cache** (Redis)
- 🐳 **Conteneurisation** (Docker, Kubernetes)
- 🔄 **CI/CD** (GitLab CI, GitHub Actions, Jenkins)
- 📊 **Monitoring** (Prometheus, Grafana, ELK Stack)

Chaque recommandation inclut :
- ✅ La solution recommandée
- 📝 **Justification détaillée** (pourquoi cette solution ?)
- 💡 **Détails techniques** (conseils pratiques, coûts estimés, exemples concrets)
- 🎯 **Niveau de confiance** (élevée, moyenne, faible)

## 🎓 Contexte Académique

Projet développé dans le cadre du cours de **Systèmes Experts** à l'ILISI. Il démontre :

- ✅ **Séparation base de connaissances / moteur d'inférence**
- ✅ **Règles SI...ALORS** explicites (30+ règles d'expertise)
- ✅ **Chaînage avant** (forward chaining)
- ✅ **Système d'explication** complet avec trace d'inférence
- ✅ **Application à un problème métier réel**

## 🚀 Fonctionnalités

### Backend (Python + FastAPI)

- **Moteur d'inférence personnalisé** avec chaînage avant
- **Base de connaissances** riche (30+ règles d'expertise)
- **API REST** complète et documentée (Swagger)
- **Système d'explication** transparent du raisonnement
- **Trace d'inférence** détaillée pour chaque consultation

### Frontend (React.js)

- **Interface moderne** avec design dark premium
- **Questionnaire interactif** (16 questions)
- **Affichage détaillé** des recommandations avec justifications
- **Visualisation** de la trace d'inférence
- **Design responsive** et animations fluides

## 🛠️ Technologies Utilisées

**Backend:**
- Python 3.8+
- FastAPI (framework web moderne)
- Pydantic (validation de données)
- Uvicorn (serveur ASGI)

**Frontend:**
- React.js 19
- Vite (build tool)
- Axios (client HTTP)
- CSS moderne avec animations

## 📦 Installation

### Prérequis

- Python 3.8+
- Node.js 16+
- npm ou yarn

### Backend

```bash
cd backend

# Activer l'environnement virtuel
..\\.venv\\Scripts\\activate  # Windows
# source ../.venv/bin/activate  # Linux/Mac

# Installer les dépendances
pip install -r requirements.txt

# Lancer le serveur
python main.py
```

Le serveur démarre sur `http://localhost:8000`  
Documentation API: `http://localhost:8000/docs`

### Frontend

```bash
cd frontend

# Installer les dépendances
npm install

# Lancer l'application
npm run dev
```

L'application démarre sur `http://localhost:5173`

## 💡 Utilisation

1. **Démarrer le backend** (port 8000)
2. **Démarrer le frontend** (port 5173)
3. **Ouvrir** `http://localhost:5173` dans votre navigateur
4. **Répondre** aux 16 questions sur votre projet
5. **Consulter** les recommandations détaillées avec justifications

## 📊 Exemple de Résultat

Pour un projet web avec charge élevée, scalabilité critique, et données sensibles, le système recommande :

- **Architecture:** Microservices (scalabilité horizontale illimitée)
- **Scalabilité:** Horizontale (ajout d'instances selon charge)
- **Langage:** Java/Spring Boot (écosystème enterprise mature)
- **Infrastructure:** Cloud Public (AWS, Azure, GCP)
- **Serveur:** Cluster auto-scaling (3-20 instances)
- **Base de données:** PostgreSQL (transactions ACID)
- **Cache:** Redis (réduction latence 90%)
- **Conteneurisation:** Docker + Kubernetes
- **CI/CD:** GitLab CI / GitHub Actions
- **Monitoring:** Prometheus + Grafana + ELK

## 🧠 Architecture du Système Expert

```
┌─────────────────────────────────────────┐
│         Interface Utilisateur           │
│            (React.js)                   │
└──────────────┬──────────────────────────┘
               │ HTTP/REST
┌──────────────▼──────────────────────────┐
│           API FastAPI                   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│      Moteur d'Inférence                 │
│    (Chaînage Avant)                     │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│    Base de Connaissances                │
│    (30+ Règles SI...ALORS)              │
└─────────────────────────────────────────┘
```

## 📁 Structure du Projet

```
projet_SYS_EXPERT/
├── backend/
│   ├── main.py                    # Point d'entrée FastAPI
│   ├── requirements.txt           # Dépendances Python
│   ├── engine/
│   │   ├── rule.py               # Classe Rule (SI...ALORS)
│   │   ├── inference_engine.py   # Moteur d'inférence
│   │   └── knowledge_base_extended.py  # 30+ règles
│   ├── api/
│   │   ├── routes.py             # Endpoints REST
│   │   └── models.py             # Modèles Pydantic
│   └── tests/
│       └── test_inference.py     # Tests unitaires
├── frontend/
│   ├── src/
│   │   ├── App.jsx               # Application React
│   │   ├── main.jsx              # Point d'entrée
│   │   └── index.css             # Design moderne
│   └── package.json
└── README.md
```

## 🔬 Tests

```bash
# Backend
cd backend
python tests/test_inference.py

# Tester l'API
# Ouvrir http://localhost:8000/docs (Swagger UI)
```

## 🎯 Endpoints API

- `GET /` - Informations sur l'API
- `GET /api/questions` - Liste des questions
- `POST /api/consult` - Consultation (envoyer faits, recevoir recommandations)
- `GET /api/rules` - Liste des règles (debug/admin)
- `GET /api/health` - Health check

## 📝 Exemple d'Utilisation de l'API

```python
import requests

# Soumettre une consultation
response = requests.post('http://localhost:8000/api/consult', json={
    "facts": {
        "type_application": "web",
        "charge_utilisateurs": "élevée",
        "scalabilite": "critique",
        "budget": "moyen",
        "transactions_acid": "oui",
        "structure_donnees": "structurée"
    }
})

# Récupérer les recommandations
recommendations = response.json()['recommendations']
for rec in recommendations:
    print(f"{rec['type']}: {rec['recommendation']}")
    print(f"Pourquoi: {rec['raison']}")
    print(f"Détails: {rec['details']}\n")
```

## 🤝 Contribution

Les contributions sont les bienvenues ! N'hésitez pas à :

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 👨‍💻 Auteur

Développé par [Votre Nom] - Projet de Systèmes Experts, ILISI

## 📄 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🙏 Remerciements

- Professeur Ramdani pour le cours de Systèmes Experts
- ILISI pour la formation en IA
- La communauté FastAPI et React

---

⭐ **Si ce projet vous a aidé, n'hésitez pas à lui donner une étoile !** ⭐
