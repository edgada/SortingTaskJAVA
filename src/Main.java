public class Main {
    public static void main(String[] args) {
        String naujasFailas = Rikiavimas.surikiuotiFaila("input.txt");
        if (naujasFailas != "0")System.out.println("Atlikta! Rezultatas išsaugotas " + naujasFailas + " faile.");
        else System.out.println("Įvyko klaida!");
    }
}
