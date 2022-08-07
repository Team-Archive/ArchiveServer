package site.archive.domain.user;

public enum UserRole {

    ADMIN("ROLE_ADMIN"), GENERAL("ROLE_GENERAL");

    private final String role;


    UserRole(String role) {
        this.role = role;
    }

    public static UserRole fromRoleString(String str) {
        for (UserRole role : UserRole.values()) {
            if (role.toString().equals(str)) {return role;}
        }
        throw new IllegalArgumentException("존재하지 않는 등급입니다: " + str);
    }

    @Override
    public String toString() {
        return role;
    }
}
