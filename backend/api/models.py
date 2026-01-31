from pydantic import BaseModel, Field
from typing import Dict, List, Any, Optional


class ConsultationRequest(BaseModel):
    facts: Dict[str, Any] = Field(
        ...,
        description="Dictionnaire des faits fournis par l'utilisateur",
        example={
            "type_application": "web",
            "charge_utilisateurs": "élevée",
            "scalabilite": "critique",
            "budget": "moyen"
        }
    )


class Recommendation(BaseModel):

    type: str = Field(..., description="Type de recommandation")
    recommendation: str = Field(..., description="Recommandation détaillée")
    confidence: str = Field(..., description="Niveau de confiance")
    raison: str = Field(default="", description="Raison de la recommandation")
    details: str = Field(default="", description="Détails techniques et conseils")


class RuleTrace(BaseModel):

    iteration: int = Field(..., description="Numéro d'itération")
    rule_name: str = Field(..., description="Nom de la règle")
    rule_description: str = Field(..., description="Description de la règle")
    conditions_matched: Dict[str, Any] = Field(..., description="Conditions satisfaites")
    new_facts: Dict[str, Any] = Field(..., description="Nouveaux faits ajoutés")
    rule_explanation: str = Field(..., description="Explication de la règle")


class ConsultationResponse(BaseModel):

    recommendations: List[Recommendation] = Field(
        ...,
        description="Liste des recommandations"
    )
    final_facts: Dict[str, Any] = Field(
        ...,
        description="Base de faits finale après inférence"
    )
    inference_trace: List[RuleTrace] = Field(
        ...,
        description="Trace d'exécution pour explication"
    )
    explanations: List[str] = Field(
        ...,
        description="Explications textuelles du raisonnement"
    )


class Question(BaseModel):

    id: str = Field(..., description="Identifiant unique de la question")
    question: str = Field(..., description="Texte de la question")
    type: str = Field(..., description="Type de question (select, boolean, etc.)")
    options: List[str] = Field(..., description="Options de réponse")


class QuestionsResponse(BaseModel):
    questions: List[Question] = Field(..., description="Liste des questions")


class RuleInfo(BaseModel):
    name: str = Field(..., description="Nom de la règle")
    description: str = Field(..., description="Description de la règle")
    priority: int = Field(..., description="Priorité de la règle")
    conditions: Dict[str, Any] = Field(..., description="Conditions de la règle")
    conclusion: Dict[str, Any] = Field(..., description="Conclusion de la règle")
    explanation: str = Field(..., description="Explication SI...ALORS")


class RulesResponse(BaseModel):
    rules: List[RuleInfo] = Field(..., description="Liste des règles")
    total_rules: int = Field(..., description="Nombre total de règles")
