import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

enum ColorEnum{
    Green,Yellow,Red
}

public class Person {
    String name;	//姓名
    int id; //编号
    String phone;  //电话
    ColorEnum QrcodeColor = ColorEnum.Green;//健康码颜色
    boolean checking = false; //正在做核酸？
    List<RnaRecord> records = new ArrayList<>();
    //

    public Person(String name, int id, String phone) {
        this.name = name;
        this.id = id;
        this.phone = phone;
    }

    public void recordResult(String place, long timestamp, Boolean checkresult) {
        if(checkresult){//根据结果赋码色
            QrcodeColor = ColorEnum.Red;
        }else {
            QrcodeColor = ColorEnum.Green;
        }

        checking = false;
        RnaRecord rnaRecord = new RnaRecord(timestamp,place,checkresult);
        records.add(rnaRecord);
    }
    public void print_info(){
        System.out.println("姓名："+name+" 编号："+id+" 电话："+phone+"，核酸记录：");
        for (int i = 0; i < records.size(); i++) {
            System.out.println(records.get(i).toString());
        }
    }

    public void setChecking(boolean checking) {
        this.checking = checking;
    }
    public void reset(){
        this.checking = false;
        this.QrcodeColor = ColorEnum.Green;
    }
}