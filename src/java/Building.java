public class Building {
    public String number;
    public String address;
    public String snapshot;
    public String appellation;
    public int floorsCount;
    public int prefixCode;
    public String type;
    public int id;
    public String constructionYear;

    public Building (String number, String address, String snapshot, String appellation, int floorsCount,
                     int prefixCode, String type, int id, String constructionYear){
        this.number = number;
        this.address = address;
        this.snapshot = snapshot;
        this.appellation = appellation;
        this.floorsCount = floorsCount;
        this.prefixCode = prefixCode;
        this.type = type;
        this.id = id;
        this.constructionYear = constructionYear;
    }
}
