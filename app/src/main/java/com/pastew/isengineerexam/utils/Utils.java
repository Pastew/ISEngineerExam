package com.pastew.isengineerexam.utils;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by Pastew on 2015-12-11.
 */
public class Utils {

    /**
     *
     * @param arrayLength number of elements
     * @param left inclusive left boundary
     * @param right inclusive right boundary
     * @return array of random elements from range @left to @right (including left and right)
     */
    public static int[] getRandomArray(int arrayLength, int left, int right) {
        if(right > arrayLength)
            arrayLength = right-left+1;

        HashSet<Integer> set = new HashSet<>();
        Random random = new Random();

        while(set.size() < arrayLength){
            int thisOne = random.nextInt(right+1-left) + left;
            set.add(thisOne);
        }

        int[] array = new int[set.size()];

        int i = 0;
        for(Integer in : set){
            array[i] = in.intValue();
            ++i;
        }

        return array;
    }

}
