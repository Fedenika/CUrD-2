package PersonDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Main {
    public static volatile List<Person> allPeople = new ArrayList<Person>();

    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    static SimpleDateFormat outputFormater = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    static SimpleDateFormat inputFormater = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        switch (args[0]){
            case "-c":
                synchronized (allPeople) {
                    for (int x = 1; x < args.length; x += 3) {
                        Date date = null;
                        try {
                            date = inputFormater.parse(args[x + 2]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (args[x+1].equals("м")) {
                            allPeople.add(Person.createMale(args[x], date));
                        }
                        if (args[x+1].equals("ж")) {
                            allPeople.add(Person.createFemale(args[x], date));
                        }
                        System.out.println(allPeople.size() - 1);
                    }
                }
                break;
            case "-u":
                synchronized (allPeople) {
                    for (int x = 1; x < args.length; x += 4) {
                        Person currentPerson = allPeople.get(Integer.parseInt(args[x]));
                        currentPerson.setName(args[x + 1]);
                        currentPerson.setSex(getSex(args[x + 2]));
                        Date date2 = null;
                        try {
                            date2 = inputFormater.parse(args[x + 3]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        allPeople.get(Integer.parseInt(args[x])).setBirthDate(date2);
                    }
                }
                break;
            case "-d":
                synchronized (allPeople) {
                    for (int x = 1; x < args.length; ++x) {
                        Person currentPerson1 = allPeople.get(Integer.parseInt(args[x]));
                        currentPerson1.setName(null);
                        currentPerson1.setSex(null);
                        currentPerson1.setBirthDate(null);
                    }
                }
                break;
            case "-i":
                synchronized (allPeople) {
                    for (int x = 1; x < args.length; ++x) {
                        Person currentPerson2 = allPeople.get(Integer.parseInt(args[x]));
                        System.out.print(currentPerson2.getName() + " ");
                        if (currentPerson2.getSex().equals(Sex.MALE)) {
                            System.out.println("м ");
                        }
                        if (currentPerson2.getSex().equals(Sex.FEMALE)) {
                            System.out.println("ж ");
                        }
                        if (currentPerson2.getSex() == null) {
                            System.out.println("null ");
                        }
                        System.out.println(outputFormater.format(currentPerson2.getBirthDate()));
                    }
                }
                break;
        }
    }

    public static Sex getSex(String sex) {
        return sex.equals("м") ? Sex.MALE : Sex.FEMALE;
    }
}
