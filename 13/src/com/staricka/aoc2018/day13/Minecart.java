package com.staricka.aoc2018.day13;

public class Minecart {
    int x;
    int y;
    CartHeading cartHeading;
    IntersectionDecision intersectionDecision;

    public Minecart(int x, int y, CartHeading cartHeading) {
        this.x = x;
        this.y = y;
        this.cartHeading = cartHeading;
        intersectionDecision = IntersectionDecision.TURN_LEFT;
    }

    public String getCoords() {
        return x + "," + y;
    }

    public void processTick(TrackSegment currentTrack) {
        switch (currentTrack.trackType) {
            case UP_DOWN: {
                if(cartHeading == CartHeading.UP) {
                    y--;
                    return;
                } else if (cartHeading == CartHeading.DOWN) {
                    y++;
                    return;
                } else {
                    throw new IllegalStateException();
                }
            }
            case LEFT_RIGHT: {
                if(cartHeading == CartHeading.LEFT) {
                    x--;
                    return;
                } else if (cartHeading == CartHeading.RIGHT) {
                    x++;
                    return;
                } else {
                    throw new IllegalStateException();
                }
            }
            case DIAGONAL_UP: {
                if(cartHeading == CartHeading.UP) {
                    x++;
                    cartHeading = CartHeading.RIGHT;
                    return;
                } else if (cartHeading == CartHeading.DOWN) {
                    x--;
                    cartHeading = CartHeading.LEFT;
                    return;
                } else if (cartHeading == CartHeading.LEFT) {
                    y++;
                    cartHeading = CartHeading.DOWN;
                    return;
                } else if (cartHeading == CartHeading.RIGHT) {
                    y--;
                    cartHeading = CartHeading.UP;
                    return;
                }
            }
            case DIAGONAL_DOWN: {
                if(cartHeading == CartHeading.UP) {
                    x--;
                    cartHeading = CartHeading.LEFT;
                    return;
                } else if (cartHeading == CartHeading.DOWN) {
                    x++;
                    cartHeading = CartHeading.RIGHT;
                    return;
                } else if (cartHeading == CartHeading.LEFT) {
                    y--;
                    cartHeading = CartHeading.UP;
                    return;
                } else if (cartHeading == CartHeading.RIGHT) {
                    y++;
                    cartHeading = CartHeading.DOWN;
                    return;
                }
            }
            case INTERSECTION: {
                cartHeading = intersectionDecision.processTurn(cartHeading);
                intersectionDecision = intersectionDecision.getNext();
                switch (cartHeading) {
                    case LEFT: {
                        x--;
                        return;
                    }
                    case RIGHT: {
                        x++;
                        return;
                    }
                    case UP: {
                        y--;
                        return;
                    }
                    case DOWN: {
                        y++;
                        return;
                    }
                }
            }
        }
    }
}
