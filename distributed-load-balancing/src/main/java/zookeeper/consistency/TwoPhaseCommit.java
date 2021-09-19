package zookeeper.consistency;

// 两阶段提交: 预提交->收到ack回复->提交
// 1. 什么时候进行第二次的提交?
//    当leader收到的ack回复超过半数(包括leader自己)

// 2. 第二次提交之后是否需要等待 ?
//    TODO: 异步提交: 不需要等所有的提交完成
//    LearnerHandler -> leader.processAck(); leader接收到ack之后如何处理ack
//
//    将包添加到队列中，然后提交自己的，再返回
//    commit(zxid); follower提交
//    inform(p);
//    然后leader提交自己的请求，更新数据到内存中
//
//    sendPacket(QuorumPacket qp) {  将包发送给所有的follower结点
//       for(LearnerHandler f: forwardingFollowers) {
//         f.queuePacket(qp);  只是将包添加到LinkedBlockingQueue<>有界阻塞队列中
//       }
//    }
public class TwoPhaseCommit {
    
}
