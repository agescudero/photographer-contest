# Backend Specification - Photographer Contest

## 1. Objetivo y alcance
Este documento define el diseño funcional backend de la aplicación de gestión de concursos fotográficos, limitado a los requisitos proporcionados.

Incluye:
- Lenguaje ubicuo.
- Diseño de dominio (módulos, aggregate groups, entidades y value objects).
- Casos de uso.
- Endpoints backend.
- Reglas de negocio, validaciones y puntos abiertos.

No incluye diseño frontend ni detalle técnico de infraestructura.

## 2. Lenguaje ubicuo
- Participante: usuario registrado como fotógrafo que participa en concursos.
- Organizador: usuario que crea concursos y categorías.
- Jurado: perfil evaluador de fotos (funcionalidad pendiente de definir).
- Concurso: evento fotográfico con fechas, bases, categorías y jurado.
- Bases: texto/normativa oficial del concurso.
- Categoría: subgrupo dentro de un concurso donde se suben fotos.
- Categoría FREE: categoría sin requisito de pago.
- Categoría PREMIUM: categoría que requiere upgrade (pago único del concurso).
- Inscripción: alta del participante en un concurso.
- Upgrade: activación de membresía premium de un concurso para un participante inscrito.
- Membresía premium de concurso: derecho de acceso a todas las categorías premium de ese concurso.
- Pago simulado: confirmación mock que activa el upgrade sin pasarela real.
- Foto: contenido subido por participante a una categoría de un concurso.
- URL de foto: enlace a documento en Google Drive proporcionado por el participante.
- Galería: colección de fotos de un participante agrupadas por concurso.
- Perfil de participante: datos de inscripción/identidad del participante.
- Campo extra: dato configurable por el organizador para el perfil/inscripción (pendiente de definición).
- Contacto: formulario para enviar consultas al soporte/organización.
- Concurso en curso: concurso abierto para participación.
- Concurso en votación: concurso en estado `ON_VOTATION`.
- Concurso cerrado: concurso finalizado.

## 3. Reglas de negocio consolidadas
- Un participante puede registrarse libremente con usuario y contraseña.
- Tras registro, se recomienda completar el perfil, pero no bloquea otras operaciones.
- Un concurso se crea sin categorías.
- Cada categoría pertenece a un único concurso.
- Una categoría define un límite de fotos por participante.
- Si una categoría es PREMIUM, exige upgrade del concurso para subir fotos.
- El upgrade es pago único por participante y concurso.
- En esta fase, el pago es simulado (mock) y activa el upgrade sin integración real.
- Si un concurso no tiene categorías FREE, el flujo de inscripción redirige a upgrade.
- Si un concurso no tiene categorías PREMIUM, no debe existir opción de upgrade en ese concurso.
- Un participante inscrito puede subir, editar (titulo/descripcion) y borrar una foto.
- El borrado de foto elimina datos y referencias internas, pero no borra el archivo en Google Drive.
- Un participante puede consultar sus concursos (en curso y cerrados) y sus galerías por concurso.
- El formulario de contacto está disponible aunque el participante no esté inscrito.
- El participante no puede ver votos del jurado mientras el concurso esté en curso o en votación.
- Los estados operativos de concurso en esta fase son: `OPEN`, `ON_VOTATION`, `CLOSED`.

## 4. Dominio (bounded modules)

### 4.1 Modulo Identity & Access
Responsabilidad: autenticación y gestión de perfiles de usuario.

Aggregate groups:
- UserAccountAggregate
  - Entidades:
    - UserAccount (id, username, passwordHash, role, status, createdAt)
    - ParticipantProfile (userId, fullName, location, email, phone, age, extraFields)
  - Value objects:
    - Username
    - PasswordHash
    - Email
    - PhoneNumber
    - Age
    - ExtraFieldValue (key, value)

### 4.2 Modulo Contest Management
Responsabilidad: ciclo de vida de concursos y categorías.

