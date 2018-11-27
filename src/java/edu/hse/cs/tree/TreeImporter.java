package edu.hse.cs.tree;
/*
4 bytes - int
8 bytes - double
 */

    /*
    MutableRootNode(Root)
        MutableParentNode(Parent0)
            MutableChildNode(Child00)
            MutableChildNode(Child01)
        MutableParentNode(Parent1)
            MutableChildNode(Child10)
        MutableChildNode(Child0)

     */


// Root ведь обязательно без \t?
// можно ли подгрузить Apache Commons?

import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class TreeImporter
{
    // возвращает число табуляций
    private static int indent(String input)
    {
        return input.length() - input.replace("   ", "").length();
    }

    public static <T> MutableRootNode<T> importMutableTree(String input) { // возвращает корень дерева
        if (input.isEmpty())
            throw new RuntimeException("Empty input!"); // тоже вопрос, нужно ли самим кидать ошибки? и обрабаоывать ли их
        // Число Node (строк)
        //String[] listOfRoot = Arrays.stream(input.split("\n")).filter((x) -> StringUtils.countMatches("\t", x) == 1); // массив строк где только одна табуляция (т.е. только ветви Root'a)

        String[] listOfStrings = input.split("\n");
        // обработаем первую строку с едиснтвенным root


        AbstractTreeNode absRoot = Factory.getParsedNode(listOfStrings[0]);
        if (!(absRoot instanceof MutableRootNode))
            throw new RuntimeException("wring root");
        MutableRootNode root = (MutableRootNode)absRoot;


        // добавим детей в Root  и вызовем заполнение этих детей
        for (int i = 1; i < listOfStrings.length; ++i)
        {
            // если отступ равен 1, т.е. если это дети Root'a
            if (indent(listOfStrings[i]) == 1) // добавим детей в RootNode
            {
                AbstractTreeNode currentNode = Factory.getParsedNode(listOfStrings[i]);

                if (currentNode instanceof MutableParentNode)
                {
                    FillParent((MutableParentNode)currentNode, 1, listOfStrings, i);
                }

                root.addChild(currentNode); // в инициализации addChild нужно учесть что возможно в несуществующий сет добаляется
                // если это ребенок Root'a- достаточно просто добавить root.addChild
            }
        }


        return root; // temporary stub
    }

    // Вызываем чтение так же рекурсивно
    //
    public static <T> void FillParent(MutableParentNode currentNode, int indent, String[] data, int position)
    {
        int i = position;
        indent++; // положение детей этого Parent находится в indent++
        while (indent(data[i]) >= indent && i < data.length) // добавим детей в RootNode
            {
                if (indent(data[i]) == indent) {
                    AbstractTreeNode child = Factory.getParsedNode(data[i]);


                    if (child instanceof MutableParentNode) {
                        FillParent((MutableParentNode)child, indent, data, i);
                    }

                    currentNode.addChild(child);

                    // если это ребенок Root'a- достаточно просто добавить root.addChild
                }
                i++;
            }
    }

}

class Factory
{
    // возвращает значение - что находится между  "(" и ")"
    private static String getValue(String input) {
        return input.substring(input.indexOf("(") + 1, input.indexOf(")"));
    }

    // FACTORY
    public static AbstractTreeNode getParsedNode(String input)
    {

        Object parsedValue;
        String value = getValue(input);
        try (Scanner scanner = new Scanner(value)) {
            if (scanner.hasNextInt() && String.valueOf(scanner.nextInt()).equals(value)) // если строка - int число и вся строка является этим числом (на случай если что-то врод 1234abc)
                parsedValue = scanner.nextInt();
            else if (scanner.hasNextDouble() && String.valueOf(scanner.nextDouble()).equals(value))  // если строка - double число и вся строка является этим числом
                parsedValue = scanner.nextDouble();
            else
                parsedValue = value; // если строка - текст
        }

            // need to check that parsedValue has correct type

            if (input.contains("Root"))
                return new MutableRootNode(parsedValue);
            if (input.contains("Parent"))
                return new MutableParentNode(parsedValue);
            if (input.contains("Child"))
                return new MutableChildNode(parsedValue);
            return null;

    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}

