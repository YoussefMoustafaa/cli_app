# Java Command Line Interface (CLI)

A Java-based command-line interface that implements common Unix/Linux commands. This project provides a simple and extensible CLI framework with support for basic file system operations and command chaining.

## Features

### System Commands
- `pwd` - Print current working directory
- `cd <directory>` - Change directory
- `echo` - Print arguments
- `ls` - List directory contents
  - `ls -a` - List including hidden files
  - `ls -r` - List in reverse order
- `mkdir <dirName>` - Create new directory
- `rmdir <dirName>` - Remove directory
- `touch <fileName>` - Create empty file
- `mv <src> <dest>` - Move or rename file
- `rm <fileName>` - Remove file
- `cat <fileName>` - Display file contents

### File Operations
- `> <fileName>` - Redirect output to file (overwrite)
- `>> <fileName>` - Append output to file
- `|` - Pipe command output to another command

### Internal Commands
- `exit` - Exit the CLI
- `help` - Display help message

## Project Structure

- `src/` - Source code directory
  - `main/` - Main application code
    - `commands/` - Command implementations
    - `fileSystem/` - File system management
    - `executor/` - Command execution logic
    - `parser/` - Command parsing logic
  - `test/` - Unit tests
- `lib/` - External dependencies
- `bin/` - Compiled output files

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Visual Studio Code with Java extensions

### Setup
1. Clone the repository
2. Open the project in Visual Studio Code
3. Ensure Java extensions are installed
4. Build the project using the integrated build tools

### Running the Application
1. Navigate to the project directory
2. Run the main class:
