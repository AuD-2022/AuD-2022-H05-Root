package h05.utils;

import h05.tree.ListItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ListItemUtils {

    private ListItemUtils() {}

    @SafeVarargs
    public static <T> @Nullable ListItem<T> of(@Nullable T... keys) {
        ListItem<T> head = new ListItem<>();
        ListItem<T> tail = head;

        for (T key : keys) {
            tail = tail.next = new ListItem<>();
            tail.key = key;
        }

        return head.next;
    }

    @SuppressWarnings("ConstantConditions")
    public static <T> @NotNull ListItem<T> headOf(@Nullable T key, @Nullable ListItem<T> next) {
        ListItem<T> listItem = of(key);
        listItem.next = next;
        return listItem;
    }

    @SafeVarargs
    public static <T> @NotNull ListItem<T> append(@NotNull ListItem<T> head, @NotNull ListItem<T>... listItems) {
        ListItem<T> tail = getTail(head);

        for (ListItem<T> listItem : listItems) {
            tail.next = listItem;
            tail = getTail(tail);
        }

        return head;
    }

    public static <T> @NotNull ListItem<T> getTail(@NotNull ListItem<T> listItem) {
        return listItem.next == null ? listItem : getTail(listItem.next);
    }

    public static String toString(@Nullable ListItem<?> listItem) {
        return listItem == null ? "null" : "HEAD\n" + toStringHelper(listItem, new LinkedList<>());
    }

    private static String toStringHelper(ListItem<?> listItem, LinkedList<Boolean> linkedList) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean lastElement = listItem.next == null;

        linkedList.forEach(bool -> stringBuilder.append(bool ? "\u2502  " : "   "));
        stringBuilder.append(!lastElement ? '\u251C' : '\u2514')
            .append("\u2500 ")
            .append(listItem.key instanceof ListItem<?>
                ? "%s".formatted(ListItem.class.getName())
                : listItem.key)
            .append('\n');

        if (listItem.key instanceof ListItem<?> key) {
            linkedList.addLast(!lastElement);
            stringBuilder.append(toStringHelper(key, linkedList));
            linkedList.removeLast();
        }

        return stringBuilder.append(lastElement ? "" : toStringHelper(listItem.next, linkedList)).toString();
    }

    public static String toSimpleString(@Nullable ListItem<?> listItem) {
        StringBuilder builder = new StringBuilder();
        toSimpleStringHelper(listItem, builder);
        String result = builder.toString();
        return result.isBlank() ? "null" : "(" + result + ")";
    }

    private static void toSimpleStringHelper(@Nullable ListItem<?> listItem, StringBuilder builder) {
        for (ListItem<?> p = listItem; p != null; p = p.next) {
            if (p.key == null) {
                builder.append("()");
            } else if (p.key instanceof ListItem key) {
                builder.append('(');
                toSimpleStringHelper(key, builder);
                builder.append(')');
            } else {
                builder.append(p.key);
            }
            builder.append(p.next != null ? " " : "");
        }
    }

    public static <T> @Nullable ListItem<T> copy(@Nullable ListItem<T> listItem) {
        if (listItem == null) {
            return null;
        } else {
            return headOf(listItem.key, listItem.next == null ? null : copy(listItem.next));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable ListItem<T> deepCopy(@Nullable ListItem<T> listItem) {
        if (listItem == null) {
            return null;
        } else {
            return headOf(listItem.key instanceof ListItem<?> key ? (T) deepCopy(key) : listItem.key,
                listItem.next == null ? null : deepCopy(listItem.next));
        }
    }

    public static boolean equals(@NotNull ListItem<?> listItem1, @NotNull ListItem<?> listItem2) {
        return Objects.equals(listItem1.key, listItem2.key);
    }

    public static boolean deepEquals(@Nullable ListItem<?> listItem1, @Nullable ListItem<?> listItem2) {
        if (listItem1 == null || listItem2 == null) {
            return Objects.equals(listItem1, listItem2);
        }

        Object key1 = listItem1.key;
        Object key2 = listItem2.key;
        boolean keysEqual;

        if (key1 instanceof ListItem<?> l1 && key2 instanceof ListItem<?> l2) {
            keysEqual = deepEquals(l1, l2);
        } else if (!(key1 instanceof ListItem<?>) && !(key2 instanceof ListItem<?>)) {
            keysEqual = Objects.equals(key1, key2);
        } else {
            keysEqual = false;
        }

        if (listItem1.next != null && listItem2.next != null && keysEqual) {
            return deepEquals(listItem1.next, listItem2.next);
        } else {
            return keysEqual;
        }
    }

    public static boolean isCyclic(@Nullable ListItem<?> listItem) {
        return isCyclic(listItem, new HashSet<>());
    }

    private static boolean isCyclic(@Nullable ListItem<?> listItem, HashSet<ListItem<?>> references) {
        if (listItem == null) {
            return false;
        }

        if (references.contains(listItem)) {
            return true;
        } else {
            references.add(listItem);
            return (listItem.key instanceof ListItem<?> key && isCyclic(key, references)) || isCyclic(listItem.next, references);
        }
    }

    public static <T> @Nullable ListItem<T> fromList(List<T> list) {
        return (ListItem<T>) of(list.toArray(Object[]::new));
    }
}
