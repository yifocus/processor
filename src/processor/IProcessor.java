package processor;

/**
 * ����ӿ�
 * Created by focus on 2018/3/16.
 */
public interface IProcessor {

    /**
     * ����id
     * @return
     */
    public String id();

    /**
     * �Ƿ����첽
     * @return true �첽��false ͬ��
     */
    public boolean isAsyn();

    /**
     * �����߼�����
     * @return
     */
    public Object process();

}
