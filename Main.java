import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static final Map<String, List<String>> GRAPH_COUNTRIES = Map.ofEntries(
            Map.entry("BW", List.of("BY", "HE", "RP")),
            Map.entry("BY", List.of("BW", "HE", "TH", "SN")),
            Map.entry("BE", List.of("BB")),
            Map.entry("BB", List.of("BE", "MV", "NI", "SN", "ST")),
            Map.entry("HB", List.of("NI")),
            Map.entry("HH", List.of("NI", "SH")),
            Map.entry("HE", List.of("BY", "BW", "NI", "NW", "RP", "TH")),
            Map.entry("MV", List.of("BB", "NI", "SH")),
            Map.entry("NI", List.of("HB", "HH", "HE", "NW", "ST", "SH", "TH", "BB", "MV")),
            Map.entry("NW", List.of("NI", "HE", "RP")),
            Map.entry("RP", List.of("NW", "HE", "BW", "SL")),
            Map.entry("SL", List.of("RP")),
            Map.entry("SN", List.of("BY", "TH", "ST", "BB")),
            Map.entry("ST", List.of("NI", "BB", "SN", "TH")),
            Map.entry("SH", List.of("NI", "HH", "MV")),
            Map.entry("TH", List.of("BY", "HE", "NI", "ST", "SN"))
    );

    public static List<String> breitenSucheWeg(final Map<String, List<String>> graph,
                                       final String start,
                                       final String ende) {

        final Map<String, String> vorgaenger = new HashMap<>();
        final Queue<String> warteschlange = new ArrayDeque<>();
        final List<String> pfad = new ArrayList<>();

        vorgaenger.put(start, null);
        warteschlange.add(start);

        while (!warteschlange.isEmpty()) {
            final String knoten = warteschlange.remove();

            if (knoten.equals(ende)) {
                for (String vorg = knoten; vorg != null; vorg = vorgaenger.get(vorg)) {
                    pfad.add(vorg);
                }
                break;
            }

            for (final String nachbar : graph.get(knoten)) {
                if (!vorgaenger.containsKey(nachbar)) {
                    vorgaenger.put(nachbar, knoten);
                    warteschlange.add(nachbar);
                }
            }
        }
        return pfad.reversed();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Von: ");
            String von = scanner.nextLine().trim();

            System.out.print("Bis: ");
            String bis = scanner.nextLine().trim();

            if (GRAPH_COUNTRIES.containsKey(von) && GRAPH_COUNTRIES.containsKey(bis)) {
                final List<String> weg = breitenSucheWeg(GRAPH_COUNTRIES, von, bis);
                for (String string : weg) {
                    System.out.println(string);
                }
            } else {
                System.out.println("Bitte gib gültige Bundeslandkürzel ein, z. B. HB und BE.");
            }
        }
    }
}
