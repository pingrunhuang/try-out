import java.util.concurrent.CountDownLatch;

/**
 * @author frank
 * @date 2019/06/26
 */
public class LatchTest {



    public static void main(String[] args){

        Runnable taskTemp = new Runnable() {

            private int iCounter;
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // 发起请求
                    //                    HttpClientOp.doGet("https://www.baidu.com/");
                    iCounter++;
                    System.out.println(System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter = " + iCounter);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        LatchTest latchTest = new LatchTest();
        latchTest.startAll(5,taskTemp);
    }



    public long startAll(int threadNums, final Runnable task){

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);


        for (int i = 0; i<threadNums;i++){
            Thread t = new Thread(){
                @Override
                public void run(){
                    try{
                        startGate.await();
                        try{

                            task.run();

                        }finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();
        }


        long start = System.currentTimeMillis();
        startGate.countDown();


        long end = System.currentTimeMillis();

        System.out.println(Thread.currentThread().getName());
        return end-start;
    }

}
