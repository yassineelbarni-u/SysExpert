package com.ilisi.expert_system.engine;

import java.util.*;

public class KnowledgeBase {

    // Helper pour construire une Map<String,Object> inline
    private static Map<String, Object> map(Object... pairs) {
        Map<String, Object> m = new LinkedHashMap<>();
        for (int i = 0; i < pairs.length; i += 2) {
            m.put((String) pairs[i], pairs[i + 1]);
        }
        return m;
    }

    public static List<Rule> createKnowledgeBase() {
        List<Rule> rules = new ArrayList<>();

        // === RÈGLES D'ARCHITECTURE APPLICATIVE ===

        // R1: Microservices pour haute scalabilité
        rules.add(new Rule(
            "R1_Microservices_Scalabilite",
            map("type_application", "web", "charge_utilisateurs", "élevée", "scalabilite", "critique"),
            map("architecture_recommandee", "Microservices",
                "architecture_confidence", "élevée",
                "architecture_raison", "Scalabilité horizontale illimitée, isolation des services, déploiements indépendants",
                "architecture_details", "Chaque service peut être scalé indépendamment selon sa charge. Permet d'ajouter des instances facilement."),
            "Pour une application web avec forte charge nécessitant une scalabilité critique",
            10
        ));

        // R2: Monolithique pour petite équipe et budget limité
        rules.add(new Rule(
            "R2_Monolithique_Budget",
            map("budget", "limité", "taille_equipe", "petite", "complexite_metier", "faible"),
            map("architecture_recommandee", "Monolithique",
                "architecture_confidence", "élevée",
                "architecture_raison", "Simplicité de développement et déploiement, coûts réduits, maintenance facile pour petite équipe",
                "architecture_details", "Une seule application déployée sur un serveur. Idéal pour démarrer rapidement avec peu de ressources."),
            "Pour un budget limité avec une petite équipe et faible complexité",
            9
        ));

        // === RÈGLES DE SCALABILITÉ ===

        // Scalabilité Horizontale pour Microservices
        rules.add(new Rule(
            "R_Scalabilite_Horizontale_Microservices",
            map("architecture_recommandee", "Microservices"),
            map("scalabilite_type", "Horizontale",
                "scalabilite_raison", "Ajout de nouvelles instances de services selon la charge",
                "scalabilite_details", "Chaque microservice peut être répliqué indépendamment. Utiliser un load balancer pour distribuer la charge. Permet une scalabilité quasi-illimitée.",
                "scalabilite_confidence", "élevée"),
            "Les microservices nécessitent une scalabilité horizontale",
            8
        ));

        // Scalabilité Verticale pour Monolithique charge faible/moyenne
        rules.add(new Rule(
            "R_Scalabilite_Verticale_Monolithique",
            map("architecture_recommandee", "Monolithique",
                "charge_utilisateurs", List.of("faible", "moyenne")),
            map("scalabilite_type", "Verticale",
                "scalabilite_raison", "Augmentation des ressources du serveur (CPU, RAM)",
                "scalabilite_details", "Pour un monolithe, il est plus simple d'augmenter la puissance du serveur. Limites: coût élevé et plafond matériel.",
                "scalabilite_confidence", "moyenne"),
            "Monolithique avec charge faible/moyenne utilise scalabilité verticale",
            7
        ));

        // Scalabilité Hybride pour Monolithique haute charge
        rules.add(new Rule(
            "R_Scalabilite_Horizontale_Monolithique_Haute",
            map("architecture_recommandee", "Monolithique", "charge_utilisateurs", "élevée"),
            map("scalabilite_type", "Horizontale + Verticale (Hybride)",
                "scalabilite_raison", "Plusieurs instances du monolithe derrière un load balancer + serveurs puissants",
                "scalabilite_details", "Déployer plusieurs copies du monolithe avec un load balancer (Nginx, HAProxy). Attention: gestion de session et base de données partagée nécessaires.",
                "scalabilite_confidence", "moyenne"),
            "Monolithique haute charge nécessite scalabilité hybride",
            7
        ));

        // === RÈGLES DE CHOIX TECHNOLOGIQUE ===

        // Java pour applications enterprise
        rules.add(new Rule(
            "R_Tech_Java_Enterprise",
            map("type_application", "web", "complexite_metier", "élevée", "transactions_acid", "oui"),
            map("langage_recommande", "Java (Spring Boot)",
                "langage_raison", "Écosystème mature, performance, typage fort, excellent pour applications complexes",
                "langage_details", "Spring Boot offre: gestion transactions, sécurité, ORM (Hibernate), microservices (Spring Cloud). Idéal pour applications enterprise.",
                "langage_confidence", "élevée"),
            "Java recommandé pour applications enterprise complexes",
            9
        ));

        // JavaScript/Node.js pour applications temps réel
        rules.add(new Rule(
            "R_Tech_NodeJS_RealTime",
            map("type_application", "web", "traitement_asynchrone", "oui"),
            map("langage_recommande", "JavaScript/Node.js (Express, NestJS)",
                "langage_raison", "Architecture événementielle, excellent pour I/O asynchrone, WebSockets natifs",
                "langage_details", "Node.js excelle pour: chat temps réel, streaming, APIs REST rapides. Écosystème npm riche. Utiliser NestJS pour structure enterprise.",
                "langage_confidence", "élevée"),
            "Node.js recommandé pour applications temps réel et asynchrones",
            9
        ));

        // JavaScript Full-Stack pour petites équipes
        rules.add(new Rule(
            "R_Tech_JavaScript_FullStack",
            map("taille_equipe", "petite", "complexite_metier", List.of("faible", "moyenne")),
            map("langage_recommande", "JavaScript Full-Stack (Node.js + React/Vue)",
                "langage_raison", "Un seul langage frontend/backend, réutilisation de code, rapidité de développement",
                "langage_details", "Petite équipe peut maîtriser un seul langage. Stack MERN/MEAN populaire. Déploiement simplifié.",
                "langage_confidence", "moyenne"),
            "JavaScript full-stack pour petites équipes",
            7
        ));

        // Python pour data-intensive
        rules.add(new Rule(
            "R_Tech_Python_Data",
            map("type_application", List.of("data-processing", "iot")),
            map("langage_recommande", "Python (FastAPI, Django)",
                "langage_raison", "Excellent pour traitement de données, ML, nombreuses bibliothèques scientifiques",
                "langage_details", "FastAPI pour APIs performantes, Django pour applications complètes. Écosystème data science (Pandas, NumPy, TensorFlow).",
                "langage_confidence", "élevée"),
            "Python recommandé pour applications data-intensive",
            9
        ));

        // === RÈGLES D'INFRASTRUCTURE ET TAILLE SERVEUR ===

        // Cloud Public pour scalabilité critique
        rules.add(new Rule(
            "R7_Cloud_Public",
            map("scalabilite", "critique", "disponibilite", "haute", "budget", List.of("moyen", "élevé")),
            map("infrastructure_recommandee", "Cloud Public (AWS, Azure, GCP)",
                "infrastructure_confidence", "élevée",
                "infrastructure_raison", "Scalabilité élastique, haute disponibilité, paiement à l'usage",
                "infrastructure_details", "Auto-scaling automatique, multi-région pour HA, services managés (BDD, cache, CDN)"),
            "Cloud public pour scalabilité critique",
            10
        ));

        // Petit serveur pour charge faible
        rules.add(new Rule(
            "R_Serveur_Petit_Charge_Faible",
            map("charge_utilisateurs", "faible", "budget", "limité"),
            map("taille_serveur", "Petit (2 vCPU, 4GB RAM)",
                "taille_serveur_raison", "Suffisant pour <1000 utilisateurs/jour, coût optimisé",
                "taille_serveur_details", "Exemples: AWS t3.small, Azure B2s, GCP e2-small. Coût ~20-30€/mois. Peut héberger monolithe ou 2-3 microservices légers.",
                "taille_serveur_confidence", "élevée"),
            "Petit serveur pour charge faible",
            8
        ));

        // Serveur moyen pour charge moyenne
        rules.add(new Rule(
            "R_Serveur_Moyen_Charge_Moyenne",
            map("charge_utilisateurs", "moyenne"),
            map("taille_serveur", "Moyen (4 vCPU, 8-16GB RAM)",
                "taille_serveur_raison", "Adapté pour 1000-10000 utilisateurs/jour",
                "taille_serveur_details", "Exemples: AWS t3.large, Azure B4ms, GCP e2-standard-4. Coût ~80-150€/mois. Peut héberger monolithe ou 5-8 microservices.",
                "taille_serveur_confidence", "élevée"),
            "Serveur moyen pour charge moyenne",
            8
        ));

        // Cluster pour charge élevée
        rules.add(new Rule(
            "R_Cluster_Charge_Elevee",
            map("charge_utilisateurs", "élevée", "scalabilite", "critique"),
            map("taille_serveur", "Cluster de serveurs (Auto-Scaling Group)",
                "taille_serveur_raison", "Plusieurs serveurs moyens/grands avec load balancer",
                "taille_serveur_details", "Minimum 3 serveurs (4 vCPU, 16GB chacun) derrière load balancer. Auto-scaling 3-20 instances selon charge. Coût: 300-2000€/mois selon trafic.",
                "taille_serveur_confidence", "élevée"),
            "Cluster auto-scalable pour charge élevée",
            10
        ));

        // === RÈGLES DE DÉPLOIEMENT ET CONTENEURISATION ===

        // Docker pour microservices
        rules.add(new Rule(
            "R15_Conteneurs_Microservices",
            map("architecture_recommandee", "Microservices"),
            map("conteneurisation", "Docker + Kubernetes",
                "conteneurisation_raison", "Isolation, portabilité, orchestration automatique",
                "conteneurisation_details", "Chaque microservice dans un container Docker. Kubernetes gère: déploiement, scaling, load balancing, health checks. Utiliser Helm pour gestion.",
                "conteneurisation_confidence", "élevée"),
            "Conteneurisation obligatoire pour microservices",
            9
        ));

        // Docker optionnel pour monolithique avec déploiements fréquents
        rules.add(new Rule(
            "R_Docker_Monolithique_Optionnel",
            map("architecture_recommandee", "Monolithique", "deploiement_frequence", "élevée"),
            map("conteneurisation", "Docker (optionnel mais recommandé)",
                "conteneurisation_raison", "Facilite déploiement, environnements reproductibles",
                "conteneurisation_details", "Un seul container pour le monolithe. Simplifie CI/CD. Peut utiliser Docker Compose pour dev local avec BDD.",
                "conteneurisation_confidence", "moyenne"),
            "Docker recommandé même pour monolithique si déploiements fréquents",
            6
        ));

        // CI/CD pour déploiement continu
        rules.add(new Rule(
            "R16_CICD_DevOps",
            map("deploiement_frequence", "élevée"),
            map("cicd_recommande", "GitLab CI / GitHub Actions / Jenkins",
                "cicd_raison", "Automatisation tests, build, déploiement. Réduction erreurs humaines.",
                "cicd_details", "Pipeline: git push → tests auto → build Docker → déploiement staging → tests E2E → déploiement production. Rollback automatique si échec.",
                "cicd_confidence", "élevée"),
            "CI/CD essentiel pour déploiements fréquents",
            8
        ));

        // === RÈGLES DE BASE DE DONNÉES ===

        // PostgreSQL pour applications transactionnelles
        rules.add(new Rule(
            "R11_SQL_Transactions",
            map("transactions_acid", "oui", "structure_donnees", "structurée"),
            map("database_recommandee", "PostgreSQL (recommandé) ou MySQL",
                "database_confidence", "élevée",
                "database_raison", "Garanties ACID, intégrité référentielle, requêtes complexes",
                "database_details", "PostgreSQL: plus de fonctionnalités (JSON, full-text search, extensions). MySQL: plus simple, très populaire. Utiliser réplication master-slave pour HA."),
            "PostgreSQL/MySQL pour transactions ACID",
            10
        ));

        // MongoDB pour données non structurées
        rules.add(new Rule(
            "R12_NoSQL_NonStructure",
            map("structure_donnees", List.of("non-structurée", "semi-structurée"), "scalabilite", "critique"),
            map("database_recommandee", "MongoDB (NoSQL document)",
                "database_confidence", "élevée",
                "database_raison", "Schéma flexible, scalabilité horizontale (sharding), performance lecture",
                "database_details", "Idéal pour: catalogues produits, logs, données IoT. Sharding automatique pour scalabilité. Attention: pas de transactions multi-documents (avant v4)."),
            "MongoDB pour données non structurées scalables",
            9
        ));

        // Redis pour cache
        rules.add(new Rule(
            "R13_Redis_Cache",
            map("charge_utilisateurs", List.of("moyenne", "élevée")),
            map("cache_recommande", "Redis",
                "cache_raison", "Cache en mémoire ultra-rapide, réduit charge BDD",
                "cache_details", "Utiliser Redis pour: sessions utilisateur, cache requêtes fréquentes, compteurs temps réel. Réduction latence de 90%. Coût: ~20€/mois (managed).",
                "cache_confidence", "élevée"),
            "Redis recommandé comme cache pour charge moyenne/élevée",
            7
        ));

        // === RÈGLES DE SÉCURITÉ ET CONFORMITÉ ===

        // On-Premise pour données sensibles
        rules.add(new Rule(
            "R8_OnPremise_Securite",
            map("donnees_sensibles", "oui", "conformite_reglementaire", "stricte"),
            map("infrastructure_recommandee", "On-Premise (serveurs dédiés)",
                "infrastructure_confidence", "élevée",
                "infrastructure_raison", "Contrôle total données, conformité RGPD/HIPAA, pas de cloud public",
                "infrastructure_details", "Datacenter privé ou colocation. Coût initial élevé (serveurs, réseau, sécurité). Maintenance interne nécessaire. Backup et DR critiques."),
            "On-premise obligatoire pour données très sensibles",
            10
        ));

        // === RÈGLES DE MONITORING ET OBSERVABILITÉ ===

        // Monitoring pour haute disponibilité
        rules.add(new Rule(
            "R_Monitoring_Production",
            map("disponibilite", List.of("haute", "critique")),
            map("monitoring_recommande", "Prometheus + Grafana + ELK Stack",
                "monitoring_raison", "Détection proactive des problèmes, alertes, analyse logs",
                "monitoring_details", "Prometheus: métriques (CPU, RAM, latence). Grafana: dashboards. ELK: logs centralisés. Alertes Slack/email si erreurs. Coût: ~50-200€/mois.",
                "monitoring_confidence", "élevée"),
            "Monitoring essentiel pour haute disponibilité",
            8
        ));

        return rules;
    }

