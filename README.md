# Test-Oriented Microkernel (TOM) Architecture

This repository contains the **TOM Automation Framework**, an extensible, modular test automation framework based on the Microkernel Architecture.

## Features
- Modular test plugin (UI, API, Performance, Mobile)
- Core execution engine (Microkernel-based)
- CI/CD Integration
- Self-Healing Mechanism (Planned)

## Getting Started

### Java Version
1. Install Java 17 and Maven.
2. Clone the repository.
3. Run tests using:
   ```sh
   mvn test
   ```

### JavaScript Version
1. Install Node.js and npm.
2. Clone the repository.
3. Install dependencies:
   ```sh
   npm install
   ```
4. Run tests using:
   ```sh
   npm test
   ```

## Appium & JMeter Support

### Mobile Testing (Appium)
- Java: `plugin/mobile/AppiumTest.java` (Requires Appium Server & Android Emulator)
- JavaScript: `plugin/mobile/AppiumTest.js` (Uses WebdriverIO for Appium)

### Performance Testing (JMeter)
- Java: `plugin/performance/JMeterTest.java` (Integrates JMeter for load testing)
- JavaScript: `plugin/performance/JMeterTest.js` (Runs JMeter tests via command line)
