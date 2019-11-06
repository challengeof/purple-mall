package net.caidingke.chenxi.algorithm;

/**
 * 查找算法
 *
 * @author bowen
 * @create 2016-11-14 17:06
 */

public class BinarySearch {

    /**
     * 二分法
     *
     * @param a
     * @param key
     * @return
     */
    public static int binarySearch(int[] a, long key) {
        int low = 0;
        int high = a.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int midVal = a[mid];

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {

                high = mid - 1;
            } else {

                return mid;
            }
        }
        return -1;
    }
}
