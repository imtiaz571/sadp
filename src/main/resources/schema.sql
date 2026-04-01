-- =============================================
-- Bus Ticket Management System - SQL Schema
-- Database: MySQL
-- =============================================

CREATE DATABASE IF NOT EXISTS bus_ticket_db;
USE bus_ticket_db;

-- -------------------------------------------
-- 1. Users Table
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100)        NOT NULL,
    email       VARCHAR(150) UNIQUE NOT NULL,
    password    VARCHAR(255)        NOT NULL,
    phone       VARCHAR(20),
    role        VARCHAR(20)         NOT NULL DEFAULT 'USER',
    created_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP
);

-- -------------------------------------------
-- 2. Buses Table
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS buses (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    bus_name     VARCHAR(100) NOT NULL,
    bus_number   VARCHAR(50) UNIQUE NOT NULL,
    bus_type     VARCHAR(30) NOT NULL,           -- AC, LUXURY, REGULAR
    total_seats  INT          NOT NULL,
    amenities    VARCHAR(500),
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- -------------------------------------------
-- 3. Routes Table (Composite Pattern: parent-child)
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS routes (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_name      VARCHAR(150) NOT NULL,
    origin          VARCHAR(100) NOT NULL,
    destination     VARCHAR(100) NOT NULL,
    distance_km     DOUBLE,
    parent_route_id BIGINT NULL,
    FOREIGN KEY (parent_route_id) REFERENCES routes(id) ON DELETE SET NULL
);

-- -------------------------------------------
-- 4. Stops Table (sub-stops for composite routes)
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS stops (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    route_id    BIGINT       NOT NULL,
    stop_name   VARCHAR(100) NOT NULL,
    stop_order  INT          NOT NULL,
    FOREIGN KEY (route_id) REFERENCES routes(id) ON DELETE CASCADE
);

-- -------------------------------------------
-- 5. Schedules Table
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS schedules (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    bus_id          BIGINT      NOT NULL,
    route_id        BIGINT      NOT NULL,
    departure_time  DATETIME    NOT NULL,
    arrival_time    DATETIME    NOT NULL,
    fare            DOUBLE      NOT NULL,
    available_seats INT         NOT NULL,
    status          VARCHAR(20) DEFAULT 'ACTIVE',
    created_at      TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bus_id)   REFERENCES buses(id)  ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES routes(id) ON DELETE CASCADE
);

-- -------------------------------------------
-- 6. Bookings Table
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS bookings (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT       NOT NULL,
    schedule_id     BIGINT       NOT NULL,
    seat_number     INT          NOT NULL,
    passenger_name  VARCHAR(100) NOT NULL,
    total_fare      DOUBLE       NOT NULL,
    booking_status  VARCHAR(20)  DEFAULT 'CONFIRMED',
    payment_status  VARCHAR(20)  DEFAULT 'PAID',
    booking_date    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)     REFERENCES users(id)     ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

-- -------------------------------------------
-- 7. Schedule Mementos Table (Memento Pattern)
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS schedule_mementos (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    schedule_id     BIGINT      NOT NULL,
    bus_id          BIGINT      NOT NULL,
    route_id        BIGINT      NOT NULL,
    departure_time  DATETIME    NOT NULL,
    arrival_time    DATETIME    NOT NULL,
    fare            DOUBLE      NOT NULL,
    available_seats INT         NOT NULL,
    status          VARCHAR(20),
    saved_at        TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

-- -------------------------------------------
-- 8. Notifications Table (Observer Pattern)
-- -------------------------------------------
CREATE TABLE IF NOT EXISTS notifications (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    message     VARCHAR(500) NOT NULL,
    is_read     BOOLEAN      DEFAULT FALSE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- -------------------------------------------
-- Insert Default Admin User (password: admin123)
-- -------------------------------------------
INSERT IGNORE INTO users (full_name, email, password, phone, role)
VALUES ('Admin', 'admin@bus.com', 'admin123', '0000000000', 'ADMIN');
