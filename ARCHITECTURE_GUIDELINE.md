# Architecture Guideline

## Overview

This project follows a modular Android architecture with a `core/` layer for shared functionality and a `feature/` layer for isolated feature modules.

The current structure is:
- `app/` — Android application entry point
- `core/` — shared modules for common business logic, UI helpers, persistence, and utilities
- `feature/` — feature-specific modules separated by layer: `data`, `domain`, `ui`, `di`

The project uses Gradle Kotlin DSL, Compose, Koin, Room, Retrofit, and serialized networking.

## Module responsibilities

### app
- Hosts the Android application configuration
- Depends on feature and core modules
- Registers shared module implementations and entry-point wiring

### core modules
- `core:data` — shared persistence/network implementation and common data sources
- `core:domain` — shared domain models and business rules
- `core:ui` — shared Compose UI components and theming
- `core:utils` — shared utility code and helpers

Core modules are reusable across feature modules and should not depend on feature-specific modules.

### feature modules
Each feature is divided into the following modules:
- `feature:<name>:data` — feature-specific data access, repositories, DTOs, and persistence
- `feature:<name>:domain` — feature-specific business logic, use cases, and domain models
- `feature:<name>:ui` — feature-specific Compose UI screens and presentation logic
- `feature:<name>:di` — feature-specific dependency injection wiring and module registration

## Dependency rules

Follow these rules when adding new modules or updating existing ones:

- `feature:<name>:data` may depend on `feature:<name>:domain` and shared `core` modules
- `feature:<name>:ui` may depend on `feature:<name>:domain` and shared `core` modules
- `feature:<name>:di` may depend on `feature:<name>:data`, `feature:<name>:domain`, and `feature:<name>:ui`
- `feature:<name>:domain` should be isolated from UI-specific and DI-specific modules
- Shared `core` modules should not depend on feature modules
- Avoid feature-to-feature dependencies unless there is a strong architectural reason; prefer shared `core` modules for cross-feature abstractions

## Naming and package conventions

Use these namespace patterns:
- `erazero1.<feature>.data`
- `erazero1.<feature>.domain`
- `erazero1.<feature>.ui`
- `erazero1.<feature>.di`
- `core.<module>` for core modules

Source directories follow the Gradle default path:
- `src/main/kotlin/feature/<feature>/<module>/...`
- `src/main/kotlin/core/<module>/...`

## Layer-specific guidelines

### Data layer
- Keep feature data modules responsible for repositories, network DTOs, caching, Room entities, mappers, and data sources.
- Prefer exposing repository interfaces from the domain layer and concrete implementations in the data layer.
- Data modules may depend on the feature domain module and shared `core` modules such as networking and persistence.
- Avoid UI or navigation code in the data layer.
- Keep error handling and mapping in the data layer; return domain-friendly results to the domain layer.

### Domain layer
- Feature domain modules should contain business logic, use cases, domain models, and flow transformations.
- Keep domain modules Android-independent whenever possible.
- Use sealed classes or result wrappers for success / error / exception flows.
- Domain modules should not depend on UI or DI modules.
- Keep feature-specific API contracts here so UI and DI modules can consume them without leaking implementation details.

### UI layer
- UI modules own Compose screens, navigation graphs, UI state, events, and presentation logic.
- Use a consistent MVI-style structure:
  - `Event`/`Intent`: a sealed type for user actions and UI intents.
  - `State`: a sealed state model representing `Initial`, `Loading`, `Success`, and `Error` cases.
  - `Action`/`Effect`: a one-time effect channel for navigation, snackbars, toasts, or other single events.
  - `ViewModel`: holds `MutableStateFlow<State>` and an action `Channel<Action>`.
  - `onEvent(event)` dispatcher in the ViewModel to map UI events into business actions.
- Keep top-level Composables small and declarative. The screen composable should:
  - obtain the ViewModel, usually via `koinViewModel()`
  - collect state with `collectAsState()`
  - observe one-time actions with `SingleEventEffect` or a similar effect handler
  - forward callbacks to `viewModel::onEvent`
- Put reusable UI pieces under `components/` and keep screen wiring separate from component internals.
- Navigation graphs should live in `navigation/` and route screen names through sealed `Screen` objects.

### DI layer
- DI modules register feature ViewModels, use cases, repositories, and other dependencies.
- Keep DI wiring separate from UI composition and business logic.
- Prefer explicit dependency declarations in DI modules for each feature package.
- DI modules may depend on feature data, domain, and UI modules, but not on feature-specific implementation details outside the feature boundary.

### Example: creating a new screen in a UI module
1. Define the screen navigation target in `navigation/Screen.kt`.
2. Add a route to the feature navigation graph in `navigation/<Feature>NavGraph.kt`.
3. Create model classes in `feature/<feature>/ui/<screen>/model/`:
   - `<Feature>Event.kt`
   - `<Feature>State.kt`
   - `<Feature>Action.kt`
4. Add a `ViewModel` in `feature/<feature>/ui/<screen>/<Feature>ViewModel.kt` with:
   - `MutableStateFlow<...State>`
   - `Channel<...Action>`
   - `onEvent(event: ...Event)` dispatcher
   - business logic that updates state and emits actions
5. Create the screen composable in `feature/<feature>/ui/<screen>/<Feature>Screen.kt`:
   - collect state from the ViewModel
   - observe `action` flow with `SingleEventEffect`
   - pass event callbacks into child components
   - display loading / error / success states using `when (state)`
6. Build UI components under `components/` and keep them stateless when possible.

## Creating new feature modules

Use the Gradle helper task to bootstrap a feature module structure.

Example:
```bash
./gradlew createFeatureModule -PmoduleName=newfeature
```

That task creates:
- `feature/<module>/data`
- `feature/<module>/domain`
- `feature/<module>/ui`
- `feature/<module>/di`

And it appends the new `include` statements to `settings.gradle.kts`.

### Generated module conventions

- `data` uses `id("base-data")`
- `domain` uses `id("base-domain")`
- `ui` uses `id("base-ui")`
- `di` uses `id("base-di")`

The generated `data`, `ui`, and `di` modules also set an Android namespace.

## Creating new core modules

Use the Gradle helper task for new core modules.

Example:
```bash
./gradlew createCoreModule -PmoduleName=analytics
```

That task creates:
- `core/<module>`

with `id("custom-android-library")` and namespace `core.<module>`.

## Plugin conventions

The project defines Gradle script plugins in `buildSrc/`:
- `custom-android-library` — base config for Android library modules
- `custom-kotlin-library` — base config for Kotlin-only modules
- `base-data` — feature data module template with Room, serialization, and networking
- `base-domain` — feature domain module template
- `base-ui` — feature UI module template with Compose support
- `base-di` — feature DI module template with Koin support

Use these plugin names consistently when creating or modifying modules.

## When to add a new module

Add a new feature module when:
- you are implementing a distinct user-facing flow
- the code belongs to one cohesive feature domain
- you want to isolate feature ownership and reduce cross-feature coupling

Add a new core module when:
- functionality is reusable across multiple features
- the logic is orthogonal to any specific feature
- you need a shared utility, library, or domain abstraction

## Best practices

- Keep feature domain logic separated from UI implementation
- Keep DI wiring in the `di` module only
- Expose feature APIs through domain and DI modules, not through internal implementation details
- Prefer `api` exposure only when a module truly offers reusable interface contracts
- Keep module dependencies directional and acyclic
- Keep module-level `.gitignore` files containing `/build` for generated artifacts

## Notes

The current repository structure is designed for modular growth. Follow the existing project conventions for feature layering and module dependencies to keep the architecture consistent and maintainable.
