from typing import List, Dict
from .rule import Rule


def create_knowledge_base() -> List[Rule]:

    rules = []
    
    # REGLES D'ARCHITECTURE APPLICATIVE (détaillées)
    
    
    # Regle 1: Architecture Microservices pour haute scalabilité
    rules.append(Rule(
        name="R1_Microservices_Scalabilite",
        conditions={
            "type_application": "web",
            "charge_utilisateurs": "élevée",
            "scalabilite": "critique"
        },
        conclusion={
            "architecture_recommandee": "Microservices",
            "architecture_confidence": "élevée",
            "architecture_raison": "Scalabilité horizontale illimitée, isolation des services, déploiements indépendants",
            "architecture_details": "Chaque service peut être scalé indépendamment selon sa charge. Permet d'ajouter des instances facilement."
        },
        description="Pour une application web avec forte charge nécessitant une scalabilité critique",
        priority=10
    ))
    
    # Regle 2: Architecture Monolithique pour petite équipe et budget limité
    rules.append(Rule(
        name="R2_Monolithique_Budget",
        conditions={
            "budget": "limité",
            "taille_equipe": "petite",
            "complexite_metier": "faible"
        },
        conclusion={
            "architecture_recommandee": "Monolithique",
            "architecture_confidence": "élevée",
            "architecture_raison": "Simplicité de développement et déploiement, coûts réduits, maintenance facile pour petite équipe",
            "architecture_details": "Une seule application déployée sur un serveur. Idéal pour démarrer rapidement avec peu de ressources."
        },
        description="Pour un budget limité avec une petite équipe et faible complexité",
        priority=9
    ))
    
    
    # REGLES DE SCALABILITÉ (NOUVELLES - très détaillées)
    
    # Regle 1: Scalabilité Horizontale pour Microservices
    rules.append(Rule(
        name="R_Scalabilite_Horizontale_Microservices",
        conditions={
            "architecture_recommandee": "Microservices"
        },
        conclusion={
            "scalabilite_type": "Horizontale",
            "scalabilite_raison": "Ajout de nouvelles instances de services selon la charge",
            "scalabilite_details": "Chaque microservice peut être répliqué indépendamment. Utiliser un load balancer pour distribuer la charge. Permet une scalabilité quasi-illimitée.",
            "scalabilite_confidence": "élevée"
        },
        description="Les microservices nécessitent une scalabilité horizontale",
        priority=8
    ))
    
    # Regle 2: Scalabilité Verticale pour Monolithique
    rules.append(Rule(
        name="R_Scalabilite_Verticale_Monolithique",
        conditions={
            "architecture_recommandee": "Monolithique",
            "charge_utilisateurs": ["faible", "moyenne"]
        },
        conclusion={
            "scalabilite_type": "Verticale",
            "scalabilite_raison": "Augmentation des ressources du serveur (CPU, RAM)",
            "scalabilite_details": "Pour un monolithe, il est plus simple d'augmenter la puissance du serveur. Limites: coût élevé et plafond matériel.",
            "scalabilite_confidence": "moyenne"
        },
        description="Monolithique avec charge faible/moyenne utilise scalabilité verticale",
        priority=7
    ))
    
    # Regle 3: Scalabilité Horizontale pour Monolithique haute charge
    rules.append(Rule(
        name="R_Scalabilite_Horizontale_Monolithique_Haute",
        conditions={
            "architecture_recommandee": "Monolithique",
            "charge_utilisateurs": "élevée"
        },
        conclusion={
            "scalabilite_type": "Horizontale + Verticale (Hybride)",
            "scalabilite_raison": "Plusieurs instances du monolithe derrière un load balancer + serveurs puissants",
            "scalabilite_details": "Déployer plusieurs copies du monolithe avec un load balancer (Nginx, HAProxy). Attention: gestion de session et base de données partagée nécessaires.",
            "scalabilite_confidence": "moyenne"
        },
        description="Monolithique haute charge nécessite scalabilité hybride",
        priority=7
    ))
    
    # REGLES DE CHOIX TECHNOLOGIQUE (Java vs JavaScript vs autres)
    
    # Regle 1: Java pour applications enterprise
    rules.append(Rule(
        name="R_Tech_Java_Enterprise",
        conditions={
            "type_application": "web",
            "complexite_metier": "élevée",
            "transactions_acid": "oui"
        },
        conclusion={
            "langage_recommande": "Java (Spring Boot)",
            "langage_raison": "Écosystème mature, performance, typage fort, excellent pour applications complexes",
            "langage_details": "Spring Boot offre: gestion transactions, sécurité, ORM (Hibernate), microservices (Spring Cloud). Idéal pour applications enterprise.",
            "langage_confidence": "élevée"
        },
        description="Java recommandé pour applications enterprise complexes",
        priority=9
    ))
    
    # Regle 2: JavaScript/Node.js pour applications temps réel
    rules.append(Rule(
        name="R_Tech_NodeJS_RealTime",
        conditions={
            "type_application": "web",
            "traitement_asynchrone": "oui"
        },
        conclusion={
            "langage_recommande": "JavaScript/Node.js (Express, NestJS)",
            "langage_raison": "Architecture événementielle, excellent pour I/O asynchrone, WebSockets natifs",
            "langage_details": "Node.js excelle pour: chat temps réel, streaming, APIs REST rapides. Écosystème npm riche. Utiliser NestJS pour structure enterprise.",
            "langage_confidence": "élevée"
        },
        description="Node.js recommandé pour applications temps réel et asynchrones",
        priority=9
    ))
    
    # Regle 3: JavaScript pour équipe frontend
    rules.append(Rule(
        name="R_Tech_JavaScript_FullStack",
        conditions={
            "taille_equipe": "petite",
            "complexite_metier": ["faible", "moyenne"]
        },
        conclusion={
            "langage_recommande": "JavaScript Full-Stack (Node.js + React/Vue)",
            "langage_raison": "Un seul langage frontend/backend, réutilisation de code, rapidité de développement",
            "langage_details": "Petite équipe peut maîtriser un seul langage. Stack MERN/MEAN populaire. Déploiement simplifié.",
            "langage_confidence": "moyenne"
        },
        description="JavaScript full-stack pour petites équipes",
        priority=7
    ))
    
    # Regle 4: Python pour data-intensive
    rules.append(Rule(
        name="R_Tech_Python_Data",
        conditions={
            "type_application": ["data-processing", "iot"],
        },
        conclusion={
            "langage_recommande": "Python (FastAPI, Django)",
            "langage_raison": "Excellent pour traitement de données, ML, nombreuses bibliothèques scientifiques",
            "langage_details": "FastAPI pour APIs performantes, Django pour applications complètes. Écosystème data science (Pandas, NumPy, TensorFlow).",
            "langage_confidence": "élevée"
        },
        description="Python recommandé pour applications data-intensive",
        priority=9
    ))
    
    
    # REGLES D'INFRASTRUCTURE ET TAILLE SERVEUR (très détaillées)
    
    
    # Regle 1: Cloud Public pour scalabilité critique
    rules.append(Rule(
        name="R7_Cloud_Public",
        conditions={
            "scalabilite": "critique",
            "disponibilite": "haute",
            "budget": ["moyen", "élevé"]
        },
        conclusion={
            "infrastructure_recommandee": "Cloud Public (AWS, Azure, GCP)",
            "infrastructure_confidence": "élevée",
            "infrastructure_raison": "Scalabilité élastique, haute disponibilité, paiement à l'usage",
            "infrastructure_details": "Auto-scaling automatique, multi-région pour HA, services managés (BDD, cache, CDN)"
        },
        description="Cloud public pour scalabilité critique",
        priority=10
    ))
    
    # Regle 2: Taille serveur pour charge faible
    rules.append(Rule(
        name="R_Serveur_Petit_Charge_Faible",
        conditions={
            "charge_utilisateurs": "faible",
            "budget": "limité"
        },
        conclusion={
            "taille_serveur": "Petit (2 vCPU, 4GB RAM)",
            "taille_serveur_raison": "Suffisant pour <1000 utilisateurs/jour, coût optimisé",
            "taille_serveur_details": "Exemples: AWS t3.small, Azure B2s, GCP e2-small. Coût ~20-30€/mois. Peut héberger monolithe ou 2-3 microservices légers.",
            "taille_serveur_confidence": "élevée"
        },
        description="Petit serveur pour charge faible",
        priority=8
    ))
    
    # Regle 3: Taille serveur pour charge moyenne
    rules.append(Rule(
        name="R_Serveur_Moyen_Charge_Moyenne",
        conditions={
            "charge_utilisateurs": "moyenne"
        },
        conclusion={
            "taille_serveur": "Moyen (4 vCPU, 8-16GB RAM)",
            "taille_serveur_raison": "Adapté pour 1000-10000 utilisateurs/jour",
            "taille_serveur_details": "Exemples: AWS t3.large, Azure B4ms, GCP e2-standard-4. Coût ~80-150€/mois. Peut héberger monolithe ou 5-8 microservices.",
            "taille_serveur_confidence": "élevée"
        },
        description="Serveur moyen pour charge moyenne",
        priority=8
    ))
    
    # Regle 4: Cluster pour charge élevée
    rules.append(Rule(
        name="R_Cluster_Charge_Elevee",
        conditions={
            "charge_utilisateurs": "élevée",
            "scalabilite": "critique"
        },
        conclusion={
            "taille_serveur": "Cluster de serveurs (Auto-Scaling Group)",
            "taille_serveur_raison": "Plusieurs serveurs moyens/grands avec load balancer",
            "taille_serveur_details": "Minimum 3 serveurs (4 vCPU, 16GB chacun) derrière load balancer. Auto-scaling 3-20 instances selon charge. Coût: 300-2000€/mois selon trafic.",
            "taille_serveur_confidence": "élevée"
        },
        description="Cluster auto-scalable pour charge élevée",
        priority=10
    ))
    
    
    # REGLES DE DÉPLOIEMENT ET CONTENEURISATION
    
    
    # Regle 1: Docker pour microservices
    rules.append(Rule(
        name="R15_Conteneurs_Microservices",
        conditions={
            "architecture_recommandee": "Microservices"
        },
        conclusion={
            "conteneurisation": "Docker + Kubernetes",
            "conteneurisation_raison": "Isolation, portabilité, orchestration automatique",
            "conteneurisation_details": "Chaque microservice dans un container Docker. Kubernetes gère: déploiement, scaling, load balancing, health checks. Utiliser Helm pour gestion.",
            "conteneurisation_confidence": "élevée"
        },
        description="Conteneurisation obligatoire pour microservices",
        priority=9
    ))
    
    # Regle 2: Docker optionnel pour monolithique
    rules.append(Rule(
        name="R_Docker_Monolithique_Optionnel",
        conditions={
            "architecture_recommandee": "Monolithique",
            "deploiement_frequence": "élevée"
        },
        conclusion={
            "conteneurisation": "Docker (optionnel mais recommandé)",
            "conteneurisation_raison": "Facilite déploiement, environnements reproductibles",
            "conteneurisation_details": "Un seul container pour le monolithe. Simplifie CI/CD. Peut utiliser Docker Compose pour dev local avec BDD.",
            "conteneurisation_confidence": "moyenne"
        },
        description="Docker recommandé même pour monolithique si deploiements frequent",
        priority=6
    ))
    
    # Regle: CI/CD pour déploiement continu
    rules.append(Rule(
        name="R16_CICD_DevOps",
        conditions={
            "deploiement_frequence": "élevée",
        },
        conclusion={
            "cicd_recommande": "GitLab CI / GitHub Actions / Jenkins",
            "cicd_raison": "Automatisation tests, build, déploiement. Réduction erreurs humaines.",
            "cicd_details": "Pipeline: git push → tests auto → build Docker → déploiement staging → tests E2E → déploiement production. Rollback automatique si échec.",
            "cicd_confidence": "élevée"
        },
        description="CI/CD essentiel pour déploiements fréquents",
        priority=8
    ))
    
    
    # REGLES DE BASE DE DONNÉES (enrichies)
    
    
    # Regle: PostgreSQL pour applications transactionnelles
    rules.append(Rule(
        name="R11_SQL_Transactions",
        conditions={
            "transactions_acid": "oui",
            "structure_donnees": "structurée"
        },
        conclusion={
            "database_recommandee": "PostgreSQL (recommandé) ou MySQL",
            "database_confidence": "élevée",
            "database_raison": "Garanties ACID, intégrité référentielle, requêtes complexes",
            "database_details": "PostgreSQL: plus de fonctionnalités (JSON, full-text search, extensions). MySQL: plus simple, très populaire. Utiliser réplication master-slave pour HA."
        },
        description="PostgreSQL/MySQL pour transactions ACID",
        priority=10
    ))
    
    # Regle: MongoDB pour données non structurées
    rules.append(Rule(
        name="R12_NoSQL_NonStructure",
        conditions={
            "structure_donnees": ["non-structurée", "semi-structurée"],
            "scalabilite": "critique"
        },
        conclusion={
            "database_recommandee": "MongoDB (NoSQL document)",
            "database_confidence": "élevée",
            "database_raison": "Schéma flexible, scalabilité horizontale (sharding), performance lecture",
            "database_details": "Idéal pour: catalogues produits, logs, données IoT. Sharding automatique pour scalabilité. Attention: pas de transactions multi-documents (avant v4)."
        },
        description="MongoDB pour données non structurées scalables",
        priority=9
    ))
    
    # Regle: Redis pour cache
    rules.append(Rule(
        name="R13_Redis_Cache",
        conditions={
            "charge_utilisateurs": ["moyenne", "élevée"]
        },
        conclusion={
            "cache_recommande": "Redis",
            "cache_raison": "Cache en mémoire ultra-rapide, réduit charge BDD",
            "cache_details": "Utiliser Redis pour: sessions utilisateur, cache requêtes fréquentes, compteurs temps réel. Réduction latence de 90%. Coût: ~20€/mois (managed).",
            "cache_confidence": "élevée"
        },
        description="Redis recommandé comme cache pour charge moyenne/élevée",
        priority=7
    ))
    
    
    # REGLES DE SÉCURITÉ ET CONFORMITE
    
    
    # Regle: On-Premise pour données sensibles
    rules.append(Rule(
        name="R8_OnPremise_Securite",
        conditions={
            "donnees_sensibles": "oui",
            "conformite_reglementaire": "stricte"
        },
        conclusion={
            "infrastructure_recommandee": "On-Premise (serveurs dédiés)",
            "infrastructure_confidence": "élevée",
            "infrastructure_raison": "Contrôle total données, conformité RGPD/HIPAA, pas de cloud public",
            "infrastructure_details": "Datacenter privé ou colocation. Coût initial élevé (serveurs, réseau, sécurité). Maintenance interne nécessaire. Backup et DR critiques."
        },
        description="On-premise obligatoire pour données très sensibles",
        priority=10
    ))
    
    
    # REGLES DE MONITORING ET OBSERVABILITE
    
    
    # Regle: Monitoring pour production
    rules.append(Rule(
        name="R_Monitoring_Production",
        conditions={
            "disponibilite": ["haute", "critique"]
        },
        conclusion={
            "monitoring_recommande": "Prometheus + Grafana + ELK Stack",
            "monitoring_raison": "Détection proactive des problèmes, alertes, analyse logs",
            "monitoring_details": "Prometheus: métriques (CPU, RAM, latence). Grafana: dashboards. ELK: logs centralisés. Alertes Slack/email si erreurs. Coût: ~50-200€/mois.",
            "monitoring_confidence": "élevée"
        },
        description="Monitoring essentiel pour haute disponibilité",
        priority=8
    ))
    
    return rules


