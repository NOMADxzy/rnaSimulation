import java.util.Random;


public class Main {
    static int total = 2;
    static int daynum = 1;
    static String[] working_period = new String[]{"7:00","8:00","9:00","10:00","11:00","12:00","1:00","2:00","3:00",
            "4:00","5:00","6:00"};
    static int num_of_people = 10000;
    static int num_of_place = 20;
    static Person[] persons = new Person[num_of_people];
    static CheckPlace[] places = new CheckPlace[num_of_place];

    static void run_check(){
        System.out.println("==========第"+daynum+"天==========");
        for (int i = 0; i < working_period.length; i++) {//每个小时段
            System.out.println("----------"+working_period[i]+"----------");
            for (int j = 0; j < num_of_place; j++) {
                CheckPlace checkPlace = places[j];
                for (int k = 0; k < checkPlace.speed; k++) {//每小时检测speed个人
                    checkPlace.check();
                }
                checkPlace.print_info();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("检测到的阳性人员如下：");
        for (int i = 0; i < num_of_people; i++) {
            Person p = persons[i];
            if(p.QrcodeColor==ColorEnum.Red){
                p.print_info();
            }
        }
    }

    static void random_distribute(){ //随机分配去向
        for (int i = 0; i < num_of_place; i++) {
            places[i].reset();
        }

        Random random = new Random();
        for (int i = 0; i < num_of_people; i++) {
            Person p = persons[i];
            p.reset();
            CheckPlace place = places[random.nextInt(num_of_place)]; //随机选择一个去向
            place.addPerson(p);
        }
    }

    public static void main(String[] args) {



        //随机产生若干人
        for (int i = 0; i < num_of_people; i++) {
            Person p = new Person(tools.getFullName(),i,tools.createMobile());
            persons[i] = p;
        }

        //随机产生若干检测点
        int avg = num_of_people/num_of_place;
        Random random = new Random();
        for (int i = 0; i < num_of_place; i++) {

            int volume = avg - avg/8 + random.nextInt(avg/4);
            int speed = random.nextInt(30)+20; //最低20人，最高50人
            CheckPlace place = new CheckPlace("机构"+(i+1),volume,speed);
            places[i] = place;
        }


        for (daynum = 1; daynum < total+1; daynum++) {//连续n天检测
            random_distribute();
            run_check();
        }
    }
}
