graph TB
    subgraph "UI Layer"
        A[HomeScreen] --> B[HomeViewModel]
        C[DetailScreen] --> D[DetailViewModel]
    end

    subgraph "Domain Layer"
        E[GetTopHeadlinesUseCase]
        F[Article Entity]
        G[INewsRepository]
    end

    subgraph "Data Layer"
        H[NewsRepository] --> I[NewsApiService]
        H --> J[ArticleMapper]
    end

    subgraph "Network Layer"
        K[Ktor Client] --> L[NewsAPI]
    end

    B --> E
    D --> E
    E --> G
    G --> H
    I --> K