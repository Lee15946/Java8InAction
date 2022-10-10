package code.chapter14;

public class TrainJourney {
    private final String stationName;
    private final int price;
    private TrainJourney onward;

    public TrainJourney(String stationName, int price) {
        this.stationName = stationName;
        this.price = price;
    }

    public TrainJourney(String stationName, int price, TrainJourney onward) {
        this.stationName = stationName;
        this.price = price;
        this.onward = onward;
    }

    public int getPrice() {
        return price;
    }

    public TrainJourney getOnward() {
        return onward;
    }

    public String getStationName() {
        return stationName;
    }

    public void setOnward(TrainJourney onward) {
        this.onward = onward;
    }
}
