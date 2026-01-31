"""
Classe Rule pour représenter les règles SI...ALORS du système expert
"""
from typing import Dict, List, Any, Callable


class Rule:
    """
    Représente une règle d'inférence SI...ALORS
    
    Une règle est composée de:
    - conditions: dictionnaire de conditions qui doivent être vraies
    - conclusion: dictionnaire de faits à ajouter si la règle s'applique
    - name: nom de la règle pour l'explication
    - description: description pour l'utilisateur
    - priority: priorité d'exécution (plus élevé = plus prioritaire)
    """
    
    def __init__(
        self,
        name: str,
        conditions: Dict[str, Any],
        conclusion: Dict[str, Any],
        description: str = "",
        priority: int = 0,
        custom_condition: Callable[[Dict[str, Any]], bool] = None
    ):
        self.name = name
        self.conditions = conditions
        self.conclusion = conclusion
        self.description = description
        self.priority = priority
        self.custom_condition = custom_condition
    
    def evaluate(self, facts: Dict[str, Any]) -> bool:
        """
        Évalue si la règle peut être appliquée avec les faits donnés
        
        Args:
            facts: Base de faits actuelle
            
        Returns:
            True si toutes les conditions sont satisfaites
        """
        # Si une condition personnalisée est définie, l'utiliser
        if self.custom_condition:
            return self.custom_condition(facts)
        
        # Vérifier chaque condition
        for key, expected_value in self.conditions.items():
            if key not in facts:
                return False
            
            actual_value = facts[key]
            
            # Gestion des listes (OR logique)
            if isinstance(expected_value, list):
                if actual_value not in expected_value:
                    return False
            # Gestion des comparaisons numériques
            elif isinstance(expected_value, dict) and "operator" in expected_value:
                if not self._evaluate_operator(actual_value, expected_value):
                    return False
            # Comparaison directe
            elif actual_value != expected_value:
                return False
        
        return True
    
    def _evaluate_operator(self, actual: Any, condition: Dict[str, Any]) -> bool:
        """Évalue une condition avec opérateur (>, <, >=, <=, etc.)"""
        operator = condition["operator"]
        value = condition["value"]
        
        operators = {
            ">": lambda a, v: a > v,
            "<": lambda a, v: a < v,
            ">=": lambda a, v: a >= v,
            "<=": lambda a, v: a <= v,
            "==": lambda a, v: a == v,
            "!=": lambda a, v: a != v,
        }
        
        if operator in operators:
            return operators[operator](actual, value)
        
        return False
    
    def apply(self, facts: Dict[str, Any]) -> Dict[str, Any]:
        """
        Applique la règle et retourne les nouveaux faits
        
        Args:
            facts: Base de faits actuelle
            
        Returns:
            Dictionnaire des nouveaux faits à ajouter
        """
        return self.conclusion.copy()
    
    def __repr__(self):
        return f"Rule(name='{self.name}', priority={self.priority})"
    
    def to_explanation(self) -> str:
        """Retourne une explication lisible de la règle"""
        conditions_str = " ET ".join([
            f"{k} = {v}" for k, v in self.conditions.items()
        ])
        conclusion_str = ", ".join([
            f"{k} = {v}" for k, v in self.conclusion.items()
        ])
        
        return f"SI {conditions_str} ALORS {conclusion_str}"
