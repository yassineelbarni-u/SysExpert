from typing import List, Dict
from .rule import Rule


def create_knowledge_base() -> List[Rule]:

    rules = []
    
    # REGLES D'ARCHITECTURE
    
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
            "architecture_raison": "Scalabilité horizontale et isolation des services"
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
            "architecture_raison": "Simplicité de développement et déploiement, coûts réduits"
        },
        description="Pour un budget limité avec une petite équipe et faible complexité",
        priority=9
    ))
    
    # Regle 3: Architecture Serverless pour charge variable
    rules.append(Rule(
        name="R3_Serverless_Variable",
        conditions={
            "charge_utilisateurs": "variable",
            "type_application": "web",
            "cout_optimisation": "oui"
        },
        conclusion={
            "architecture_recommandee": "Serverless",
            "architecture_confidence": "élevée",
            "architecture_raison": "Paiement à l'usage et auto-scaling automatique"
        },
        description="Pour une charge variable avec optimisation des coûts",
        priority=9
    ))
    
    # Regle 4: Architecture Event-Driven pour traitement asynchrone
    rules.append(Rule(
        name="R4_EventDriven_Async",
        conditions={
            "traitement_asynchrone": "oui",
            "integration_systemes": "nombreux"
        },
        conclusion={
            "architecture_recommandee": "Event-Driven (Architecture événementielle)",
            "architecture_confidence": "élevée",
            "architecture_raison": "Découplage et traitement asynchrone efficace"
        },
        description="Pour des traitements asynchrones avec nombreuses intégrations",
        priority=8
    ))
    
    # Regle 5: Architecture Microservices pour équipe distribuée
    rules.append(Rule(
        name="R5_Microservices_Equipe",
        conditions={
            "taille_equipe": "grande",
            "equipe_distribuee": "oui",
            "complexite_metier": "élevée"
        },
        conclusion={
            "architecture_recommandee": "Microservices",
            "architecture_confidence": "moyenne",
            "architecture_raison": "Permet l'autonomie des équipes et déploiements indépendants"
        },
        description="Pour une grande équipe distribuée avec complexité métier élevée",
        priority=7
    ))
    
    # Regle 6: Architecture Monolithique Modulaire pour transition
    rules.append(Rule(
        name="R6_Monolithique_Modulaire",
        conditions={
            "type_application": "web",
            "evolution_future": "microservices",
            "budget": "moyen"
        },
        conclusion={
            "architecture_recommandee": "Monolithique Modulaire",
            "architecture_confidence": "moyenne",
            "architecture_raison": "Facilite la transition future vers microservices"
        },
        description="Pour une évolution future vers microservices",
        priority=6
    ))
    
    
    # REGLES D'INFRASTRUCTURE
    
    # Regle 7: Cloud Public pour scalabilité et disponibilité
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
            "infrastructure_raison": "Scalabilité élastique et haute disponibilité"
        },
        description="Pour une scalabilité critique avec haute disponibilité",
        priority=10
    ))
    
    # Regle 8: On-Premise pour données sensibles
    rules.append(Rule(
        name="R8_OnPremise_Securite",
        conditions={
            "donnees_sensibles": "oui",
            "conformite_reglementaire": "stricte",
            "controle_infrastructure": "total"
        },
        conclusion={
            "infrastructure_recommandee": "On-Premise",
            "infrastructure_confidence": "élevée",
            "infrastructure_raison": "Contrôle total et conformité réglementaire"
        },
        description="Pour des données sensibles avec conformité stricte",
        priority=10
    ))
    
    # Regle 9: Cloud Hybride pour migration progressive
    rules.append(Rule(
        name="R9_Hybride_Migration",
        conditions={
            "infrastructure_existante": "on-premise",
            "migration_cloud": "progressive",
            "donnees_sensibles": "oui"
        },
        conclusion={
            "infrastructure_recommandee": "Cloud Hybride",
            "infrastructure_confidence": "moyenne",
            "infrastructure_raison": "Migration progressive tout en conservant données sensibles on-premise"
        },
        description="Pour une migration progressive vers le cloud",
        priority=8
    ))
    
    # Regle 10: Cloud Public pour startup
    rules.append(Rule(
        name="R10_Cloud_Startup",
        conditions={
            "type_entreprise": "startup",
            "croissance_rapide": "oui",
            "budget": "limité"
        },
        conclusion={
            "infrastructure_recommandee": "Cloud Public (offres startup)",
            "infrastructure_confidence": "élevée",
            "infrastructure_raison": "Pas d'investissement initial, paiement à l'usage, crédits startup disponibles"
        },
        description="Pour une startup en croissance rapide",
        priority=9
    ))
    

    # REGLES DE BASE DE DONNÉES
    
    # Regle 11: Base de données relationnelle pour transactions
    rules.append(Rule(
        name="R11_SQL_Transactions",
        conditions={
            "transactions_acid": "oui",
            "structure_donnees": "structurée"
        },
        conclusion={
            "database_recommandee": "Base de données relationnelle (PostgreSQL, MySQL)",
            "database_confidence": "élevée",
            "database_raison": "Garanties ACID et intégrité référentielle"
        },
        description="Pour des transactions ACID avec données structurées",
        priority=10
    ))
    
    # Regle 12: NoSQL pour données non structurées
    rules.append(Rule(
        name="R12_NoSQL_NonStructure",
        conditions={
            "structure_donnees": "non-structurée",
            "scalabilite": "critique",
            "transactions_acid": "non"
        },
        conclusion={
            "database_recommandee": "NoSQL (MongoDB, couchDB)",
            "database_confidence": "élevée",
            "database_raison": "Flexibilité du schéma et scalabilité horizontale"
        },
        description="Pour des données non structurées avec forte scalabilité",
        priority=9
    ))
    
    # Regle 13: Base de données en mémoire pour cache
    rules.append(Rule(
        name="R13_InMemory_Cache",
        conditions={
            "performance": "critique",
            "type_donnees": "cache"
        },
        conclusion={
            "database_recommandee": "Base de données en mémoire (Redis, Memcached)",
            "database_confidence": "élevée",
            "database_raison": "Performances extrêmes pour le cache"
        },
        description="Pour des performances critiques avec cache",
        priority=8
    ))
    
    # Regle 14: Base de données graphe pour relations complexes
    rules.append(Rule(
        name="R14_Graph_Relations",
        conditions={
            "type_donnees": "graphe",
            "relations_complexes": "oui"
        },
        conclusion={
            "database_recommandee": "Base de données graphe (Neo4j)",
            "database_confidence": "élevée",
            "database_raison": "Optimisée pour les requêtes sur relations complexes"
        },
        description="Pour des relations complexes entre données",
        priority=8
    ))
    
    
    # REGLES DE TECHNOLOGIES ET FRAMEWORKS
    
    # Regle 15: Conteneurisation pour microservices
    rules.append(Rule(
        name="R15_Conteneurs_Microservices",
        conditions={
            "architecture_recommandee": "Microservices"
        },
        conclusion={
            "technologies_recommandees": "Docker + Kubernetes",
            "technologies_confidence": "élevée",
            "technologies_raison": "Orchestration et déploiement de microservices"
        },
        description="Conteneurisation pour architecture microservices",
        priority=7
    ))
    
    # Regle 16: CI/CD pour déploiement continu
    rules.append(Rule(
        name="R16_CICD_DevOps",
        conditions={
            "deploiement_frequence": "élevée",
            "automatisation": "oui"
        },
        conclusion={
            "technologies_recommandees": "CI/CD (GitLab CI, GitHub Actions, Jenkins)",
            "technologies_confidence": "élevée",
            "technologies_raison": "Automatisation du déploiement et qualité du code"
        },
        description="CI/CD pour déploiements fréquents",
        priority=6
    ))
    
    # Regle 17: Message Queue pour event-driven
    rules.append(Rule(
        name="R17_MessageQueue_Events",
        conditions={
            "architecture_recommandee": "Event-Driven (Architecture événementielle)"
        },
        conclusion={
            "technologies_recommandees": "Message Queue (RabbitMQ, Kafka)",
            "technologies_confidence": "élevée",
            "technologies_raison": "Communication asynchrone entre services"
        },
        description="Message queue pour architecture événementielle",
        priority=7
    ))
    
    # Regle 18: API Gateway pour microservices
    rules.append(Rule(
        name="R18_APIGateway_Microservices",
        conditions={
            "architecture_recommandee": "Microservices",
            "api_externe": "oui"
        },
        conclusion={
            "technologies_recommandees": "API Gateway (Kong, AWS API Gateway)",
            "technologies_confidence": "moyenne",
            "technologies_raison": "Point d'entrée unique et gestion du trafic"
        },
        description="API Gateway pour exposition des microservices",
        priority=6
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
