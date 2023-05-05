import java.util.ArrayList;
import java.util.List;

enum StatusEnum {
    normal,full,empty;
}
public class CheckPlace {
    String name; //检测点名称
    int volume; //检测容量
    int speed; //检测速度，每小时检测多少人
    int checked; //已检测人数
    StatusEnum status; //检测点状态
    List<Person> queue = new ArrayList<>(); //排队队列
    List<RnaRecord> records = new ArrayList<>(); //记录
    List<Integer> positive = new ArrayList<>(); //阳性人员编号
    List<Integer> negative = new ArrayList<>(); //阴性人员编号


    public CheckPlace(String name, int volume, int speed) {
        this.name = name;
        this.volume = volume;
        this.speed = speed;
        this.checked = 0;
    }

    public void addPerson(Person p){
        queue.add(p);
        p.setChecking(true);
    }

    public void check(){
        if(queue.size()==0){
            status = StatusEnum.empty;
            return;//队列无人，直接返回
        }else if(queue.size()+checked>volume){
            status = StatusEnum.full;
        }else {
            status = StatusEnum.normal;
        }
        if(checked>=volume) return; //无法继续检测了
        Person p = queue.get(0); //取出队伍第一个人做核酸
        queue.remove(0);

        Boolean result = get_check_result();
        if (result){
            positive.add(p.id);
        }else {
            negative.add(p.id);
        }
        long timestamp = System.currentTimeMillis();
        records.add(new RnaRecord(timestamp,p.id,result)); //检测点保存记录
        p.recordResult(name,timestamp,result); //个人保存记录
        checked += 1;

    }

    public int get_remain(){
        return volume - checked;
    }

    boolean get_check_result()//按阴性概率f生成检测结果
    {
        float f = (float) 0.023;
        float fl = (float) Math.random();
        if (fl <= f)
            return true;
        return false;
    }

    public void reset(){//重置
        checked = 0; //已检测人数
        status = StatusEnum.empty; //检测点状态
        queue = new ArrayList<>(); //排队队列
        positive = new ArrayList<>(); //阳性人员编号
        negative = new ArrayList<>(); //阴性人员编号
    }

    public void print_info() {
        String st;
        if (status==StatusEnum.empty){
            st = "无人";
        }else if(status==StatusEnum.full){
            st = "爆满";
        }else {
            st = "正常";
        }
        System.out.println(name+"(容量"+volume+"人)(速度：每小时"+speed+"人) 已检测"+checked+"人，发现阳性"+positive.size()+"人，阴性"+negative.size()+"人，状态："+st+"，"+queue.size()+"人正在排队，还可以检测"+(volume-checked)+"人。");
    }
}
