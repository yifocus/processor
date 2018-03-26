package processor;

/**
 * 任务接口
 * Created by focus on 2018/3/16.
 */
public interface IProcessor {

    /**
     * 任务id
     * @return
     */
    public String id();

    /**
     * 是否是异步
     * @return true 异步，false 同步
     */
    public boolean isAsyn();

    /**
     * 任务逻辑处理
     * @return
     */
    public Object process();

}
