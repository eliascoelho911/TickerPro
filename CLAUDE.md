# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

TickerPro is a multi-module Android application for investment portfolio management built with Kotlin and Jetpack Compose. The project uses a modular architecture with feature modules, common modules, and a design system to manage investment portfolios and track financial assets.

## Build System

The project uses Gradle with Kotlin DSL and version catalogs for dependency management.

### Key Build Commands

- **Build the project**: `./gradlew build`
- **Run tests**: `./gradlew test`
- **Run Android tests**: `./gradlew connectedAndroidTest`
- **Clean build**: `./gradlew clean`
- **Build specific module**: `./gradlew :app:build` or `./gradlew :features:dashboard:build`

### Build Configuration

- **Compile SDK**: 35
- **Min SDK**: 26
- **Target SDK**: 35
- **Version catalogs**: Dependencies are managed in `gradle/libs.versions.toml`
- **BuildSrc**: Custom Gradle plugins are located in `buildSrc/src/main/kotlin/`

## Architecture

### Module Structure

The project follows a multi-module architecture:

- **`:app`** - Main application module with MainActivity and Application class
- **`:common:common`** - Common utilities and file management
- **`:common:data-domain`** - Data domain layer for managing models and data sources
- **`:common:core`** - Core architecture components (BaseViewModel, Navigation, UI patterns)
- **`:design-system`** - UI components and theming for investment portfolio UI
- **`:features:...`** - All the features of the application, such as home, portfolio, and settings.
- **`:logs`** - Logging utilities with AndroidLogger and ComposeLogger

### Architecture Patterns

- **MVI (Model-View-Intent)**: Uses UIState, UIIntent, and UIAction pattern
- **BaseViewModel**: All ViewModels extend `BaseViewModel<ACTION, STATE, INTENT>`
- **Navigation**: Uses Jetpack Navigation Compose with type-safe routes
- **Dependency Injection**: Koin for DI with module-based configuration
- **Logging**: Custom logging framework with AndroidLogger and ComposeLogger

### Key Components

- **BaseViewModel**: Located in `common/core/src/main/java/com/eliascoelho911/tickerpro/core/arch/viewmodel/`
- **Navigation**: Type-safe navigation using sealed classes in `core/navigation/`
- **UI Architecture**: UIState, UIIntent, UIAction interfaces in `core/arch/`

## Custom Gradle Plugins

The project uses several custom Gradle plugins in `buildSrc/`:

- **CommonAndroidPlugin**: Base Android configuration
- **CommonAndroidComposePlugin**: Compose-specific configuration
- **AndroidFeaturePlugin**: Feature module configuration with standard dependencies
- **SetupTestPlugin**: Test configuration
- **SetupJavaVersion**: Java version setup

## Dependencies

### Key Libraries

- **UI**: Jetpack Compose with Material 3
- **Architecture**: Lifecycle, ViewModel, Navigation Compose
- **DI**: Koin
- **Networking**: Ktor client (likely for financial data APIs)
- **Serialization**: Kotlinx Serialization
- **Testing**: JUnit, Mockk, Robolectric, Turbine
- **Logging**: Custom logging framework with SLF4J and Logback

## Development Workflow

### Adding New Features

1. Create new feature module in `features/` directory
2. Apply `AndroidFeaturePlugin` in the module's build.gradle.kts
3. Follow the established MVI pattern with BaseViewModel
4. Use Koin for dependency injection
5. Add navigation routes in `core/navigation/screen/`

### Testing

- Unit tests use Robolectric for Android components
- ViewModels can be tested with Turbine for Flow testing
- Test configuration is handled by `SetupTestPlugin`

## Code Style

- Kotlin official code style (configured in gradle.properties)
- Uses custom logging framework - prefer AndroidLogger over standard logging
- Navigation uses type-safe routes with sealed classes
- ViewModels follow MVI pattern with BaseViewModel

## Package Structure

- `com.eliascoelho911.tickerpro` - Main app package
- `com.eliascoelho911.tickerpro.core` - Core architecture components
- `com.eliascoelho911.tickerpro.home` - Home feature for portfolio overview
- `com.eliascoelho911.tickerpro.ds` - Design system components
- `com.eliascoelho911.common` - Common utilities
- `com.eliascoelho911.logs` - Logging framework

## Important Notes

- Investment portfolio management application
- Uses custom build plugins for consistent module configuration
- Compose compiler reports are generated in `build/compose_compiler`
- Stability configuration for Compose is in `stability_config.conf`
- The MainActivity currently has placeholder navigation setup
- Ktor client likely used for fetching financial market data