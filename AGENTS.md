# AGENTS.md

## Sacred rule

Always inform about the skills you are using and the documents you are consulting at the beginning of your response.

## Purpose

This repository contains a Java backend application built with Spring Boot.

This file defines the default operating rules for coding agents working in this repository.
It is intentionally concise and acts as the entry point for repository-wide guidance.

Do not treat this file as a complete implementation manual.
Use it to:
- understand how to work in this repository,
- locate the correct project documentation,
- select the appropriate skills for the current task,
- apply consistent engineering standards before making changes.


For project context, architecture, business rules, integrations, persistence, and testing strategy, consult the relevant files under:
- `docs/project/`

---

## Repository profile

This repository is a Java backend service using:
- Java
- Spring Boot
- Maven
- JUnit 5
- Mockito
- Testcontainers

The expected architectural style is:
- Hexagonal / Ports and Adapters
- SOLID-oriented design
- Clean Code practices
- Clear separation of domain, application, and infrastructure concerns

This is a backend-only repository unless explicitly documented otherwise.

---

## How to work in this repository
Before making changes:

1. Classify the task
2. Load only the relevant project documents and skills
3. Inspect the existing code near the change
4. Prefer the smallest correct change that solves the problem

Do not load every project document or every skill by default.
Keep context focused.

If the task is ambiguous, high-risk, or spans multiple modules, create a short micro-spec before implementation.

---

## Source of truth

The source of truth for repository-specific decisions is, in order:

1. User task and explicit constraints
2. This file
3. Selected skills
4. Project documentation under `docs/
5. Existing local code conventions near the affected code

If documentation and implementation conflict:
- identify the conflict,
- follow the most consistent and intentional project convention visible in the code,
- mention the discrepancy in the final summary.

---

## Build and test commands

Use the Maven wrapper if it exists in the repository.  
If the wrapper is not available, use the installed Maven version.

Preferred commands:

### Full build
- `./mvnw clean verify`
- `mvn clean verify`

### Run unit and integration tests
- `./mvnw test`
- `mvn test`

### Run a specific test class
- `./mvnw -Dtest=ClassName test`
- `mvn -Dtest=ClassName test`

### Run a specific test method
- `./mvnw -Dtest=ClassName#methodName test`
- `mvn -Dtest=ClassName#methodName test`

### Package artifact
- `./mvnw clean package`
- `mvn clean package`

### Run static compile validation
- `./mvnw clean compile`
- `mvn clean compile`

### Run Spring Boot application
- `./mvnw spring-boot:run`
- `mvn spring-boot:run`

### Run tests with detailed failure output when needed
- `./mvnw -e test`
- `mvn -e test`

### When container-based integration tests exist
Assume Testcontainers may require Docker or a compatible container runtime to be available before running integration tests.

---

## Engineering principles

Always optimize for:
1. correctness,
2. safety,
3. maintainability,
4. testability,
5. minimal and reviewable diffs.

Prefer:
- explicit, readable code over clever code,
- local changes over broad refactors,
- composition over unnecessary inheritance,
- domain-driven naming,
- immutable value objects where appropriate,
- dependency inversion through ports/interfaces,
- constructor injection,
- deterministic tests.

Avoid:
- speculative abstractions,
- incidental refactors unrelated to the task,
- hidden side effects,
- unnecessary framework coupling in domain/application code,
- leaking infrastructure concerns into the domain,
- large rewrites when a smaller change is enough.

---

## Architecture rules

Respect hexagonal boundaries.

### Domain
Domain code should:
- contain business rules and invariants,
- be framework-agnostic,
- avoid Spring annotations unless there is a documented exception,
- avoid infrastructure dependencies,
- model the business in clear language.

### Application
Application code should:
- orchestrate use cases,
- depend on domain abstractions and ports,
- contain transaction/use-case coordination when appropriate for the project design,
- not absorb infrastructure details that belong in adapters.

### Infrastructure / Adapters
Infrastructure code should:
- implement ports,
- integrate with Spring, persistence, messaging, HTTP clients, security, and external systems,
- remain replaceable and isolated from domain logic,
- translate external concerns into application/domain concepts cleanly.

### Controllers / Entry points
Entry-point code should:
- be thin,
- validate and map requests/responses,
- delegate business behavior to use cases,
- avoid embedding business logic.

### Persistence
Persistence code should:
- keep ORM/database concerns local to adapter layers,
- avoid leaking persistence entities into domain logic,
- preserve transactional consistency and invariants.

---

## Spring Boot conventions

Use Spring Boot pragmatically, not pervasively.

Prefer:
- constructor injection,
- explicit configuration,
- narrow bean responsibilities,
- configuration properties for externalized settings,
- small and focused controllers/services/adapters.

