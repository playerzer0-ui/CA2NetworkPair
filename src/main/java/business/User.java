package business;

public class User {
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password) {
        String validationError = validateUsernameAndPassword(username, password);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }

    private String validateUsernameAndPassword(String username, String password) {
        String usernameError = validateUsername(username);
        String passwordError = validatePassword(password);

        if (usernameError != null && passwordError != null) {
            return usernameError + "\n" + passwordError;
        } else if (usernameError != null) {
            return usernameError;
        } else if (passwordError != null) {
            return passwordError;
        }
        return null;
    }

    private String validateUsername(String username) {
        if (!isValidUsername(username)) {
            return "Invalid username. Username must be at least 5 characters long and cannot be empty.";
        }
        return null;
    }

    private String validatePassword(String password) {
        if (!isValidPassword(password)) {
            return "Invalid password. Password must be at least 8 characters long, cannot be empty, and must contain at least one capital letter.";
        }
        return null;
    }

    private boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() >= 5;
    }

    private boolean isValidPassword(String password) {
        return password != null && !password.trim().isEmpty() && password.length() >= 8 && containsCapitalLetter(password);
    }

    private boolean containsCapitalLetter(String password) {
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }


    public User(String username, String password, boolean isAdmin) {
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        } else {
            throw new IllegalArgumentException("Username and password cannot be blank");
        }
    }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            return username.equals(user.username);
        }

        @Override
        public int hashCode() {
            return username.hashCode();
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", isAdmin=" + isAdmin +
                    '}';
        }
    }


