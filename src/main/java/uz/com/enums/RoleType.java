package uz.com.enums;

public enum RoleType {
    SYS_ADMIN("SYS_ADMIN"),
    ADMIN("ADMIN"),
    SUPPLY_ADMIN("SUPPLY_ADMIN"),
    SUPPLY_RESPONSIBLE("SUPPLY_RESPONSIBLE"),
    SUPPLY_USER("SUPPLY_USER"),
    DIRECTOR("DIRECTOR"),
    FINANCE_HEAD("FINANCE_HEAD"),
    FINANCE_ADMIN("FINANCE_ADMIN"),
    FINANCE_USER("FINANCE_USER"),
    VIEW("VIEW"),
    FOUNDER("FOUNDER");

    public String code;

    RoleType(String code) {
        this.code = code;
    }
}
