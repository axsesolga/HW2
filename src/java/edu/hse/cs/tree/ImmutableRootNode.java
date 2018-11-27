package edu.hse.cs.tree;

import java.util.Collection;
import java.util.Set;

/**
 * We should not have a specific logic in constructors. Specific logic should be placed into factory methods instead.
 *
 * @param <T> - the type of wrapped object.
 */
public class ImmutableRootNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IParent<T> {
    private final Set<? extends IChild<T>> children;

    public ImmutableRootNode(T object, Set<? extends IChild<T>> children) {
        super(object);
        throw new RuntimeException("not implemented yet!");
    }

    public ImmutableRootNode(MutableRootNode<T> source) {
        // TODO implement constructor that creates ImmutableRootNode identical to MutableRootNode
        super(null);    // tmp stub
        this.children = null;  // tmp stub
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * TODO: test that children set can be read but cannot be changed.
     *
     * @return - children set view.
     */
    @Override
    public Set<? extends IChild<T>> getChildren() {
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        // TODO implement getAllDescendants in ImmutableRootNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public boolean contains(T childValue) {
        // TODO implement contains in ImmutableRootNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public boolean containsDescendants(T childValue) {
        // TODO implement containsDescendants in ImmutableRootNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public String toStringForm(String indent) // indent = "" т.к. Root единственен и самый леывй
    {
        if (this.getObject() == null)
            throw new RuntimeException("not implemented yet!");


        StringBuffer output = new StringBuffer("ImmutableRootNode(" + this.getObject().toString() + ")\n" );

        // По примеру можно точно сказать что проход в глубину. Признак возвращения "наверх" - child или отсутссвие у Parent child которые не были выведены

        // Если вывели потомка выкидываем его из outputCollection
        Set<? extends IChild<T>> outputCollection = this.getChildren();

        while (!outputCollection.isEmpty()) // пока есть что выводить
        {
            IChild<T> elem = outputCollection.iterator().next(); // родитель и ребенок реализуют IChild
            output.append(elem.toString());

            // добавление в output
            if (elem instanceof ImmutableChildNode)
                output.append(((ImmutableChildNode<T>) elem).toStringForm(INDENT));

            if (elem instanceof ImmutableParentNode)
                output.append(((ImmutableParentNode<T>) elem).toStringForm(INDENT));

            outputCollection.remove(elem);
        }
        return output.toString();

    }
}
