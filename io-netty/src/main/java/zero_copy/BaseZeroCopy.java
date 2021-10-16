package zero_copy;

import java.nio.ByteBuffer;

// 零拷贝技术:
// 1. 通过引用直接操作物理内存(直接内存)，数据不需要存储在JVM的堆内存中
// 2. 操作需要通过native方法调用操作系统的内核函数
// 使用场景: Netty, Kafka
public class BaseZeroCopy {

    public void showExecuteInterval() {
        long startTime = System.currentTimeMillis();
        testAccessHeapMemory();
        testAccessDirectMemory();
        long endTime = System.currentTimeMillis();
        System.out.println("Execute interval: " + (endTime - startTime) + " ms");
    }

    // ByteBuffer.allocate()
    // 使用HeapByteBuffer直接在JVM的数据区"堆"内存中分配空间
    // 添加的数据可以直接通过buffer查看
    private void testAccessHeapMemory() {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        for (int j = 0; j < 200; j++) buffer.putInt(j);
        buffer.flip(); // 重置到写入的其实位置
        for (int j = 0; j < 200; j++) buffer.getInt();
        buffer.clear();
    }

    // 使用DirectByteBuffer调用操作系统内核函数分配内存空间，返回指向内存空间的指针
    // DirectByteBuffer() {
    //    base = unsafe.allocateMemory(size);
    //    address = base;  然后将指针(引用)address属性(存的是内存地址)
    // }
    // TODO: java通过native本地方法调用openjdk底层c语言的实现
    // unsafe.cpp
    // UNSAFE_ENTRY(..)
    //    void* x = os::malloc(sz, mtInternal); 分配内存空间，并返回指向内存空间的指针
    //    return addr_to_java(x);  将指针转成java类型
    private void testAccessDirectMemory() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1000);
        for (int j = 0; j < 200; j++) buffer.putInt(j);
        buffer.flip();
        for (int j = 0; j < 200; j++) buffer.getInt();
        buffer.clear();
    }
}
