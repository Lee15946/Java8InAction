package code.chapter14;

public class TrainJourneyUtil {
    public static void main(String[] args) {
        compareLinkAndAppend();
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
     * Use recursion and will generate a copy of TrainJourney a, but still reuse TrainJourney b.
     * So need to be alert that can not modify TrainJourney b in the future to avoid breaking existing logic
     */
    static TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.getStationName(), a.getPrice(), append(a.getOnward(), b));
    }

    public static void compareLinkAndAppend() {
        var xian = new TrainJourney("xian", 20);
        var wuhan = new TrainJourney("wuhan", 30);
        var chengdu = new TrainJourney("chengdu", 40);
        var beijing = new TrainJourney("beijing", 50);
        //Link two station, xian and wuhan, chengdu and beijing
        xian.setOnward(wuhan);
        chengdu.setOnward(beijing);
        //Route: xian->wuhan->chengdu->beijing
        var xianToChengdu = link(xian, chengdu);
        if (xian.equals(xianToChengdu)) {
            System.out.println("Xian has been changed by link");
            System.out.println("Wuhan's next station is " + wuhan.getOnward().getStationName());
        }
        //Reassign each TrainJourney object
        xian = new TrainJourney("xian", 20);
        wuhan = new TrainJourney("wuhan", 30);
        chengdu = new TrainJourney("chengdu", 40);
        beijing = new TrainJourney("beijing", 50);
        xian.setOnward(wuhan);
        chengdu.setOnward(beijing);
        //Route: xian->wuhan->chengdu->beijing
        xianToChengdu = append(xian, chengdu);
        if (!xian.equals(xianToChengdu)) {
            System.out.println("Xian has not been changed by append");
            System.out.println("Wuhan's next station is " + xianToChengdu.getOnward().getOnward().getStationName());
        }
    }
}
