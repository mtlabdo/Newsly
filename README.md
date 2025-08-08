# 📰 Newsly - Android News Application

Une application mobile Android pour consulter les dernières actualités internationales, construite avec Clean Architecture, Jetpack Compose, et les dernières technologies Android (MAD).

## 🚀 Stack Technique

- **UI:** Jetpack Compose + Material 3
- **Architecture:** Clean Architecture + MVVM
- **DI:** Koin 4.1.0
- **Network:** Ktor Client 3.2.3
- **Navigation:** Navigation 3 (Alpha)
- **Build:** Convention Plugins + Version Catalog

## ✨ Fonctionnalités

### ✅ Implémentées
- Liste des dernières actualités avec images et métadonnées.
- Vue détaillée des articles
- Consultez l'article complet sur le site officiel.
- Pull-to-refresh.
- Gestion des erreurs.

### 🚧 À implémenter
- Cache local (Room)
- Fonctionnalité de recherche
- Favoris
- Catégories
- Partage d'articles
- Choix de la langue

## 🛠️ Installation

1. **Obtenir une clé NewsAPI** depuis [newsapi.org](https://newsapi.org)

2. **Configurer la clé API**
- Ajouter dans `local.properties` :
  ```properties

  API_KEY=votre_cle_newsapi_ici


##  📐 Vue d'ensemble de l'Architecture

```mermaid
graph TB
    subgraph "UI Layer"
        UI1[HomeScreen]
        UI2[DetailScreen]
        UI3[HomeViewModel]
        UI4[DetailViewModel]
        UI1 --> UI3
        UI2 --> UI4
    end
    
    subgraph "Domain Layer"
        D1[GetTopHeadlinesUseCase]
        D2[Article ]
        D3[INewsRepository]
        D4[Result]
    end
    
    subgraph "Data Layer"
        DA1[NewsRepository]
        DA2[ArticleMapper]
    end
    
    subgraph "Network Layer"
        N1[NewsApiService]
        N3[ApiClient]
        N4[NewsResponse]
    end
    
    subgraph "Core Layer"
        C1[Exception Handling]
        C2[Network Checker]
        C3[DateTime Utils]
    end

    subgraph " "
        S1[ Server]
    end
    
    UI3 --> D1
    D1 --> D3
    D3 --> DA1
    DA1 --> DA2
    DA1 --> N1
    N1 --> N3
    
    DA1 --> C1
    DA1 --> C2
    DA2 --> C3

    N3 --> S1


```


### 🔄 Flux de Données (Exemple de chargement et affichage de l'actualité)

```mermaid
sequenceDiagram
    participant HS as HomeScreen
    participant HVM as HomeViewModel
    participant UC as GetTopHeadlinesUseCase
    participant R as NewsRepository
    participant API as NewsApiService
    participant KC as Ktor Client
    participant NA as NewsAPI Server
    
    HS->>HVM: Send LoadNews Event
    HVM->>UC: getTopHeadlines()
    UC->>R: getTopHeadlines()
    R->>API: fetchTopHeadlines()
    API->>KC: GET /v2/top-headlines
    KC->>NA: HTTP Request
    NA-->>KC: JSON Response
    KC-->>API: NewsResponse DTO
    API-->>R: NewsResponse
    R->>R: Map To Article Domain
    R->>R: Wrap To Result
    R-->>UC: Result<List<Article>>
    UC-->>HVM: Success/Error Result State
    HVM->>HVM: Update UIState
    HVM-->>HS: StateFlow emission
    HS->>HS: Recompose UI
```

### 📂 Architecture Modulaire

🎯 Principe de Séparation

Le projet Newsly suit une **architecture modulaire multi-modules** inspirée du **Clean Architecture**.

```mermaid
graph TD
    
    %% Styles
    classDef app fill:#a1c4fd,stroke:#4a90e2,stroke-width:2px,color:#000;
    classDef common fill:#d4fc79,stroke:#90c53f,stroke-width:2px,color:#000;
    classDef feature fill:#ffecd2,stroke:#fcb69f,stroke-width:2px,color:#000;
    
    %% App Layer en haut
    subgraph APP["📱 App Layer"]
        A[app]:::app
        A --> AF1[app-feature:home]:::feature
        A --> AF2[app-feature:detail]:::feature
    end
    
    %% Common Modules en bas
    subgraph CM["🔧 Common Modules"]
        C1[common:core]:::common
        C2[common:domain]:::common
        C3[common:data]:::common
        C4[common:remote]:::common
        C5[common:ui]:::common
        C6[common:designsystem]:::common
    end
    
    %% Liaisons Features → Common
    AF1 --> C5
    AF1 --> C6
    AF1 --> C2
    AF2 --> C5
    AF2 --> C6
    AF2 --> C2

    %% Common → Common
    C5 --> C1
    C3 --> C2
    C3 --> C4
    C4 --> C1

    %% App → Common
    A --> C1
    A --> C2
    A --> C3
    A --> C4
    A --> C5
    A --> C6

```



| Couche             | Modules                                                                                      | Rôle principal |
|--------------------|----------------------------------------------------------------------------------------------|----------------|
| **Présentation**   | `app`, `app-feature:home`, `app-feature:detail`, `common:ui`, `common:designsystem`          | Affichage UI, interaction avec l’utilisateur, logique d’écran |
| **Domaine**        | `common:domain`                                                                              | Cas d’usage, logique métier pure, indépendance des frameworks |
| **Données**        | `common:data`, `common:remote`                                                               | Gestion des sources de données (API, base locale, etc.) |
| **Infrastructure** | `common:core`                                                                                | Outils, helpers|


- **Modulaire** : Chaque fonctionnalité est isolée dans son propre module (`app-feature:*`).
- **Réutilisable** : Les modules `common:*` peuvent être utilisés par plusieurs features ou directement par l’app.
- **Facile à maintenir** : Une modification dans un module impacte peu les autres.


🛠️ Stack Technique Détaillé :
Clean Architecture Implementation


🏢  Developped by Abdo

