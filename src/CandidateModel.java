import java.util.Scanner;

public class CandidateModel implements Comparable<CandidateModel>{

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String cityRegion;
    private String party;
    private String elecArea;

    // setting sorting type
    // 1 - by id
    // 2 - by first name
    // 3 - by second name
    // 4 - by address
    // 5 - by party
    // 6 - by election area
    private static int sortingMethod = 1;

    public CandidateModel(String csvLine) {
        Scanner sectionScanner = new Scanner(csvLine);

        // using " delimiter to separate out address from the rest of the csv line
        sectionScanner.useDelimiter("\"");

        String beforeQuotesSection = sectionScanner.next(); // id, first name, last name
        String address = sectionScanner.next();             // address
        String afterQuotesSection = sectionScanner.next();  // party, electoral area

        // extracting data from first section
        String [] nameParts = beforeQuotesSection.split(",");
        this.id = Integer.parseInt(nameParts[0]);
        this.firstName = nameParts[1];
        this.lastName = nameParts[2];

        // extracting data from address section
        String [] addressParts = address.split(",");

        String add = "";
        for (int i = 0; i < addressParts.length-1; i++) {
            // address only here
            add += addressParts[i];
            if (i != addressParts.length-2)
                add +=",";
        }
        this.address = add;
        this.cityRegion = addressParts[addressParts.length -1].substring(1);    // removing extra space

        // extracting data from third section
        String [] partyParts = afterQuotesSection.split(",");
        this.party = partyParts[1];
        this.elecArea = partyParts[2];

        sectionScanner.close();
    }

    public CandidateModel() {

    }

    /*Getters and Setters*/
    public static void setSortingMethod(int sm)
    {
        // checking for valid sorting method
        if (sm > 0 && sm <= 6)
        {
            sortingMethod = sm;
        }
        else
        {
            // if user entered invalid sorting method, we set it to default
            sortingMethod = 1;
        }
    }

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

    public String getCityRegion() {
        return cityRegion;
    }

    public void setCityRegion(String cityRegion) {
        this.cityRegion = cityRegion;
    }

    @Override
    public String toString() {
        return
                "\nCandidate NO: " + id +
                "\nName: " + firstName + " " + lastName +
                "\nAddress: " + address + ", " + cityRegion +
                "\nParty: '" + party + "'" +
                "\nLocal Electoral Area: " + elecArea + "\n";
    }

    @Override
    public int compareTo(CandidateModel o) {
        if (sortingMethod == 1)     //Comparing by id
        {
            if (id < o.getId())
                return -1;
            else if (id > o.getId())
                return 1;
            else
                return 0;
        }
        else if (sortingMethod == 2)//Comparing by first name
        {
            return firstName.compareTo(o.getFirstName());
        }
        else if (sortingMethod == 3)//Comparing by last name
        {
            return lastName.compareTo(o.getLastName());
        }
        else if (sortingMethod == 4)//Comparing by address
        {
            return address.compareTo(o.getAddress());
        }
        else if (sortingMethod == 5)//Comparing by party
        {
            return party.compareTo(o.getParty());
        }
        else                        //Comparing by elections area
        {
            return elecArea.compareTo(o.getElecArea());
        }
    }
}
