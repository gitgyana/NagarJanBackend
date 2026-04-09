# Project README

## Team Details

* **Team Number:** 21
* **Team Name:** SYNTX
* **Team Members:**

  * Gyana Priyadarshi
  * Sarojini Maharana

---

## Selected Problem Statement

**PS-01: Public Grievance Prioritisation Engine for Municipal Bodies**

Municipal corporations receive hundreds of grievances daily across multiple channels such as mobile apps, emails, phone calls, and social media. Currently, most grievances are handled on a *first-come-first-served* basis, which fails to account for urgency, severity, or impact.

This project aims to build an intelligent system that:

* Ingests grievances from multiple sources
* Automatically classifies them by type and severity
* Generates a **daily prioritized work-order list** for field teams

The prioritization considers:

* Issue age
* Recurrence frequency
* Affected population density

---

## Key Features

### Multi-Source Data Ingestion

* Collects complaints from apps, emails, calls, and social media
* Normalizes and aggregates data into a unified format

### Deduplication Engine

* Identifies and merges duplicate complaints
* Prevents redundancy and reduces workload

### Severity Classification Model

* Uses machine learning/NLP to classify grievances
* Assigns severity levels (e.g., Low, Medium, High, Critical)

### Smart Prioritization Algorithm

* Ranks complaints based on:

  * Urgency (time since reported)
  * Frequency (recurring issues)
  * Impact (population density affected)

### Daily Work-Order Generation

* Produces an actionable, ranked task list
* Helps field teams focus on high-impact issues first

### Scalable & Adaptable

* Designed to handle large volumes of complaints
* Can be extended with additional data sources or rules

---

## Technical Challenge

* Handling **multi-source ingestion** with accurate deduplication
* Building a reliable **severity classification model**
* Designing a **multi-factor prioritization algorithm** that balances competing signals into a clear, actionable ranking

---
