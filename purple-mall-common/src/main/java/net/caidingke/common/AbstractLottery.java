package net.caidingke.common;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bowen
 */
public abstract class AbstractLottery<T> {
    private static final Map<Integer, Double> RATE = new ConcurrentHashMap<>();

    //TODO 通用的算法概率应该外部传入
    static {
        RATE.put(0, 0.4);
        RATE.put(1, 0.3);
        RATE.put(2, 0.15);
        RATE.put(3, 0.1);
        RATE.put(4, 0.05);
    }

    private AbstractLottery() {

    }

    private static <T> List<T> lucky(List<T> candidate, int prizeQuantity) {
        Random random = new Random();
        Set<T> luckyStars = new HashSet<>(prizeQuantity);
        while (luckyStars.size() < prizeQuantity) {
            luckyStars.add(candidate.get(random.nextInt(candidate.size())));
        }
        return new ArrayList<>(luckyStars);
    }

    public static <T> List<T> lottery(List<T> candidate, int prizeQuantity, boolean fair) {
        if (CollectionUtils.isEmpty(candidate) || prizeQuantity <= 0) {
            throw new RuntimeException("Candidate can not be empty or end prize quantity can not be less or equal to 0!");
        }
        if (prizeQuantity >= candidate.size()) {
            return candidate;
        }
        if (fair) {
            return FairLottery.lottery(candidate, prizeQuantity);
        }
        return ProbabilityLottery.lottery(candidate, prizeQuantity);
    }

    private static final class FairLottery<T> extends AbstractLottery<T> {
        private static <T> List<T> lottery(List<T> candidate, int prizeQuantity) {
            Collections.shuffle(candidate);
            return lucky(candidate, prizeQuantity);
        }
    }

    private static final class ProbabilityLottery extends AbstractLottery {

        private static <T> List<T> lottery(List<T> candidate, int prizeQuantity) {
            Set<T> luckyStars = new HashSet<>(prizeQuantity);
            while (luckyStars.size() < prizeQuantity) {
                List<T> result = new ArrayList<>();
                List<List<T>> partition = partition(candidate, RATE.size());
                for (int i = 0; i < partition.size(); i++) {
                    List<T> ts = partition.get(i);
                    result.addAll(shuffleSublist(ts, (int) Math.ceil((ts.size() * RATE.get(i)))));
                }
                List<T> lucky = lucky(result, 1);
                luckyStars.addAll(lucky);
                candidate.removeAll(lucky);
            }
            return new ArrayList<>(luckyStars);
        }

        static <T> List<T> shuffleSublist(List<T> objects, int size) {
            if (CollectionUtils.isEmpty(objects)) {
                return Collections.emptyList();
            }
            Collections.shuffle(objects);
            return ImmutableList.copyOf(Iterables.limit(objects, size));
        }

        static <T> List<List<T>> partition(List<T> source, int n) {
            List<List<T>> result = new LinkedList<>();
            int remainder = source.size() % n;
            int number = source.size() / n;
            int offset = 0;
            for (int i = 0; i < n; i++) {
                List<T> value;
                if (remainder > 0) {
                    value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                    remainder--;
                    offset++;
                } else {
                    value = source.subList(i * number + offset, (i + 1) * number + offset);
                }
                result.add(value);
            }
            return result;
        }
    }
}