Avoid:
- field injection,
- god services,
- excessive use of static helpers for business logic,
- mixing orchestration, domain rules, and infrastructure code in one class,
- introducing Spring-specific behavior into the domain model without strong justification.

---

## Java conventions

Write production-grade Java.

Prefer:
- small, cohesive classes,
- intention-revealing names,
- records for simple immutable transport/value types when appropriate,
- enums only when the domain is truly closed and stable,
- package structures aligned with architectural boundaries,
- null-safety by design,
- clear exception semantics.

Avoid:
- ambiguous utility classes,
- primitive obsession where a value object would clarify intent,
- long methods with multiple responsibilities,
- deep nesting when a clearer structure is possible,
- broad visibility when package-private or private is enough.

---

## SOLID and clean code expectations

Apply SOLID with judgment, not mechanically.

Expect:
- single responsibility at class and method level,
- open/closed by adding new behavior with minimal modification to stable code,
- substitutability when introducing abstractions,
- small and role-focused interfaces,
- dependency inversion at architectural boundaries.

Clean code expectations:
- methods should do one thing well,
- names should explain intent and business meaning,
- duplication should be removed when it represents the same knowledge,
- comments should explain why, not restate what the code already says,
- error handling should be explicit and consistent.

Do not introduce patterns unless they simplify the code for this repository.

---

## Testing expectations

All non-trivial changes should be validated.

Use the existing test pyramid and keep tests aligned with the change scope.

### Unit tests
Prefer unit tests for:
- domain logic,
- value objects,
- policies/specifications/strategies,
- application services with mocked ports when appropriate.

Use:
- JUnit 5
- Mockito sparingly and only where mocking clarifies the test

Avoid:
- over-mocking,
- tests that only verify implementation trivia,
- brittle tests tightly coupled to private behavior.

### Integration tests
Prefer integration tests for:
- repository adapters,
- persistence mappings,
- transactional behavior,
- messaging adapters,
- HTTP clients where the repository already uses integration-style verification.

Use Testcontainers where containerized dependencies are part of the project test strategy.

### Controller / API tests
Validate:
- request mapping,
- status codes,
- serialization/deserialization,
- error contracts,
- input validation.

### Testing rules
Tests should be:
- readable,
- deterministic,
- isolated,
- behavior-focused,
- proportionate to risk.

When fixing a bug:
- add or adjust a test that fails before the fix and passes after it.

When refactoring:
- preserve behavior and update tests only where structure or intent requires it.

---

## Validation and completion rules

Before considering a task complete:

1. Ensure the requested behavior is implemented or the requested analysis is complete
2. Run the most relevant validations available for the scope of change
3. Check for unintended architectural violations
4. Review for readability and unnecessary complexity
5. Summarize what changed and any remaining risks

Use validation proportionally:

- small refactor -> `./mvnw test -Dtest=...` or targeted compile/test validation
- business rule change -> relevant unit tests, then broader `./mvnw test` if appropriate
- persistence change -> relevant integration tests and, if needed, `./mvnw clean verify`
- API change -> controller/integration verification plus relevant targeted tests
- cross-cutting or risky change -> `./mvnw clean verify`

If Maven wrapper is present, prefer `./mvnw`.
If commands cannot be run, state exactly which command should be executed.
If Testcontainers-based tests are relevant, ensure Docker or equivalent runtime is available.

---

## Security and reliability baseline

Treat backend changes as potentially security-relevant.

Always consider:
- input validation,
- authorization boundaries,
- secret handling,
- sensitive data exposure,
- logging hygiene,
- failure modes and fallback behavior,
- transactional consistency,
- idempotency where relevant,
- concurrency implications where relevant.

Do not log secrets, tokens, credentials, or sensitive personal data.

Do not weaken validation, authorization, or error handling without explicit task requirements.

---


## Change management rules

Do not perform git write actions such as commit, push, pull, merge, rebase, branch deletion, or pull request creation unless the user explicitly requests that exact action.

Do not make unrelated changes.

Do not rename, move, or restructure broadly unless the task requires it.

Do not add dependencies unless clearly justified.

Do not silently change public behavior, contracts, or business rules.

If a better design is obvious but out of scope:
- mention it in the summary,
- do not expand the task unless requested.

Prefer diffs that are easy to review.

---

## Output expectations for implementation tasks

At the end of the task, report:

1. Task classification
2. Files and documents consulted
3. Summary of changes
4. Validation performed
5. Risks, assumptions, or follow-up notes

For analysis-only tasks:
- do not implement,
- provide the best possible design/spec/review aligned with this repository.

---

## Practical rule

When in doubt:
- read less, but read the right things,
- change less, but change the right thing,
- test enough to justify confidence,
- keep the result easy for a human reviewer to understand.