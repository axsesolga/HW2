package edu.hse.cs.tree;

import java.util.*;

public class MutableRootNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IParent<T> {

    private Set<? extends IChild<T>> children;

    public MutableRootNode(T object) {
        super(object);
        children = new LinkedHashSet<>();
    }

    public MutableRootNode(ImmutableRootNode<T> source) {
        // TODO implement constructor that creates MutableRootNode identical to ImmutableRootNode
        super(null);   // tmp stub
        this.children = null; // tmp stub
    }


    // Поскльку все изменения дерева доступны через функции, то в данном случае я нужно передавать копию объекта, иначе будет меняться внутрянняя колецкция без контроля ошибок
    @Override
    public final Set<? extends IChild<T>> getChildren() {
        Set<? extends IChild<T>> newChildren = new LinkedHashSet<>(children);
        return newChildren;
    }

    // Не уверена. TO DO не стоит, но нужно всем этим детям задать родителя...
    public final void setChildren(Set<? extends IChild<T>> newValue) {
        children = newValue;
        for (IChild child : getChildren())
        {
            if (child instanceof MutableParentNode)
                ((MutableParentNode) child).setParent(this);
            else
                ((MutableChildNode) child).setParent(this);
        }
    }

    @Override
    // тут включаются и Parent-ы и Child-ы
    // аналогично getChildren, передаем копию иначе потеря инкапсуляции
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
        Integer value = null;
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
    public AbstractTreeNode<T> removeChildByValue(T childValue) {
        Iterator<? extends IChild<T>> it = children.iterator();

        while (it.hasNext())
        {
            IChild<T> child  = it.next();
            if (((IWrapper<T>) child).getObject().equals(childValue))
            {
                children.remove(child);
                if (child instanceof MutableParentNode)
                    ((MutableParentNode) child).setParent(null);
                else
                    ((MutableChildNode<Object>) child).setParent(null);
                return (AbstractTreeNode<T>) child;
            }
        }

        return null;
    }

    /**
     * Removes this node descendants having the specified value.S
     * ОБХОД В ШИРИНУ
     * @param childValue - the value of the descendant of this node that must be removed.
     * @return true if at least one descendant was removed, false - otherwise.
     */
    public boolean removeDescendantsByValue(T childValue) {
        // TODO implement removeDescendantsByValue in MutableRootNode
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
        StringBuffer output = new StringBuffer("MutableRootNode(" + this.getObject().toString() + ")\n" );

        // По примеру можно точно сказать что проход в глубину. Признак возвращения "наверх" - child или отсутссвие у Parent child которые не были выведены

        // Если вывели потомка выкидываем его из outputCollection
        Set<? extends IChild<T>> outputCollection = this.getChildren();

        while (!outputCollection.isEmpty()) // пока есть что выводить
        {
            IChild<T> elem = outputCollection.iterator().next(); // родитель и ребенок реализуют IChild

            // добавление в output
            if (elem instanceof MutableChildNode)
                output.append(((MutableChildNode<T>) elem).toStringForm(INDENT));

            if (elem instanceof MutableParentNode)
                output.append(((MutableParentNode<T>) elem).toStringForm(INDENT));

            outputCollection.remove(elem);
        }
        return output.toString();
    }
}
