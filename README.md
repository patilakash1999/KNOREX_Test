


 #VAST XML Parser

This application parses VAST XML data, converts it into a structured model, and stores it in a MySQL database. It also supports querying the stored data.
Features

    Parse VAST XML data.
    Store parsed data into a MySQL database.
    Query stored data by ID.
    Export data as JSON.

Technologies

    Backend: Java
    Database: MySQL

Prerequisites

    Java 11 or higher
    MySQL 8.0 or higher
    MySQL Connector/J  jar file for Java














Setup
 Configure MySQL Database

    Create the Database and Tables:

    Run the following SQL script in your MySQL client (e.g., MySQL Workbench):

    sql

CREATE DATABASE vast_db;

USE vast_db;

CREATE TABLE vast (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vast_version VARCHAR(10),
    ad_id VARCHAR(255),
    ad_title VARCHAR(255),
    ad_description TEXT
);

CREATE TABLE impression (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vast_id INT,
    impression_id VARCHAR(255),
    impression_url TEXT,
    FOREIGN KEY (vast_id) REFERENCES vast(id)
);

CREATE TABLE creatives (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vast_id INT,
    creative_id VARCHAR(255),
    duration INT,
    FOREIGN KEY (vast_id) REFERENCES vast(id)
);

CREATE TABLE companions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creative_id INT,
    companion_id VARCHAR(255),
    width INT,
    height INT,
    type VARCHAR(255),
    source TEXT,
    click_through_url TEXT,
    FOREIGN KEY (creative_id) REFERENCES creatives(id)
);

CREATE TABLE tracking_events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creative_id INT,
    event_type VARCHAR(255),
    event_url TEXT,
    FOREIGN KEY (creative_id) REFERENCES creatives(id)
);

CREATE TABLE video_clicks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creative_id INT,
    click_id VARCHAR(255),
    click_url TEXT,
    FOREIGN KEY (creative_id) REFERENCES creatives(id)
);

CREATE TABLE media_files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creative_id INT,
    type VARCHAR(255),
    bitrate INT,
    width INT,
    height INT,
    source TEXT,
    FOREIGN KEY (creative_id) REFERENCES creatives(id)
);

Update Database Connection Details:
Edit the VastDatabaseHandler class to match your MySQL credentials and database URL.
