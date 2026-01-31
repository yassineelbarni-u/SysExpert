"""
Point d'entrée de l'application FastAPI
Système Expert d'Aide au Choix d'Architecture Applicative et d'Infrastructure
"""
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from api.routes import router

# Créer l'application FastAPI
app = FastAPI(
    title="Système Expert d'Architecture",
    description="Système expert d'aide au choix d'architecture applicative et d'infrastructure",
    version="1.0.0",
    docs_url="/docs",
    redoc_url="/redoc"
)

# Configuration CORS pour permettre les requêtes depuis React
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000", "http://localhost:5173"],  # React dev servers
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Inclure les routes de l'API
app.include_router(router)


@app.get("/")
async def root():
    """Route racine avec informations sur l'API"""
    return {
        "message": "Système Expert d'Architecture - API",
        "version": "1.0.0",
        "documentation": "/docs",
        "endpoints": {
            "consultation": "/api/consult",
            "questions": "/api/questions",
            "rules": "/api/rules",
            "health": "/api/health"
        }
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "main:app",
        host="0.0.0.0",
        port=8000,
        reload=True
    )
