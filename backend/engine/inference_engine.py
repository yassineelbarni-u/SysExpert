from typing import Dict, List, Any, Tuple
from .rule import Rule

class InferenceEngine:
    # initialisation du moteur d'inférence avec des liste de règles
    def __init__(self, rules: List[Rule]):

        self.rules = sorted(rules, key=lambda r: r.priority, reverse=True)
        self.applied_rules: List[Rule] = []
        self.inference_trace: List[Dict[str, Any]] = []
    # inferance avec chainage avant
    def infer(self, initial_facts: Dict[str, Any]) -> Tuple[Dict[str, Any], List[Dict[str, Any]]]:
      
        facts = initial_facts.copy()
        self.applied_rules = []
        self.inference_trace = []
        
        # Chaînage avant :appliquer les règles jusqu'au point fixe
        changed = True
        iteration = 0
        max_iterations = 100
        
        while changed and iteration < max_iterations:
            changed = False
            iteration += 1
            
            # parcourir les règles
            for rule in self.rules:
                if rule not in self.applied_rules and rule.evaluate(facts):
                    new_facts = rule.apply(facts)
                    
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
    # Génère une explication textuelle du raisonnement
    def explain(self) -> List[str]:
        
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
