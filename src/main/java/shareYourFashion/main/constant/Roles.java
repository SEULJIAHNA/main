package shareYourFashion.main.constant;

public enum Roles {

    ADMIN , USER;

    private static final String ROLE_PREFIX = "ROLE_";

    public String getSecurityRole() {
        return ROLE_PREFIX + name();
    }
}
