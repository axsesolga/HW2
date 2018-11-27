package edu.hse.cs.tree;

import java.util.Collection;
import java.util.Set;

public class ImmutableParentNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IChild<T>,
        IParent<T> {
    private final IParent<T> parent;
    private final Set<? extends IChild<T>> children;

    public ImmutableParentNode(T object, IParent<T> parent, Set<? extends IChild<T>> children) {
        super(object);
        this.parent = parent;
        this.children = null; //tmp stub
    }

    @Override
    public IParent<T> getParent() {
        return parent;
    }


    //Возвращает множество узлов-детей, принадлежащих данному родителю.
    @Override
    public Set<? extends IChild<T>> getChildren() {
        // TODO
        throw new RuntimeException("not implemented yet!");
    }

    //  Возвращает коллекцию, состоящую из всех потомков узла, т.е. детей узла, детей детей узла и т.д.
    @Override
    public Collection<? extends IChild<T>> getAllDescendants() {
        // TODO implement getAllDescendants in ImmutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    //	Возвращает булево значение, сообщающее о наличии ребенка с указанным значением в узле.
    @Override
    public boolean contains(T childValue) {
        // TODO implement contains in ImmutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    //  Возвращает булево значение, сообщающее о наличии потомка с указанным значением, т.е. среди детей узла, детей детей узла и т.д.
    @Override
    public boolean containsDescendants(T childValue) {
        // TODO implement containsDescendants in ImmutableParentNode
        throw new RuntimeException("not implemented yet!");
    }

    @Override
    public String toStringForm(String indent /*отступ*/) { // Вопрос - обязательно ли в порядке parent-parent-child?
        if (this.getObject() == null)
            throw new RuntimeException("not implemented yet!");
        StringBuffer output = new StringBuffer(indent + "MutableParentNode(" + this.getObject().toString() + ")\n" );

        // По примеру можно точно сказать что проход в глубину. Признак возвращения "наверх" - child или отсутссвие у Parent child которые не были выведены

        // Если вывели потомка выкидываем его из outputCollection
        Set<? extends IChild<T>> outputCollection = this.getChildren();

        while (!outputCollection.isEmpty()) // пока есть что выводить
        {
            IChild<T> elem = outputCollection.iterator().next(); // родитель и ребенок реализуют IChild
            output.append(elem.toString());

            // добавление в output
            if (elem instanceof ImmutableChildNode)
                output.append(((ImmutableChildNode<T>) elem).toStringForm(indent + INDENT));

            if (elem instanceof ImmutableParentNode)
                output.append(((ImmutableParentNode<T>) elem).toStringForm(indent + INDENT));

            outputCollection.remove(elem);
        }



        return output.toString();
    }
}