Aggregate groups:
- ContestAggregate
  - Entidades:
    - Contest (id, title, rules, startDate, endDate, status)
    - Category (id, contestId, name, description, type, photoLimitPerParticipant)
    - JuryMember (id, contestId, displayName) [modelo mínimo, pendiente funcional]
  - Value objects:
    - ContestStatus (OPEN, ON_VOTATION, CLOSED)
    - CategoryType (FREE, PREMIUM)
    - DateRange

### 4.3 Modulo Enrollment & Membership
Responsabilidad: inscripción al concurso y estado premium.

Aggregate groups:
- EnrollmentAggregate
  - Entidades:
    - Enrollment (id, contestId, participantId, enrolledAt, membershipStatus)
    - MembershipUpgrade (id, enrollmentId, paymentStatus, paidAt, amount, currency, provider)
  - Value objects:
    - MembershipStatus (FREE_ONLY, PREMIUM_ACTIVE)
    - PaymentStatus (SIMULATED_SUCCESS, SIMULATED_FAILED)
    - Money (amount, currency)
    - PaymentProvider (MOCK)

### 4.4 Modulo Submission Gallery
Responsabilidad: gestión de fotos por concurso/categoría.

Aggregate groups:
- PhotoSubmissionAggregate
  - Entidades:
    - PhotoSubmission (id, contestId, categoryId, participantId, title, description, mediaUrl, createdAt, updatedAt)
  - Value objects:
    - PhotoTitle
    - PhotoDescription
    - MediaUrl

### 4.5 Modulo Contact
Responsabilidad: recepción de consultas.

Aggregate groups:
- ContactMessageAggregate
  - Entidades:
    - ContactMessage (id, participantId nullable, subject, message, createdAt, status)
  - Value objects:
    - ContactStatus (RECEIVED, IN_REVIEW, CLOSED)

### 4.6 Modulo Judging (placeholder)
Responsabilidad: evaluación de fotos por jurado.
Estado: pendiente de definición funcional.

Aggregate groups (propuesto mínimo, no implementable aún):
- JuryVoteAggregate
  - Entidades:
    - JuryVote (id, contestId, categoryId, photoId, juryMemberId, score, comment, createdAt)

## 5. Casos de uso

### 5.1 Participante
- UC-P-001 Registrarse como participante.
- UC-P-002 Iniciar sesión.
- UC-P-003 Completar/editar perfil de participante (opcional, no bloqueante).
- UC-P-004 Listar concursos disponibles.
- UC-P-005 Consultar bases de un concurso.
- UC-P-006 Consultar jurado de un concurso.
- UC-P-007 Inscribirse en concurso.
- UC-P-008 Realizar upgrade premium simulado de concurso inscrito.
- UC-P-009 Listar mis concursos (en curso, en votación y cerrados).
- UC-P-010 Subir foto a categoría.
- UC-P-011 Editar metadatos de foto (titulo/descripcion).
- UC-P-012 Borrar foto y eliminar rastro interno.
- UC-P-013 Consultar galería por concurso.
- UC-P-014 Enviar formulario de contacto.

### 5.2 Organizador
- UC-O-001 Crear concurso sin categorías.
- UC-O-002 Añadir categoría (FREE/PREMIUM) a concurso.
- UC-O-003 Cambiar estado de concurso (`OPEN`, `ON_VOTATION`, `CLOSED`).

### 5.3 Jurado
- UC-J-XXX Pendiente de definir.

## 6. Endpoints backend (REST)
Base path sugerido: `/api/v1`

### 6.1 Auth y perfil
- `POST /auth/register`
  - Alta de participante con `username` y `password`.
- `POST /auth/login`
  - Obtención de token de sesión.
- `GET /participants/me/profile`
  - Consultar perfil del participante autenticado.
- `PUT /participants/me/profile`
  - Crear/actualizar perfil (nombre, localización, email, teléfono, edad, campos extra).

### 6.2 Concursos y categorías
- `GET /contests`
  - Listar concursos visibles/disponibles.
