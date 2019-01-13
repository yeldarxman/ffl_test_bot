package models;

public enum CurrencyType {
    KZT("KZT"),
    USD("USD");

    private String value;

    CurrencyType(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }
}
