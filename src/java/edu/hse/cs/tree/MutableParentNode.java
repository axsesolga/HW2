package edu.hse.cs.tree;

import java.util.*;

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
        children = new LinkedHashSet<>();
        parent = null;
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

    // Поскльку все изменения дерева доступны через функции, то в данном случае я думаю разумно передавать копию объекта, иначе будет меняться внутрянняя колецкция без контроля ошибок
    // т.е. нет инкапсуляции
    @Override
    public Set<? extends IChild<T>> getChildren() {
        Set<? extends IChild<T>> newChildren = new LinkedHashSet<>(children);
        return newChildren;
    }

    public void setChildren(Set<? extends IChild<T>> newValue) {
        children = newValue;

        for (IChild child : getChildren())
        {
            if (child instanceof MutableParentNode)
                ((MutableParentNode) child).setParent(this);
            else
                ((MutableChildNode) child).setParent(this);
        }
    }

    // аналогично getChildren, передаем копию иначе потеря инкапсуляции
    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        Set<IChild<T>> output = new LinkedHashSet<>(this.children);
        for (Iterator<? extends IChild<T>> it = this.children.iterator(); it.hasNext(); )
        {
            IChild<T> temp = it.next();
            if (temp instanceof MutableParentNode)
            {
                output.add(temp);
                output.addAll(((MutableParentNode<T>) temp).getAllDescendants());
            }
            else
                output.add(temp);
        }
        return  output;
    }


    //	Возвращает булево значение, сообщающее о наличии ребенка с указанным значением в узле.
    @Override
    public boolean contains(T childValue) {
        for (IChild child : getChildren())
        {
            if (((AbstractTreeNode) child).getObject().equals(childValue))
                return true;
        }
        return  false;
    }

    @Override
    public boolean containsDescendants(T childValue) {
        for (IChild desc : getAllDescendants()) {
            if (((IWrapper) desc).getObject() == childValue) {
                return true;
            }
        }
        return false;
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
                children.remove(child); // iterator supports remove
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
    public boolean removeDescendantsByValue(T childValue) {
        //TODO:
        Iterator<? extends IChild<T>> it = getAllDescendants().iterator();

        boolean del = false;
        while (it.hasNext())
        {
            IChild<T> child  = it.next();
            if (((MutableChildNode<T>) child).getObject().equals(childValue))
            {
                getAllDescendants().remove(child);
                ((MutableChildNode<T>) child).setParent(null);
                del = true;
            }
        }
        return del;
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

        Set<IChild<T>> set = new LinkedHashSet<>(this.children);
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
        this.setChildren(set);
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
