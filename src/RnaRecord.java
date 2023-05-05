//public class RnaRecord {
//
//}
import java.text.SimpleDateFormat;
import java.util.Date;

public class RnaRecord {
    long timestamp;
    String place;
    Boolean isPositive;
    int id;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RnaRecord(long timestamp, String place, Boolean isPositive) {
        this.timestamp = timestamp;
        this.place = place;
        this.isPositive = isPositive;
    }

    public RnaRecord(long timestamp,  int id,Boolean isPositive) {
        this.timestamp = timestamp;
        this.isPositive = isPositive;
        this.id = id;
    }

    public String toString(){//重写toString方法

        String timestring = sdf.format(new Date(timestamp));
        return timestring + " " + place + (isPositive?" 阳性":" 阴性");
    }
}
