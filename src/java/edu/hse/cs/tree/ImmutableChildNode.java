package edu.hse.cs.tree;

public class ImmutableChildNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IChild<T> {

    private final IParent<T> parent;

    ImmutableChildNode(T object, IParent<T> parent) {
        super(object);
        this.parent = parent;
    }

    public IParent<T> getParent() {
        return parent;
    }

    @Override
    public String toStringForm(String indent /*отступ*/) {
        if (this.getObject() == null)
            throw new RuntimeException("not implemented yet!");

        return indent + "ImmutableChildNode(" + this.getObject().toString() + ")\n";
    }
}
