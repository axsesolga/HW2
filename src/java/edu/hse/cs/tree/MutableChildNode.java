package edu.hse.cs.tree;

public class MutableChildNode<T>
        extends
        AbstractTreeNode<T>
        implements
        IChild<T> {

    private IParent<T> parent;

    public MutableChildNode(T object) {
        super(object);
    }

    @Override
    public IParent<T> getParent() {
        return parent;
    }

    public void setParent(IParent<T> newValue) {
        this.parent = newValue;
    }

    @Override
    public String toStringForm(String indent) {
        // TODO implement toStringForm in MutableChildNode
        throw new RuntimeException("not implemented yet!");
    }
}
