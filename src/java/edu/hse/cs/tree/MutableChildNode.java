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

    @Override
    public String toStringForm(String indent) {
        if (this.getObject() == null)
            throw new RuntimeException("not implemented yet!");

        return indent + "MutableChildNode(" + this.getObject().toString() + ")\n";
    }
}
