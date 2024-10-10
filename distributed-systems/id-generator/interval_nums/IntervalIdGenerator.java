import java.util.ArrayList;
import java.util.List;

// 约定范围区间ID生成器:
// 排除指定IDs后，在指定的区间范围内逐个生成下一个ID
public class IntervalIdGenerator {

    private long nextId;
    private int intervalEndIndex;
    private final List<Long> intervalList;

    public IntervalIdGenerator(List<Long> excludedIdList, long maxIdLimit) {
        this.intervalList = new ArrayList<>();
        this.buildIntervalList(excludedIdList, maxIdLimit);

        this.nextId = intervalList.get(0);
        this.intervalEndIndex = 1;
    }

    // list = 0 1 2 7 8 ... limit
    // IntervalList = [3~6] + [9...limit] 保存闭合区间
    private void buildIntervalList(List<Long> excludedIdList, long maxIdLimit) {
        if (maxIdLimit <= 0) {
            throw new RuntimeException("Limit can't be less than 0");
        }
        excludedIdList.add(maxIdLimit);
        // TODO. 避免因为重复ID造成计算IntervalList区间出错 => 无论如何都需要排序
        excludedIdList = excludedIdList.stream().sorted().distinct().toList();

        long startIdValue = 0;
        for (long idValue : excludedIdList) {
            if (startIdValue < idValue) {
                intervalList.add(startIdValue);
                intervalList.add(idValue - 1);
                startIdValue = idValue + 1;
            } else {
                startIdValue++;
            }
            // 不考虑超过指定Limit值的数据
            if (idValue == maxIdLimit) {
                break;
            }
        }
        if (intervalList.isEmpty()) {
            throw new RuntimeException("Cannot generate IntervalList");
        }
    }

    // TODO. 多线程来获取Next ID需要保证并发安全
    // 取完一个区间后移动过到下一个区间，可能区间中所有ID被取完
    public synchronized long getNextId() {
        if (this.nextId > intervalList.get(intervalEndIndex)) {
            if (this.intervalEndIndex == intervalList.size() - 1) {
                throw new RuntimeException("Can not get Next ID any more");
            }
            this.nextId = intervalList.get(this.intervalEndIndex + 1);
            this.intervalEndIndex += 2;
        }
        return this.nextId++;
    }
}
