package fr.maoz.barscanner;

public class Product {
    public static final String TABLE_NAME = "products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REFERENCE = "reference";
    public static final String COLUMN_EMPLACEMENT = "emplacement";
    public static final String COLUMN_STOCK = "stock";

    private int id;
    private String reference;
    private String emplacement;
    private int stock;


    // Creation table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    /*+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"*/
                    + COLUMN_REFERENCE + " TEXT PRIMARY KEY,"
                    + COLUMN_EMPLACEMENT + " TEXT,"
                    + COLUMN_STOCK + " INTEGER"
                    +")";

    public Product() {

    }
    public Product(/*int id, */String reference, String emplacement, int stock) {
        this.id = id;
        this.reference = reference;
        this.emplacement = emplacement;
        this.stock = stock;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}