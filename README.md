# SkyWay — Airline & Bus Ticket Management System ✈️🚌

SkyWay is a comprehensive Spring Boot web application designed for managing and reserving tickets for travel. It features a modern, enterprise-grade **Microsoft Fluent UI** interface, interactive 3D aircraft visualization, and robust backend administrative tools.

## Features

- **Modern Microsoft Fluent UI:** Clean, professional, and highly accessible user interface built using Microsoft's design language conventions.
- **3D Aircraft/Bus Viewer:** Interactive 3D seat map visualization built with **Three.js** to allow users to select their seats with an immersive experience.
- **Dynamic Pricing Engine:** Calculates dynamic fares based on various criteria (e.g., weekends, holidays, special deals).
- **Admin Dashboard:** Comprehensive control panel for administrators to manage routes, schedules, buses/aircraft, and view bookings.
- **Real-Time Notifications:** Automatically alerts users of booking confirmations and updates.

## Design Patterns Implemented

This project extensively uses GoF (Gang of Four) Design Patterns to ensure maintainable, scalable, and loosely coupled code:

- **Facade Pattern (`BookingFacade`):** Simplifies the complex process of booking a ticket, updating inventory, and dispatching notifications into a unified, easy-to-use interface.
- **Strategy Pattern (`PricingStrategy`):** Encapsulates different pricing algorithms (Regular, Weekend Surge, Holiday Surge) allowing dynamic swapping of pricing logic at runtime.
- **Observer Pattern (`BookingEventPublisher`):** Allows asynchronous notification dispatches (e.g., Email, SMS) when a booking event occurs without coupling the booking logic to outbound services.
- **Factory Pattern (`BusFactory`):** Centralizes and encapsulates the creation logic of different classes of transportation vehicles.
- **Iterator Pattern (`SeatIterator`):** Provides a standard way to traverse through available and booked seat collections cleanly.
- **Memento Pattern (`ScheduleMemento`):** Used to capture and restore the state of routes and schedules, useful for audit trails or data restoration in the admin panel.

## Tech Stack

- **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Hibernate
- **Database:** Standard SQL (Spring Boot compatible)
- **Frontend:** HTML5, Bootstrap 5, Thymeleaf, Vanilla CSS (Fluent UI Design System), Three.js (for 3D viewer)
- **Build Tool:** Maven

## Getting Started

### Prerequisites
- JDK 17 or higher
- Git

### Installation & Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/imtiaz571/sadp.git
   ```

2. **Navigate into the project directory:**
   ```bash
   cd sadp
   ```

3. **Run the application:**
   Using the bundled Maven Wrapper:
   ```bash
   # On Windows
   .\mvnw.cmd spring-boot:run
   
   # On Mac/Linux
   ./mvnw spring-boot:run
   ```

4. **Access the application:**
   Open your browser and navigate to: [http://localhost:8080](http://localhost:8080)
