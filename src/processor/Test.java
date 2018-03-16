package processor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by focus on 2018/3/16.
 */
public class Test {

    class Process1 implements IProcessor {

        private Boolean isAsync;
        Process1(boolean isAsync){
            this.isAsync = isAsync;
        }

        @Override
        public String id() {
            return "process1";
        }

        @Override
        public boolean isAsyn() {
            return isAsync;
        }

        @Override
        public Object process() {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" hello " + id() + " isAsync " + isAsyn());
            return id();
        }
    }

    class Process2 implements IProcessor {

        private Boolean isAsync;
        Process2(boolean isAsync){
            this.isAsync = isAsync;
        }
        @Override
        public String id() {
            return "process2";
        }

        @Override
        public boolean isAsyn() {
            return isAsync;
        }

        @Override
        public Object process() {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" hello " + id() + " isAsync " + isAsyn());
            return id();
        }
    }
    class Process3 implements IProcessor {

        @Override
        public String id() {
            return "process3";
        }

        private Boolean isAsync;
        Process3(boolean isAsync){
            this.isAsync = isAsync;
        }
        @Override
        public boolean isAsyn() {
            return isAsync;
        }

        @Override
        public Object process() {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" hello " + id() + " isAsync " + isAsyn());
            return id();
        }
    }

    class Process4 implements IProcessor {

        @Override
        public String id() {
            return "process4";
        }

        private Boolean isAsync;
        Process4(boolean isAsync){
            this.isAsync = isAsync;
        }
        @Override
        public boolean isAsyn() {
            return isAsync;
        }

        @Override
        public Object process() {
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" hello " + id() + " isAsync " + isAsyn());
            return id();
        }
    }


    public static void main(String[] args) throws Exception {

        Test t = new Test();
        List<IProcessor> list = new CopyOnWriteArrayList<>();
        list.add(t.new Process1(true));
        list.add(t.new Process2(true));
        list.add(t.new Process3(true));
        list.add(t.new Process4(true));
        list.add(t.new Process2(true));
        list.add(t.new Process1(true));

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.currentTimeMillis();
        ProcessorExecuteService processorPool = new ProcessorExecuteService(list);
        processorPool.executeAsync();

        System.out.println("end execute" + (System.currentTimeMillis() - start));

        System.out.println(processorPool.getProcessorResult("process1"));
        System.out.println(processorPool.getProcessorResult("process2"));
        System.out.println(processorPool.getProcessorResult("process3"));
        System.out.println(processorPool.getProcessorResult("process4"));

        System.out.println("end get" + (System.currentTimeMillis() - start));

    }
}
