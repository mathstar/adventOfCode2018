package com.staricka.aoc2018.day9;

/**
 * Basically a doubly-linked list with adjustable "pointer"
 */
public class EfficientCircle {
    Element current;

    public EfficientCircle(Marble initial) {
        Element initialElement = new Element();
        initialElement.value = initial;
        initialElement.next = initialElement;
        initialElement.previous = initialElement;
        current = initialElement;
    }

    public Marble getRelative(int relativeIndex) {
        return seek(relativeIndex).value;
    }

    public void insertRelativeAndMakeCurrent(Marble value, int relativeIndex) {
        Element destination = seek(relativeIndex);
        Element previous = destination.previous;

        Element newElement = new Element();
        newElement.value = value;
        newElement.previous = previous;
        newElement.next = destination;

        destination.previous = newElement;
        previous.next = newElement;

        current = newElement;
    }

    public void removeRelativeAndMakeNextCurrent(int relativeIndex) {
        Element destination = seek(relativeIndex);
        Element previous = destination.previous;
        Element next = destination.next;

        previous.next = next;
        next.previous = previous;

        current = next;
    }

    public void shiftCursor(int relativeIndex) {
        current = seek(relativeIndex);
    }

    private Element seek(int relativeIndex) {
        Element destination = current;
        if(relativeIndex >= 0) {
            for(int i = 0; i < relativeIndex; i++) {
                destination = destination.next;
            }
        } else {
            for(int i = 0; i > relativeIndex; i--) {
                destination = destination.previous;
            }
        }
        return destination;
    }

    private class Element {
        Marble value;
        Element next;
        Element previous;
    }

    @Override
    public String toString() {
        Element toPrint = current;
        while (toPrint.value.getId() != 0) {
            toPrint = toPrint.next;
        }

        StringBuilder stringBuilder = new StringBuilder();
        do {
            if(toPrint == current) {
                stringBuilder.append("(" + toPrint.value.getId() + ") ");
            } else {
                stringBuilder.append(toPrint.value.getId() + " ");
            }
            toPrint = toPrint.next;
        } while (toPrint.value.getId() != 0);

        return stringBuilder.toString();
    }
}
