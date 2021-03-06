package splitter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Register {
    private final List<Transaction> list;

    public Register() {
        this.list = new ArrayList<>();
    }

    public List<Transaction> getList() {
        return Collections.unmodifiableList(list);
    }

    public void add(Transaction record) {
        list.add(record);
    }
}
