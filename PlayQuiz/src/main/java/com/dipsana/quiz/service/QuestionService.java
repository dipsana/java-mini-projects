// Add your questions and answers, here
// src/main/java/com/dipsana/quiz/service/QuestionService.java
package com.dipsana.quiz.service;

import com.dipsana.quiz.model.Question;
import java.util.ArrayList;
import java.util.List;

public final class QuestionService {

    private final List<Question> questions = new ArrayList<>();

    // Constructor to initialize questions
    public QuestionService() {

        // Set 0
        questions.add(new Question(0,
                "Which keyword is used to define a class in Java?",
                "interface",
                "enum",
                "class",
                "method",
                "class"
        ));
        questions.add(new Question(1,
                "Java is primarily which type of programming language?",
                "Functional",
                "Procedural",
                "Object-Oriented",
                "Logical",
                "Object-Oriented"
        ));
        questions.add(new Question(2,
                "Which keyword is used for defining an integer variable in Java?",
                "Int",
                "num",
                "digit",
                "int",
                "int"
        ));
        questions.add(new Question(3,
                "Which component of Java is responsible for running bytecode?",
                "JRE",
                "JVM",
                "JDK",
                "JavaC",
                "JVM"
        ));
        questions.add(new Question(4,
                "Which method serves as the entry point of a Java program?",
                "start",
                "run",
                "main",
                "execute",
                "main"
        ));

        // Set 1
        questions.add(new Question(5,
                "Which method is used to add a new line character at the end of the output in the console?",
                "print()",
                "printf()",
                "println()",
                "log()",
                "println()"
        ));
        questions.add(new Question(6,
                "Is Java a Strictly Typed Language?",
                "Yes",
                "No",
                "Maybe",
                "There is no such thing",
                "Yes"
        ));
        questions.add(new Question(7,
                "What kind of error will be thrown if the main method is not found in the Java program?",
                "Semantic Error",
                "Logical Error",
                "Runtime Error",
                "Compile Error",
                "Runtime Error"
        ));
        questions.add(new Question(8,
                "Which of the following is the valid way to represent a long literal in Java?",
                "123l",
                "0b1010L",
                "123L",
                "All of the above",
                "All of the above"
        ));
        questions.add(new Question(9,
                "What do you mean by type promotion in Java?",
                "Conversion of data from lower to higher data types",
                "Conversion of data from higher to lower data types",
                "Conversion of data from string type to other data types",
                "Conversion of data from object data types to primitive data types",
                "Conversion of data from lower to higher data types"
        ));
        questions.add(new Question(10,
                "Choose an invalid way to declare a character variable in Java.",
                "char c = 'a'",
                "char c = \"a\"",
                "char c = 97",
                "char c = '\\u0061'",
                "char c = \"a\""
        ));
        questions.add(new Question(11,
                "What is the default value assigned to a variable of boolean type?",
                "True",
                "False",
                "0",
                "1",
                "False"
        ));
        questions.add(new Question(12,
                "What is the result of the following code snippet?\n\nclass Main {\n\tpublic static void main(String[] args) {\n\t\tchar ch = 'a';\n\t\tchar result = (char) (ch + 1);\n\t\tSystem.out.println(result);\n\t}\n}\n",
                "a",
                "b",
                "98",
                "Compilation Error",
                "b"
        ));
        questions.add(new Question(13,
                "What is the result of the following code snippet?\n\nclass Main {\n\tpublic static void main(String[] args) {\n\t\tbyte b1=5,b2=6;\n\t\tbyte b3=(byte)(b1+b2);\n\t\tSystem.out.println(b3);\n\t}\n}\n",
                "11",
                "5",
                "Run-time error",
                "Compile-time error",
                "11"
        ));
        questions.add(new Question(14,
                "What does a high-order bit represent for an integer?",
                "sign of integer",
                "magnitude of integer",
                "binary number",
                "nonnegative number",
                "sign of integer"
        ));

        // Set 3
        questions.add(new Question(15,
                "What is the result of the following expression: !(true || false)?",
                "true",
                "false",
                "Error",
                "0",
                "false"
        ));
        questions.add(new Question(16,
                "What will be the value of variables 'a' and 'b' after the execution of the given code?\n\nclass Main {\n\tpublic static void main(String[] args) {\n\t\tint a, b;\n\t\ta=b=10;\n\t\tSystem.out.println(a);\n\t\tSystem.out.println(b);\n\t}\n}\n",
                "compile time error",
                "runtime error",
                "a=0 and b=10",
                "a=10 and b=10",
                "a=10 and b=10"
        ));
        questions.add(new Question(17,
                "Predict the output of the following code:\n\nclass Main {\n\tpublic static void main(String[] args) {\n\t\tint a = 3;\n\t\tint b = 6;\n\t\tint result = (~a & b) | (a & ~b);\n\t\tSystem.out.println(result);\n\t}\n}\n",
                "5",
                "7",
                "0",
                "1",
                "5"
        ));
        questions.add(new Question(18,
                "Which conditional statement is used to check multiple possible values of a variable?",
                "if-else",
                "switch",
                "ternary operator",
                "nested if-else",
                "switch"
        ));
        questions.add(new Question(19,
                "What is the output of the following program in Java?\n\nif(1) {\n\tSystem.out.println(\"Aloo Bhujiya\");\n}\n",
                "Aloo Bhujiya",
                "1",
                "Compile-time error",
                "Run-time error",
                "Compile-time error"
        ));
        questions.add(new Question(20,
                "Predict the output of the code:\n\nint x = 2;\nswitch(x) {\n\tcase 1:\n\n\tcase 2:\n\n\tcase 3:\n\n\t\tSystem.out.println(\"Fly\");\n\t\tbreak;\n\tcase 4:\n\n\t\tSystem.out.println(\"Swim\");\n}\n",
                "Compile-time error",
                "Fly",
                "Swim",
                "No Output",
                "Fly"
        ));
        questions.add(new Question(21,
                "What will be the value of variables 'x' and 'y' from the given code?\n\nint x = 5;\nint y = 10;\nint z = (x++ > 5 && y-- < 10) ? x-- : y;\n",
                "x=7 and y=10",
                "x=6 and y=10",
                "x=5 and y=9",
                "x=5 and y=10",
                "x=6 and y=10"
        ));
        questions.add(new Question(22,
                "Which loop executes the code block based on a condition that is checked before each iteration in Java?",
                "while loop",
                "for loop",
                "do-while loop",
                "Both a and b",
                "Both a and b"
        ));
        questions.add(new Question(23,
                "What is the type of the do-while loop in terms of control flow?",
                "Entry-controlled loop",
                "Exit-controlled loop",
                "Infinite loop",
                "Nested loop",
                "Exit-controlled loop"
        ));
        questions.add(new Question(24,
                "What will be the output of the given code snippet?\n\npublic static void main(String[] args) {\n\tint i, j;\n\ti = 100;\n\tj=300;\n\twhile(++i < --j);\n\tSystem.out.println(i);\n}\n",
                "150",
                "100",
                "200",
                "Compile-time error",
                "200"
        ));

        // Set 3
        questions.add(new Question(25,
                "Does the 'new' keyword allocate memory for an object during runtime in Java?",
                "Yes",
                "No",
                "It doesn't exist",
                "new is not a keyword",
                "Yes"
        ));
        questions.add(new Question(26,
                "Does the main() method be overloaded in Java?",
                "Yes",
                "No",
                "main is not a method",
                "Only in special cases",
                "Yes"
        ));
        questions.add(new Question(27,
                "What will happen if a local variable has the same name as an instance variable within a class?",
                "Local variable hides the instance variable",
                "Instance variable hides the local variable",
                "Local variable executes after instance variable",
                "A compile-time error will occur",
                "Local variable hides the instance variable"
        ));
        questions.add(new Question(28,
                "Which term is used when we can define two or more methods of the same name within the same class?",
                "Method overriding",
                "Method overloading",
                "Method hiding",
                "Method construction",
                "Method overloading"
        ));
        questions.add(new Question(29,
                "What will be printed after the execution of the given code?\n\nclass Message {\n\tpublic void printMessage(int message) {\n\t\t\tSystem.out.println(\"Message primitive: \" + message);\n\t}\n\n\tpublic void printMessage(Integer message) {\n\tSystem.out.println(\"Message object: \" + message);\n\t}\n}\n\nclass Main {\n\tpublic static void main(String[] ars) {\n\t\tint a = 3;\n\t\tMessage ob=new Message();\n\t\tob.printMessage(5);\n\t}\n}\n",
                "Message primitive: 5",
                "Message object: 5",
                "Compile-time error",
                "Run-time error",
                "Message primitive: 5"
        ));
        questions.add(new Question(30,
                "Where are instance variables allocated in memory?",
                "Stack",
                "Heap",
                "Queue",
                "Array",
                "Heap"
        ));
        questions.add(new Question(31,
                "Which subsystem of the JVM is responsible for loading class files?",
                "ClassLoader",
                "JVM Loader",
                "Bytecode Loader",
                "Run-time Loader",
                "ClassLoader"
        ));
        questions.add(new Question(32,
                "What does arr[arr.length-1].length represents in a multidimensional array?Length of the longest column in the array",
                "Length of the longest column in the array",
                "Number of columns in the array",
                "Total number of elements in the array",
                "Length of the last row in the array",
                "Length of the last row in the array"
        ));
        questions.add(new Question(33,
                "What will be printed after the execution of the given code?\n\nclass Main{\n\tpublic static void main(String[] args) {\n\t\tString password[] = { \"XY01\", \"XY02\", \"XY03\", \"XY04\"};\n\t\tString result =\" \";\n\t\tfor(int i = password.length-1; i>=2; i--) {\n\t\t\tresult = result + password[i];\n\t\t}\n\t\tSystem.out.println(result); }\n}\n",
                "XY01XY02",
                "XY03XY02",
                "XY04XY03",
                "Run-time error",
                "XY04XY03"
        ));

        // Set 4
        questions.add(new Question(34,
                "Which access modifier is used to access members of an inherited class when two classes are in different packages?",
                "private",
                "default",
                "protected",
                "static",
                "protected"
        ));
        questions.add(new Question(35,
                "Which principle emphasizes the binding of data and methods within a class?",
                "Polymorphism",
                "Encapsulation",
                "Abstraction",
                "Inheritance",
                "Encapsulation"
        ));
        questions.add(new Question(36,
                "Can the value of a variable be changed if it is initialized with the 'final' keyword?",
                "Yes",
                "Sometimes",
                "No",
                "Only once",
                "No"
        ));
        questions.add(new Question(37,
                "Does the given code defines the valid way to initialize the value to the final variable?\n\nclass MyClass {\n\tprivate final int myVariable;\n\t{\n\t\tmyVariable = 10;\n\t}\n}\n",
                "No",
                "Yes",
                "Compile-time error",
                "Run-time error",
                "Yes"
        ));
        questions.add(new Question(38,
                "Which keyword is used to define a class member that can be accessed without reference to any object?",
                "private",
                "public",
                "final",
                "static",
                "static"
        ));
        questions.add(new Question(39,
                "Which keyword is used to achieve inheritance in Java?",
                "final",
                "extends",
                "this",
                "implements",
                "extends"
        ));
        questions.add(new Question(40,
                "Can a class be a superclass of itself in Java?",
                "Yes",
                "No",
                "I don't know",
                "Java allows circular reference",
                "No"
        ));
        questions.add(new Question(41,
                "Is method shadowing or method hiding possible with the private method?",
                "Yes",
                "No",
                "I don't know",
                "There is no concept as method shadowing",
                "Yes"
        ));
        questions.add(new Question(42,
                "What is the return type of overridden methods?",
                "Covariant Return Types",
                "Different from a superclass",
                "void only",
                "Not specified",
                "Covariant Return Types"
        ));
        questions.add(new Question(43,
                "What interface is implemented by the String, StringBuffer, and StringBuilder classes?",
                "StringSequence",
                "CharSequence",
                "StringManipulator",
                "CharSequenceBuffer",
                "CharSequence"
        ));
        questions.add(new Question(44,
                "What will be the output of the given code snippet?\n\nchar chars[] = {'a', 'b', 'c', 'd', 'e', 'f'};\nString str = new String(chars,1,3);\nSystem.out.println(str);\n",
                "['b', 'c', 'd']",
                "['b', 'c', 'd', 'e']",
                "bcd",
                "bcde",
                "bcd"
        ));
        questions.add(new Question(45,
                "Which constructor of StringBuffer reserves room for 16 characters without reallocation?",
                "StringBuffer()",
                "StringBuffer(int size)",
                "StringBuffer(String str)",
                "StringBuffer(CharSequence chars)",
                "StringBuffer()"
        ));
        questions.add(new Question(46,
                "Which class can be used when multiple threads are involved and thread-safety is required?",
                "StringBuffer",
                "StringBuilder",
                "Both can be used",
                "None of the above",
                "StringBuffer"
        ));
        questions.add(new Question(47,
                "What will be the value of the s1 and s2 variables after the execution of the following code?\n\nclass Main{\n\tpublic static void main(String[] args) {\n\t\tchar ch [] = {'T', 'e', 'l', 'u', 's', 'k', 'o'};\n\t\tString st1 = new String(ch);\n\t\tString st2 = new String(st1);\n\t\tSystem.out.println(st1);\n\t\tSystem.out.println(st2);\n",
                "Telusko and null",
                "Telusko and st1",
                "Telusko and Telusko",
                "Telusko and ['T', 'e', 'l', 'u', 's', 'k', 'o']",
                "Telusko and Telusko"
        ));
        questions.add(new Question(48,
                "Determine the capacity of the StringBuffer before and after the use of trimToSize() method in the given code:\n\nStringBuffer sb = new StringBuffer(\"Java Code\");\nSystem.out.println(sb.capacity());\nsb.trimToSize();\nSystem.out.println(sb.capacity());\n",
                "16 and 16",
                "16 and 9",
                "25 and 9",
                "25 and 16",
                "25 and 9"
        ));
        questions.add(new Question(49,
                "What Java keyword is employed for specifying a default method within a Java interface?",
                "new",
                "override",
                "implements",
                "default",
                "default"
        ));
        questions.add(new Question(50,
                "When implementing a Java interface, methods must possess the following access modifier:",
                "public",
                "abstract",
                "private",
                "protected",
                "public"
        ));
        questions.add(new Question(51,
                "By default, the methods enclosed within a Java interface are characterized as:",
                "abstract and public",
                "synchronized and void",
                "private and static",
                "final and protected",
                "abstract and public"
        ));
        questions.add(new Question(52,
                "What is the output of the following code snippet?\n\ninterface MyCode{\n\tdouble myScore();\n}\n\npublic class Main{\n\tpublic static void main(String[] args) {\n\t\tMyCode myScore;\n\t\tmyCode = () -> 87;\n\t\tSystem.out.println(MyCode.myScore());\n\t}\n}\n",
                "87",
                "87.0",
                "Run-time error",
                "Compile-time error",
                "Compile-time error"
        ));
        questions.add(new Question(53,
                "What is the output of the code?\n\npublic class Main{\n\tint a=5;\n\tA obj =()->{ System.out.println(this.a);};\n\tpublic static void main(String[] args) {\n\t\tMain ob=new Main();\n\t\tob.obj.run();\n\t}\n}\n\ninterface A {\n\t public void run();\n}\n",
                "5",
                "0",
                "Run-time error",
                "Compile-time error",
                "5"
        ));
        questions.add(new Question(54,
                "Comment on the statement: \"The method defined by a lambda expression does not have a name.\"",
                "True",
                "False",
                "Not a valid question",
                "Skip",
                "False"
        ));
        questions.add(new Question(55,
                "What is the output of the code?\n\nclass A {\n\tpublic void run() {\n\t\tSystem.out.println(\"I am running\");\n\t}\n}\n\nclass Outer {\n\tstatic A obj=new A(){};\n\tpublic static void main(String[] args) {\n\t\tobj.run();\n\t}\n}\n",
                "No output",
                "I am running",
                "Run-time error",
                "Compile-time error",
                "I am running"
        ));
        questions.add(new Question(56,
                "What does Inner classes promote in Java?",
                "Composition and aggregation",
                "Inheritance",
                "Polymorphism",
                "Abstraction",
                "Composition and aggregation"
        ));
        questions.add(new Question(57,
                "How do you access the method \"m1()\" of the inner class inside the main method?\n\nclass Outer {\n\tclass Inner {\n\t\tpublic void m1() {\n\t\t\tSystem.out.println(\"inner class instance m1()\");}\n\t}\npublic static void main(String[] args) {}\n}\n",
                "Outer.Inner i = new Outer().Inner();",
                "Inner i = new Outer().new Inner();",
                "Outer.Inner i = new Outer().new Inner();",
                "new Outer().new Inner().m1();",
                "new Outer().new Inner().m1();"
        ));
        questions.add(new Question(58,
                "What is the key requirement for an interface to be considered a functional interface?",
                "It must define at least one abstract method",
                "It must define multiple abstract methods",
                "It must define only one abstract method",
                "It must define a method named \"run\"",
                "It must define only one abstract method"
        ));
        questions.add(new Question(59,
                "Which of the given expressions returns 'true' if the value of the parameter n is even?",
                "(n) -> (n % 2) != 0",
                "(n) -> (n % 2) == 0",
                "() -> (n % 2) == 0",
                "(n % 2) -> (n) == 0",
                "(n) -> (n % 2) == 0"
        ));
        questions.add(new Question(60,
                "Can a subclass reference variable be assigned to a superclass reference variable?",
                "Maybe",
                "No",
                "Yes",
                "abstract classes only",
                "Yes"
        ));
        questions.add(new Question(61,
                "What is the purpose of the lambda operator (->) in Java lambda expressions?",
                "It specifies the required parameters of an expression",
                "It converts an expression to a method with a name",
                "Divides the expression into parameter list and lambda body",
                "It indicates a constant value in expression",
                "Divides the expression into parameter list and lambda body"
        ));
        questions.add(new Question(62,
                "In what order are the constructors executed when a class hierarchy is created?",
                "From subclass to superclass",
                "From superclass to subclass",
                "Any random order",
                "They do not execute in a class hierarchy",
                "From superclass to subclass"
        ));
        questions.add(new Question(63,
                "Can an abstract class be instantiated directly with the new operator in Java?",
                "Yes",
                "No",
                "I'm not sure",
                "Maybe",
                "No"
        ));

        // Set 5
        questions.add(new Question(64,
                "What is the primary purpose of JDBC in Java?",
                "To create web pages",
                "To connect Java applications to databases",
                "To compile Java code",
                "To generate user interfaces",
                "To connect Java applications to databases"
        ));
        questions.add(new Question(65,
                "Which of the following is required to set up PostgreSQL for JDBC?",
                "Installing JDBC UI plugin",
                "Creating a new Java SDK",
                "Installing PostgreSQL and setting up the database",
                "Writing SQL code in JavaScript",
                "Installing PostgreSQL and setting up the database"
        ));
        questions.add(new Question(66,
                "What is the correct order of steps in JDBC programming?",
                "Create Statement -> Load Driver -> Execute Query -> Close Connection",
                "Load Driver -> Establish Connection -> Create Statement -> Execute Query -> Close Connection",
                "Execute Query -> Load Driver -> Create Statement -> Close Connection",
                "Load Driver -> Execute Query -> Close Connection -> Create Statement",
                "Load Driver -> Establish Connection -> Create Statement -> Execute Query -> Close Connection"
        ));
        questions.add(new Question(67,
                "Which external file is needed to connect PostgreSQL with Java using JDBC?",
                "jdbc-utils.jar",
                "pgadmin.jar",
                "mysql-connector.jar",
                "postgresql-<version>.jar",
                "postgresql-<version>.jar"
        ));
        questions.add(new Question(68,
                "Which method is used to establish a connection between Java and the database?",
                "Driver.connect()",
                "DriverManager.getConnection()",
                "Connection.create()",
                "SQL.connect()",
                "DriverManager.getConnection()"
        ));
        questions.add(new Question(69,
                "Which JDBC interface is used to execute SQL queries and retrieve results?",
                "ResultSet",
                "Statement",
                "Connection",
                "DriverManager",
                "Statement"
        ));
        questions.add(new Question(70,
                "What does ResultSet in JDBC represent?",
                "A database connection",
                "An SQL statement",
                "A set of database drivers",
                "A table of data representing the result of a query",
                "A table of data representing the result of a query"
        ));
        questions.add(new Question(71,
                "Which CRUD operation does executeUpdate() typically support in JDBC?",
                "INSERT, UPDATE, DELETE",
                "SELECT",
                "CONNECT",
                "EXECUTE",
                "INSERT, UPDATE, DELETE"
        ));
        questions.add(new Question(72,
                "Why is using Statement in JDBC considered risky?",
                "It cannot execute SQL queries",
                "It only works with PostgreSQL",
                "It is vulnerable to SQL injection attacks",
                "It is deprecated in newer JDBC versions",
                "It is vulnerable to SQL injection attacks"
        ));
        questions.add(new Question(73,
                "What is the main benefit of using PreparedStatement over Statement?",
                "Easier syntax",
                "It reduces network traffic",
                "It prevents SQL injection and improves performance",
                "It improves GUI rendering",
                "It prevents SQL injection and improves performance"
        ));

        // Set 6
        questions.add(new Question(74,
                "Do you know, what's the output? [Hint: staticA]\n\nclass A {\n\tpublic void run() {\n\t\tSystem.out.println(\"Run is running\");\n\t}\n}\n\nclass Outer {\n\tstaticA obj=new A(){};\n\tpublic static void main(String[] args) {\n\t\tobj.run();\n\t}\n}\n",
                "No output",
                "Run is running",
                "Run-time error",
                "Compile-time error",
                "Compile-time error"
        ));
        questions.add(new Question(75,
                "Which format specifier is used to print a double value in Java?",
                "%d",
                "%f",
                "%lf",
                "%s",
                "%f"
        ));
        questions.add(new Question(76,
                "What is the output of the code snippet?\n\ninterface MyCode{\n\tdouble myScore();\n}\n\npublic class Main{\n\tpublic static void main(String[] args) {\n\t\tMyCode myScore = () -> 99;\n\t\tSystem.out.printf(\"%d\", myScore.myScore());\n\t\tSystem.out.print(myScore.myScore());\n\t}\n}\n",
                "9999",
                "9999.0",
                "Run-time error",
                "Compile-time error",
                "Run-time error"
        ));
        questions.add(new Question(77,
                "If we replace %d with %.0f in the previous question, what is the output?\n\ninterface MyCode{\n\tdouble myScore();\n}\n\npublic class Main{\n\tpublic static void main(String[] args) {\n\t\tMyCode myScore = () -> 99;\n\t\tSystem.out.printf(\"%.0f\", myScore.myScore());\n\t\tSystem.out.print(myScore.myScore());\n\t}\n}\n",
                "9999",
                "9999.0",
                "Run-time error",
                "Compile-time error",
                "9999.0"
        ));
    }

    // questions getter
    public List<Question> getQuestions() {
        return questions;
    }
}
