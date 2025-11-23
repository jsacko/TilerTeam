# 🧱 Team Up! - Collaborative Tiling Game (Java)

This repository contains a collaborative, turn-based tiling game developed in **Java** using Object-Oriented Programming (OOP) principles. The goal of the game, **Team Up!**, is for a team to efficiently and strategically tile a shared wall according to complex placement rules.

This project showcases clean architectural design, effective use of JUnit for testing, and robust validation logic for a complex ruleset.

## ✨ Features

- **Core Game Logic:** Complete implementation of the **Team Up!** game rules.
- **Dynamic Wall:** The wall maintains a fixed width of **5 units** but has an effectively "infinite" height, expanding as tiles are successfully placed.
- **Tile Management:** Implementation of 9 unique tile shapes (up to 3x3 dimensions) and two tile sets (Blue and Red).
- **Rule Enforcement:** Robust input validation and placement constraints (e.g., checking alignment, overlap, and coverage rules). The program provides clear, interactive feedback to the user on why a placement failed.
- **Object-Oriented Design:** Clear separation of concerns using classes like `Wall`, `Tile`, and `Deck` (Paquet).

---

## 🏗️ Project Architecture

The application is structured around a simple, yet rigorous, Object-Oriented design to handle the state of the game board, the tile inventory, and the rule enforcement logic.

### UML Class Diagram

The core of the application is represented by the following classes, defining the relationships between the game components:

### Key Components

| Class Name | Description |
| --- | --- |
| `Constantes` | Stores static game parameters, such as the fixed wall width (5) and tile dimensions. |
| `Wall` (`Mur`) | Represents the primary game board. Handles state management, tracking which coordinates are occupied by tiles. Responsible for updating the wall's height. |
| `Tile` (`Carreau`) | Abstract class or interface representing the various tile shapes and their properties (e.g., color, geometry). |
| `Deck` (`Paquet`) | Manages the available tile inventory and the draw mechanism based on the 'instruction cards' mechanic. |
| `Main` | The entry point of the application, handling the game loop, user interaction (CLI), and rendering the current state of the wall. |

---

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or newer.
- A Java IDE (e.g., IntelliJ IDEA, Eclipse) or a command-line environment configured for Java.

### Installation

1. **Clone the repository:**
    
    ```bash
    git clone [<https://github.com/jsacko/java-teamup-tiling-game.git>](<https://github.com/jsacko/java-teamup-tiling-game.git>)
    cd java-teamup-tiling-game
    
    ```
    
2. **Build the project** (if using a build system like Maven or Gradle, otherwise compile manually):
    
    ```bash
    # Example compilation command (adjust as necessary)
    javac -d out src/**/*.java
    
    ```
    

### Usage

Run the main application file:

```bash
# Example execution command
java -cp out Main
```
