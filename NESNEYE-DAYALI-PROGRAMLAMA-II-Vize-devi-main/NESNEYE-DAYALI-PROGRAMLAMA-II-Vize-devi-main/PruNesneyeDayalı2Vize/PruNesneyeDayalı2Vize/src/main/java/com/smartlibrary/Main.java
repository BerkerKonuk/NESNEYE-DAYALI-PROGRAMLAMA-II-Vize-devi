package com.smartlibrary;

public class Main {
    public static void main(String[] args) {
        DatabaseHelper.createTable();

        DatabaseHelper.addBook("Kürk Mantolu Madonna", "Sabahattin Ali");
        DatabaseHelper.addBook("Simyacı", "Paulo Coelho");
        DatabaseHelper.addBook("Yüzüklerin Efendisi", "J.R.R. Tolkien");

        System.out.println("--- İlk Ekleme Sonrası Liste ---");
        DatabaseHelper.listBooks();

        DatabaseHelper.deleteBook(1);

        System.out.println("--- Silme İşlemi Sonrası Liste ---");
        DatabaseHelper.listBooks();
    }
}