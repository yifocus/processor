package processor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * ���̲߳���ִ�м����ȹ������
 * Created by focus on 2018/3/16.
 */
public class ProcessorExecuteService {

    Future future;
    ProcessorQueue chain;
    Map<String,Object> responseResult = new ConcurrentHashMap<>();
    public ProcessorExecuteService(List<IProcessor> processors){
        chain = new ProcessorQueue(this);
        for(IProcessor processor : processors){
            chain.addProcessor(processor);
        }
    }

    void addResult(String id,Object o){
        this.responseResult.put(id, o);
    }

    /**
     * ��ȡ����ִ�����֮��Ľ��
     * @param id
     * @return
     */
    public Object getProcessorResult(String id){
        if(future != null && !future.isDone()){
            try {
                future.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return responseResult.get(id);
    }

    /**
     * ����ִ��
     * <br>
     * @return
     */
    public void execute(){
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ProcessorAction(chain));
    }

    /**
     * �첽ִ������
     */
    public Future executeAsync(){
        ForkJoinPool pool = new ForkJoinPool();
        future = pool.submit(new ProcessorAction(chain));
        return future;
    }

    /**
     * �ͷ���Դ
     * @return
     */
    public boolean close(){

        chain.clear();
        chain = null;
        if(future != null){
            future.cancel(true);
            future = null;
        }

        responseResult.clear();
        responseResult = null;

        return true;
    }
}
