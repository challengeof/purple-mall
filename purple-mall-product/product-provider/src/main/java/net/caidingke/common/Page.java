package net.caidingke.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bowen
 */
@Getter
@Setter
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 3979108363063036995L;
    private List<T> items = new ArrayList<>();

    private long count;

    private long showCount;

    private List<T> extraItems = new ArrayList<>();

    public Page() {
    }

    public Page(List<T> items, long count, long showCount) {
        this.items = items;
        this.count = count;
        this.showCount = showCount;
    }

    public Page<T> add(T item) {
        this.extraItems.add(item);
        return this;
    }

    public int size() {
        return this.items.size() + this.extraItems.size();
    }

    public boolean contains(T item) {
        return this.items.contains(item) || this.extraItems.contains(item);
    }
}
