package com.example.lib;

import java.util.*;

import java.util.*;


public class TreeTraverse {

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }
    LinkedList<Integer> list = new LinkedList();
    public void xianxu(TreeNode root){
        // 非递归插入
        Stack<TreeNode> stack = new Stack();
        if(root == null){
            return;
        }
        stack.push(root);
        while(stack.size() > 0){
            TreeNode temp = stack.pop();
            list.add(temp.val);
            if(temp.right != null){
                stack.push(temp.right);
            }
            if(temp.left != null){
                stack.push(temp.left);
            }
        }
    }

    /**
     *  1.每个节点都要进栈后再取出栈
     *  2.每次处理出栈处理都不是处理左子树，右子树，都是处理当前节点，把当前节点当做根节点来处理。
     *  3.进栈是不断遍历根节点的左子树，处理根节点，下一个处理右子树，右子树变成根节点。
     *
     *  如何把整体流程分解成多个局部流程的循环
     *  手动操作到代码执行操作的抽象
     * @param rootNode
     */
    public void zhongxu(TreeNode rootNode){
        Stack<TreeNode> stack = new Stack();
        if(rootNode == null){
            return;
        }
        TreeNode temp = rootNode;
        while(stack.size()>0 || temp != null){
            while(temp != null){
                stack.push(temp);
                temp = temp.left;
            }
            temp = stack.pop();
            list.add(temp.val);
            temp = temp.right;
        }
//        zhongxu(root.left);
//        list.add(root.val);
//        zhongxu(root.right);
    }

    /**
     *
     * @param root
     */
    public void houxu(TreeNode rootNode){
        Stack<TreeNode> stack = new Stack();
        TreeNode right = null;
        if(rootNode == null){
            return;
        }
        TreeNode temp = rootNode;
        while(stack.size()>0 || temp != null){
            while(temp != null){
                stack.push(temp);
                temp = temp.left;
            }
            temp = stack.peek();
            // 右子树为空或者右子树处理过
            if(temp.right == null || temp.right == right){
                list.add(temp.val);
                right = temp;
                stack.pop();
            }else{
                temp = temp.right;
            }
        }
//        houxu(root.left);
//        houxu(root.right);
//        list.add(root.val);
    }

    public int[][] threeOrders (TreeNode root) {
        // write code here
        xianxu(root);
        int length = list.size();
        int[][] array = new int[3][length];
        for(int i=0;i< list.size();i++){
            array[0][i] = list.get(i);
        }
        list.clear();
        zhongxu(root);
        for(int i=0;i< list.size();i++){
            array[1][i] = list.get(i);
        }
        list.clear();
        houxu(root);
        for(int i=0;i< list.size();i++){
            array[2][i] = list.get(i);
        }

        return array;

    }
}