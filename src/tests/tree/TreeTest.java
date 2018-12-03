package tree;

import edu.hse.cs.tree.*;
    import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TreeTest {
    String populateString = "MutableRootNode(0)\n" +
            "    MutableChildNode(1)\n" +
            "    MutableParentNode(2)\n" +
            "        MutableChildNode(0)\n" +
            "        MutableChildNode(1)\n" +
            "    MutableParentNode(3)\n" +
            "        MutableChildNode(0)\n" +
            "        MutableChildNode(1)\n" +
            "        MutableParentNode(2)\n" +
            "            MutableChildNode(0)\n" +
            "            MutableChildNode(1)";

    @Test
    public void testTreeImporter_and_populateTree()
    {
        MutableRootNode<String> root = populateTree();
        String tree = root.toStringForm("");
        MutableRootNode<String> root2 = TreeImporter.importMutableTree(tree);

        //System.out.println(root.toStringForm(""));
        //System.out.println(root2.toStringForm(""));
        Collection<? extends IChild<String>> root2Desc = root2.getAllDescendants();
        Collection<? extends IChild<String>> root1Desc = root.getAllDescendants();
        Assertions.assertEquals(root.getChildren().size(), root2.getChildren().size());
        Assertions.assertEquals(root1Desc.size(), root2Desc.size());


        Set<? extends IChild<String>> set = root.getChildren();
        for (  Iterator<? extends IChild<String>> it = set.iterator(); it.hasNext(); )
        {
            IChild<String> next = it.next();
            Assertions.assertEquals((next.getParent()), root);
        }


    }

    @Test
    public void rootContains()
    {
        MutableRootNode<String> root = populateTree();


        Set<? extends IChild<String>> set = root.getChildren();
        int i = 0;
        for (Iterator<? extends IChild<String>> it = set.iterator(); it.hasNext(); )
        {
            IChild<String> next = it.next();
            if (i == 0)
                Assertions.assertEquals(((IWrapper)next).getObject(), "child");
            if (i == 1)
                Assertions.assertEquals(((IWrapper)next).getObject(), "par0");
            if (i == 2)
                Assertions.assertEquals(((IWrapper)next).getObject(), "par1");
            i++;
        }
        Assertions.assertEquals(i,3); // проверка что размер set = 3

        Assertions.assertEquals(root.contains("child0-0"), false);
        Assertions.assertEquals(root.contains("child"), true);
    }

    @Test
    public void LinkedSetTest() {
        MutableRootNode<String> root = populateTree();
        Set<? extends IChild<String>> set = root.getChildren();
        int i = 0;
        for (Iterator<? extends IChild<String>> it = set.iterator(); it.hasNext(); ) {
            IChild<String> next = it.next();
            if (i == 0)
                Assertions.assertEquals(((IWrapper) next).getObject(), "child");
            if (i == 1)
                Assertions.assertEquals(((IWrapper) next).getObject(), "par0");
            if (i == 2)
                Assertions.assertEquals(((IWrapper) next).getObject(), "par1");
            i++;
        }
        Assertions.assertEquals(i, 3); // проверка что размер set = 3}
    }

    @Test
    public void RemoveAndGetChildByValue()
    {
        MutableRootNode<String> root = populateTree();
        List<? extends IChild> list = new ArrayList<>(root.getChildren());
        IChild second = list.get(1);

        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child0-0"), true);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child0-1"), true);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child1-1"), false);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child"), false);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child10-2"), false);


        Assertions.assertEquals(root.getAllDescendants().size(), 10);
        Assertions.assertEquals(root.getChildren().size(), 3);
        root.removeChildByValue(((MutableParentNode<String>) second).getObject());

        Assertions.assertEquals(root.getAllDescendants().size(), 7);
        Assertions.assertEquals(root.getChildren().size(), 2);

        Assertions.assertEquals(second.getParent(), null);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child0-0"), true);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child0-1"), true);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child1-1"), false);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child"), false);
        Assertions.assertEquals(((MutableParentNode<String>)second).contains("child10-2"), false);
    }


    @Test
    public void containsDescendantsTest()
    {
        MutableRootNode<String> root = populateTree();
        List<? extends IChild> list = new ArrayList<>(root.getChildren());

        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child0-0"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child0-1"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child1-1"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child10-2"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child10-3"), false);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("root"), false);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("chil"), false);
    }

    @Test
    public void removeDescTest()
    {
        MutableRootNode<String> root = populateTree();
        List<? extends IChild> list = new ArrayList<>(root.getChildren());
        root.removeDescendantsByValue("child10-2");

        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child0-0"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child0-1"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child1-1"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child10-2"), true);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("child10-3"), false);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("root"), false);
        Assertions.assertEquals(((MutableRootNode<String>)root).containsDescendants("chil"), false);



    }






    private static MutableRootNode<String> populateTree(){
        MutableRootNode<String> root = new MutableRootNode<>("root");
        MutableParentNode<String> parent0 = new MutableParentNode<>("par0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("par1");
        MutableParentNode<String> parent10 = new MutableParentNode<>("par10");



        Set<IChild<String>> parent10set = new HashSet<>(5);
        MutableChildNode<String> child101 = new MutableChildNode<>("child10-1");
        MutableChildNode<String> child102 = new MutableChildNode<>("child10-2");
        parent10set.add(child101);
        parent10set.add(child102);
        parent10.setChildren(parent10set);


        MutableChildNode<String> child0 = new MutableChildNode<>("child");
        MutableChildNode<String> child11 = new MutableChildNode<>("child1-1");
        MutableChildNode<String> child00 = new MutableChildNode<>("child0-0");
        MutableChildNode<String> child01 = new MutableChildNode<>("child0-1");
        MutableChildNode<String> child10 = new MutableChildNode<>("child1-0");

        Set<IChild<String>> rootChildren = new HashSet<>(5);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);

        Set<IChild<String>> parent0children = new HashSet<>(5);
        parent0children.add(child00);
        parent0children.add(child01);
        parent0.setChildren(parent0children);

        Set<IChild<String>> parent1children = new HashSet<>(5);
        parent1children.add(child10);
        parent1children.add(child11);
        parent1children.add(parent10);
        parent1.setChildren(parent1children);

        return root;
    }
}
