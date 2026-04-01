package com.busticket.pattern.iterator;

import java.util.List;

/**
 * ITERATOR PATTERN: SeatCollection
 * 
 * An iterable collection that provides a SeatIterator.
 * Encapsulates the seat data and provides a clean interface
 * for iterating through available seats.
 */
public class SeatCollection implements Iterable<Integer> {

    private final List<Integer> availableSeats;

    public SeatCollection(List<Integer> availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public SeatIterator iterator() {
        return new SeatIterator(availableSeats);
    }

    public int size() {
        return availableSeats.size();
    }

    public List<Integer> getSeats() {
        return availableSeats;
    }

    public boolean isEmpty() {
        return availableSeats.isEmpty();
    }
}
