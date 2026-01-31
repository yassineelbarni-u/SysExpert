"""
Moteur d'inférence pour le système expert
Implémente le chaînage avant (forward chaining)
"""
from typing import Dict, List, Any, Tuple
from .rule import Rule


class InferenceEngine:
    """
    Moteur d'inférence avec chaînage avant
    
    Le moteur prend une base de faits initiale et applique les règles
    de manière itérative jusqu'à ce qu'aucune nouvelle règle ne puisse
    être appliquée (point fixe).
    """
    
    def __init__(self, rules: List[Rule]):
        """
        Initialise le moteur avec une liste de règles
        
        Args:
            rules: Liste des règles du système expert
        """
        self.rules = sorted(rules, key=lambda r: r.priority, reverse=True)
        self.applied_rules: List[Rule] = []
        self.inference_trace: List[Dict[str, Any]] = []
    
    def infer(self, initial_facts: Dict[str, Any]) -> Tuple[Dict[str, Any], List[Dict[str, Any]]]:
        """
        Effectue l'inférence avec chaînage avant
        
        Args:
            initial_facts: Base de faits initiale (réponses de l'utilisateur)
            
        Returns:
            Tuple contenant:
            - Base de faits finale (avec conclusions)
            - Trace d'exécution pour explication
        """
        facts = initial_facts.copy()
        self.applied_rules = []
        self.inference_trace = []
        
        # Chaînage avant: appliquer les règles jusqu'au point fixe
        changed = True
        iteration = 0
        max_iterations = 100  # Protection contre boucles infinies
        
        while changed and iteration < max_iterations:
            changed = False
            iteration += 1
            
            for rule in self.rules:
                # Si la règle n'a pas encore été appliquée et peut l'être
                if rule not in self.applied_rules and rule.evaluate(facts):
                    # Appliquer la règle
                    new_facts = rule.apply(facts)
                    
                    # Enregistrer la trace
                    trace_entry = {
                        "iteration": iteration,
                        "rule_name": rule.name,
                        "rule_description": rule.description,
                        "conditions_matched": rule.conditions,
                        "new_facts": new_facts,
                        "rule_explanation": rule.to_explanation()
                    }
                    self.inference_trace.append(trace_entry)
                    
                    # Mettre à jour la base de faits
                    facts.update(new_facts)
                    self.applied_rules.append(rule)
                    changed = True
        
        return facts, self.inference_trace
    
    def get_recommendations(self, facts: Dict[str, Any]) -> List[Dict[str, Any]]:
        """
        Extrait les recommandations de la base de faits finale
        
        Args:
            facts: Base de faits après inférence
            
        Returns:
            Liste des recommandations structurées avec détails
        """
        recommendations = []
        
        # Extraire les recommandations d'architecture
        if "architecture_recommandee" in facts:
            recommendations.append({
                "type": "Architecture Applicative",
                "recommendation": facts["architecture_recommandee"],
                "confidence": facts.get("architecture_confidence", "moyenne"),
                "raison": facts.get("architecture_raison", ""),
                "details": facts.get("architecture_details", "")
            })
        
        # Extraire les recommandations de scalabilité
        if "scalabilite_type" in facts:
            recommendations.append({
                "type": "Stratégie de Scalabilité",
                "recommendation": facts["scalabilite_type"],
                "confidence": facts.get("scalabilite_confidence", "moyenne"),
                "raison": facts.get("scalabilite_raison", ""),
                "details": facts.get("scalabilite_details", "")
            })
        
        # Extraire les recommandations de langage/technologie
        if "langage_recommande" in facts:
            recommendations.append({
                "type": "Langage de Programmation",
                "recommendation": facts["langage_recommande"],
                "confidence": facts.get("langage_confidence", "moyenne"),
                "raison": facts.get("langage_raison", ""),
                "details": facts.get("langage_details", "")
            })
        
        # Extraire les recommandations d'infrastructure
        if "infrastructure_recommandee" in facts:
            recommendations.append({
                "type": "Infrastructure",
                "recommendation": facts["infrastructure_recommandee"],
                "confidence": facts.get("infrastructure_confidence", "moyenne"),
                "raison": facts.get("infrastructure_raison", ""),
                "details": facts.get("infrastructure_details", "")
            })
        
        # Extraire les recommandations de taille serveur
        if "taille_serveur" in facts:
            recommendations.append({
                "type": "Taille et Configuration Serveur",
                "recommendation": facts["taille_serveur"],
                "confidence": facts.get("taille_serveur_confidence", "moyenne"),
                "raison": facts.get("taille_serveur_raison", ""),
                "details": facts.get("taille_serveur_details", "")
            })
        
        # Extraire les recommandations de base de données
        if "database_recommandee" in facts:
            recommendations.append({
                "type": "Base de Données",
                "recommendation": facts["database_recommandee"],
                "confidence": facts.get("database_confidence", "moyenne"),
                "raison": facts.get("database_raison", ""),
                "details": facts.get("database_details", "")
            })
        
        # Extraire les recommandations de cache
        if "cache_recommande" in facts:
            recommendations.append({
                "type": "Système de Cache",
                "recommendation": facts["cache_recommande"],
                "confidence": facts.get("cache_confidence", "moyenne"),
                "raison": facts.get("cache_raison", ""),
                "details": facts.get("cache_details", "")
            })
        
        # Extraire les recommandations de conteneurisation
        if "conteneurisation" in facts:
            recommendations.append({
                "type": "Conteneurisation et Orchestration",
                "recommendation": facts["conteneurisation"],
                "confidence": facts.get("conteneurisation_confidence", "moyenne"),
                "raison": facts.get("conteneurisation_raison", ""),
                "details": facts.get("conteneurisation_details", "")
            })
        
        # Extraire les recommandations CI/CD
        if "cicd_recommande" in facts:
            recommendations.append({
                "type": "CI/CD et DevOps",
                "recommendation": facts["cicd_recommande"],
                "confidence": facts.get("cicd_confidence", "moyenne"),
                "raison": facts.get("cicd_raison", ""),
                "details": facts.get("cicd_details", "")
            })
        
        # Extraire les recommandations de monitoring
        if "monitoring_recommande" in facts:
            recommendations.append({
                "type": "Monitoring et Observabilité",
                "recommendation": facts["monitoring_recommande"],
                "confidence": facts.get("monitoring_confidence", "moyenne"),
                "raison": facts.get("monitoring_raison", ""),
                "details": facts.get("monitoring_details", "")
            })
        
        return recommendations
    
    def explain(self) -> List[str]:
        """
        Génère une explication textuelle du raisonnement
        
        Returns:
            Liste d'explications pour chaque règle appliquée
        """
        explanations = []
        
        for trace in self.inference_trace:
            explanation = (
                f"Règle '{trace['rule_name']}' appliquée: "
                f"{trace['rule_description']}\n"
                f"  → {trace['rule_explanation']}"
            )
            explanations.append(explanation)
        
        return explanations
    
    def reset(self):
        """Réinitialise le moteur pour une nouvelle consultation"""
        self.applied_rules = []
        self.inference_trace = []
