package com.staricka.aoc2018.day4;

import com.staricka.aoc2018.day4.Guard.MinuteResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    private static final Pattern guardPattern = Pattern.compile("Guard #([0-9]+)");
    private static final Pattern dateTimePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}");
    private static final Pattern beginsShiftPattern = Pattern.compile("begins shift");
    private static final Pattern sleepPattern = Pattern.compile("falls asleep");
    private static final Pattern wakePattern = Pattern.compile("wakes up");

    private static final DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final DateFormat shiftDateFormat = new SimpleDateFormat("MM-dd");

    public static void main(String[] args) throws Exception {
        Map<String, Guard> guards = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        List<String> lines = bufferedReader.lines().sorted().collect(Collectors.toList());

        Guard currentGuard;
        Shift currentShift = null;
        Integer sleepStart = null;
        for (String line : lines) {
            Matcher guardMatcher = guardPattern.matcher(line);
            Matcher dateTimeMatcher = dateTimePattern.matcher(line);
            Matcher beginsShiftMatcher = beginsShiftPattern.matcher(line);
            Matcher sleepMatcher = sleepPattern.matcher(line);
            Matcher wakeMatcher = wakePattern.matcher(line);

            dateTimeMatcher.find();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputDateFormat.parse(dateTimeMatcher.group()));

            if (beginsShiftMatcher.find()) {
                guardMatcher.find();
                String id = guardMatcher.group(1);
                currentGuard = guards.computeIfAbsent(id, x -> new Guard(id));

                if (calendar.get(Calendar.HOUR_OF_DAY) != 0) {
                    // arrived before midnight, increment date
                    calendar.roll(Calendar.DAY_OF_MONTH, 1);
                }

                String date = shiftDateFormat.format(calendar.getTime());
                currentShift = new Shift(date);

                currentGuard.addShift(currentShift);
            } else if (sleepMatcher.find()) {
                sleepStart = calendar.get(Calendar.MINUTE);
            } else if (wakeMatcher.find()) {
                currentShift.sleep(sleepStart, calendar.get(Calendar.MINUTE));
                sleepStart = null;
            }
        }

        Guard sleepiestGuard = mostTimeAsleep(guards.values());
        int sleepiestGuardsSleepiestMinute = sleepiestGuard.sleepiestMinute().minute;
        System.out.println("Sleepiest guard: " + sleepiestGuard.id);
        System.out.println("Sleepiest guard's sleepiest minute: " + sleepiestGuardsSleepiestMinute);

        System.out.println();

        MinuteResult sleepiestMinute = sleepiestMinute(guards.values());
        System.out.println("Sleepiest minute's guard: " + sleepiestMinute.guard.id);
        System.out.println("Sleepiest minute: " + sleepiestMinute.minute);
    }

    private static Guard mostTimeAsleep(Collection<Guard> guards) {
        int maxTimeAsleep = 0;
        Guard sleepyGuard = null;

        for (Guard guard : guards) {
            int timeAsleep = guard.shifts.stream().mapToInt(Shift::timeAsleep).sum();
            if (timeAsleep > maxTimeAsleep) {
                maxTimeAsleep = timeAsleep;
                sleepyGuard = guard;
            }
        }

        return sleepyGuard;
    }

    private static MinuteResult sleepiestMinute(Collection<Guard> guards) {
        List<MinuteResult> results =
                guards.stream().map(Guard::sleepiestMinute).sorted(Comparator.comparingInt(MinuteResult::getFrequency))
                        .collect(Collectors.toList());
        return results.get(results.size() - 1);
    }
}
