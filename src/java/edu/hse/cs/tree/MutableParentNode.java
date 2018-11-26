package edu.hse.cs.tree;

import java.util.Collection;
import java.util.Set;

public class MutableParentNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IChild<T>,
        IParent<T> {
    private IParent<T> parent;
    private Set<? extends IChild<T>> children;

    public MutableParentNode(T object) {
        super(object);
        throw new RuntimeException("not implemented yet!");
    }

    // IChild implementation:
    @Override
    public IParent<T> getParent() {
        return parent;
    }

    public void setParent(IParent<T> newValue) {
        this.parent = newValue;
    }

    // IParent implementation:
    @Override
    public Set<? extends IChild<T>> getChildren() {
        throw new RuntimeException("not implemented yet!");
    }

    public void setChildren(Set<? extends IChild<T>> newValue) {
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        // TODO implement getAllDescendants in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public boolean contains(T childValue) {
        // TODO implement contains in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public boolean containsDescendants(T childValue) {
        // TODO implement containsDescendants in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * Removes the first child of this node that has the specified value
     *
     * @param childValue - the value of the child to be removed
     * @return - the child removed, or null if the child with the given value was not found.
     */
    AbstractTreeNode<T> removeChildByValue(T childValue) {
        // TODO implement removeChildByValue in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * Removes this node descendants having the specified value.
     *
     * @param childValue - the value of the descendant of this node that must be removed.
     * @return true if at least one descendant was removed, false - otherwise.
     */
    boolean removeDescendantsByValue(T childValue) {
        // TODO implement removeDescendantsByValue in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    /**
     * Adds child to the node.
     * <p>
     * N.B. Node may not implement IChild, in this case it should be recreated as a
     * MutableParentNode with the same list of children that node has. It should be done
     * to set a node a new parent.
     *
     * @param node node to be added
     */
    void addChild(AbstractTreeNode<T> node) {
        // TODO implement addChild in MutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public String toStringForm(String indent) {
        if (this.getObject() == null)
            throw new RuntimeException("not implemented yet!");
        StringBuffer output = new StringBuffer("MutableParentNode(" + this.getObject().toString() + ")\n");

        // По примеру можно точно сказать что проход в глубину. Признак возвращения "наверх" - child или отсутссвие у Parent child которые не были выведены

        // Если вывели потомка выкидываем его из outputCollection
        Set<? extends IChild<T>> outputCollection = this.getChildren();

        while (!outputCollection.isEmpty()) // пока есть что выводить
        {
            IChild<T> elem = outputCollection.iterator().next(); // родитель и ребенок реализуют IChild
            output.append(elem.toString());

            // добавление в output
            if (elem instanceof ImmutableChildNode)
                output.append(((ImmutableChildNode<T>) elem).toStringForm(indent + "\t"));

            if (elem instanceof ImmutableParentNode)
                output.append(((ImmutableParentNode<T>) elem).toStringForm(indent + "\t"));

            outputCollection.remove(elem);
        }
        return output.toString();
    }
}
