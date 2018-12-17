import model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Person> person = getPersonList();

        Stream<Person> personStream = getPersonList().stream();
        Stream<Person> personParalellStream = getPersonList().parallelStream();

        System.out.println("count wit sequential stream" + personStream.count());
        System.out.println("------------------------");
        System.out.println("count with parallel stream" + personParalellStream.count());

        Map<Long, String> personFirstNameMap =
                person.stream()
                        .collect(Collectors.toMap(Person::getId, Person::getFirstName));

        System.out.println("FirstName Map " + personFirstNameMap);



        Map<Long, String> personLastNameMap =
                person.stream()
                        .collect(Collectors.toMap(Person::getId, Person::getLastName));

        System.out.println("LastName Map " + personLastNameMap);


        Map<String, Long> resultPersonMap =
                Stream.concat(personFirstNameMap.entrySet().stream(), personLastNameMap.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getValue,
                        Map.Entry::getKey

                ));
        System.out.println(resultPersonMap);

    }

    private static List<Person> getPersonList() {
        Person martin = new Person();
        Person gustavo = new Person();

        martin.setFirstName("Mike");
        martin.setLastName("Peterson");
        martin.setId(1L);

        gustavo.setId(2L);
        gustavo.setFirstName("Kale");
        gustavo.setLastName("Hagman");

        List<Person> personList = new ArrayList<>();
        personList.add(martin);
        personList.add(gustavo);

        return personList;
    }
}
