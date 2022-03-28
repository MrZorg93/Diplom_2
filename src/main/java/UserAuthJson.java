import org.apache.commons.lang3.RandomStringUtils;

public class UserAuthJson {

    private String email;
    private String password;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserAuthJson(){}

    public UserAuthJson(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserAuthJson(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserAuthJson randomUser( int emailLengths, int nameLength, int passwordLength){
        String email = RandomStringUtils.randomAlphabetic(emailLengths) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(passwordLength);
        String name = RandomStringUtils.randomAlphabetic(nameLength);
        if(emailLengths == 0) email = "";
        return new UserAuthJson(email, password, name);
    }


    @Override
    public String toString(){
        return("User: \n   " +
                "email : "+ email +"\n   " +
                "password : "+ password +"\n   " +
                "name : "+ name);
    }
}
