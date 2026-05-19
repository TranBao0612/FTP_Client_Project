# Simple FTP Client Program

This project is a **Simple FTP Client Program** developed in Java using JavaFX.  
The application allows users to connect to an FTP server and perform common remote file management operations through a user-friendly graphical interface.

## Integrity Declaration
I confirm that this submission is my own work. I did not copy code from AI tools, classmates, or online repositories.

## Project Overview

The project is designed to demonstrate:
- Network programming concepts
- FTP client-server communication
- JavaFX GUI development
- File transfer operations
- Error handling and logging mechanisms (via GUI)

## Installation Requirements

Before running the project, ensure the following are installed:
- Java JDK 17 (or any version of Java that is compatible with `JavaFX 21`)
- Maven
JavaFX dependencies are already included in the `pom.xml` file, so no additional JavaFX installation is required.


## Repository Structure

```bash
FTP_Client_Project
│   
├───.mvn
│           
├───src
│   ├───main
│   │   ├───java
│   │   │   └───vgu
│   │   │       └───SoSe2026_Compnet2
│   │   │           ├───constants
│   │   │           ├───controller
│   │   │           ├───data
│   │   │           ├───ui
│   │   │           │   ├───object
│   │   │           │   └───panel  
│   │   │           └───util
│   │   │                   
│   │   └───resources
│   │       ├───css
│   │       └───images
│   │               
│   └───test
│       └───java
│           └───vgu
│               └───SoSe2026_Compnet2
│                   └───ui
│                       ├───object
│                       └───panel
│                               
└───target
    ├───classes    
    ├───generated-sources
    ├───generated-test-sources
    ├───maven-status
    ├───surefire-reports
    └───test-classes
```

## Features

- Connect to an FTP server using host, username, and password
- Upload files to the remote server
- Download files from the remote server
- Perform remote file operations: create directory, delete file/directory
- Simple and intuitive GUI for easy control
- Connection status and error handling
- Live logging console for monitoring activities and debugging
`Note`: the program only supports 1 server command at a time

## Technology Stack Summary

### Programming Language and Frameworks
- Java 17
- JavaFX for GUI development
- CSS for styling and enhancing GUI

### Tools and IDE
- Maven project build tool
- Git + Github for version control
- Visual Studio Code

### Additional Libraries in used to the Restriction
| Library | Used in Class | Purpose |
|:--------|:--------------|:--------|
| `java.time.LocalDateTime` | `vgu.SoSe2026_Compnet2.util.DateFormatter` | Add real-time timestamp for logging purpose |
| `java.time.format.DateTimeFormatter` | `vgu.SoSe2026_Compnet2.util.DateFormatter` | Format time for logging purpose |
| `java.nio.file.Path` | `vgu.SoSe2026_Compnet2.constants.Directory` | Generate path usable path on all OS and platform |

## Error Handling

The application includes:

- Connection validation
- Connection closed & File transfer error handling
- Invalid credential detection
- Live status updates through the logging console


## Future Improvements

Possible enhancements include:
- Multiple concurrent server commands handling
- Transfer progress bars
- Upload/Download folders and multiple files at once

## Run Guide

1. Open a terminal
2. Navigate to the project root folder, the contains the `pom.xml` file:

```bash
cd <your_directory>/FTP_Client_Project
```

3. Run the following command:

```bash
mvn javafx:run
```

## FTP Commands used for each Feature
| Feature | FTP Command |
|:--------|:------------|
| Login _(after successfully connected to server)_ | `USER` + `PASS` |
| Disconnect | `QUIT` |
| UI Rendering | `PWD` + `TYPE A` + `PASV` + `LIST` |
| Change Directory | `CDUP` to change to Parent Dir and `CD` to change to sub-folders | 
| Create Remote Folder | `MKD` |
| Delete | `RMD` for Folder and `DELE` for File |
| Download | `TYPE I` + `PASV` + `RETR` |
| Upload | `TYPE I` + `PASV` + `STOR` |

## UI: User Guide
![Program Launch](src\main\resources\images\UI_launch.png)
- The program is launched, not connected to any server yet.
- Most feature are disbled at this state and when any command is fired. User can only click `Connect` button to connect to FTP Server.
- However, user file panel is interactable at any time and state:
    - User can select a file/folder by click primary mouse (usually left mouse), and deselect by click secondary mouse (usually right mouse). Multiple rows selection is not support.
    - User can change directory to sub-folders by double-click primary mouse on the folder.
    - User can change directory to any folder by `Change Directory` button _(further guide below)_
- UI Components:
    - Top: a text to display connection status. Will display in connecting host and username after successfully connected to a server.
    - Bottom: a log console, log error in red text, command sent to server in green text, server response in black text, and system info in blue text.
    - Left panel: displaying server folder, Right panel: displaying user folder
        - Top bar: display current directory
        - Table: files and folders in current directory, items of other type (e.g. symbolic link) will not be displayed
        - Bottom text: summary of the current directory
    - Center: control buttons
    
---
![Prompt Connection Info](src\main\resources\images\UI_prompt_connection_info.png)
- When user click `Connect` button, a dialog will pop up to prompt the connection information: hostname, username, password.
- If choose `Anonymous Login`, user can only specify hostname.
- If user leave the input field empty, default credentials (prompt text) will be used to initialize connection.

---
![Connected Successfully](src\main\resources\images\UI_connected.png)
- After connected and login successfully to server, all features are enabled.
- `Server File Panel`: user can change directory to sub-folders by double-click mouse, select and deselect items as in `User File Panel`. Or can change directory to parents folder by `To Parent Folder` button.
- `Disconnect` button: To disconnect from server.
- `Refresh` button: to reload `Server File Panel` and `User File Panel`.
- `Create Folder` button: to create a new folder in current directory of server.
- `Delete` button: to delete currently selected file in `Server File Panel`.
- `Download` button: to download currently selected file in `Server File Panel` to current directory of user.
- `Upload` button: to download currently selected file in `User File Panel` to current directory of server.

---
![CD in User File Panel](src\main\resources\images\UI_userpanel_changedir.png)
- After click `Change Directory` button, a directory chooser dialog will pop up to choose folder to navigate.
- The dialog always start at user home directory.
- Rows are sorted in descending of last modified time by default (recently modified first). However, in `User File Panel`, the sorting order is type first (folders before names), followed by ascending order of files' name.

---
![Prompt Folder name to Create Remote Directory](src\main\resources\images\UI_prompt_folder_name.png)
- When user click `Create Folder` button, a dialog will pop up to ask for the folder name to be created.

---
![Disconnected from Server](src\main\resources\images\UI_disconnected.png)
- When user click `Disconnect` button, UI will return to the state similar to when it was launched.
- However, text in `Log Console` will not be cleared.