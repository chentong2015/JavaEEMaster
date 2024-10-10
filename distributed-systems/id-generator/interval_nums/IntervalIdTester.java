import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntervalIdTester {

    // 测试案例分析:
    // Case 1: 测试边界异常
    // Case 2: 测试excludedIdList包含重复ID
    // Case 3: 测试excludedIdList没有重复ID
    // Case 4: 测试下一个ID取到limit极限值
    // Case 5: 测试limit位于excludedIdList表中间位置
    public static void main(String[] args) {
        // Long[] items = {0L, 1L, 2L, 5L, 10L};
        // List<Long> excludedIdList = new ArrayList<>(Arrays.asList(items));
        // IntervalIdGenerator idGenerator = new IntervalIdGenerator(excludedIdList, 7L);
        // System.out.println(idGenerator.getNextId());
        // System.out.println(idGenerator.getNextId());
        // System.out.println(idGenerator.getNextId());

        Long[] items1 = {0L, 1L, 1L, 1L, 2L, 2L, 5L, 10L};
        List<Long> excludedIdList1 = new ArrayList<>(Arrays.asList(items1));
        IntervalIdGenerator idGenerator1 = new IntervalIdGenerator(excludedIdList1, 20L);
        System.out.println(idGenerator1.getNextId());
        System.out.println(idGenerator1.getNextId());
        System.out.println(idGenerator1.getNextId());
    }
}
