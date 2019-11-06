package net.caidingke.chenxi.algorithm;

/**
 * 二叉树
 *
 * @author bowen
 * @create 2016-11-14 17:08
 */

public class BinaryTree {

    /**
     * 输出结点信息
     */
    public void printNode(TreeNode<String> node) {
        System.out.print(node.getData() + "  ");
    }

    /**
     * 定义结点
     */
    public class TreeNode<T> {
        private T data;
        private TreeNode<T> leftNode;
        private TreeNode<T> rightNode;

        public TreeNode(T data, TreeNode<T> leftNode, TreeNode<T> rightNode) {
            this.data = data;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }


        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public TreeNode<T> getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(TreeNode<T> leftNode) {
            this.leftNode = leftNode;
        }

        public TreeNode<T> getRightNode() {
            return rightNode;
        }

        public void setRightNode(TreeNode<T> rightNode) {
            this.rightNode = rightNode;
        }

    }

    // 初始化二叉树
    public TreeNode<String> init() {
        TreeNode<String> D = new TreeNode<String>("D", null, null);
        TreeNode<String> H = new TreeNode<String>("H", null, null);
        TreeNode<String> I = new TreeNode<String>("I", null, null);
        TreeNode<String> J = new TreeNode<String>("J", null, null);
        TreeNode<String> P = new TreeNode<String>("P", null, null);
        TreeNode<String> G = new TreeNode<String>("G", P, null);
        TreeNode<String> F = new TreeNode<String>("F", null, J);
        TreeNode<String> E = new TreeNode<String>("E", H, I);
        TreeNode<String> B = new TreeNode<String>("B", D, E);
        TreeNode<String> C = new TreeNode<String>("C", F, G);
        TreeNode<String> A = new TreeNode<String>("A", B, C);
        return A;
    }

    /**
     * 先序遍历二叉树
     */
    public void xianIterator(TreeNode<String> node) {
        this.printNode(node);
        if (node.getLeftNode() != null) {
            this.xianIterator(node.getLeftNode());
        }
        if (node.getRightNode() != null) {
            this.xianIterator(node.getRightNode());
        }
    }

    /**
     * 中序遍历二叉树
     */
    public void zhongIterator(TreeNode<String> node) {
        if (node.getLeftNode() != null) {
            this.zhongIterator(node.getLeftNode());
        }
        this.printNode(node);
        if (node.getRightNode() != null) {
            this.zhongIterator(node.getRightNode());
        }
    }

    /**
     * 后序遍历二叉树
     */
    public void houIterator(TreeNode<String> node) {
        if (node.getLeftNode() != null) {
            this.houIterator(node.getLeftNode());
        }
        if (node.getRightNode() != null) {
            this.houIterator(node.getRightNode());
        }
        this.printNode(node);
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.zhongIterator(binaryTree.init());
    }
}
