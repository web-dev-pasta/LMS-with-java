import java.util.*;

public class LibraryManagementSystem {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private Scanner scanner;
    private int MAX_BORROW_LIMIT = 1;

    public LibraryManagementSystem() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        scanner = new Scanner(System.in);
        booksAndMembers();
    }

    private void booksAndMembers() {
        books.add(new Book("1984", "George Orwell", "1234", 1949));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "5678", 1960));
        members.add(new Member("John Doe", "M001"));
        members.add(new Member("Jane Smith", "M002"));
    }

    public void run() {
    while (true) {
        try {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAvailableBooks();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    addNewBook();
                    break;
                case 6:
                    viewMemberDetails();
                    break;
                case 7:
                    System.out.println("Good bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    continue;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice!");
            scanner.nextLine();
        }
    }}
    
    private void displayMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Display Available Books");
        System.out.println("2. Search Books");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Add New Book");
        System.out.println("6. View Member Details");
        System.out.println("7. Exit");
        System.out.print("Enter choice: ");
    }

    private void displayAvailableBooks() {
        System.out.println("\nAvailable Books:");
        boolean found = false;
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Sorry, There is no available at the moment.");
        }
    };
    private void searchBooks() {
        System.out.print("Enter search keyword (title/author): ");
        String keyword = scanner.nextLine().toLowerCase();
        
        System.out.println("\nSearch Results:");
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword) || 
                book.getAuthor().toLowerCase().contains(keyword)) {
                    found = true;
                    System.out.println(book);
            }
        }
        if (!found) {
            System.out.println("No books found matching '" + keyword + "'");
        }
    };
    private void borrowBook() {};
    private void returnBook() {};
    private void addNewBook() {};
    private void viewMemberDetails() {};

    private Member findMember(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().toLowerCase().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    private Book findBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        library.run();
    }
}

class Book {
    private String title;
    private String author;
    private String isbn;
    private int year;
    private boolean available;

    public Book(String title, String author, String isbn, int year) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.available = true;
    }
    
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("'%s' by %s (%d) - ISBN: %s - %s", 
                            title, author, year, isbn, 
                            available ? "Available" : "Borrowed");
    }
}

class Member {
    private String name;
    private String memberId;
    private ArrayList<Book> borrowedBooks;
    private static final int MAX_BORROW_LIMIT = 1;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }

    public boolean borrowMemberBook(Book book) {
        Boolean result = !!(borrowedBooks.size() < MAX_BORROW_LIMIT);
        if (borrowedBooks.size() < MAX_BORROW_LIMIT) {
            borrowedBooks.add(book);
        }
        return result;
    }

    public boolean returnMemberBook(Book book) {
        return borrowedBooks.remove(book);
    }
    
    public int getBorrowedBooks() {
        return this.borrowedBooks.size();
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books borrowed");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println("  - " + book.getTitle());
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Member: %s (ID: %s) - Borrowed: %d/%d books", 
                            name, memberId, borrowedBooks.size(), MAX_BORROW_LIMIT);
    }
}