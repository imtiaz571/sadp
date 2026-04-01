# 🎨 Design Patterns Applied in Bus Ticket Management System

This document explains **how and where** each design pattern was applied in the project.
These are not just "demo" implementations — they are integral to the business logic.

---

## 1. 🏭 Factory Pattern — `BusFactory`

**📁 Location:** `com.busticket.pattern.factory.BusFactory`

**What it does:**  
Creates different types of Bus objects (**AC**, **LUXURY**, **REGULAR**) with pre-configured defaults.

**How it works:**
- The `BusFactory.createBus(type, name, number)` method uses a `switch` expression on the bus type.
- Each type has factory-configured defaults:
  - **AC**: 36 seats, amenities include AC, Reclining Seats, Charging Ports
  - **LUXURY**: 24 seats, amenities include WiFi, Entertainment, Snacks, Blankets
  - **REGULAR**: 48 seats, basic amenities (Fan)

**Where it's used:**
- `BusService.createBus()` — delegates bus creation to the factory.
- `AdminController.addBus()` — when adding a new bus from the admin panel.

**Why:**  
Encapsulates object creation logic. Adding a new bus type (e.g., SLEEPER) only requires adding one method to the factory.

---

## 2. 📊 Strategy Pattern — `PricingStrategy`

**📁 Location:** `com.busticket.pattern.strategy.*`

**What it does:**  
Implements dynamic pricing. The fare changes based on the travel date:
- **Regular** (weekdays): No surcharge (1x)
- **Weekend** (Sat/Sun): +25% surge
- **Holiday** (national holidays): +50% surge

**Components:**
| Class | Role |
|---|---|
| `PricingStrategy` | Interface defining `calculateFare(baseFare)` |
| `RegularPricingStrategy` | No surcharge |
| `WeekendSurgePricingStrategy` | 1.25x multiplier |
| `HolidaySurgePricingStrategy` | 1.50x multiplier |
| `PricingContext` | Selects strategy based on date |

**Where it's used:**
- `BookingFacade.bookTicket()` — calculates the final fare during booking.
- `SearchController.showResults()` — displays dynamic pricing on search results.
- `BookingController.showBookingPage()` — shows fare with pricing badge.

**Why:**  
Eliminates messy `if-else` chains for pricing. New pricing rules can be added by implementing `PricingStrategy`.

---

## 3. 🏢 Facade Pattern — `BookingFacade`

**📁 Location:** `com.busticket.pattern.facade.BookingFacade`

**What it does:**  
Provides a **single method** `bookTicket()` that handles the complex booking workflow:

```
1. Fetch the schedule
2. Check seat availability
3. Check if specific seat is taken
4. Calculate dynamic fare (Strategy Pattern)
5. Create and save the booking
6. Update available seat count
7. Notify observers (Observer Pattern)
```

**Where it's used:**
- `BookingController.processBooking()` — **one call** handles everything.

**Why:**  
The controller doesn't need to know about seat checking, pricing, or notifications. It just calls `bookingFacade.bookTicket()`.

---

## 4. 👁️ Observer Pattern — Notification System

**📁 Location:** `com.busticket.pattern.observer.*`

**What it does:**  
When a booking is confirmed, all registered **observers** are notified automatically.

**Components:**
| Class | Role |
|---|---|
| `BookingObserver` | Interface with `onBookingCreated(booking)` |
| `EmailNotificationObserver` | Saves an "email" notification to DB |
| `SMSNotificationObserver` | Saves an "SMS" notification to DB |
| `BookingEventPublisher` | Subject — holds observer list and triggers notifications |

**How it works:**
1. Spring auto-injects all `BookingObserver` beans into `BookingEventPublisher`.
2. When `BookingFacade.bookTicket()` completes, it calls `eventPublisher.notifyObservers()`.
3. Each observer creates a `Notification` record in the database.
4. Users can view their notifications at `/notifications`.

**Why:**  
Decouples booking logic from notification logic. New notification channels (Push, WhatsApp) can be added simply by implementing `BookingObserver`.

---

