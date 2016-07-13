import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class LambdaFun {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void quiz(){
        final Set<Function<String, String>> functions = new HashSet<>();

        functions.add(a -> a);
        functions.add(b -> b);

        assertThat(functions.size(), is(2));

        functions.add(createNiceFunction());
        functions.add(createNiceFunction());

        assertThat(functions.size(), is(3));
    }

    public Function<String, String> createNiceFunction(){
        return a -> a;
    }

    @Test
    public void notSerializable() throws IOException {
        expected.expect(NotSerializableException.class);
        final FileOutputStream fos = new FileOutputStream(folder.newFile());
        final Function<String, String> notSerializable = a -> a;
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(notSerializable);
        }
    }

    @Test
    public void serializable() throws IOException {
        final FileOutputStream fos = new FileOutputStream(folder.newFile());
        Function<String, String> serializable = (Function<String, String> & Serializable) a -> a;
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(serializable);
        }
    }

    @Test
    public void javascriptify(){
        final String helloGregor = sayHelloTo().apply("Gregor");

        assertThat(helloGregor, is("Hello, Gregor"));
    }

    public Function<String, String> sayHelloTo(){
        final String hello = "Hello, ";
        return name -> hello + name;
    }
}
