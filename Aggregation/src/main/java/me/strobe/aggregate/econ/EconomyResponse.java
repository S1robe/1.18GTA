package me.strobe.aggregate.econ;

public class EconomyResponse {
    public final double amount;
    public final double balance;
    public final ResponseType type;
    public final String errorMessage;

    public EconomyResponse(final double amount, final double balance, final ResponseType type, final String errorMessage) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.errorMessage = errorMessage;
    }

    public boolean transactionSuccess() {
        return this.type == ResponseType.SUCCESS;
    }

    public enum ResponseType
    {
        SUCCESS(1),
        FAILURE(2),
        NOT_IMPLEMENTED(3);

        private final int id;

        ResponseType(final int id) {
            this.id = id;
        }

        int getId() {
            return this.id;
        }
    }
}