package com.lhx.cloud;

/**
 * @author lihongxu
 * @since 2019/4/5 上午10:39
 */
public class L1CacheMiss {
    private static final int RUNS = 10;
    private static final int DIMENSION_1 = 1024 * 1024;
    private static final int DIMENSION_2 = 6;

    private static long[][] longs;

    public static void main(String[] args) throws Exception {
        Thread.sleep(10000);
        longs = new long[DIMENSION_1][];
        for (int i = 0; i < DIMENSION_1; i++) {
            longs[i] = new long[DIMENSION_2];
            for (int j = 0; j < DIMENSION_2; j++) {
                longs[i][j] = 1L;
            }
        }
        System.out.println("starting....");

        long sum = 0L;
        for (int r = 0; r < RUNS; r++) {

            final long start = System.nanoTime();

////            slow
//            for (int j = 0; j < DIMENSION_2; j++) {
//                for (int i = 0; i < DIMENSION_1; i++) {
//                    sum += longs[i][j];
//                }
//            }

            //fast
            for (int i = 0; i < DIMENSION_1; i++) {
                for (int j = 0; j < DIMENSION_2; j++) {//每次取出6个相加
                    sum += longs[i][j];
                }
            }

            System.out.println((System.nanoTime() - start)/1000000.0);


        }

        System.out.println("sum:"+sum);
        while (true){
            Thread.sleep(1000);
        }

    }
}
