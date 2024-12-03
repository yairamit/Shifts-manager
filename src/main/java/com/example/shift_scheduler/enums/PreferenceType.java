// src/main/java/com/example/shift_scheduler/enums/PreferenceType.java
package com.example.shift_scheduler.enums;

/**
 * Represents possible preference types for shifts.
 * Used to indicate an employee's availability and desire for specific shifts.
 */
public enum PreferenceType {
    WANTED,      // Employee wants this shift (Green)
    AVAILABLE,   // Employee is available for this shift (Yellow)
    UNAVAILABLE, // Employee cannot work this shift (Red)
    PREFERRED_SEQUENCE  // Part of a preferred sequence of shifts (Purple)
}