# SEDS519 - SOFTWARE DESIGN PATTERNS - TEAM PROJECT

## Table of Contents

About the Project

Features

Design Patterns

Technologies Used

Setup and Running the Application

API Endpoints

Contributors

License

## About the Project

This project implements a Spring Boot backend application responsible for managing and generating weekly course schedules. It supports reading course data from various file formats (Excel, PDF, TXT) and dynamically generates schedules based on course type (undergraduate or graduate). The application exposes RESTful APIs to display these schedules in an HTML format.

The core idea is to provide a flexible and extensible system for handling course scheduling, demonstrating various design patterns.

## Features

Multi-format Schedule Reading: Reads course data from:

- Excel (.xlsx): Parses course information from a predefined Excel file.

- PDF (.pdf): Integrates with an external FastAPI service (http://localhost:8000/process-static-pdf/) to extract course data from PDF documents.

- TXT (.txt): Reads course data from multiple .txt files located in the sources/ directory, associating courses with instructors based on filenames (e.g., AYigit. txt implies instructor "AYigit").

### Dynamic Schedule Generation:

- Generates separate schedules for Undergraduate and Graduate courses.

- Applies scheduling logic to prevent time overlaps (NoOverlapStrategy).

- Courses are sorted by day and time for a clear schedule display.

### Web API for Schedule Display:

- Provides an endpoint (/file) to fetch the full course schedule as an HTML table based on the input file type.

- Provides an endpoint (/generateSchedule) to generate and display filtered schedules (undergraduate or graduate) as HTML tables.

## Design Patterns

This project leverages several object-oriented design patterns to achieve flexibility, maintainability, and extensibility:

### Factory Method Pattern:

- ScheduleFactory (Abstract Creator): Defines the interface for creating Scheduler objects and the build method for scheduling.

- GraduateScheduleFactory & UndergraduateScheduleFactory (Concrete Creators):
  Implement the createScheduler method to produce specific Scheduler instances (e.g: GraduateScheduleFactory creates a LoggingScheduler wrapping a BasicScheduler). They also define filtering logic (accept method) for graduate and undergraduate courses respectively, based on the course name's numeric part (e.g: "CENG632" is graduate if the first digit of the number is 5 or greater).

- **ADVANTAGE**: Decouples the client code from the concrete types of schedulers and course filtering logic, making it easy to add new scheduling types without modifying existing code.

### Adapter Pattern:

- AdapterFileReader: Defines the standard readSchedule() method that all file readers must implement.

- ExcelReader, PdfReader, TxtReader (Adapters): Implement AdapterFileReader to read course data from their respective formats, adapting their specific reading mechanisms to the common interface.

- FileReaderConverter: Uses the AdapterFileReader interface to get the correct reader based on fileType parameter.

- **ADVANTAGE**: Allows different file reading implementations to be used interchangeably through a common interface, promoting reusability.

### Decorator Pattern:

- Scheduler (Interface): Defines the schedule and getCourses methods.

- BasicScheduler (Implements Scheduler): Provides the core scheduling logic (adding courses if a time slot is valid according to the given TimeSelectionStrategy).

- SchedulerDecorator (Abstract Decorator): Maintains a reference to a Scheduler object and implements the Scheduler interface.

- LoggingScheduler (Concrete Decorator): Extends SchedulerDecorator and adds logging functionality before delegating the schedule call to the wrapped Scheduler.

- **ADVANTAGE**: Allows dynamic addition of responsibilities (like logging) to Scheduler objects without altering their core functionality.

### Strategy Pattern:

- TimeSelectionStrategy (Strategy Interface): Declares the isValidTimeSlot method.

- NoOverlapStrategy (Concrete Strategy): Implements isValidTimeSlot to ensure no time conflicts for a course (same day and time).

- MaxCoursesPerDayStrategy (Concrete Strategy - example of extension): (Though not directly used in the current ScheduleFactory implementations, it's provided as an example of another strategy). It would implement isValidTimeSlot to enforce a maximum number of courses per day.

- **ADVANTAGE**: Encapsulates scheduling algorithms into separate classes, making them interchangeable and allowing new scheduling rules to be introduced easily without changing the BasicScheduler class.

## Technologies Used

- Java
- Spring Boot: For building the RESTful backend application.
- Maven: For dependency management and project build.
- Apache POI: For reading .xlsx Excel files.
- Jackson: For JSON processing (used in PdfReader to parse FastAPI responses).
- HttpClient: For making HTTP requests to the FastAPI service.
- HTML: For rendering the schedule directly in the browser.

## Setup and Running the Application

### Prerequisites:

-Java 17
-Maven
-(Optional, for PDF reading) A running FastAPI service at http://localhost:8000/process-static-pdf/ that processes PDF files and returns course data in JSON format. The PdfReader class is configured to call this endpoint.

### Steps to Run

Clone the repository:

```bash
git clone https://github.com/umutakin-dev/seds519-design-patterns-project.git
```

Navigate into the backend directory:

```bash
cd backend
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The application will start on port http://localhost:8080.

## API Endpoints

1. Get Schedule as HTML
   Retrieves a course schedule from a specified file type and renders it as an HTML table.
   Endpoint: GET /file

Example Usage:
http://localhost:8080/file?fileType=excel
http://localhost:8080/file?fileType=pdf (Requires FastAPI service to be running)

Response: HTML content representing the weekly course schedule.

2. Generate and Get Filtered Schedule as HTML
   Generates a schedule based on a specific class type (undergraduate or graduate) by reading from .txt files and applies scheduling logic.

Endpoint: GET /generateSchedule

Example Usage:
http://localhost:8080/generateSchedule?classType=undergraduate
http://localhost:8080/generateSchedule?classType=graduate

Response: HTML content representing the filtered and scheduled courses.

## Contributors

- Umut Akın
- Barış Yenigün
- Naveen Akbar

## License

This project was developed as coursework for _SEDS519 - Software Design Patterns_ at university and is for educational use only.
