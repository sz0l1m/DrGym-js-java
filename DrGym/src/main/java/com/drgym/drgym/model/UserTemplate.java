public abstract class UserTemplate {
    private int id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public UserTemplate(
            int id,
            String username,
            String name,
            String surname,
            String email,
            String password // will be encrypted in the future
    ) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // setters
    public void setId(int newId) {
        this.id = newId;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
