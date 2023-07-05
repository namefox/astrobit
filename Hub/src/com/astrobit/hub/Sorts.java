package com.astrobit.hub;

import java.util.Comparator;

public class Sorts {

    public static int MULTIPLIER = -1;

    public static Comparator<Project> defaultComparator() {
        return (o1, o2) -> 0;
    }

    public static Comparator<Project> lastModified() {
        return (o1, o2) -> {
            long time1 = o1.modified().getTime();
            long time2 = o2.modified().getTime();

            if (time1 > time2) return MULTIPLIER;
            if (time1 < time2) return -MULTIPLIER;

            return 0;
        };
    }

    public static Comparator<Project> alphabetical() {
        return (o1, o2) -> {
            String[] split = o1.path().split("/");
            char[] chars1 = split[split.length - 1].toCharArray();

            split = o2.path().split("/");
            char[] chars2 = split[split.length - 1].toCharArray();

            for (int i = 0; i < chars1.length; i++) {
                if (i >= chars2.length) return MULTIPLIER;

                if (chars1[i] == chars2[i]) continue;

                if (chars1[i] > chars2[i]) return -MULTIPLIER;
                if (chars1[i] < chars2[i]) return MULTIPLIER;
            }

            return 0;
        };
    }

    public static Comparator<String> alphabeticalMap() {
        return (one, two) -> {
            String o1 = one == null ? "_Unnamed" : one;
            String o2 = two == null ? "_Unnamed" : two;

            char[] chars1 = o1.toCharArray();
            char[] chars2 = o2.toCharArray();

            for (int i = 0; i < chars1.length; i++) {
                if (i >= chars2.length) return MULTIPLIER;

                if (chars1[i] == chars2[i]) continue;

                if (chars1[i] > chars2[i]) return -MULTIPLIER;
                if (chars1[i] < chars2[i]) return MULTIPLIER;
            }

            return 0;
        };
    }

    public static Comparator<Install> editorVersionInstall() {
        return (o1, o2) -> {
            char[] chars1 = o1.version().toCharArray();
            char[] chars2 = o2.version().toCharArray();

            for (int i = 0; i < chars1.length; i++) {
                if (i >= chars2.length) return MULTIPLIER;

                if (chars1[i] == chars2[i]) continue;

                if (chars1[i] > chars2[i]) return -MULTIPLIER;
                if (chars1[i] < chars2[i]) return MULTIPLIER;
            }

            return 0;
        };
    }

    public static Comparator<Project> editorVersion() {
        return (o1, o2) -> {
            char[] chars1 = o1.editorVersion().toCharArray();
            char[] chars2 = o2.editorVersion().toCharArray();

            for (int i = 0; i < chars1.length; i++) {
                if (i >= chars2.length) return MULTIPLIER;

                if (chars1[i] == chars2[i]) continue;

                if (chars1[i] > chars2[i]) return -MULTIPLIER;
                if (chars1[i] < chars2[i]) return MULTIPLIER;
            }

            return 0;
        };
    }

    public static Comparator<Project> get(int index) {
        switch (index) {
            case 1:
                return alphabetical();
            case 2:
                return lastModified();
            case 3:
                return editorVersion();
        }

        return defaultComparator();
    }
}