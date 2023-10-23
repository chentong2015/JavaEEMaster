package distributed_transactions.seata;

// Seata: �ֲ�ʽ������
// 1. ֧��TCC, ������(��Ӱ��ҵ���߼�)
// 2. ����ͨѶ�ײ����Netty��ʵ��
//
// TCC: �ֲ�ʽ������, ��ҵ���������(��Ҫ�ֶ��޸Ĵ��룬�Զ����ύ����)
public class BaseSeata {

    // TC: ȫ������Э���� DefaultCoordinator �����ͳ��
    // TM: ȫ����������� @GlobalTransactional, TransactionTemplate ִ�ж������ύ
    // RM: ��Դ������    DataSourceProxy, ConnectionProxy ����������м��: ����
    // XID: ȫ������ID   TransactionPropagationFilter

    // ʹ�ò��ԣ�
    // Server1: @Service
    // @GlobalTransaction
    // @Transactional
    // public void test() {
    //    daoObject.insert("server1");
    //    HttpClient.get("http://127.0.0.1:8082/server2/test");
    //    int i = 10 / 0;
    // }
    //
    // Server2: @Service
    // @GlobalTransaction �������Ҫ����ע�⣬����Server2������û�а취�ύ
    // @Transactional
    // public void test() {
    //    daoObject.insert("server2"); ��server2��DB�в�������
    // }
}