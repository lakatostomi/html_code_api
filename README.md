# 🧩 HTML Code API

A **Java REST API** that generates HTML documents programmatically. Clients can build an HTML page step-by-step by adding, ordering, and removing supported elements, then retrieve the final rendered document. The project is composed of two modules: a **core HTML generation library** and the **REST API** that exposes it.

> 🔗 This service is used as the middleware layer in the 3-tier GCP deployment described in [rest-api-on-gcp (Version 3)](https://github.com/lakatostomi/rest-api-on-gcp#version-3--3-tier-web-application--cloud-sql), where it receives population data from the backend and returns a fully rendered HTML page as a String.

---

## 📖 Table of Contents

- [Overview](#overview)
- [How It Works](#how-it-works)
- [Supported HTML Elements](#supported-html-elements)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Related Projects](#related-projects)

---

## Overview

The HTML Code API allows clients to dynamically construct HTML documents through a REST interface. Elements are added one at a time, each optionally carrying a single attribute and, for container elements, child elements. Before finalising the document, individual elements can be removed. The final HTML is assembled in the order in which elements were saved.

Only the `<body>` of the HTML document can be edited — the API manages the surrounding document structure automatically.

---

## ⚙️ How It Works

1. **Add elements** — POST individual HTML elements (with optional attributes and children) to build up the document body
2. **Review & remove** — delete any unwanted elements before finalising
3. **Generate** — retrieve the completed HTML document, assembled in the saved order

---

## 🏷 Supported HTML Elements

| Element | Supports Attribute | Supports Child Elements |
|---|---|---|
| `h1` | ✅ (one attribute) | ❌ |
| `p` | ✅ (one attribute) | ✅ |
| `a` | ✅ (one attribute) | ❌ |
| `table` | ✅ (one attribute) | ✅ (`tr`) |
| `tr` | ✅ (one attribute) | ✅ (`td`) |
| `td` | ✅ (one attribute) | ❌ |

> **Note:** Each element supports a maximum of **one attribute**.

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Build Tool | Maven |
| Module: Core | `html_code_generator` — HTML generation library |
| Module: API | `rest-api` — Spring Boot REST layer |

---

## 📁 Project Structure
```
html_code_api/
├── html_code_generator/   # Core module: HTML element models and document builder logic
├── rest-api/              # REST API module: Spring Boot controllers and endpoints
├── pom.xml                # Parent Maven POM (multi-module project)
└── README.md
```

The project follows a **multi-module Maven** layout, keeping the HTML generation logic decoupled from the REST layer. The `rest-api` module depends on `html_code_generator` as a library.

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven

### Run Locally

**1. Clone the repository**
```bash
git clone https://github.com/lakatostomi/html_code_api.git
cd html_code_api
```

**2. Build the project**
```bash
mvn clean install
```

**3. Run the REST API**
```bash
cd rest-api
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## 📡 API Endpoints

### Add an element
```
POST /api/elements
```

Request body example (a heading):
```json
{
  "type": "h1",
  "attribute": "class=\"title\"",
  "content": "World Population Data"
}
```

Request body example (a table with children):
```json
{
  "type": "table",
  "attribute": "border=\"1\"",
  "children": [
    {
      "type": "tr",
      "children": [
        { "type": "td", "content": "Country" },
        { "type": "td", "content": "Year" },
        { "type": "td", "content": "Population" }
      ]
    }
  ]
}
```

### Remove an element
```
DELETE /api/elements/{id}
```

### Generate the HTML document
```
GET /api/document
```

Returns the complete HTML page as a `text/html` String, with all saved elements assembled in order inside the `<body>`.

---

## 🔗 Related Projects

| Project | Description |
|---|---|
| [rest-api-on-gcp](https://github.com/lakatostomi/rest-api-on-gcp) | The backend population data API; uses this service as the middleware in its v3 3-tier GCP deployment |
| [restapi_with_fastapi](https://github.com/lakatostomi/restapi_with_fastapi) | A standalone Python/FastAPI version of the population API with its own frontend |
| [3tier-web-app-on-gcp (Terraform)](https://gitlab.com/terraform_projects2/3tier-web-app-on-gcp) | Terraform & GitLab CI for the full 3-tier GCP infrastructure this service is part of |