import model.Person;
import sun.text.normalizer.UTF16;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

        System.out.println("Compress string 1: " + compressStr("112asdasd   123  ! [] {}sdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasddddddddddddddddddddasdasdsdaasdasdasddddddddddddasdasdddddddddddddddddddd"));
        System.out.println("Compress string 2: " + compressStr("1234"));

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

    private static String compressStr(String stringToCompress) {
        try {
            // Encode a String into bytes
            String inputString = stringToCompress;
            byte[] input = inputString.getBytes("UTF-8");

            // Compress the bytes
            byte[] output = new byte[100];
            Deflater compresser = new Deflater();
            compresser.setInput(input);
            compresser.finish();
            int compressedDataLength = compresser.deflate(output);
            System.out.println(output);
            String output1 = new String(output);
            System.out.println(compressedDataLength);
            compresser.end();

            // Decompress the bytes
            Inflater decompresser = new Inflater();
            decompresser.setInput(output1.getBytes(StandardCharsets.UTF_8), 0, compressedDataLength);
            byte[] result = new byte[100];
            int resultLength = decompresser.inflate(result);
            decompresser.end();

            // Decode the bytes into a String
            String outputString = new String(result, 0, resultLength, "UTF-8");
            return outputString;
        } catch (java.io.UnsupportedEncodingException ex) {
            // handle
        } catch (java.util.zip.DataFormatException ex) {
            // handle
        }
        return null;
    }

}
