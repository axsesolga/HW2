package tree;

import edu.hse.cs.tree.*;
    import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TreeTest {

    @Test
    public void testGetChildren(){
        MutableRootNode<String> root = populateTree();

        System.out.println(root.toStringForm(""));

        String tree = root.toStringForm("");

        MutableRootNode<String> root2 = TreeImporter.importMutableTree(tree);

        System.out.println(root2.toStringForm(""));

    }


    private static MutableRootNode<String> populateTree(){
        MutableRootNode<String> root = new MutableRootNode<>("Root");

        MutableParentNode<String> parent0 = new MutableParentNode<>("Parent0");
        MutableParentNode<String> parent1 = new MutableParentNode<>("Parent1");

        MutableChildNode<String> child0 = new MutableChildNode<>("Child0");
        MutableChildNode<String> child00 = new MutableChildNode<>("Child00");
        MutableChildNode<String> child01 = new MutableChildNode<>("Child01");
        MutableChildNode<String> child10 = new MutableChildNode<>("Child10");

        Set<IChild<String>> rootChildren = new HashSet<>(3);
        rootChildren.add(parent0);
        rootChildren.add(parent1);
        rootChildren.add(child0);
        root.setChildren(rootChildren);

        Set<IChild<String>> parent0children = new HashSet<>(3);
        parent0children.add(child00);
        parent0children.add(child01);
        parent0children.add(child10);
        parent0.setChildren(parent0children);

        Set<IChild<String>> parent1children = new HashSet<>(3);
        parent1children.add(child10);
        parent1.setChildren(parent1children);

        return root;
    }
}