- `GET /contests/{contestId}`
  - Detalle del concurso y bases.
- `GET /contests/{contestId}/jury`
  - Listado de jurado del concurso.
- `GET /contests/{contestId}/categories`
  - Categorías del concurso.

### 6.3 Inscripción y upgrade
- `POST /contests/{contestId}/enrollments`
  - Inscribir participante autenticado en concurso.
  - Regla: si no hay categorías FREE, devolver estado de "requiere upgrade".
- `GET /participants/me/enrollments`
  - Mis inscripciones con estado del concurso (`OPEN`, `ON_VOTATION`, `CLOSED`).
- `POST /contests/{contestId}/upgrade`
  - Ejecutar upgrade premium con pago simulado (mock).
- `GET /contests/{contestId}/membership`
  - Consultar estado de membresía del participante en el concurso.

### 6.4 Fotos y galería
- `POST /contests/{contestId}/categories/{categoryId}/photos`
  - Subir foto (titulo, descripción, `mediaUrl` de Google Drive).
  - Validar límite por categoría y permisos FREE/PREMIUM.
- `PATCH /contests/{contestId}/categories/{categoryId}/photos/{photoId}`
  - Editar titulo/descripcion de foto propia.
- `DELETE /contests/{contestId}/categories/{categoryId}/photos/{photoId}`
  - Borrar foto propia y eliminar rastro interno.
- `GET /participants/me/galleries`
  - Galerías propias organizadas por concurso.
- `GET /participants/me/galleries/{contestId}/photos`
  - Fotos propias en un concurso.

### 6.5 Contacto
- `POST /contact/messages`
  - Enviar formulario de contacto, inscrito o no inscrito.

### 6.6 Organizador
- `POST /organizer/contests`
  - Crear concurso (fechas, bases).
- `POST /organizer/contests/{contestId}/categories`
  - Crear categoría vacía indicando FREE/PREMIUM, nombre, descripción y límite de fotos.
- `PATCH /organizer/contests/{contestId}/status`
  - Cambiar estado del concurso a `OPEN`, `ON_VOTATION` o `CLOSED`.

## 7. Reglas de autorización (resumen)
- Participante:
  - Puede operar solo sobre su perfil, inscripciones y fotos.
- Organizador:
  - Puede crear concursos, categorías y cambiar estado del concurso.
- Jurado:
  - Sin endpoints definidos todavía.

## 8. Validaciones backend críticas
- `username` único en registro.
- `email` formato válido.
- `age` dentro de rango configurable.
- No permitir inscripción duplicada de un participante al mismo concurso.
- No permitir subir foto en categoría PREMIUM sin `PREMIUM_ACTIVE`.
- No permitir superar `photoLimitPerParticipant` por categoría.
- No permitir editar o borrar fotos de terceros.
- Validar que `mediaUrl` pertenezca a formato de enlace de Google Drive.
- En concurso `OPEN` u `ON_VOTATION`, no exponer votos del jurado a participantes.
- En borrado de foto, eliminar metadata y referencias internas, sin borrar el archivo externo de Drive.

## 9. Ambigüedades, huecos y decisiones pendientes
1. Jurado "Por definir": faltan operaciones (asignación de jurado, votación, escala, desempates, publicación de resultados).
2. Flujo de pago: en esta fase es mock. Queda pendiente definir integración real con pasarela.
3. Campos extra personalizables por organizador: pendiente definir modelo de configuración (tipo, obligatorio, visibilidad, versionado).
4. Estados de concurso: definidos en esta fase (`OPEN`, `ON_VOTATION`, `CLOSED`), pero faltan reglas de transición exactas.
5. Contacto: no se define destinatario, SLA ni flujo de respuesta.

## 10. Criterios de calidad del documento
- Trazabilidad requisito -> regla -> caso de uso -> endpoint.
- Lenguaje consistente con términos del negocio.
- Separación clara por módulos de dominio.
- Cobertura de permisos y validaciones críticas.
- Registro explícito de incertidumbres para cierre funcional.
