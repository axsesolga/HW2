package edu.hse.cs.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
        children = new HashSet<>();
        parent = null;
    }

    // IChild implementation:
    @Override
    public IParent<T> getParent() {
        return parent;
    }

    // тут нужна проверка что если родитель был, то у прошлого родителя удаляем ребенка
    public void setParent(IParent<T> newValue) {
        if (this.parent != null)
        {
            IParent par = this.parent;
            if (par instanceof MutableParentNode)
                ((MutableParentNode) par).removeChildByValue(this.getObject());
            else
                ((MutableRootNode) par).removeChildByValue(this.getObject());
        }

            this.parent = newValue;

    }

    // IParent implementation:

    // Поскльку все изменения дерева доступны через функции, то в данном случае я думаю разумно передавать копию объекта, иначе будет меняться внутрянняя колецкция без контроля ошибок
    @Override
    public Set<? extends IChild<T>> getChildren() {
        Set<? extends IChild<T>> newChildren = new HashSet<>(children);
        return newChildren;
    }

    public void setChildren(Set<? extends IChild<T>> newValue) {
        children = newValue;
        Iterator<? extends IChild<T>> i = children.iterator();
        while (i.hasNext())
        {
            IChild next = i.next();
            if (next instanceof MutableParentNode)
                ((MutableParentNode) next).setParent(this);
            else
                ((MutableChildNode) next).setParent(this);
        }
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
    AbstractTreeNode<T> removeChildByValue(T childValue)
    {
        Iterator<? extends IChild<T>> it = children.iterator();

        while (it.hasNext())
        {
            IChild<T> child  = it.next();
            if (((MutableChildNode<T>) child).getObject().equals(childValue))
            {
                children.remove(child);
                ((MutableChildNode<T>) child).setParent(null);
                return (AbstractTreeNode<T>) child;
            }
        }

        return null;
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
        if (node instanceof  ImmutableChildNode || node instanceof ImmutableParentNode || node instanceof  ImmutableRootNode)
            throw new  IllegalStateException("Cant add immutable to mutable tree");

        Set<IChild<T>> set = new HashSet<>(this.children);
        if (node instanceof MutableRootNode)
        {
            MutableParentNode parentNode = new MutableParentNode(node.getObject());
            parentNode.setChildren(((MutableRootNode) node).getChildren());

            set.add(parentNode);
        }
        else
        {
            if (node instanceof MutableParentNode)
                set.add((MutableParentNode) node);
            else
                set.add((MutableChildNode) node);
        }
        children = set;
    }

    @Override
    public String toStringForm(String indent) {
        if (this.getObject() == null)
            throw new RuntimeException("not implemented yet!");
        StringBuffer output = new StringBuffer(indent + "MutableParentNode(" + this.getObject().toString() + ")\n");

        // По примеру можно точно сказать что проход в глубину. Признак возвращения "наверх" - child или отсутссвие у Parent child которые не были выведены

        // Если вывели потомка выкидываем его из outputCollection
        Set<? extends IChild<T>> outputCollection = this.getChildren();

        while (!outputCollection.isEmpty()) // пока есть что выводить
        {
            IChild<T> elem = outputCollection.iterator().next(); // родитель и ребенок реализуют IChild

            // добавление в output
            if (elem instanceof MutableChildNode)
                output.append(((MutableChildNode<T>) elem).toStringForm(indent+INDENT));

            if (elem instanceof MutableParentNode)
                output.append(((MutableParentNode<T>) elem).toStringForm(indent+INDENT));

            outputCollection.remove(elem);
        }
        return output.toString();
    }
}
