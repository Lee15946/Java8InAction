package code.chapter13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSplitter {

    private ListSplitter() {
    }

    static List<List<Integer>> subsets(List<Integer> list) {
        if (list.isEmpty()) {
            return List.of(Collections.emptyList());
        }
        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());

        List<List<Integer>> subans = subsets(rest);
        List<List<Integer>> subans2 = insertAll(first, subans);
        return concat(subans, subans2);
    }

    private static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b) {
        List<List<Integer>> result = new ArrayList<>(a);
        result.addAll(b);
        return result;
    }

    private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> result = new ArrayList<>();
        lists.forEach(list -> {
            List<Integer> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(list);
            result.add(copyList);
        });
        return result;
    }
}
