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
        list.add(new Process1("����һ",true));
        list.add(new Process1("�����",true));
        list.add(new Process1("������",false));
        list.add(new Process1("������",false));
        list.add(new Process1("������",true));
        list.add(new Process1("������",true));
        list.add(new Process1("������",true));


        long start = System.currentTimeMillis();
        ProcessorExecuteService processorPool = new ProcessorExecuteService(list);
        processorPool.executeAsync();

        System.out.println("end execute: " + (System.currentTimeMillis() - start));

        System.out.println(processorPool.getProcessorResult("����һ"));
        System.out.println(processorPool.getProcessorResult("�����"));
        System.out.println(processorPool.getProcessorResult("������"));
        System.out.println(processorPool.getProcessorResult("������"));
        System.out.println(processorPool.getProcessorResult("������"));
        System.out.println(processorPool.getProcessorResult("������"));
        System.out.println(processorPool.getProcessorResult("������"));

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
