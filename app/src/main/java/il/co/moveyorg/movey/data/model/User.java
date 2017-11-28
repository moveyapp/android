package il.co.moveyorg.movey.data.model;

/**
 * Created by eladk on 11/27/17.
 */

public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private String country;

    public User() {
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userName = builder.userName;
        this.country = builder.country;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getCountry() {
        return country;
    }

    public static class Builder {
        String firstName;
        String lastName;
        String userName;
        String id;
        String email;
        String country;

        public Builder(String id, String email) {
            this.id = id;
            this.email = email;
        }

        public Builder firstName(String firstName) {
          this.firstName = firstName;
          return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
