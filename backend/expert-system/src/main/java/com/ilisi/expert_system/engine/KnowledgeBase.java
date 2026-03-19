package com.ilisi.expert_system.engine;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base de connaissances du système expert.
 * Contient toutes les règles d'inférence et les questions.
 */
@Component
public class KnowledgeBase {

    private final List<Rule> rules;

    public KnowledgeBase() {
        this.rules = buildRules();
    }

    public List<Rule> getRules() {
        return rules;
    }

    private List<Rule> buildRules() {
        List<Rule> rules = new ArrayList<>();

        // ── REGLES D'ARCHITECTURE APPLICATIVE ──────────────────────────────

        // R1: Microservices pour haute scalabilité
        rules.add(new Rule(
                "R1_Microservices_Scalabilite",
                Map.of("type_application", "web",
                       "charge_utilisateurs", "élevée",
                       "scalabilite", "critique"),
                Map.of("architecture_recommandee", "Microservices",
                       "architecture_confidence", "élevée",
                       "architecture_raison", "Scalabilité horizontale illimitée, isolation des services, déploiements indépendants",
                       "architecture_details", "Chaque service peut être scalé indépendamment selon sa charge. Permet d'ajouter des instances facilement."),
                "Pour une application web avec forte charge nécessitant une scalabilité critique",
                10
        ));

        // R2: Monolithique pour petite équipe / budget limité
        rules.add(new Rule(
                "R2_Monolithique_Budget",
                Map.of("budget", "limité",
                       "taille_equipe", "petite",
                       "complexite_metier", "faible"),
                Map.of("architecture_recommandee", "Monolithique",
                       "architecture_confidence", "élevée",
                       "architecture_raison", "Simplicité de développement et déploiement, coûts réduits, maintenance facile pour petite équipe",
                       "architecture_details", "Une seule application déployée sur un serveur. Idéal pour démarrer rapidement avec peu de ressources."),
                "Pour un budget limité avec une petite équipe et faible complexité",
                9
        ));

        // ── REGLES DE SCALABILITÉ ───────────────────────────────────────────

        // Scalabilité horizontale pour Microservices
        rules.add(new Rule(
                "R_Scalabilite_Horizontale_Microservices",
                Map.of("architecture_recommandee", "Microservices"),
                Map.of("scalabilite_type", "Horizontale",
                       "scalabilite_raison", "Ajout de nouvelles instances de services selon la charge",
                       "scalabilite_details", "Chaque microservice peut être répliqué indépendamment. Utiliser un load balancer pour distribuer la charge. Permet une scalabilité quasi-illimitée.",
                       "scalabilite_confidence", "élevée"),
                "Les microservices nécessitent une scalabilité horizontale",
                8
        ));

        // Scalabilité verticale pour Monolithique charge faible/moyenne
        Map<String, Object> scalVertCond = new HashMap<>();
        scalVertCond.put("architecture_recommandee", "Monolithique");
        scalVertCond.put("charge_utilisateurs", List.of("faible", "moyenne"));
        rules.add(new Rule(
                "R_Scalabilite_Verticale_Monolithique",
                scalVertCond,
                Map.of("scalabilite_type", "Verticale",
                       "scalabilite_raison", "Augmentation des ressources du serveur (CPU, RAM)",
                       "scalabilite_details", "Pour un monolithe, il est plus simple d'augmenter la puissance du serveur. Limites: coût élevé et plafond matériel.",
                       "scalabilite_confidence", "moyenne"),
                "Monolithique avec charge faible/moyenne utilise scalabilité verticale",
                7
        ));

        // Scalabilité hybride pour Monolithique haute charge
        rules.add(new Rule(
                "R_Scalabilite_Horizontale_Monolithique_Haute",
                Map.of("architecture_recommandee", "Monolithique",
                       "charge_utilisateurs", "élevée"),
                Map.of("scalabilite_type", "Horizontale + Verticale (Hybride)",
                       "scalabilite_raison", "Plusieurs instances du monolithe derrière un load balancer + serveurs puissants",
                       "scalabilite_details", "Déployer plusieurs copies du monolithe avec un load balancer (Nginx, HAProxy). Attention: gestion de session et base de données partagée nécessaires.",
                       "scalabilite_confidence", "moyenne"),
                "Monolithique haute charge nécessite scalabilité hybride",
                7
        ));

        // ── REGLES DE CHOIX TECHNOLOGIQUE ──────────────────────────────────

        // Java / Spring Boot pour applications enterprise
        rules.add(new Rule(
                "R_Tech_Java_Enterprise",
                Map.of("type_application", "web",
                       "complexite_metier", "élevée",
                       "transactions_acid", "oui"),
                Map.of("langage_recommande", "Java (Spring Boot)",
                       "langage_raison", "Écosystème mature, performance, typage fort, excellent pour applications complexes",
                       "langage_details", "Spring Boot offre: gestion transactions, sécurité, ORM (Hibernate), microservices (Spring Cloud). Idéal pour applications enterprise.",
                       "langage_confidence", "élevée"),
                "Java recommandé pour applications enterprise complexes",
                9
        ));

        // Node.js pour applications temps réel
        rules.add(new Rule(
                "R_Tech_NodeJS_RealTime",
                Map.of("type_application", "web",
                       "traitement_asynchrone", "oui"),
                Map.of("langage_recommande", "JavaScript/Node.js (Express, NestJS)",
                       "langage_raison", "Architecture événementielle, excellent pour I/O asynchrone, WebSockets natifs",
                       "langage_details", "Node.js excelle pour: chat temps réel, streaming, APIs REST rapides. Écosystème npm riche. Utiliser NestJS pour structure enterprise.",
                       "langage_confidence", "élevée"),
                "Node.js recommandé pour applications temps réel et asynchrones",
                9
        ));

        // JavaScript full-stack pour petites équipes
        Map<String, Object> jsFsCond = new HashMap<>();
        jsFsCond.put("taille_equipe", "petite");
        jsFsCond.put("complexite_metier", List.of("faible", "moyenne"));
        rules.add(new Rule(
                "R_Tech_JavaScript_FullStack",
                jsFsCond,
                Map.of("langage_recommande", "JavaScript Full-Stack (Node.js + React/Vue)",
                       "langage_raison", "Un seul langage frontend/backend, réutilisation de code, rapidité de développement",
                       "langage_details", "Petite équipe peut maîtriser un seul langage. Stack MERN/MEAN populaire. Déploiement simplifié.",
                       "langage_confidence", "moyenne"),
                "JavaScript full-stack pour petites équipes",
                7
        ));

        // Python pour applications data-intensive
        Map<String, Object> pyDataCond = new HashMap<>();
        pyDataCond.put("type_application", List.of("data-processing", "iot"));
        rules.add(new Rule(
                "R_Tech_Python_Data",
                pyDataCond,
                Map.of("langage_recommande", "Python (FastAPI, Django)",
                       "langage_raison", "Excellent pour traitement de données, ML, nombreuses bibliothèques scientifiques",
                       "langage_details", "FastAPI pour APIs performantes, Django pour applications complètes. Écosystème data science (Pandas, NumPy, TensorFlow).",
                       "langage_confidence", "élevée"),
                "Python recommandé pour applications data-intensive",
                9
        ));

        // ── REGLES D'INFRASTRUCTURE ─────────────────────────────────────────

        // Cloud Public pour scalabilité critique
        Map<String, Object> cloudCond = new HashMap<>();
        cloudCond.put("scalabilite", "critique");
        cloudCond.put("disponibilite", "haute");
        cloudCond.put("budget", List.of("moyen", "élevé"));
        rules.add(new Rule(
                "R7_Cloud_Public",
                cloudCond,
                Map.of("infrastructure_recommandee", "Cloud Public (AWS, Azure, GCP)",
                       "infrastructure_confidence", "élevée",
                       "infrastructure_raison", "Scalabilité élastique, haute disponibilité, paiement à l'usage",
                       "infrastructure_details", "Auto-scaling automatique, multi-région pour HA, services managés (BDD, cache, CDN)"),
                "Cloud public pour scalabilité critique",
                10
        ));

        // Petit serveur pour charge faible
        rules.add(new Rule(
                "R_Serveur_Petit_Charge_Faible",
                Map.of("charge_utilisateurs", "faible",
                       "budget", "limité"),
                Map.of("taille_serveur", "Petit (2 vCPU, 4GB RAM)",
                       "taille_serveur_raison", "Suffisant pour <1000 utilisateurs/jour, coût optimisé",
                       "taille_serveur_details", "Exemples: AWS t3.small, Azure B2s, GCP e2-small. Coût ~20-30€/mois. Peut héberger monolithe ou 2-3 microservices légers.",
                       "taille_serveur_confidence", "élevée"),
                "Petit serveur pour charge faible",
                8
        ));

        // Serveur moyen pour charge moyenne
        rules.add(new Rule(
                "R_Serveur_Moyen_Charge_Moyenne",
                Map.of("charge_utilisateurs", "moyenne"),
                Map.of("taille_serveur", "Moyen (4 vCPU, 8-16GB RAM)",
                       "taille_serveur_raison", "Adapté pour 1000-10000 utilisateurs/jour",
                       "taille_serveur_details", "Exemples: AWS t3.large, Azure B4ms, GCP e2-standard-4. Coût ~80-150€/mois. Peut héberger monolithe ou 5-8 microservices.",
                       "taille_serveur_confidence", "élevée"),
                "Serveur moyen pour charge moyenne",
                8
        ));

        // Cluster pour charge élevée
        rules.add(new Rule(
                "R_Cluster_Charge_Elevee",
                Map.of("charge_utilisateurs", "élevée",
                       "scalabilite", "critique"),
                Map.of("taille_serveur", "Cluster de serveurs (Auto-Scaling Group)",
                       "taille_serveur_raison", "Plusieurs serveurs moyens/grands avec load balancer",
                       "taille_serveur_details", "Minimum 3 serveurs (4 vCPU, 16GB chacun) derrière load balancer. Auto-scaling 3-20 instances selon charge. Coût: 300-2000€/mois selon trafic.",
                       "taille_serveur_confidence", "élevée"),
                "Cluster auto-scalable pour charge élevée",
                10
        ));

        // ── REGLES DE DÉPLOIEMENT ET CONTENEURISATION ───────────────────────

        // Docker + Kubernetes pour microservices
        rules.add(new Rule(
                "R15_Conteneurs_Microservices",
                Map.of("architecture_recommandee", "Microservices"),
                Map.of("conteneurisation", "Docker + Kubernetes",
                       "conteneurisation_raison", "Isolation, portabilité, orchestration automatique",
                       "conteneurisation_details", "Chaque microservice dans un container Docker. Kubernetes gère: déploiement, scaling, load balancing, health checks. Utiliser Helm pour gestion.",
                       "conteneurisation_confidence", "élevée"),
                "Conteneurisation obligatoire pour microservices",
                9
        ));

        // Docker optionnel pour monolithique
        rules.add(new Rule(
                "R_Docker_Monolithique_Optionnel",
                Map.of("architecture_recommandee", "Monolithique",
                       "deploiement_frequence", "élevée"),
                Map.of("conteneurisation", "Docker (optionnel mais recommandé)",
                       "conteneurisation_raison", "Facilite déploiement, environnements reproductibles",
                       "conteneurisation_details", "Un seul container pour le monolithe. Simplifie CI/CD. Peut utiliser Docker Compose pour dev local avec BDD.",
                       "conteneurisation_confidence", "moyenne"),
                "Docker recommandé même pour monolithique si déploiements fréquents",
                6
        ));

        // CI/CD pour déploiement continu
        rules.add(new Rule(
                "R16_CICD_DevOps",
                Map.of("deploiement_frequence", "élevée"),
                Map.of("cicd_recommande", "GitLab CI / GitHub Actions / Jenkins",
                       "cicd_raison", "Automatisation tests, build, déploiement. Réduction erreurs humaines.",
                       "cicd_details", "Pipeline: git push → tests auto → build Docker → déploiement staging → tests E2E → déploiement production. Rollback automatique si échec.",
                       "cicd_confidence", "élevée"),
                "CI/CD essentiel pour déploiements fréquents",
                8
        ));

        // ── REGLES DE BASE DE DONNÉES ───────────────────────────────────────

        // PostgreSQL pour applications transactionnelles
        rules.add(new Rule(
                "R11_SQL_Transactions",
                Map.of("transactions_acid", "oui",
                       "structure_donnees", "structurée"),
                Map.of("database_recommandee", "PostgreSQL (recommandé) ou MySQL",
                       "database_confidence", "élevée",
                       "database_raison", "Garanties ACID, intégrité référentielle, requêtes complexes",
                       "database_details", "PostgreSQL: plus de fonctionnalités (JSON, full-text search, extensions). MySQL: plus simple, très populaire. Utiliser réplication master-slave pour HA."),
                "PostgreSQL/MySQL pour transactions ACID",
                10
        ));

        // MongoDB pour données non structurées
        Map<String, Object> mongoCond = new HashMap<>();
        mongoCond.put("structure_donnees", List.of("non-structurée", "semi-structurée"));
        mongoCond.put("scalabilite", "critique");
        rules.add(new Rule(
                "R12_NoSQL_NonStructure",
                mongoCond,
                Map.of("database_recommandee", "MongoDB (NoSQL document)",
                       "database_confidence", "élevée",
                       "database_raison", "Schéma flexible, scalabilité horizontale (sharding), performance lecture",
                       "database_details", "Idéal pour: catalogues produits, logs, données IoT. Sharding automatique pour scalabilité. Attention: pas de transactions multi-documents (avant v4)."),
                "MongoDB pour données non structurées scalables",
                9
        ));

        // Redis pour cache
        Map<String, Object> redisCond = new HashMap<>();
        redisCond.put("charge_utilisateurs", List.of("moyenne", "élevée"));
        rules.add(new Rule(
                "R13_Redis_Cache",
                redisCond,
                Map.of("cache_recommande", "Redis",
                       "cache_raison", "Cache en mémoire ultra-rapide, réduit charge BDD",
                       "cache_details", "Utiliser Redis pour: sessions utilisateur, cache requêtes fréquentes, compteurs temps réel. Réduction latence de 90%. Coût: ~20€/mois (managed).",
                       "cache_confidence", "élevée"),
                "Redis recommandé comme cache pour charge moyenne/élevée",
                7
        ));

        // ── REGLES DE SÉCURITÉ ──────────────────────────────────────────────

        // On-Premise pour données sensibles
        rules.add(new Rule(
                "R8_OnPremise_Securite",
                Map.of("donnees_sensibles", "oui",
                       "conformite_reglementaire", "stricte"),
                Map.of("infrastructure_recommandee", "On-Premise (serveurs dédiés)",
                       "infrastructure_confidence", "élevée",
                       "infrastructure_raison", "Contrôle total données, conformité RGPD/HIPAA, pas de cloud public",
                       "infrastructure_details", "Datacenter privé ou colocation. Coût initial élevé (serveurs, réseau, sécurité). Maintenance interne nécessaire. Backup et DR critiques."),
                "On-premise obligatoire pour données très sensibles",
                10
        ));

        // ── REGLES DE MONITORING ────────────────────────────────────────────

        Map<String, Object> monitorCond = new HashMap<>();
        monitorCond.put("disponibilite", List.of("haute", "critique"));
        rules.add(new Rule(
                "R_Monitoring_Production",
                monitorCond,
                Map.of("monitoring_recommande", "Prometheus + Grafana + ELK Stack",
                       "monitoring_raison", "Détection proactive des problèmes, alertes, analyse logs",
                       "monitoring_details", "Prometheus: métriques (CPU, RAM, latence). Grafana: dashboards. ELK: logs centralisés. Alertes Slack/email si erreurs. Coût: ~50-200€/mois.",
                       "monitoring_confidence", "élevée"),
                "Monitoring essentiel pour haute disponibilité",
                8
        ));

        return rules;
    }

