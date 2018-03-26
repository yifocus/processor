package processor;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务链表
 * Created by focus on 2018/3/16.
 */
public class ProcessorQueue{

    ConcurrentLinkedQueue<IProcessor> queue = new ConcurrentLinkedQueue<>();
    private AtomicInteger size = new AtomicInteger();
    ProcessorExecuteService processorService;

    public ProcessorQueue(ProcessorExecuteService processorService){
        this.processorService = processorService;
    }

    public void addProcessor(IProcessor processor){
        size.incrementAndGet();
        queue.add(processor);
    }

    public boolean isProcessEnable(){
        return size.get() == 1;
    }

    public IProcessor nextProcessor(){
        return queue.poll();
    }

    public ProcessorQueue buildNewQueue(IProcessor processor){
        ProcessorQueue queue = new ProcessorQueue(processorService);
        queue.addProcessor(processor);
        return queue;
    }

    public void clear(){
        queue.clear();
        size.set(0);
    }
}
