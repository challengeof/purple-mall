package net.caidingke.chenxi.algorithm;

/**
 * 排序算法
 *
 * @author bowen
 * @create 2016-11-14 14:59
 */

public class Sort {
    /**
     * 冒泡排序，顾名思义，就是像气泡一样，轻的在上，重的在下，从下往上扫描：凡扫描到违反本原则的轻气泡，就使其向上”飘浮”。
     * 如此反复进行，直到最后任何两个气泡都是轻者在上，重者在下为止。
     *
     * @param arr
     * @return
     */
    public static void bubbleSort(int arr[]) {

        if (arr.length <= 1) {
            return;
        }
        boolean flag;
        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;
            for (int j = 0; j < arr.length - i - 1; j++) {

                if (arr[j] > arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                return;
            }
        }
    }

    /**
     * 选择
     *
     * @param data
     */
    public static void selectSort(int[] data) {
        if (data == null || data.length == 0) {
            return;
        }

        for (int i = 0; i < data.length - 1; i++) {
            int min = i; // 将当前下标定为最小值下标
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[min]) {
                    min = j;
                }
            }

            if (i != min) {
                int tmp = data[i];
                data[i] = data[min];
                data[min] = tmp;
            }
        }
    }

    /**
     * 插入
     * 每一步都将一个待排数据按照其大小插入到已经排序的数据中的适当位置，算法导论里是用扑克牌来举例的，你每抓起一张牌都会放到适当的位置，
     * 也就是从摸牌开始到最后，你手里的牌是排好序的（大多数情况。）
     *
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j - 1] <= arr[j]) {
                    break;
                }
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
    }

    public static void mergeSort(int[] arr) {
        int len = arr.length;
        int[] reg = new int[len];
        mergeSortRecursive(arr, reg, 0, len - 1);
    }

    /**
     * 递归(假设有n个元素)
     * 1.讲序列每相邻的两个数字进行归并操作，形成floor(n/2)个序列，排序后每个序列包含两个元素
     * 2.将上诉序列再次归并，形成floor(n/4)个序列，每个序列包含四个元素
     * 3.重复步骤2，知道所有元素排序完毕
     *
     * @param arr
     * @param reg
     * @param start
     * @param end
     */
    private static void mergeSortRecursive(int[] arr, int[] reg, int start, int end) {
        if (start >= end) {
            return;
        }
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        mergeSortRecursive(arr, reg, start1, end1);
        mergeSortRecursive(arr, reg, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2) {
            reg[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        }
        while (start1 <= end1) {
            reg[k++] = arr[start1++];
        }
        while (start2 <= end2) {
            reg[k++] = arr[start2++];
        }
        for (k = start; k <= end; k++) {
            arr[k] = reg[k];
        }
    }

    /**
     * 迭代
     * 1.申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
     * 2.设定两个指针，最初位置分别为两个已经排序序列的起始位置
     * 3.比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
     * 4.重复步骤3直到某一指针到达序列尾
     * 5.将另一序列剩下的所有元素直接复制到合并序列尾
     *
     * @param arr
     */
    public static void mergeSortIteration(int[] arr) {
        int len = arr.length;
        int[] result = new int[len];
        int block, start;

        for (block = 1; block < len; block *= 2) {
            for (start = 0; start < len; start += 2 * block) {
                int low = start;
                int mid = (start + block) < len ? (start + block) : len;
                int high = (start + 2 * block) < len ? (start + 2 * block) : len;
                //两个块的起始下标及结束下标
                int start1 = low, end1 = mid;
                int start2 = mid, end2 = high;
                //开始对两个block进行归并排序
                while (start1 < end1 && start2 < end2) {
                    result[low++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
                }
                while (start1 < end1) {
                    result[low++] = arr[start1++];
                }
                while (start2 < end2) {
                    result[low++] = arr[start2++];
                }
            }
            int[] temp = arr;
            arr = result;
            result = temp;
        }
    }

    /**
     * 快排
     *
     * @param data
     * @param start
     * @param end
     */
    public static void quickSort(int[] data, int start, int end) {
        // 设置关键数据key为要排序数组的第一个元素，
        // 即第一趟排序后，key右边的数全部比key大，key左边的数全部比key小
        int key = data[start];
        // 设置数组左边的索引，往右移动比key大的数
        int i = start;
        // 设置数组右边的索引，往左移动比key小的数
        int j = end;
        // 如果左边索引比右边索引小，则还有数据没有排序
        while (i < j) {
            while (data[j] > key && j > i) {
                j--;
            }
            data[i] = data[j];

            while (data[i] < key && i < j) {
                i++;
            }
            data[j] = data[i];
        }
        // 此时 i==j
        data[i] = key;

        // 递归调用
        if (i - 1 > start) {
            // 递归调用，把key前面的完成排序
            quickSort(data, start, i - 1);
        }
        if (i + 1 < end) {
            // 递归调用，把key后面的完成排序
            quickSort(data, i + 1, end);
        }
    }

    /**
     * 快排
     *
     * @param arr
     * @param start
     * @param end
     */
    private static void quick(int[] arr, int start, int end) {

        int p;

        if (start < end) {

            p = partition(arr, start, end);
            quick(arr, start, p - 1);
            quick(arr, p + 1, end);
        }
    }

    private static int partition(int[] arr, int i, int j) {

        int p = arr[i];

        while (i < j) {//从区间两端交替向中间扫描，直至i=j为止
            while (i < j && arr[j] >= p) {//p相当于在位置i上
                j--;//从右向左扫描，查找第1个关键字小于p的记录arr[j]
            }
            if (i < j) {//表示找到的arr[j]的关键字<p
                arr[i++] = arr[j]; //相当于交换arr[i]和arr[j]，交换后i指针加1
            }
            while (i < j && arr[i] <= p) {//p相当于在位置j上
                i++;//从左向右扫描，查找第1个关键字大于p的记录arr[i]
            }
            if (i < j) {//表示找到了arr[i]，使arr[i]>p
                arr[j--] = arr[i];//相当于交换arr[i]和arr[j]，交换后j指针减1
            }
        }
        arr[i] = p;//基准记录已被最后定位
        return i;
    }

    public static void main(String[] args) {
//        int[] arr = {1, 3, 2, 6, 4, 8, 7};
//        bubbleSort(arr);
//        for (int i : arr) {
//            System.out.println(i);
//        }

        int a = 0;
        int b = 1;
        int m;
        System.out.print(" 0" + " ");
        do {
            m = a + b;
            int temp = 0;
            temp = m;//交换所得结果
            a = b;
            b = temp;
            System.out.print(a + " ");
        } while (m <= 1000);
    }

}
