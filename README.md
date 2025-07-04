# Climate Thread Performance

Projeto para avaliação substitutiva da disciplina **Programação Concorrente e Distribuída**.  
Compara o desempenho do processamento de dados climáticos de 27 capitais brasileiras usando diferentes números de threads.

## 🎯 Objetivo

O projeto visa demonstrar, por meio de um experimento prático, o impacto do uso de threads na performance de um algoritmo.  
Para isso, coleta dados de temperatura (hora a hora) durante o mês de janeiro de 2024 das 27 capitais brasileiras utilizando a API gratuita [Open-Meteo](https://open-meteo.com/en/docs/).

A cada execução, o programa realiza:
- Requisições HTTP com latitude e longitude de cada capital;
- Processamento dos dados para calcular temperaturas **mínima**, **máxima** e **média diárias** (31 dias × 3 medidas = 93 resultados por cidade);
- Impressão no console dos resultados processados;
- Cálculo do tempo de execução para comparar as abordagens.

O experimento é executado em **quatro versões**:
1. Sem uso de threads (apenas a `main`);
2. Com 3 threads (cada uma responsável por 9 capitais);
3. Com 9 threads (cada uma com 3 capitais);
4. Com 27 threads (uma para cada capital).

## 🧠 Motivação
O uso de múltiplas threads pode acelerar programas que realizam operações bloqueantes, como chamadas HTTP. Porém, excesso de threads pode causar overhead ou até ser limitado pela API consumida. Este projeto visa analisar empiricamente esse trade-off, ajudando a compreender melhor os conceitos de concorrência e paralelismo na prática.

## 🗂️ Estrutura do Projeto

```bash
climate-thread-performance/
├── README.md
├── relatorio.pdf
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── br/
        │       └── ucb/
        │           └── climate/
        │               ├── model/
        │               │   ├── City.java
        │               │   └── WeatherData.java
        │               ├── controller/
        │               │   ├── WeatherFetcher.java
        │               │   └── ThreadManager.java
        │               ├── view/
        │               │   └── ConsolePrinter.java
        │               ├── threads/
        │               │   ├── Main3Threads.java
        │               │   ├── Main9Threads.java
        │               │   └── Main27Threads.java
        │               ├── MainSingleThread.java
        │               └── Util.java
        └── resources/
            └── cities.json
```

## ⚙️ Como Executar
O projeto foi desenvolvido com Java 17 e Maven.

### ✅ Pré-requisitos
- Java 17+ instalado
- Maven instalado

### 📌 Instruções

1. Clone o repositório:

    ```bash
    git clone https://github.com/carinysaldanha/climate-thread-performance.git
    cd climate-thread-performance
    ```

2. Compile o projeto:

    ```bash
    mvn clean compile
    ```

3. Execute uma das versões:

- Versão sem threads:
```bash
mvn exec:java -Dexec.mainClass="br.ucb.climate.MainSingleThread"
```

Versão com 3 threads:
```bash
mvn exec:java -Dexec.mainClass="br.ucb.climate.threads.Main3Threads"
```

- Versão com 9 threads:
```bash
mvn exec:java -Dexec.mainClass="br.ucb.climate.threads.Main9Threads"
```
- Versão com 27 threads:
```bash
mvn exec:java -Dexec.mainClass="br.ucb.climate.threads.Main27Threads"
```

## 💡 Dica:
Para salvar a saída em um arquivo de texto:

```bash
mvn exec:java -Dexec.mainClass="br.ucb.climate.threads.Main27Threads" > saida.txt
```

## 📊 Resultados
Cada versão executa o experimento por 10 rodadas e imprime o tempo médio de execução ao final.

Para cada cidade, são apresentados

| Cidade         | Data       | Temperatura Mínima (°C) | Temperatura Máxima (°C) | Temperatura Média (°C) |
|----------------|------------|-------------------------|-------------------------|-----------------------|
| São Paulo      | 2024-01-01 | 20.10                   | 30.50                   | 25.30                 |
| Rio de Janeiro | 2024-01-01 | 22.30                   | 31.20                   | 26.80                 |

Os resultados completos e a análise comparativa entre as 4 versões estão disponíveis no relatório.

## 📄 Relatório
O arquivo [relatorio.pdf]() contém:

- Explicação teórica sobre threads, concorrência e paralelismo;
- Tabela com os tempos médios de execução por versão;
- Gráfico comparativo entre os tempos das 4 abordagens;
- Referências bibliográficas utilizadas.

## 📦 Dependências
As bibliotecas estão configuradas no pom.xml:

- org.json — Manipulação de JSON;
- commons-io — Leitura de arquivos.

Instaladas automaticamente pelo Maven.

## 👨‍💻 Autor
- Cariny Saldanha — [GitHub](https://github.com/carinysaldanha)

## 📝 Licença
Este projeto é parte de uma avaliação acadêmica e não possui licença comercial.