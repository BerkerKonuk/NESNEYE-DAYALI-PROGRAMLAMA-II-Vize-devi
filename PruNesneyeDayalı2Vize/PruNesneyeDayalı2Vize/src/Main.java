import database.Database;
import repository.*;
import model.*;

import java.util.Scanner;
// Veritabanı için Yardımcı main.java
public class Main {
    public static void main(String[] args) {

        Database.createTables();

        BookRepository bookRepo = new BookRepository();
        StudentRepository studentRepo = new StudentRepository();
        LoanRepository loanRepo = new LoanRepository();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== SMART LIBRARY =====");
            System.out.println("1- Kitap Ekle");
            System.out.println("2- Kitapları Listele");
            System.out.println("3- Öğrenci Ekle");
            System.out.println("4- Öğrencileri Listele");
            System.out.println("5- Kitap Ödünç Ver");
            System.out.println("6- Ödünç Listesini Gör");
            System.out.println("7- Kitap Teslim Al");
            System.out.println("0- Çıkış");
            System.out.print("Seçim: ");
            int sec = sc.nextInt(); sc.nextLine();

            switch (sec) {
                case 1 -> {
                    System.out.print("Kitap Adı: "); String t= sc.nextLine();
                    System.out.print("Yazar: "); String a = sc.nextLine();
                    System.out.print("Yıl: "); int y = sc.nextInt();
                    bookRepo.add(new Book(t,a,y));
                }
                case 2 -> bookRepo.getAll().forEach(System.out::println);
                case 3 -> {
                    System.out.print("İsim: "); String n=sc.nextLine();
                    System.out.print("Bölüm: "); String d=sc.nextLine();
                    studentRepo.add(new Student(n,d));
                }
                case 4 -> studentRepo.getAll().forEach(System.out::println);
                case 5 -> {
                    System.out.print("Öğrenci ID: "); int sid = sc.nextInt();
                    System.out.print("Kitap ID: "); int bid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tarih (gg.aa.yyyy): "); String date=sc.nextLine();
                    loanRepo.add(new Loan(bid,sid,date));
                }
                case 6 -> loanRepo.getAll().forEach(System.out::println);
                case 7 -> {
                    System.out.print("Loan ID: "); int lid=sc.nextInt(); sc.nextLine();
                    System.out.print("Teslim Tarihi: "); String dt=sc.nextLine();
                    loanRepo.returnBook(lid,dt);
                }
                case 0 -> System.exit(0);
                default -> System.out.println("Hatalı seçim.");
            }
        }
    }
}
