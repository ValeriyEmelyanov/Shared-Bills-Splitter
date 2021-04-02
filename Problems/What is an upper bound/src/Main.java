// Do not remove imports

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Set;
import java.util.Scanner;

class ListParameterInspector {
    // Do not change the method
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String methodName = scanner.next();

        ListParameterInspector inspector = new ListParameterInspector();
        inspector.printParameterType(new TestClass(), methodName);
    }

    public void printParameterType(TestClass obj, String methodName) throws Exception {
        Type genericReturnType = obj.getClass()
                .getDeclaredMethod(methodName)
                .getGenericReturnType();
        Type actualTypeArgument = ((ParameterizedType) genericReturnType)
                .getActualTypeArguments()[0];
        String name = ((WildcardType) actualTypeArgument)
                .getUpperBounds()[0]
                .getTypeName();
        System.out.println(name);
    }
}

class TestClass {
    public Set<? extends String> someMethod() {
        return Set.of("Hi");
    }
}