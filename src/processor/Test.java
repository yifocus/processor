package processor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by focus on 2018/3/16.
 */
public class Test {
    public static void main(String[] args) throws Exception {

        Test t = new Test();
        List<IProcessor> list = new CopyOnWriteArrayList<>();
        list.add(new Process1("任务一",true));
        list.add(new Process1("任务二",true));
        list.add(new Process1("任务三",false));
        list.add(new Process1("任务四",false));
        list.add(new Process1("任务五",true));
        list.add(new Process1("任务六",true));
        list.add(new Process1("任务七",true));


        long start = System.currentTimeMillis();
        ProcessorExecuteService processorPool = new ProcessorExecuteService(list);
        processorPool.executeAsync();

        System.out.println("end execute: " + (System.currentTimeMillis() - start));

        System.out.println(processorPool.getProcessorResult("任务一"));
        System.out.println(processorPool.getProcessorResult("任务二"));
        System.out.println(processorPool.getProcessorResult("任务三"));
        System.out.println(processorPool.getProcessorResult("任务四"));
        System.out.println(processorPool.getProcessorResult("任务五"));
        System.out.println(processorPool.getProcessorResult("任务六"));
        System.out.println(processorPool.getProcessorResult("任务七"));

        System.out.println("end get : " + (System.currentTimeMillis() - start));

    }
}


class Process1 implements IProcessor {

    private Boolean isAsync;
    private String id;
    Process1(String id,boolean isAsync){
        this.isAsync = isAsync;
        this.id = id;
    }

    @Override
    public String id() {
        return id;
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
