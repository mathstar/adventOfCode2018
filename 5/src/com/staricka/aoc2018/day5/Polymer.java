package com.staricka.aoc2018.day5;

public class Polymer {
    String value;

    public Polymer(String value) {
        this.value = value;
    }

    public void processReaction() {
        boolean reacted;
        do {
            reacted = processReactionPhase();
        } while (reacted);
    }

    private boolean processReactionPhase(){
        char[] units = value.toCharArray();
        StringBuilder postReaction = new StringBuilder();
        boolean reacted = false;

        for(int i = 0; i < units.length; i++) {
            char current = units[i];

            if (i == units.length - 1) {
                postReaction.append(current);
                continue;
            }

            char next = units[i+1];

            if(current != next && String.valueOf(current).toLowerCase().equals(String.valueOf(next).toLowerCase())) {
                reacted = true;
                i++;
            } else {
                postReaction.append(current);
            }
        }
        value = postReaction.toString();
        return reacted;
    }
}
