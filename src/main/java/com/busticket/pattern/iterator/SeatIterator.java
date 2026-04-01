package com.busticket.pattern.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * ITERATOR PATTERN: SeatIterator
 * 
 * Custom iterator to traverse a list of seat numbers.
 * Demonstrates the Iterator Pattern by providing a way to
 * sequentially access seats without exposing the underlying structure.
 * 
 * Usage: Used in the booking page to iterate through available seats
 * and display them as radio button options.
 */
public class SeatIterator implements Iterator<Integer> {

    private final List<Integer> seats;
    private int currentIndex = 0;

    public SeatIterator(List<Integer> seats) {
        this.seats = seats;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < seats.size();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more seats available.");
        }
        return seats.get(currentIndex++);
    }

    /**
     * Reset the iterator to the beginning.
     */
    public void reset() {
        currentIndex = 0;
    }

    /**
     * Get the total number of seats.
     */
    public int getTotalSeats() {
        return seats.size();
    }
}