    /**
     * Retourne la liste des questions posées à l'utilisateur.
     */
    public List<Map<String, Object>> getQuestions() {
        List<Map<String, Object>> questions = new ArrayList<>();

        questions.add(buildQuestion("type_application",
                "Quel type d'application souhaitez-vous développer ?",
                "select", List.of("web", "mobile", "desktop", "iot", "data-processing")));

        questions.add(buildQuestion("charge_utilisateurs",
                "Quelle est la charge utilisateurs attendue ?",
                "select", List.of("faible", "moyenne", "élevée", "variable")));

        questions.add(buildQuestion("scalabilite",
                "La scalabilité est-elle critique pour votre application ?",
                "select", List.of("non", "moyenne", "critique")));

        questions.add(buildQuestion("budget",
                "Quel est votre budget ?",
                "select", List.of("limité", "moyen", "élevé")));

        questions.add(buildQuestion("taille_equipe",
                "Quelle est la taille de votre équipe de développement ?",
                "select", List.of("petite", "moyenne", "grande")));

        questions.add(buildQuestion("complexite_metier",
                "Quelle est la complexité métier de votre application ?",
                "select", List.of("faible", "moyenne", "élevée")));

        questions.add(buildQuestion("equipe_distribuee",
                "Votre équipe est-elle distribuée géographiquement ?",
                "boolean", List.of("oui", "non")));

        questions.add(buildQuestion("traitement_asynchrone",
                "Avez-vous besoin de traitement asynchrone ?",
                "boolean", List.of("oui", "non")));

        questions.add(buildQuestion("integration_systemes",
                "Combien de systèmes externes devez-vous intégrer ?",
                "select", List.of("aucun", "quelques-uns", "nombreux")));

        questions.add(buildQuestion("donnees_sensibles",
                "Manipulez-vous des données sensibles ?",
                "boolean", List.of("oui", "non")));

        questions.add(buildQuestion("conformite_reglementaire",
                "Avez-vous des contraintes de conformité réglementaire ?",
                "select", List.of("aucune", "moyenne", "stricte")));

        questions.add(buildQuestion("disponibilite",
                "Quel niveau de disponibilité requis ?",
                "select", List.of("normale", "haute", "critique")));

        questions.add(buildQuestion("transactions_acid",
                "Avez-vous besoin de transactions ACID ?",
                "boolean", List.of("oui", "non")));

        questions.add(buildQuestion("structure_donnees",
                "Quelle est la structure de vos données ?",
                "select", List.of("structurée", "semi-structurée", "non-structurée")));

        questions.add(buildQuestion("type_entreprise",
                "Quel type d'entreprise êtes-vous ?",
                "select", List.of("startup", "pme", "grande-entreprise", "administration")));

        questions.add(buildQuestion("deploiement_frequence",
                "Quelle est la fréquence de déploiement souhaitée ?",
                "select", List.of("faible", "moyenne", "élevée")));

        return questions;
    }

    private Map<String, Object> buildQuestion(String id, String question, String type, List<String> options) {
        Map<String, Object> q = new HashMap<>();
        q.put("id", id);
        q.put("question", question);
        q.put("type", type);
        q.put("options", options);
        return q;
    }
}
