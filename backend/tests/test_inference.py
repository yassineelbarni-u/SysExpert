"""
Tests pour le moteur d'inférence
"""
import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

from engine.knowledge_base import create_knowledge_base
from engine.inference_engine import InferenceEngine


def test_microservices_recommendation():
    """Test: Recommandation architecture microservices"""
    print("\n=== Test 1: Architecture Microservices ===")
    
    knowledge_base = create_knowledge_base()
    engine = InferenceEngine(knowledge_base)
    
    facts = {
        "type_application": "web",
        "charge_utilisateurs": "élevée",
        "scalabilite": "critique",
        "budget": "moyen"
    }
    
    final_facts, trace = engine.infer(facts)
    recommendations = engine.get_recommendations(final_facts)
    
    print(f"Faits initiaux: {facts}")
    print(f"\nNombre de règles appliquées: {len(trace)}")
    print(f"\nRecommandations:")
    for rec in recommendations:
        print(f"  - {rec['type']}: {rec['recommendation']} (confiance: {rec['confidence']})")
    
    assert "architecture_recommandee" in final_facts
    assert final_facts["architecture_recommandee"] == "Microservices"
    print("\n✓ Test réussi!")


def test_monolithic_recommendation():
    """Test: Recommandation architecture monolithique"""
    print("\n=== Test 2: Architecture Monolithique ===")
    
    knowledge_base = create_knowledge_base()
    engine = InferenceEngine(knowledge_base)
    
    facts = {
        "budget": "limité",
        "taille_equipe": "petite",
        "complexite_metier": "faible"
    }
    
    final_facts, trace = engine.infer(facts)
    recommendations = engine.get_recommendations(final_facts)
    
    print(f"Faits initiaux: {facts}")
    print(f"\nNombre de règles appliquées: {len(trace)}")
    print(f"\nRecommandations:")
    for rec in recommendations:
        print(f"  - {rec['type']}: {rec['recommendation']} (confiance: {rec['confidence']})")
    
    assert "architecture_recommandee" in final_facts
    assert final_facts["architecture_recommandee"] == "Monolithique"
    print("\n✓ Test réussi!")


def test_cloud_infrastructure():
    """Test: Recommandation infrastructure cloud"""
    print("\n=== Test 3: Infrastructure Cloud ===")
    
    knowledge_base = create_knowledge_base()
    engine = InferenceEngine(knowledge_base)
    
    facts = {
        "scalabilite": "critique",
        "disponibilite": "haute",
        "budget": "moyen"
    }
    
    final_facts, trace = engine.infer(facts)
    recommendations = engine.get_recommendations(final_facts)
    
    print(f"Faits initiaux: {facts}")
    print(f"\nNombre de règles appliquées: {len(trace)}")
    print(f"\nRecommandations:")
    for rec in recommendations:
        print(f"  - {rec['type']}: {rec['recommendation']} (confiance: {rec['confidence']})")
    
    assert "infrastructure_recommandee" in final_facts
    print("\n✓ Test réussi!")


def test_complete_scenario():
    """Test: Scénario complet avec plusieurs recommandations"""
    print("\n=== Test 4: Scénario Complet ===")
    
    knowledge_base = create_knowledge_base()
    engine = InferenceEngine(knowledge_base)
    
    facts = {
        "type_application": "web",
        "charge_utilisateurs": "élevée",
        "scalabilite": "critique",
        "budget": "moyen",
        "taille_equipe": "grande",
        "equipe_distribuee": "oui",
        "complexite_metier": "élevée",
        "traitement_asynchrone": "oui",
        "integration_systemes": "nombreux",
        "disponibilite": "haute",
        "transactions_acid": "oui",
        "structure_donnees": "structurée"
    }
    
    final_facts, trace = engine.infer(facts)
    recommendations = engine.get_recommendations(final_facts)
    explanations = engine.explain()
    
    print(f"Faits initiaux: {len(facts)} faits")
    print(f"\nNombre de règles appliquées: {len(trace)}")
    print(f"\nRecommandations ({len(recommendations)}):")
    for rec in recommendations:
        print(f"  - {rec['type']}: {rec['recommendation']} (confiance: {rec['confidence']})")
    
    print(f"\nExplications:")
    for i, exp in enumerate(explanations, 1):
        print(f"{i}. {exp}")
    
    assert len(recommendations) > 0
    print("\n✓ Test réussi!")


if __name__ == "__main__":
    print("=" * 60)
    print("Tests du Système Expert")
    print("=" * 60)
    
    try:
        test_microservices_recommendation()
        test_monolithic_recommendation()
        test_cloud_infrastructure()
        test_complete_scenario()
        
        print("\n" + "=" * 60)
        print("✓ Tous les tests sont passés avec succès!")
        print("=" * 60)
    except AssertionError as e:
        print(f"\n✗ Test échoué: {e}")
    except Exception as e:
        print(f"\n✗ Erreur: {e}")
        import traceback
        traceback.print_exc()
