package model.entity.enums;

public enum OrderType {
    BUY,
    SELL;

    @Override
    public String toString() {
        return name(); // باعث میشه در ComboBox و TableView همون “Buy” یا “Sell” نمایش داده بشه
    }
}
