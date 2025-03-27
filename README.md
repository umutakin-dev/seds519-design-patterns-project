# SEDS519 - SOFTWARE DESIGN PATTERNS - TEAM PROJECT

## TTECH STACK

- FRONTEND
  - PNPM (PACKAGE MANAGER)
  - REACT (FRAMEWORK)
  - FETCH API / AXIOS
- BACKEND
  - MAVEN
  - JAVA
  - SPRING FRAMEWORK (REST API)
  - APACHE POI ? (FILE PARSING)
- SERVICES
  - PDF PARSER
    - FASTAPI
    - UVICORN
    - GOOGLE GEMINI
- DIAGRAMS
  - DRAW.IO
  - EXCALIDRAW.COM
  
## BACKEND

```bash
cd backend
mvn spring-boot:run
```

## PDF SERVICE

```bash
cd services/course-parse/
```

Inside this folder, we need to create a file ".env".
Inside this file, we need to create a variable called "GEMINI_API_KEY" and add our gemini key here.

It will look like this:

```text
GEMINI_API_KEY=<API_KEY>
```

```bash
pip install -r requirements.txt
uvicorn main:app --reload
```

## FRONTEND

### 1. Create React App

```bash
pnpm dlx create-react-app frontend
```

### 2. Start Frontend Server

```bash
cd frontend
pnpm start
```
