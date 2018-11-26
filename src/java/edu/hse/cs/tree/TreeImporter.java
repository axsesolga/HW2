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


// а как же импорт MutableRootNode?
// Root ведь обязательно без \t?
// можно ли подгрузить Apache Commons?

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class TreeImporter  // Вопрос - согласно условию "cтрока, 32-битное знаковое целочисленное число, 64-битное число с плавающей точкой". Только 1 или любой тип?
{
    public static <T> MutableRootNode<T> importMutableTree(String input) { // возвращает корень дерева
        if (input.isEmpty())
            throw new RuntimeException("Empty input!"); // тоже вопрос, нужно ли самим кидать ошибки? и обрабаоывать ли их
        // Число Node (строк)
        //String[] listOfRoot = Arrays.stream(input.split("\n")).filter((x) -> StringUtils.countMatches("\t", x) == 1); // массив строк где только одна табуляция (т.е. только ветви Root'a)

        String[] listOfStrings = input.split("\n");
        // обработаем первую строку с едиснтвенным root
        if (!listOfStrings[0].contains("Root"))
            throw new RuntimeException("Wrong data, first Node isn't a Root");

        Set<? extends IChild<T>> children;
        MutableRootNode<T> root = new MutableRootNode<>(listOfStrings[0])
/*
ImmutableChildNode(T object, IParent<T> parent)
ImmutableParentNode(T object, IParent<T> parent, Set<? extends IChild<T>> children)
ImmutableRootNode(T object, Set<? extends IChild<T>> children)
 */



        for (int i = 1; i < n; ++i)
        {
            String line = listOfStrings[i];
            if (StringUtils.countMatches("\t", line) == 1)  // если это первый уровень ветвей
            {
                if (line.contains("Child"))
                {

                }
                if (line.contains("Parent"))
                {

                }
            }
        }



        return null; // temporary stub
    }

    // Вызываем чтение так же рекурсивно
    //
    public static <T> MutableParentNode<T> stringToParent(String input, String indent)
    {


        return  null;
    }

    public static <T> MutableChildNode<T> stringToChild(String input, String indent)
    {

        return  null;
    }


    // FACTORY

    public abstract class AbstractReader {
        public abstract void read(Object input);
    }


    // Создает Node c T = int
    public class IntReader extends AbstractReader {
        public void read(Object input) {
            // method body
        }
    }

    // Создает Node c T = double
    public class DoubleReader extends AbstractReader {
        public void read(Object input) {
            // method body
        }
    }

    // Создает Node c T = string
    public class StringReader extends AbstractReader {
        public void read(Object input) {
            // method body
        }
    }

    // https://goo.gl/VY9Eeg
    // тут код определить что в скобках лежит
    // так же надо в Factory прописать определить тип Node
    public class FactoryMethod {
        public AbstractReader getReader(Object object)
        {
            AbstractReader reader = null;

            if (object instanceof Integer)
                reader = new IntReader();

            else
                if (object instanceof Double)
                    reader = new DoubleReader();
                else
                    reader = new StringReader();

            return reader;
        }
    }
}