## 5. 🔒 Singleton Pattern — Spring-Managed Beans

**📁 Location:** All `@Component`, `@Service`, and `@Controller` classes.

**What it does:**  
Spring Boot creates and manages **exactly one instance** of each bean (singleton scope by default).

**Key singletons:**
- `PricingContext` — single pricing context shared across the app
- `BookingEventPublisher` — single event publisher
- `BookingFacade` — single facade instance
- All Service and Repository classes

**Why:**  
Prevents unnecessary object creation, ensures shared state consistency, and conserves memory.

---

## 6. 🔄 Iterator Pattern — `SeatIterator` & `SeatCollection`

**📁 Location:** `com.busticket.pattern.iterator.*`

**What it does:**  
Provides a custom iterator for traversing the list of available seats.

**Components:**
| Class | Role |
|---|---|
| `SeatIterator` | Implements `Iterator<Integer>` for sequential seat access |
| `SeatCollection` | Implements `Iterable<Integer>`, provides `SeatIterator` |

**Where it's used:**
- `ScheduleService.getAvailableSeats()` — builds a `SeatCollection` of available seats.
- `BookingController.showBookingPage()` — passes seat list to Thymeleaf.
- `booking.html` — iterates through seats to render radio buttons.

**How it works:**
1. Total seats come from `bus.getTotalSeats()`.
2. Booked seats are queried from the `bookings` table.
3. Available seats = Total seats - Booked seats.
4. The `SeatCollection` wraps this list and provides a `SeatIterator`.

---

## 7. 🌳 Composite Pattern — Route Management

**📁 Location:** `com.busticket.entity.Route`

**What it does:**  
A **Route** can have:
- **Sub-routes** (child routes): e.g., "Dhaka → Chittagong" can have sub-routes "Dhaka → Comilla" and "Comilla → Chittagong".
- **Stops**: ordered sub-stops within a route.

**How it works:**
- `Route` has a self-referencing `@ManyToOne parentRoute` and `@OneToMany subRoutes`.
- `getTotalDistance()` recursively sums distance from all sub-routes.
- Admin can assign a parent route when creating a route (Composite tree structure).

**Where it's used:**
- `RouteService.addSubRoute()` — creates a sub-route under a parent.
- `admin-route-form.html` — dropdown to select parent route.
- `admin-routes.html` — displays parent route badge.

---

## 8. 📦 Memento Pattern — Schedule Edit History

**📁 Location:** `com.busticket.entity.ScheduleMemento` + `ScheduleService`

**What it does:**  
Before a schedule is edited, its current state is **automatically saved** as a snapshot (memento). Admins can **revert** to the previous state.

**How it works:**
1. `ScheduleService.updateSchedule()` calls `saveState()` first.
2. `saveState()` creates a `ScheduleMemento` from the current `Schedule` and saves it to the DB.
3. `revertToLastState()` fetches the most recent memento and restores the schedule.

**Where it's used:**
- `AdminController.editSchedule()` — auto-saves state before update.
- `AdminController.revertSchedule()` — accessible via the ↺ button on the schedules page.
- `admin-schedule-form.html` — shows memento history table.

---

## 📋 Summary Table

| # | Pattern | Class(es) | Used In |
|---|---------|-----------|---------|
| 1 | **Factory** | `BusFactory` | `BusService`, `AdminController` |
| 2 | **Strategy** | `PricingStrategy`, `PricingContext` | `BookingFacade`, `SearchController` |
| 3 | **Facade** | `BookingFacade` | `BookingController` |
| 4 | **Observer** | `BookingEventPublisher`, `EmailNotificationObserver`, `SMSNotificationObserver` | `BookingFacade` |
| 5 | **Singleton** | All Spring `@Component`/`@Service` beans | Entire application |
| 6 | **Iterator** | `SeatIterator`, `SeatCollection` | `ScheduleService`, `BookingController` |
| 7 | **Composite** | `Route` (self-referencing) | `RouteService`, Admin pages |
| 8 | **Memento** | `ScheduleMemento` | `ScheduleService`, Admin schedules |
