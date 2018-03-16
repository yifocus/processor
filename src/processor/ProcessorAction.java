package processor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveTask;

/**
 * 多线程任务调度处理
 * Created by focus on 2018/3/16.
 */
public class ProcessorAction extends RecursiveTask{

    private ProcessorQueue queue;
    public ProcessorAction(ProcessorQueue queue){
        this.queue = queue;
    }

    @Override
    protected Object compute() {

        IProcessor processor;
        Object result = null;

        if(queue.isProcessEnable()){
            processor = queue.nextProcessor();
            return processor.process();
        }else{

            List<IProcessor> asyncProcessors = new CopyOnWriteArrayList<>();
            while((processor = queue.nextProcessor()) != null){
                if(processor.isAsyn()){
                    asyncProcessors.add(processor);
                }else{

                    if(!asyncProcessors.isEmpty()){
                        invokeAllAsyncProcessors(asyncProcessors);
                    }

                    // 执行同步程序
                    ProcessorAction action = new ProcessorAction(queue.buildNewQueue(processor));
                    result = action.invoke();

                    queue.processorService.addResult(processor.id(),result);
                    asyncProcessors.clear();
                }
            }

            if(!asyncProcessors.isEmpty()){
                invokeAllAsyncProcessors(asyncProcessors);
            }
        }
        return result;
    }

    /**
     * 执行所有异步程序
     * @param asyncProcessors
     */
    public void invokeAllAsyncProcessors(Collection<IProcessor> asyncProcessors){

        Map<String, ProcessorAction> actionMap = new HashMap<String, ProcessorAction>();
        for(IProcessor processor: asyncProcessors){
            actionMap.put(processor.id(), new ProcessorAction(queue.buildNewQueue(processor)));
        }

        Collection<ProcessorAction> actions = actionMap.values();
        for(ProcessorAction action : actions){
            action.fork();
        }

        ProcessorExecuteService result = queue.processorService;
        for(Map.Entry<String, ProcessorAction> entry : actionMap.entrySet()){
            result.addResult(entry.getKey(),entry.getValue().join());
        }
    }
}
