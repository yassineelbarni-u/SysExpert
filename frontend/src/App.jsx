import { useState, useEffect } from 'react';
import axios from 'axios';
import './index.css';

const API_BASE_URL = 'http://localhost:8000/api';

function App() {
  const [currentView, setCurrentView] = useState('home');
  const [questions, setQuestions] = useState([]);
  const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
  const [answers, setAnswers] = useState({});
  const [results, setResults] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchQuestions();
  }, []);

  const fetchQuestions = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/questions`);
      setQuestions(response.data.questions);
    } catch (err) {
      setError('Erreur lors du chargement des questions');
      console.error(err);
    }
  };

  const handleStartQuestionnaire = () => {
    setCurrentView('questionnaire');
    setCurrentQuestionIndex(0);
    setAnswers({});
    setResults(null);
  };

  const handleAnswer = (questionId, value) => {
    setAnswers(prev => ({
      ...prev,
      [questionId]: value
    }));
  };

  const handleNext = () => {
    if (currentQuestionIndex < questions.length - 1) {
      setCurrentQuestionIndex(prev => prev + 1);
    } else {
      submitConsultation();
    }
  };

  const handlePrevious = () => {
    if (currentQuestionIndex > 0) {
      setCurrentQuestionIndex(prev => prev - 1);
    }
  };

  const submitConsultation = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.post(`${API_BASE_URL}/consult`, {
        facts: answers
      });
      setResults(response.data);
      setCurrentView('results');
    } catch (err) {
      setError('Erreur lors de la consultation. Assurez-vous que le serveur backend est démarré.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleRestart = () => {
    setCurrentView('home');
    setCurrentQuestionIndex(0);
    setAnswers({});
    setResults(null);
  };

  if (currentView === 'home') {
    return (
      <div className="container">
        <header className="header">
          <h1> Système Expert d'Architecture</h1>
          <p>Aide au choix d'architecture applicative et d'infrastructure</p>
        </header>

        <div className="card" style={{ textAlign: 'center', maxWidth: '600px', margin: '0 auto' }}>
          <h2>Bienvenue</h2>
          <p style={{ marginBottom: '2rem', color: 'var(--text-secondary)' }}>
            Ce système expert vous aide à choisir la meilleure architecture applicative
            et infrastructure pour votre projet en fonction de vos besoins spécifiques.
          </p>

          <div style={{ marginBottom: '2rem' }}>
            <h3 style={{ fontSize: '1.2rem', marginBottom: '1rem' }}>Comment ça marche ?</h3>
            <div style={{ textAlign: 'left', maxWidth: '400px', margin: '0 auto' }}>
              <p style={{ marginBottom: '0.5rem' }}>✓ Répondez à quelques questions sur votre projet</p>
              <p style={{ marginBottom: '0.5rem' }}>✓ Le système analyse vos besoins</p>
              <p style={{ marginBottom: '0.5rem' }}>✓ Recevez des recommandations personnalisées</p>
              <p>✓ Consultez les explications détaillées</p>
            </div>
          </div>

          <button className="btn btn-primary" onClick={handleStartQuestionnaire}>
            Commencer la consultation
          </button>
        </div>
      </div>
    );
  }

  // Vue questionnaire
  if (currentView === 'questionnaire') {
    const currentQuestion = questions[currentQuestionIndex];
    const progress = ((currentQuestionIndex + 1) / questions.length) * 100;
    const currentAnswer = answers[currentQuestion?.id];

    return (
      <div className="container">
        <header className="header">
          <h1> Système Expert d'Architecture</h1>
          <p>Question {currentQuestionIndex + 1} sur {questions.length}</p>
        </header>

        <div className="progress-bar">
          <div className="progress-fill" style={{ width: `${progress}%` }}></div>
        </div>

        {currentQuestion && (
          <div className="card">
            <h2>{currentQuestion.question}</h2>

            {currentQuestion.type === 'select' && (
              <div className="form-group">
                <select
                  className="form-select"
                  value={currentAnswer || ''}
                  onChange={(e) => handleAnswer(currentQuestion.id, e.target.value)}
                >
                  <option value="">-- Sélectionnez une option --</option>
                  {currentQuestion.options.map(option => (
                    <option key={option} value={option}>{option}</option>
                  ))}
                </select>
              </div>
            )}

            {currentQuestion.type === 'boolean' && (
              <div className="radio-group">
                {currentQuestion.options.map(option => (
                  <div key={option} className="radio-option">
                    <input
                      type="radio"
                      id={`${currentQuestion.id}-${option}`}
                      name={currentQuestion.id}
                      value={option}
                      checked={currentAnswer === option}
                      onChange={(e) => handleAnswer(currentQuestion.id, e.target.value)}
                    />
                    <label htmlFor={`${currentQuestion.id}-${option}`}>
                      {option}
                    </label>
                  </div>
                ))}
              </div>
            )}

            <div style={{ display: 'flex', gap: '1rem', marginTop: '2rem' }}>
              <button
                className="btn btn-secondary"
                onClick={handlePrevious}
                disabled={currentQuestionIndex === 0}
              >
                ← Précédent
              </button>
              <button
                className="btn btn-primary"
                onClick={handleNext}
                disabled={!currentAnswer}
                style={{ marginLeft: 'auto' }}
              >
                {currentQuestionIndex === questions.length - 1 ? 'Obtenir les recommandations' : 'Suivant →'}
              </button>
            </div>
          </div>
        )}

        {loading && (
          <div style={{ textAlign: 'center' }}>
            <div className="spinner"></div>
            <p>Analyse en cours...</p>
          </div>
        )}

        {error && (
          <div className="card" style={{ borderColor: 'var(--error)', background: 'rgba(239, 68, 68, 0.1)' }}>
            <p style={{ color: 'var(--error)' }}>⚠️ {error}</p>
          </div>
        )}
      </div>
    );
  }

  // Vue résultats
  if (currentView === 'results' && results) {
    return (
      <div className="container">
        <header className="header">
          <h1> Recommandations</h1>
          <p>Voici les recommandations basées sur vos réponses</p>
        </header>

        {/* Recommandations */}
        <div className="card">
          <h2> Recommandations Détaillées</h2>
          <p style={{ color: 'var(--text-muted)', marginBottom: '1.5rem' }}>
            Voici toutes les recommandations pour votre projet avec justifications complètes :
          </p>
          {results.recommendations.map((rec, index) => (
            <div key={index} className="recommendation-card">
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '0.5rem' }}>
                <h3 style={{ margin: 0, fontSize: '1.2rem' }}>{rec.type}</h3>
                <span className={`badge badge-${rec.confidence === 'élevée' ? 'success' : rec.confidence === 'moyenne' ? 'warning' : 'info'}`}>
                  Confiance: {rec.confidence}
                </span>
              </div>
              <p style={{ fontSize: '1.2rem', color: 'var(--text-primary)', fontWeight: '600', margin: '0.5rem 0' }}>
                ✅ {rec.recommendation}
              </p>
              {rec.raison && (
                <p style={{ fontSize: '1rem', color: 'var(--text-secondary)', margin: '0.75rem 0', fontStyle: 'italic' }}>
                  <strong>Pourquoi ?</strong> {rec.raison}
                </p>
              )}
              {rec.details && (
                <div style={{
                  background: 'rgba(99, 102, 241, 0.05)',
                  border: '1px solid rgba(99, 102, 241, 0.2)',
                  borderRadius: '8px',
                  padding: '1rem',
                  marginTop: '0.75rem'
                }}>
                  <p style={{ fontSize: '0.95rem', color: 'var(--text-secondary)', margin: 0, lineHeight: '1.6' }}>
                    <strong>💡 Détails techniques:</strong><br />
                    {rec.details}
                  </p>
                </div>
              )}
            </div>
          ))}
        </div>

        {/* Explications */}
        <div className="card">
          <h2> Explications du raisonnement</h2>
          <p style={{ color: 'var(--text-muted)', marginBottom: '1.5rem' }}>
            Voici les règles qui ont été appliquées pour arriver à ces recommandations :
          </p>
          {results.explanations.map((explanation, index) => (
            <div key={index} className="explanation-item">
              <p style={{ margin: 0, whiteSpace: 'pre-line' }}>{explanation}</p>
            </div>
          ))}
        </div>

        {/* Trace d'inférence détaillée */}
        <div className="card">
          <h2> Trace d'inférence (détails techniques)</h2>
          <p style={{ color: 'var(--text-muted)', marginBottom: '1.5rem' }}>
            Détails techniques du processus d'inférence :
          </p>
          {results.inference_trace.map((trace, index) => (
            <div key={index} className="explanation-item">
              <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem' }}>
                <strong>{trace.rule_name}</strong>
                <span className="badge badge-info">Itération {trace.iteration}</span>
              </div>
              <p style={{ color: 'var(--text-secondary)', fontSize: '0.9rem', margin: '0.5rem 0' }}>
                {trace.rule_description}
              </p>
              <p style={{ fontFamily: 'monospace', fontSize: '0.9rem', color: 'var(--primary-light)', margin: 0 }}>
                {trace.rule_explanation}
              </p>
            </div>
          ))}
        </div>

        <div style={{ textAlign: 'center', marginTop: '2rem' }}>
          <button className="btn btn-primary" onClick={handleRestart}>
            Nouvelle consultation
          </button>
        </div>
      </div>
    );
  }

  return null;
}

export default App;
