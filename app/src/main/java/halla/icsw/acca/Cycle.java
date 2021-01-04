package halla.icsw.acca;

class Cycle {
    public Cycle() {
    }

    String engineoilCycle(double distance) {
        double engineoil = 40000d;
        String remainDistance = String.valueOf(engineoil - distance);
        return remainDistance;
    }

    int engineoilState(double distance) {
        int state;

        if (distance / 40000 * 100 > 66)
            state = 0;
        else if (distance / 40000 * 100 <= 66 && distance / 40000 * 100 > 33)
            state = 1;
        else
            state = 2;

        return state;
    }

    String autooilCycle(double distance) {
        double autooil = 40000d;
        String remainDistance = String.valueOf(autooil - distance);
        return remainDistance;
    }

    int autooilState(double distance) {
        int state;

        if (distance / 40000 * 100 > 66)
            state = 0;
        else if (distance / 40000 * 100 <= 66 && distance / 40000 * 100 > 33)
            state = 1;
        else
            state = 2;

        return state;
    }

    String poweroilCycle(double distance) {
        double poweroil = 40000d;
        String remainDistance = String.valueOf(poweroil - distance);
        return remainDistance;
    }

    int poweroilState(double distance) {
        int state;

        if (distance / 40000 * 100 > 66)
            state = 0;
        else if (distance / 40000 * 100 <= 66 && distance / 40000 * 100 > 33)
            state = 1;
        else
            state = 2;

        return state;
    }

    String brakeoilCycle(double distance) {
        double brakeoil = 40000d;
        String remainDistance = String.valueOf(brakeoil - distance);
        return remainDistance;
    }

    int brakeoilState(double distance) {
        int state;

        if (distance / 40000 * 100 > 66)
            state = 0;
        else if (distance / 40000 * 100 <= 66 && distance / 40000 * 100 > 33)
            state = 1;
        else
            state = 2;

        return state;
    }

    String brakepadeCycle(double distance) {
        double brakepade = 20000d;
        String remainDistance = String.valueOf(brakepade - distance);
        return remainDistance;
    }

    int brakepadeState(double distance) {
        int state;

        if (distance / 20000 * 100 > 66)
            state = 0;
        else if (distance / 20000 * 100 <= 66 && distance / 20000 * 100 > 33)
            state = 1;
        else
            state = 2;

        return state;
    }

    String timingbeltCycle(double distance) {
        double timingbelt = 80000d;
        String remainDistance = String.valueOf(timingbelt - distance);
        return remainDistance;
    }

    int timingbeltState(double distance) {
        int state;

        if (distance / 80000 * 100 > 66)
            state = 0;
        else if (distance / 80000 * 100 <= 66 && distance / 80000 * 100 > 33)
            state = 1;
        else
            state = 2;

        return state;
    }
}
