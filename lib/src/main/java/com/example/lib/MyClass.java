package com.example.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MyClass {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static void main(String args[]) {
        System.out.println("========");
//        System.out.println(maxLength(new int[]{2,2,3,4,8,99,3}));
    System.out.println(findKth(new int[]{10,10,9,9,8,7,5,6,4,3,4,2},12,5));
    int[] a = new int[]{10,10,9,9,8,7,5,6,4,3,4,2};
    QuickSort(a,0,11);
    ArrayList arrayList = new ArrayList();
    for(int i=0;i<12;i++)
    arrayList.add(a[i]);
    System.out.println(arrayList);

    }

    // 判断链表有环
    public boolean hasCycle(ListNode head) {
        ListNode quick = head;
        ListNode slow = head;
        while (quick != null && quick.next != null) {
            quick = quick.next.next;
            slow = slow.next;
            if (quick == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设计LRU(最近最少使用)缓存结构，该结构在构造时确定大小，假设大小为 k ，并有如下两个功能
     * 1. set(key, value)：将记录(key, value)插入该结构
     * 2. get(key)：返回key对应的value值
     *
     * 提示:
     * 1.某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的，然后都会刷新缓存。
     * 2.当缓存的大小超过k时，移除最不经常使用的记录。
     * 3.输入一个二维数组与k，二维数组每一维有2个或者3个数字，第1个数字为opt，第2，3个数字为key，value
     * 若opt=1，接下来两个整数key, value，表示set(key, value)
     * 若opt=2，接下来一个整数key，表示get(key)，若key未出现过或已被移除，则返回-1
     * 对于每个opt=2，输出一个答案
     * 4.为了方便区分缓存里key与value，下面说明的缓存里key用""号包裹
     *
     * 要求：set和get操作复杂度均为
     * O
     * (
     * 1
     * )
     * O(1)
     */
    /**
     * lru design
     *
     * @param operators int整型二维数组 the ops
     * @param k         int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU(int[][] operators, int k) {
        // write code here
        // [[1,1,1],[1,2,2],[1,3,2],[2,1],[1,4,4],[2,2]],3
        // 优先级低到高
        LinkedList<int[]> resultList = new LinkedList();
        ArrayList<Integer> endlist = new ArrayList();
        for (int i = 0; i < operators.length; i++) {
            int data[] = operators[i];
            if (data[0] == 1) {
                boolean hasData = false;
                for (int j = 0; j < resultList.size(); j++) {
                    if (resultList.get(j)[0] == data[1]) {
                        hasData = true;
                        resultList.remove(j);
                        int temp[] = new int[]{data[1], data[2]};
                        resultList.add(temp);
                        break;
                    }
                }
                if (hasData == false) {
                    if (resultList.size() < k) {
                        resultList.add(new int[]{data[1], data[2]});
                    } else {
                        resultList.removeFirst();
                        resultList.add(new int[]{data[1], data[2]});
                    }
                }
            }
            if (data[0] == 2) {
                boolean hasData = false;
                for (int j = 0; j < resultList.size(); j++) {
                    if (resultList.get(j)[0] == data[1]) {
                        endlist.add(resultList.get(j)[1]);
                        hasData = true;
                        int temp[] = resultList.remove(j);
                        resultList.add(temp);
                        break;
                    }
                }
                if (hasData == false)
                    endlist.add(-1);
            }
        }
        int endArray[] = new int[endlist.size()];
        for (int i = 0; i < endlist.size(); i++) {
            endArray[i] = endlist.get(i);
        }
        return endArray;
    }

    // 反转链表
    public static ListNode ReverseList(ListNode head) {
        if (head == null)
            return null;
        ListNode right = head.next;
        head.next = null;
        while (right != null) {
            ListNode temp = right;
            right = right.next;
            temp.next = head;
            head = temp;
        }
        return head;
    }


    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int v) {
            val = v;
        }
    }

    // 层次遍历
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> list = new ArrayList();
        if (root == null)
            return list;
        int kNum;
        ArrayList<TreeNode> levelList = new ArrayList();
        levelList.add(root);
        while (levelList.size() != 0) {
            kNum = levelList.size();
            ArrayList<Integer> temp = new ArrayList();
            for (int j = 0; j < kNum; j++) {
                if (levelList.get(j).left != null)
                    levelList.add(levelList.get(j).left);
                if (levelList.get(j).right != null)
                    levelList.add(levelList.get(j).right);
                temp.add(levelList.get(j).val);
            }
            list.add(temp);
            while (kNum > 0) {
                levelList.remove(0);
                kNum--;
            }
        }
        return list;
    }

    // 两个栈实现一个队列
    public static class ListStack {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();

        public void push(int node) {
            while (stack1.size() > 0) {
                stack2.push(stack1.pop());
            }
            stack2.push(node);

            while (stack2.size() > 0) {
                stack1.push(stack2.pop());
            }

        }

        public int pop() {
            return stack1.pop();
        }
    }

    // 最近公共祖先节点，深度优先遍历，用一个栈结构来记录已经访问的节点，根节点是在栈底
    public static int lowestCommonAncestor(TreeNode root, int o1, int o2) {
        // write code here
        HashMap<TreeNode, TreeNode> map = new HashMap<>();
        // 先序遍历 非递归
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode t1 = null;
        TreeNode t2 = null;
        while (stack.size() > 0) {
            TreeNode treeNode = stack.pop();
            if (treeNode.val == o1) {
                t1 = treeNode;
            }
            if (treeNode.val == o2) {
                t2 = treeNode;
            }
            if (t1 != null && t2 != null) {
                break;
            }
            if (treeNode.right != null) {
                map.put(treeNode.right, treeNode);
                stack.push(treeNode.right);
            }
            if (treeNode.left != null) {
                map.put(treeNode.left, treeNode);
                stack.push(treeNode.left);
            }
        }

        while (map.containsKey(t1)) {
            System.out.println("t1: " + t1.val);
            TreeNode temp = t2;
            while (map.containsKey(temp)) {
                System.out.println("temp: " + temp.val);
                if (t1.val == temp.val) {
                    return t1.val;
                } else {
                    temp = map.get(temp);
                }
            }
            t1 = map.get(t1);
        }

        return root.val;

    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * <p>
     * 如果目标值存在返回下标，否则返回 -1
     *
     * @param nums   int整型一维数组
     * @param target int整型
     * @return int整型
     * [1,1,2,3,7,7,7,9,9,10],2
     */
    public static int search(int[] nums, int target) {
        // write code here
        if (nums.length == 0)
            return -1;
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int data = nums[(end - start) / 2 + start];

            if (data < target) {
                start = (end - start) / 2 + start + 1;
            } else if (data > target) {
                end = (end - start) / 2 + start - 1;
            } else {

                int before = (end - start) / 2 + start;
                while (before > 0 && nums[before] == nums[before - 1]) {
                    before--;
                }

                return before;
            }
        }
        return -1;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
     * 求该青蛙跳上一个 n 级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     *
     * @param target
     * @return
     */
    public int jumpFloor(int target) {
        if (target == 1) return 1;
        if (target == 2) return 2;
        return jumpFloor(target - 1) + jumpFloor(target - 2);
    }

    /**
     * @param arr int整型一维数组 the array
     * @return int整型
     * 给定一个长度为n的数组arr，返回arr的最长无重复元素子数组的长度，无重复指的是所有数字都不相同。
     * 子数组是连续的，比如[1,3,5,7,9]的子数组有[1,3]，[3,5,7]等等，但是[1,3,7]不是子数组
     */
    public static int maxLength(int[] arr) {
        // write code here

        if (arr.length == 0) return 0;
        if (arr.length == 1) return 1;

        int max = 0;
        int j = 0;
        int k = 0;
        List list = new ArrayList();
        while (j < arr.length) {
            while (j < arr.length) {
                if (list.contains(arr[j])) {
                    if (max < list.size()) {
                        max = list.size();
                    }
                    k = list.indexOf(arr[j]);
                    list = list.subList(k + 1, list.size());
                    list.add(arr[j]);
                    System.out.println(arr[j]);
                    System.out.println(list);
                    j++;
                    break;
                }
                list.add(arr[j]);
                j++;
                if (max < list.size()) {
                    max = list.size();
                }
            }

        }
        return max;
    }

    /**
     * 4,5,1,6,2,7,3,8
     * 5：00
     * 给定一个长度为 n 的可能有重复值的数组，找出其中不去重的最小的 k 个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4(任意顺序皆可)。
     */
    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input.length == 0) {
            return list;
        }
        if (k == 0) {
            return list;
        }
        if (input.length < k) {
            for (int i = 0; i < input.length; i++)
                list.add(input[i]);
            return list;
        }
        list.add(input[0]);
        for (int i = 1; i < k; i++) {
            if (input[i] >= list.get(list.size() - 1)) {
                list.add(input[i]);
                continue;
            }
            if (input[i] <= list.get(0)) {
                list.add(0, input[i]);
                continue;
            }
            for (int j = 0; j < list.size() - 1; j++) {
                if (input[i] >= list.get(j) && input[i] <= list.get(j + 1)) {
                    list.add(j + 1, input[i]);
                    break;
                }
            }
        }
        for (int i = k; i < input.length; i++) {
            if (input[i] >= list.get(k - 1)) {
                continue;
            }
            if (input[i] <= list.get(0)) {
                list.add(0, input[i]);
                list.remove(k);
                continue;
            }
            for (int j = 0; j < k - 1; j++) {
                if (input[i] >= list.get(j) && input[i] <= list.get(j + 1)) {
                    list.add(j + 1, input[i]);
                    list.remove(k);
                    break;
                }
            }
        }

        return list;

    }

    /**
     * 有一个整数数组，请你根据快速排序的思路，找出数组中第 k 大的数。
     * 给定一个整数数组 a ,同时给定它的大小n和要找的 k ，请返回第 k 大的数(包括重复的元素，不用去重)，保证答案存在。
     * 要求：时间复杂度(nlogn)，空间复杂度(1)
     *
     * @param a
     * @param n
     * @param k
     * @return
     */
    public static int findKth(int[] a, int n, int k) {
        // write code here
        int start = 0;
        int end = n-1;
        int to = findN(a, start, end);

        while (to != k-1) {
            if (to < k-1) {
                start = to+1;
                to = findN(a, start, end);

            } else {
                end = to-1;
                to = findN(a, start, end);
            }
        }

        return a[to];

    }

    // 从小到大 7,8,9,4,2,19,88
    public static int findN(int[] a, int start, int end) {
        if(start>=end) {
            return start;
        }
        int a0 = a[start];
        int i = start;
        int j = end;
        while (i < j) {
            //  必须是从右到左，因为是取左边第一个来替换比较，如果出现相等也是向左边逼近
            while (a[j] <= a0 && i < j) {
                j--;
            }
            while (a[i] >= a0 && i < j) {
                i++;
            }

            if (i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        a[start] = a[i];
        a[i] = a0;
        return i;
    }


    private static void QuickSort(int[] num, int left, int right) {
        //如果left等于right，即数组只有一个元素，直接返回
        if(left>=right) {
            return;
        }
        //设置最左边的元素为基准值
        int key=num[left];
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i=left;
        int j=right;
        while(i<j){
            //j向左移，直到遇到比key小的值
            while(num[j]<=key && i<j){
                j--;
            }
            //i向右移，直到遇到比key大的值
            while(num[i]>=key && i<j){
                i++;
            }
            //i和j指向的元素交换
            System.out.println("***  i===>"+ i+" j==> "+j);
            if(i<j){
                int temp=num[i];
                num[i]=num[j];
                num[j]=temp;
            }
        }
        num[left]=num[i];
        num[i]=key;
        ArrayList arrayList = new ArrayList();
        for(int m = 0;m<num.length;m++){
            arrayList.add(num[m]);
        }
        System.out.println(arrayList+" xxx==> "+i+"  , "+ j);
        QuickSort(num,left,i-1);
        QuickSort(num,i+1,right);
    }

    /**
     * 输入一个长度为n的整型数组array，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
     * @param array
     * @return
     */
//    public int FindGreatestSumOfSubArray(int[] array) {
//
//    }
}