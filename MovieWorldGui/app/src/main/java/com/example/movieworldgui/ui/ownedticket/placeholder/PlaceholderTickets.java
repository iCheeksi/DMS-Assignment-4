package com.example.movieworldgui.ui.ownedticket.placeholder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class PlaceholderTickets {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<TicketItem> ITEMS = new ArrayList<TicketItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, TicketItem> ITEM_MAP = new HashMap<String, TicketItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createPlaceholderItem(i));
//        }
    }

    public static void addItem(TicketItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static TicketItem createPlaceholderItem(int position) {
        return new TicketItem(String.valueOf(position), "Ticket " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Ticket: ").append(position);

        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class TicketItem implements Serializable {
        public final String id;
        public final String content;
        public final String details;

        public TicketItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}