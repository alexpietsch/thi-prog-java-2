public class Person implements DatenEinlesen, DatenAusgeben {

    private String name;
    private String email;
    
    public Person() {
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }


    @Override
    public String[] informationen() {
        return new String[]{name,email};
    }

    @Override
    public String wasFehltNoch() {
        if(name != null && !name.isEmpty()){
            return name;
        }
        if(email != null && !email.isEmpty()){
            return email;
        }
        return null;
    }

    @Override
    public void setzeNaechstenWert(String value) {
        if(name != null && !name.isEmpty()){
            this.name = value;
        }
        if(email != null && !email.isEmpty()){
            this.email = value;
        }
    }
}