def get_questions() -> List[Dict]:

    return [
        {
            "id": "type_application",
            "question": "Quel type d'application souhaitez-vous développer ?",
            "type": "select",
            "options": ["web", "mobile", "desktop", "iot", "data-processing"]
        },
        {
            "id": "charge_utilisateurs",
            "question": "Quelle est la charge utilisateurs attendue ?",
            "type": "select",
            "options": ["faible", "moyenne", "élevée", "variable"]
        },
        {
            "id": "scalabilite",
            "question": "La scalabilité est-elle critique pour votre application ?",
            "type": "select",
            "options": ["non", "moyenne", "critique"]
        },
        {
            "id": "budget",
            "question": "Quel est votre budget ?",
            "type": "select",
            "options": ["limité", "moyen", "élevé"]
        },
        {
            "id": "taille_equipe",
            "question": "Quelle est la taille de votre équipe de développement ?",
            "type": "select",
            "options": ["petite", "moyenne", "grande"]
        },
        {
            "id": "complexite_metier",
            "question": "Quelle est la complexité métier de votre application ?",
            "type": "select",
            "options": ["faible", "moyenne", "élevée"]
        },
        {
            "id": "equipe_distribuee",
            "question": "Votre équipe est-elle distribuée géographiquement ?",
            "type": "boolean",
            "options": ["oui", "non"]
        },
        {
            "id": "traitement_asynchrone",
            "question": "Avez-vous besoin de traitement asynchrone ?",
            "type": "boolean",
            "options": ["oui", "non"]
        },
        {
            "id": "integration_systemes",
            "question": "Combien de systèmes externes devez-vous intégrer ?",
            "type": "select",
            "options": ["aucun", "quelques-uns", "nombreux"]
        },
        {
            "id": "donnees_sensibles",
            "question": "Manipulez-vous des données sensibles ?",
            "type": "boolean",
            "options": ["oui", "non"]
        },
        {
            "id": "conformite_reglementaire",
            "question": "Avez-vous des contraintes de conformité réglementaire ?",
            "type": "select",
            "options": ["aucune", "moyenne", "stricte"]
        },
        {
            "id": "disponibilite",
            "question": "Quel niveau de disponibilité requis ?",
            "type": "select",
            "options": ["normale", "haute", "critique"]
        },
        {
            "id": "transactions_acid",
            "question": "Avez-vous besoin de transactions ACID ?",
            "type": "boolean",
            "options": ["oui", "non"]
        },
        {
            "id": "structure_donnees",
            "question": "Quelle est la structure de vos données ?",
            "type": "select",
            "options": ["structurée", "semi-structurée", "non-structurée"]
        },
        {
            "id": "type_entreprise",
            "question": "Quel type d'entreprise êtes-vous ?",
            "type": "select",
            "options": ["startup", "pme", "grande-entreprise", "administration"]
        },
        {
            "id": "deploiement_frequence",
            "question": "Quelle est la fréquence de déploiement souhaitée ?",
            "type": "select",
            "options": ["faible", "moyenne", "élevée"]
        }
    ]
