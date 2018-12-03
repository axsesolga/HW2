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




import com.sun.javaws.exceptions.InvalidArgumentException;

import java.text.ParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class TreeImporter {
    // возвращает число табуляций
    private static int indent(String input) {
        return input.length() - input.replace(" ", "").length();
    }

    public static <T> MutableRootNode<T> importMutableTree(String input) { // возвращает корень дерева
        if (input.isEmpty())
            throw new IllegalArgumentException("Empty input!");
        // Число Node (строк)
        //String[] listOfRoot = Arrays.stream(input.split("\n")).filter((x) -> StringUtils.countMatches("\t", x) == 1); // массив строк где только одна табуляция (т.е. только ветви Root'a)

        String[] listOfStrings = input.split("\n");
        // обработаем первую строку с едиснтвенным root


        AbstractTreeNode absRoot = Factory.getParsedNode(listOfStrings[0]);
        if (!(absRoot instanceof MutableRootNode))
            throw new IllegalArgumentException("First line isn't root");
        MutableRootNode root = (MutableRootNode) absRoot;



        // добавим детей в Root  и вызовем заполнение этих детей
        for (int i = 1; i < listOfStrings.length; ++i) {
            // если отступ равен 1, т.е. если это дети Root'a
            if (indent(listOfStrings[i]) == AbstractTreeNode.INDENT.length()) // добавим детей в RootNode
            {
                AbstractTreeNode currentNode = Factory.getParsedNode(listOfStrings[i]);


                if (currentNode.getObject().getClass() != root.getObject().getClass())
                    throw new IllegalArgumentException("Tree can be only filld with one type!");


                if (currentNode instanceof MutableParentNode) {
                    FillParent((MutableParentNode) currentNode, AbstractTreeNode.INDENT.length(), listOfStrings, i, root);
                }

                root.addChild(currentNode); // в инициализации addChild нужно учесть что возможно в несуществующий сет добаляется и нужно ребенку поменять parent
                // если это ребенок Root'a- достаточно просто добавить root.addChild
            }
        }


        return root; // temporary stub
    }

    // Вызываем чтение так же рекурсивно
    //
    public static <T> void FillParent(MutableParentNode<T> currentNode, int indent, String[] data, int position, MutableRootNode<T> root) {

        if (currentNode.getObject().getClass() != root.getObject().getClass())
            throw new IllegalArgumentException("Tree can be only filld with one type!");

        indent += AbstractTreeNode.INDENT.length();
        if (indent(data[position + 1]) != indent) // если следующий элемент находится в неверном числе отступов, то кидаем ошибку
            throw new InputMismatchException("Wrong data was given");


        // положение детей этого Parent находится в indent+4
        for (int i = position + 1;  i < data.length && indent(data[i]) == indent; i++) {
            if (indent(data[i]) == indent) {
                AbstractTreeNode<T> child = Factory.getParsedNode(data[i]);
                if (child instanceof MutableParentNode) {
                    FillParent((MutableParentNode) child, indent, data, i, root);
                }
                currentNode.addChild(child);
                // если это ребенок - достаточно просто добавить root.addChild
            }
        }
    }
}



class Factory {
    // возвращает значение - что находится между  "(" и ")"
    private static String getValue(String input) {
        return input.substring(input.indexOf("(") + 1, input.indexOf(")"));
    }

    // FACTORY
    public static  AbstractTreeNode getParsedNode(String input) {

        Object parsedValue;
        String value = getValue(input);
        try (Scanner scanner = new Scanner(value)) {
            if (scanner.hasNextInt() && String.valueOf(scanner.nextInt()).equals(value)) // если строка - int число и вся строка является этим числом (на случай если что-то врод 1234abc)
                parsedValue = Integer.parseInt(value);
            else if (scanner.hasNextDouble() && String.valueOf(scanner.nextDouble()).equals(value))  // если строка - double число и вся строка является этим числом
                parsedValue = Double.parseDouble(value);
            else
                parsedValue = value; // если строка - текст
        }

        // need to check that parsedValue has correct type

        if (input.contains("MutableRootNode"))
            return new MutableRootNode(parsedValue);
            if (input.contains("MutableParentNode"))
                return new MutableParentNode(parsedValue);
                if (input.contains("MutableChildNode"))
                    return new MutableChildNode(parsedValue);

        throw new IllegalArgumentException("node can be root parent or child only");

    }
}

