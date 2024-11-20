import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ATMCashMachine {

    private static final Map<Integer, User> users = new HashMap<>();


    public static void main(String[] args) {

        Account account1 = new Account(123, 1234, 1250.00);
        Account account2 = new Account(234, 2345, 7900.00);
        Account account3 = new Account(345, 3456, 5440.00);
        Account account4 = new Account(456, 4567, 3270.00);
        Account account5 = new Account(567, 5678, 6320.00);


        User user1 = new User("Kätriin", account1);
        User user2 = new User("Kermo", account2);
        User user3 = new User("Peeter", account3);
        User user4 = new User("Triinu", account4);
        User user5 = new User("Bert", account5);

        users.put(account1.getAccountNumber(), user1);
        users.put(account2.getAccountNumber(), user2);
        users.put(account3.getAccountNumber(), user3);
        users.put(account4.getAccountNumber(), user4);
        users.put(account5.getAccountNumber(), user5);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Tere Tulemast Swedbank Sularahaautomaati");
        System.out.println("Kuna teil kaarti ei ole, siis sisestage oma konto number: ");

        int accountNumber = scanner.nextInt();

        User currentUser = users.get(accountNumber);
        if (currentUser != null) {

            System.out.println("Tere, " + currentUser.getName());

            if (validatePin(scanner, currentUser.getAccount())) {
                showMenu(scanner, currentUser.getAccount());
            } else {
                System.out.println("Vale PIN kood. Ligipääs puudub.");
            }
        } else {
            System.out.println("Kontot ei leitud. Sulgen süsteemi!");
        }
    }


    private static boolean validatePin(Scanner scanner, Account account) {

        System.out.println("Sisestage oma PIN kood: ");

        int enteredPin = scanner.nextInt();
        return enteredPin == account.getPin();
    }

    private static void showMenu(Scanner scanner, Account account) {
        while (true) {
            System.out.println("   MENÜÜ:");
            System.out.println("1. Kontrolli konto jääki");
            System.out.println("2. Sisesta raha");
            System.out.println("3. Võta raha välja");
            System.out.println("4. Muuda PIN koodi");
            System.out.println("5. Sulge");
            System.out.println("Sisesta valik:");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> checkBalance(account);
                case 2 -> depositMoney(scanner, account);
                case 3 -> withdrawMoney(scanner, account);
                case 4 -> changePin(scanner, account);
                case 5 -> {
                    System.out.println("Täname, et Kasutasite Swedbank Sularahaautomaati!");
                    return;
                }
                default -> System.out.println("Vale valik! Proovige uuesti 1-5 menüüst!");
            }
        }
    }

    private static void checkBalance(Account account) {
        System.out.println("Teie kontojääk on: " + account.getBalance());
    }

    private static void depositMoney(Scanner scanner, Account account) {
        System.out.println("Sisestage summa mida soovite sisestada:D : ");
        double amount = scanner.nextDouble();
        if (account.deposit(amount)){
            System.out.println("Sisestatud raha: " + amount +  " Kontojääk peale tehingut: " + account.getBalance());
        } else {
            System.out.println("Vale summa. Sisestamine ebaõnnestus.");
        }


    }
    private static void withdrawMoney(Scanner scanner, Account account) {
        System.out.print("Sisestage summa mida soovite välja võtta: ");
        double amount = scanner.nextDouble();
        if (account.withdraw(amount)) {
            System.out.println("Välja võetud summa: " + amount + " Kontojääk peale tehingut:" + account.getBalance());
        } else {
            System.out.println("Ebapiisav saldo või vale summa. Väljavõtmine ebaõnnestus.");
        }
    }

    private static void changePin(Scanner scanner, Account account) {
        System.out.println("Sisestage PIN kood: ");
        int enteredPin = scanner.nextInt();

        if (enteredPin == account.getPin()) {
            System.out.println("Sisestage uus PIN kood: ");
            int newPin = scanner.nextInt();
            System.out.println("Kinnitage uusi PIN kood: ");
            int confirmPin = scanner.nextInt();

            if (newPin == confirmPin) {
                account.setPin(newPin);
                System.out.println("New pin code is: " + account.getPin());
                System.out.println("PIN kood edukalt vahetatud");
            } else {
                System.out.println("PIN kood ei ühti. Proovige uuesti.");
            }
        } else {
            System.out.println("Vale PIN kood. PIN koodi vahetus ebaõnnestus.");
        }
    }
}
