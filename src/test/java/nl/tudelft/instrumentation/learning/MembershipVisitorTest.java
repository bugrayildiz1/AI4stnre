package nl.tudelft.instrumentation.learning;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MembershipVisitorTest {

    public CompilationUnit instrument(String code){
        // read string as Java code
        JavaParser parser = new JavaParser();
        ParseResult<CompilationUnit> results = parser.parse(new StringReader(code));
        CompilationUnit unit = results.getResult().get();

        // print code
        System.out.println(unit.toString());

        // instrumentation
        unit.accept(new MembershipVisitor(), null);
        return unit;
    }

    @Test
    public void testInstrumentationShouldAddImport(){
        String imprt = "import nl.tudelft.instrumentation.learning.*;";
        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), imprt);
        assertTrue(unit.toString().contains(imprt));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldCreatCallMethod(){
        String call = "public Void call()";
        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), call);
        assertTrue(unit.toString().contains(call));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldInsertImplements(){
        String implement = "public class Test implements CallableTraceRunner<Void>";
        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), implement);
        assertTrue(unit.toString().contains(implement));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldAddSequence(){
        String sequence = "public String[] sequence";
        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), sequence);
        assertTrue(unit.toString().contains(sequence));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldAddSetSequence(){
        String setSequence = "public void setSequence(String[] trace)";
        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), setSequence);
        assertTrue(unit.toString().contains(setSequence));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldAddImportForCallable(){
        String importCallable = "import nl.tudelft.instrumentation.runner.CallableTraceRunner;";
        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b) && a.equals(b) && a.equals(b) && a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), importCallable);
        assertTrue(unit.toString().contains(importCallable));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldCreateCallMethod(){
        String callMethod = "public Void call() {\n" +
                "        Test cp = new Test();\n" +
                "        for (String s : sequence) {\n" +
                "            try {\n" +
                "                cp.calculateOutput(s);\n" +
                "            } catch (Exception e) {\n" +
                "                LearningTracker.output(\"Invalid input: \" + e.getMessage());\n" +
                "            }\n" +
                "            LearningTracker.processedInput();\n" +
                "        }\n" +
                "        return null;\n" +
                "    }";

        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b) && a.equals(b) && a.equals(b) && a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), callMethod);
        assertTrue(unit.toString().contains(callMethod));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldCreateSetSequenceMethod(){
        String setSequenceMethod = "public void setSequence(String[] trace) {\n" +
                "        sequence = trace;\n" +
                "    }";

        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b) && a.equals(b) && a.equals(b) && a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), setSequenceMethod);
        assertTrue(unit.toString().contains(setSequenceMethod));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldCreateSequenceAttribute(){
        String sequenceAttribute = "public String[] sequence;";

        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        if (a.equals(b) && a.equals(b) && a.equals(b) && a.equals(b))\n")
                .append("           return \"INVALID\";\n" )
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), sequenceAttribute);
        assertTrue(unit.toString().contains(sequenceAttribute));
        assertEquals(1, count);
    }

    @Test
    public void testInstrumentationShouldCreateRunCall(){
        String runCall = "LearningTracker.run(eca.inputs, eca);";

        StringBuilder builder = new StringBuilder();
        builder.append("public class Test {\n")
                .append("    public static void main(String[] args) {\n")
                .append("        Test eca = new Test();\n")
                .append("    }\n")
                .append("\n")
                .append("}");

        String code = builder.toString();
        CompilationUnit unit = instrument(code);
        System.out.println(unit.toString());

        int count = StringUtils.countMatches(unit.toString(), runCall);
        assertTrue(unit.toString().contains(runCall));
        assertEquals(1, count);
    }



}

