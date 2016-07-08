import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

    @Test public void method_handle_of_value_of_integer() throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodType mt = MethodType.methodType(String.class, int.class);
        final MethodHandle mh = lookup.findStatic(String.class, "valueOf", mt);

        final String result = (String) mh.invoke(1);

        System.out.println(result);
    }

    @Test public void method_handle_of_virtual_method() throws Throwable {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        final MethodHandle mh = lookup.findGetter(Person.class, "name", String.class);

        System.out.println((String) mh.invoke(new Person("Gregor")));
        System.out.println((String) mh.invoke(new Person("Artem")));
    }

    @Test public void method_handle_invoke_dynamic(){

    }

    public class Person{
        public final String name;

        public Person(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }
}