    public static List<Map<String, Object>> getQuestions() {
        List<Map<String, Object>> questions = new ArrayList<>();

        questions.add(map(
            "id", "type_application",
            "question", "Quel type d'application souhaitez-vous développer ?",
            "type", "select",
            "options", List.of("web", "mobile", "desktop", "iot", "data-processing")
        ));
        questions.add(map(
            "id", "charge_utilisateurs",
            "question", "Quelle est la charge utilisateurs attendue ?",
            "type", "select",
            "options", List.of("faible", "moyenne", "élevée", "variable")
        ));
        questions.add(map(
            "id", "scalabilite",
            "question", "La scalabilité est-elle critique pour votre application ?",
            "type", "select",
            "options", List.of("non", "moyenne", "critique")
        ));
        questions.add(map(
            "id", "budget",
            "question", "Quel est votre budget ?",
            "type", "select",
            "options", List.of("limité", "moyen", "élevé")
        ));
        questions.add(map(
            "id", "taille_equipe",
            "question", "Quelle est la taille de votre équipe de développement ?",
            "type", "select",
            "options", List.of("petite", "moyenne", "grande")
        ));
        questions.add(map(
            "id", "complexite_metier",
            "question", "Quelle est la complexité métier de votre application ?",
            "type", "select",
            "options", List.of("faible", "moyenne", "élevée")
        ));
        questions.add(map(
            "id", "equipe_distribuee",
            "question", "Votre équipe est-elle distribuée géographiquement ?",
            "type", "boolean",
            "options", List.of("oui", "non")
        ));
        questions.add(map(
            "id", "traitement_asynchrone",
            "question", "Avez-vous besoin de traitement asynchrone ?",
            "type", "boolean",
            "options", List.of("oui", "non")
        ));
        questions.add(map(
            "id", "integration_systemes",
            "question", "Combien de systèmes externes devez-vous intégrer ?",
            "type", "select",
            "options", List.of("aucun", "quelques-uns", "nombreux")
        ));
        questions.add(map(
            "id", "donnees_sensibles",
            "question", "Manipulez-vous des données sensibles ?",
            "type", "boolean",
            "options", List.of("oui", "non")
        ));
        questions.add(map(
            "id", "conformite_reglementaire",
            "question", "Avez-vous des contraintes de conformité réglementaire ?",
            "type", "select",
            "options", List.of("aucune", "moyenne", "stricte")
        ));
        questions.add(map(
            "id", "disponibilite",
            "question", "Quel niveau de disponibilité requis ?",
            "type", "select",
            "options", List.of("normale", "haute", "critique")
        ));
        questions.add(map(
            "id", "transactions_acid",
            "question", "Avez-vous besoin de transactions ACID ?",
            "type", "boolean",
            "options", List.of("oui", "non")
        ));
        questions.add(map(
            "id", "structure_donnees",
            "question", "Quelle est la structure de vos données ?",
            "type", "select",
            "options", List.of("structurée", "semi-structurée", "non-structurée")
        ));
        questions.add(map(
            "id", "type_entreprise",
            "question", "Quel type d'entreprise êtes-vous ?",
            "type", "select",
            "options", List.of("startup", "pme", "grande-entreprise", "administration")
        ));
        questions.add(map(
            "id", "deploiement_frequence",
            "question", "Quelle est la fréquence de déploiement souhaitée ?",
            "type", "select",
            "options", List.of("faible", "moyenne", "élevée")
        ));

        return questions;
    }
}
