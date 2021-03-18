public class CandidateModel {

    private int id;
    String firstName, lastName, address, party, elecArea;

    public CandidateModel() {

    }


    /*Getters and Setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getElecArea() {
        return elecArea;
    }

    public void setElecArea(String elecArea) {
        this.elecArea = elecArea;
    }

}
