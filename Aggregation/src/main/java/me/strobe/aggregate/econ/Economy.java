package me.strobe.aggregate.econ;

import lombok.Getter;
import me.strobe.aggregate.Aggregation;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.Formatter;

@Getter
public class Economy {

    private final String name;
    private final String namePlural;
    private final byte decimalCount;
    private final long maxValue;

    private Economy(Economy.Builder builder){
        this.name = builder.name;
        this.namePlural = builder.namePlural;
        this.decimalCount = builder.decimalcount;
        this.maxValue = builder.maxValue;
    }

    public String format(final double p0) {
        return String.format("%."+decimalCount+"f", p0);
    }

    boolean hasAccount(final OfflinePlayer p0) {
        return Aggregation.getPlugin().getDm().hasAccount(p0);
    }

    boolean hasAccount(final OfflinePlayer p0, final String p1) {
        return Aggregation.getPlugin().getDm().hasAccount(p0, p1);
    }

    double getBalance(final OfflinePlayer p0) {
        return Aggregation.getPlugin().getDm().getBalance(p0);
    }

    double getBalance(final OfflinePlayer p0, final String p1) {
        return Aggregation.getPlugin().getDm().getBalance(p0, p1);
    }

    boolean has(final OfflinePlayer p0, final double p1) {
        return Aggregation.getPlugin().getDm().has(p0, p1);
    }

    boolean has(final OfflinePlayer p0, final String p1, final double p2) {
        return Aggregation.getPlugin().getDm().has(p0, p1, p2);
    }

    EconomyResponse withdrawPlayer(final OfflinePlayer p0, final double p1) {
        assert hasAccount(p0);
    }

    EconomyResponse withdrawPlayer(final OfflinePlayer p0, final String p1, final double p2) {
        assert hasAccount(p0, p1);

    }

    EconomyResponse depositPlayer(final OfflinePlayer p0, final double p1) {
        assert hasAccount(p0);

    }

    EconomyResponse depositPlayer(final OfflinePlayer p0, final String p1, final double p2) {
        assert hasAccount(p0, p1);
    }

    EconomyResponse createBank(final String p0, final OfflinePlayer p1) {

    }

    EconomyResponse deleteBank(final String p0) {

    }

    EconomyResponse bankBalance(final String p0) {

    }

    EconomyResponse bankHas(final String p0, final double p1) {

    }

    EconomyResponse bankWithdraw(final String p0, final double p1) {

    }

    EconomyResponse bankDeposit(final String p0, final double p1) {

    }

    EconomyResponse isBankOwner(final String p0, final OfflinePlayer p1) {

    }

    EconomyResponse isBankMember(final String p0, final OfflinePlayer p1) {

    }

    boolean createPlayerAccount(final OfflinePlayer p0) {
        assert !hasAccount(p0);
        return Aggregation.getPlugin().getDm().createPlayerAccount(p0);
    }

    boolean createPlayerAccount(final OfflinePlayer p0, final String p1) {
        assert !hasAccount(p0, p1);
        return Aggregation.getPlugin().getDm().createPlayerAccount(p0, p1);
    }

    public static class Builder {
        private String name;
        private String namePlural;
        private byte decimalcount;
        private long maxValue;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNamePlural(String namePlural) {
            this.namePlural = namePlural;
            return this;
        }

        public Builder setDecimalcount(byte decimalcount) {
            this.decimalcount = decimalcount;
            return this;
        }

        public Builder setMaxValue(long maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        public Economy build(){
            return new Economy(this);
        }

    }
}