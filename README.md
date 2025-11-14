# Por que usar DispatcherProvider e infraestrutura de Dispatchers customizados?

| Arquivo                     | Função                                                               |
|:----------------------------|:---------------------------------------------------------------------|
| DispatcherProvider.kt       | Interface e provider padrão (Dispatchers.Main, Dispatchers.IO, etc.) |
| DispatcherProviderModule.kt | Fornece o provider via Hilt                                          |
| TestDispatcherProvider.kt   | Versão fake para testes unitários                                    |
| MainDispatcherRule.kt       | Substitui Dispatchers.Main                                           |
| LoginViewModel              | Usa DispatcherProvider em vez de CoroutineDispatcher direto          |
| Testes                      | Instanciam LoginViewModel com TestDispatcherProvider                 |

## Motivação

Para evitar o acoplamento direto do código aos dispatchers da plataforma (Dispatchers.Main, Dispatchers.IO, etc.), 
o projeto utiliza uma arquitetura baseada em injeção de dependência e abstrações de executores.
Essa abordagem traz maior flexibilidade, menor fragilidade e testes mais confiáveis.

### Problemas que isso resolve

1. Dificuldade em testar código que usa Dispatchers reais
   Testes que dependem de threads reais (IO/Main) tornam-se:
- lentos
- não determinísticos
- sujeitos a race conditions
- impossíveis de controlar com runTest

2. Substituir dispatchers reais por testáveis garante que:

- não há concorrência real
- todos os atrasos (delay) podem ser controlados
- os testes rodam de forma síncrona e previsível

3. Acoplamento do ViewModel aos Dispatchers do Android
Se o ViewModel usa Dispatchers.IO diretamente, ele:

- depende da plataforma Android (difícil de testar)
- não pode ser executado em testes de unidade puros
- quebra se o ambiente não fornecer um Main dispatcher real

4. Impossibilidade de isolar agendamento de coroutines
Sem abstração, você não consegue simular cenários como:

- mudança de thread
- erros vindos de IO
- comportamento de código assíncrono

## Arquivos e responsabilidades

### DispatcherProvider.kt

Interface que expõe os dispatchers usados no app.
Ela abstrai Dispatchers.Main, Dispatchers.IO, etc., permitindo que o código “use um dispatcher” sem saber qual é.

**Benefício:**

✔ Desacoplamento total entre lógica de negócio e detalhes de threading.


___

### DispatcherProviderModule.kt

Módulo Hilt que injeta o DispatcherProvider padrão em produção.

**Benefício:**

✔ No app real, tudo usa os dispatchers corretos, sem configuração extra.<br><br>

___

### TestDispatcherProvider.kt

Implementação fake usada em testes, onde todos os dispatchers apontam para um único TestDispatcher.

**Benefícios:**

✔ Testes totalmente controláveis

✔ Sem threads reais

✔ Determinismo total durante runTest

✔ Testes rodam muito mais rápido e nunca “intermitentes”<br><br>

___

### MainDispatcherRule.kt

Uma Test Rule que troca o Dispatchers.Main por um `TestDispatcher` durante os testes.


**Benefícios:**

✔ Permite testar ViewModels que usam código com Main dispatcher

✔ Elimina erros “Module with the Main dispatcher had not been initialized”

✔ Garante que Dispatchers.Main se comporte de forma síncrona no teste

LoginViewModel usando DispatcherProvider

Agora o ViewModel recebe `DispatcherProvider via Hilt`, e não mais um dispatcher fixo.

**Benefícios:**

✔ O ViewModel fica desacoplado do Android
✔ Pode ser testado sem Android Framework
✔ Pode trocar de threading no futuro sem refatorar nada
✔ Código mais limpo, previsível e escalável

Testes usando TestDispatcherProvider

Nos testes, o ViewModel é criado com um dispatcher totalmente controlável.

***Benefícios:***

✔ advanceUntilIdle() funciona em todos os cenários
✔ delay() pode ser avançado manualmente
✔ Testes assíncronos ficam 100% determinísticos
✔ Elimina flakiness ("às vezes passa, às vezes falha")

## Conclusão

> Esta arquitetura de dispatchers customizados melhora a testabilidade e a estrutura do código ao:
Desacoplar lógica de negócio dos dispatchers da plataforma
Permitir testes unitários rápidos, determinísticos e sem dependência do Android
Controlar totalmente o comportamento assíncrono durante os testes
Facilitar injeção de dependência via Hilt
Tornar o ViewModel independente de threading real, melhorando manutenção e escalabilidade.
> 
>> ### Em resumo:
>> Essa solução resolve problemas reais de testabilidade de coroutines, elimina flakiness, desacopla o 
código da plataforma e torna o projeto muito mais profissional e confiável.
> 