package com.zhoujian.algorithm;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合并时间区间集合
 *
 * 给定条件:
 *
 * 1、给定的时间区间集合按照开始时间从小到大排序，即为有序
 *
 * 2、其中每一行表示一个时间区间（闭区间），0表示时间区间的开始，1表示时间区间结束
 *
 * @author zhoujian
 */
public class MergeTimeIntervals {

    public static void main(String[] args) {
        int [][] timeIntervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
//        int [][] timeIntervals = {{4,5}, {1,4}, {0,1}};
//        int [][] timeIntervals = {{1, 4}, {4, 5}};
        int [][] result = merge(timeIntervals);
        System.out.println(Arrays.deepToString(result));
    }


    /**
     * 时间区间合并方法
     *
     * @param intervals
     * @return
     */
    public static int [][] merge(int [][] intervals) {
        // 二维数组行数
        int rows = intervals.length;

        if (rows <= 1) {
            return intervals;
        }

        // 排序
        sort(intervals, rows);

        // 使用同样大小的二维数组用于存放计算结果
        int [][] resultIntervals = new int[rows][2];
        // 指向上一次计算出的结果区间
        int resultIntervalsPosition = 0;
        resultIntervals[resultIntervalsPosition] = intervals[0];
        /**
         * 如果给定区间集合中当前位置区间的左节点数值大于上一次计算出的结果区间的右节点，
         * 那么此时必定不存在交集，将该区间放入结果集合中
         * 反之，则重新计算结果并更新对应的结果区间（区间右节点取最大值）
         */
        for (int i = 1; i < rows; i++) {
            int[] currentResultInterval = resultIntervals[resultIntervalsPosition];
            if (intervals[i][0] > currentResultInterval[1]) {
                resultIntervalsPosition ++;
                resultIntervals[resultIntervalsPosition] = intervals[i];
            } else {
                currentResultInterval[1] = Math.max(intervals[i][1], currentResultInterval[1]);
                resultIntervals[resultIntervalsPosition] = currentResultInterval;
            }
        }
        // 使用相等大小的数组作为结果集时，可能会出现结果集中存在空值情况，所以在末尾进行一次拷贝
        return Arrays.copyOf(resultIntervals, resultIntervalsPosition + 1, resultIntervals.getClass());
    }

    /**
     * 冒泡排序, 对二维数组按照开始时间节点进行排序
     * @param intervals
     * @param size
     * @return
     */
    private static int [][] sort(int [][] intervals, int size) {
        if (size <= 1) {
            return intervals;
        }
        for (int i = 0; i < size; ++i) {
            boolean flag = false;
            for (int j = 0; j < size - i - 1; ++j) {
                if (intervals[j][0] > intervals[j + 1][0]) {
                    // 开始交换
                    int [] temp = intervals[j];
                    intervals[j] = intervals[j + 1];
                    intervals[j + 1] = temp;
                    // 表示存在数据交换
                    flag = true;
                }
            }
            if (!flag){
                // 没有数据交换，提前退出
                break;
            }
        }
        return intervals;
    }

    /**
     * 时间区间合并方法
     *
     * @param intervals
     * @return
     */
    public static int [][] mergeWithList(int [][] intervals) {
        int rows = intervals.length;
        List<int []> resultIntervals = new ArrayList<>();
        int resultIntervalsPosition = -1;
        resultIntervals.add(intervals[0]);
        resultIntervalsPosition ++;
        for (int i = 1; i < rows; i++) {
            int[] currentResultInterval = resultIntervals.get(resultIntervalsPosition);
            if (intervals[i][0] > currentResultInterval[1]) {
                resultIntervals.add(intervals[i]);
                resultIntervalsPosition ++;
            } else {
                currentResultInterval[1] = Math.max(intervals[i][1], currentResultInterval[1]);
                resultIntervals.set(resultIntervalsPosition, currentResultInterval);
            }
        }
        return resultIntervals.toArray(new int[resultIntervalsPosition][]);
    }

}
