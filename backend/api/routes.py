
from fastapi import APIRouter, HTTPException
from typing import List
from .models import (
    ConsultationRequest,
    ConsultationResponse,
    QuestionsResponse,
    RulesResponse,
    Question,
    Recommendation,
    RuleTrace,
    RuleInfo
)
from engine.knowledge_base_extended import create_knowledge_base, get_questions
from engine.inference_engine import InferenceEngine

router = APIRouter(prefix="/api", tags=["Expert System"])

# Initialiser le moteur d'inférence avec la base de connaissances
knowledge_base = create_knowledge_base()
inference_engine = InferenceEngine(knowledge_base)


@router.post("/consult", response_model=ConsultationResponse)
async def consult(request: ConsultationRequest):

    try:
        inference_engine.reset()
        
        final_facts, trace = inference_engine.infer(request.facts)
        
        recommendations = inference_engine.get_recommendations(final_facts)
        
        explanations = inference_engine.explain()
        
        return ConsultationResponse(
            recommendations=[
                Recommendation(**rec) for rec in recommendations
            ],
            final_facts=final_facts,
            inference_trace=[
                RuleTrace(**t) for t in trace
            ],
            explanations=explanations
        )
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Erreur lors de la consultation: {str(e)}")


@router.get("/questions", response_model=QuestionsResponse)
async def get_questions_endpoint():
    
    try:
        questions = get_questions()
        return QuestionsResponse(
            questions=[Question(**q) for q in questions]
        )
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Erreur lors de la récupération des questions: {str(e)}")


@router.get("/rules", response_model=RulesResponse)
async def get_rules():

    try:
        rules_info = []
        for rule in knowledge_base:
            rules_info.append(
                RuleInfo(
                    name=rule.name,
                    description=rule.description,
                    priority=rule.priority,
                    conditions=rule.conditions,
                    conclusion=rule.conclusion,
                    explanation=rule.to_explanation()
                )
            )
        
        return RulesResponse(
            rules=rules_info,
            total_rules=len(rules_info)
        )
    
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Erreur lors de la récupération des règles: {str(e)}")


@router.get("/health")
async def health_check():
    return {
        "status": "healthy",
        "total_rules": len(knowledge_base),
        "message": "Système expert opérationnel"
    }
