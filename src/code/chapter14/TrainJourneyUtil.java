package code.chapter14;

public class TrainJourneyUtil {
    private TrainJourneyUtil() {
    }

    /**
     * Link station b with station a, find onward station from an util next onward is null, then assign b as a new onward for the last station
     * However, it will break the old TrainJourney a, extend its responsibility by including TrainJourney b
     */
    static TrainJourney link(TrainJourney a, TrainJourney b) {
        if (a == null) return b;
        TrainJourney t = a;
        while (t.getOnward() != null) {
            t = t.getOnward();
        }
        t.setOnward(b);
        return a;
    }

    /**
     * Use recursion and will get a copy of TrainJourney a, but still reuse TrainJourney b.
     * So need to be alert that can not modify TrainJourney in the future to avoid breaking existing logic
     */

    static TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.getPrice(), append(a.getOnward(), b));
    }
}
