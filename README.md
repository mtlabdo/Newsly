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
- Structure évolutive.

### 🚧 À implémenter
- Cache local (Room) (pas encore fait pour évite la complexité pour ce test)
- Fonctionnalité de recherche
- Favoris
- Catégories
- Partage d'articles
- Choix de la langue
- WorkManager pour sync en arrière-plan
- Paging 3 pour chargement infini
- Paramétrage de l'application (DataStore pour préférences utilisateur)
- Tests avec coverage minimale 80%


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


## Choix Techniques :

#### Pourquoi Clean Architecture ?

<img width="373" height="309" alt="image" src="https://github.com/user-attachments/assets/6532e205-4fa2-4437-bfca-97fa4ce2654f" />

"La Clean Architecture offre une structure modulaire et évolutive, séparant les responsabilités pour faciliter la maintenance et l’évolution."
Références :  [https://developer.android.com/topic/architecture?hl=fr](https://blog.alphorm.com/avantages-clean-architecture-android)

##### Couche domaine (Le Domain au centre)

App-feature(ui) → Domain ← Data
###### Dans l'implémentation de l'app Newsly, la couche domain ne dépend d'aucune autre couche.
   + **Isolation métier** - Logique métier indépendanet et isolée.
   + **Testabilité** - Domain sans dépendances externes.
   + **Réutilisabilité** - Logique métier portable entre plateformes.
   + Changement dans la couche data = **pas d'impact sur le métier**.


##### Architecture modulaire 
Structure multi-modules avec séparation par fonctionnalité et par couche.
+ Compilation incrémentale, builds parallèles.
+ La possibilité que chaque équipe peut travailler indépendamment sur un module.
+ Moins de conflits Git.
+ Visibilité contrôlée.
+ Responsabilité unique.

##### Pourquoi Koin ? 

```Koin 4.1.0 pour l'injection de dépendances.```
+ **Simplicité** : Configuration plus simple, moins de boilerplate et pas d'annotations complexes.
+ **Kotlin-first** : juste du Kotlin DSL (ex : module { single { ... } })
+ **Pas de génération de code à la compilation** : tout se fait à l’exécution (runtime) donc pas de compilation longue à cause de la génération de code.
+ Dans un projet multi-modules (app, app-feature:*, common:*), Koin permet d’avoir des modules DI **indépendants facilement injectables**.
+ **Courbe de démarrage**

> Le choix dans ce projet est basé sur : rapidité, flexibilité et simplicité.

> Cela dit, je suis pas totalement opposé à l’utilisation de **Hilt** : il reste un excellent choix, notamment pour sa vérification à la compilation et son intégration officielle dans l’écosystème Android).

##### Pourquoi Ktor ?
```Ktor Client 3.2.3 pour interagir avec l'API [newsapi.org](https://newsapi.org)```
+ **Coroutines natives** : Intégration parfaite avec les coroutines.
+ **Natif Kotlin**
+ **Multiplatform par défaut** (Une éventuel possibilité de partager le module remote pour un projet KMM).
+ **Sans annotations** : tout est défini en code Kotlin pur et moins de génération de code.
+ **Support natif de la sérialisation JSON** (`kotlinx.serialization`).
+ **Stack 100% Kotlin**, pas de Java.
> Ktor a été choisi ici pour sa **flexibilité** et sa **portabilité**.

##### Pourquoi Navigation 3 en version Alpha ?
```Convention plugins personnalisés dans build-logic.```

Le but prochain est de faire de l'app Newsly une application compatible pour tout type d'écran, comme par exemple un écran large, Newsly est censée se comporter d'une maniere plus fluide : 

--------
<img width="800" height="450" alt="image" src="https://github.com/user-attachments/assets/bb95579a-e62a-4661-8c54-1f2438360c7d" />

[ HomeScreen | DetailScreen ] pour les écrans larges

[ HomeScreen ] → [ DetailScreen ] pour les petits écrans

Réf : https://android-developers.googleblog.com/2025/05/announcing-jetpack-navigation-3-for-compose.html

--------

+ **Layouts adaptatifs** : nouvelle API `Scenes` permettant de gérer facilement les affichages mono-pane et multi-pane (idéal pour tablettes ou écrans larges). (À integrer dans le projet dans une prochaine évolution)
+ **Support amélioré du multi-backstack** : gestion plus simple de plusieurs graphes de navigation en parallèle.
- **Dans Navigation 2** (NavController classique), la navigation est une “boîte noire” : on envoie que des commandes (navigate(), popBackStack()) et la librairie stocke l’état quelque part, inaccessible directement.
+ **Dans Navigation 3**, le back stack est juste une liste qu'on définis et qu'on peut facielement contrôlé.
+ **Debug plus simple** : peux afficher le contenu du back stack à tout moment.
- **Risque** : Version alpha, API peut changer.

Avec Nav2 (ancienne version) : 

  ```
navController.navigate("detail/article1")
  ```


Avec Nav3 (own the back stack) :

````
val backStack = rememberNavBackStack<NavKey>(Home)
backStack.add(Detail("article1"))
backStack.removeLastOrNull() // revenir en arr
````

##### Pourquoi Convention Plugins ?
```Convention plugins personnalisés dans build-logic.```
+   **Configuration centralisée ** : pas de duplication.
+   **Cohérence ** : Même configuration pour tous les modules


🏢  Developped by Abdo

