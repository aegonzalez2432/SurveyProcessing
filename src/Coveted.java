public class Coveted {
    private String gender, ageGroup, state, rating;
    public Coveted(String g, String age, String st, String rate){
        this.gender = g;
        ageGroup = age;
        state = st;
        rating = rate;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public String getGender() {
        return gender;
    }

    public String getRating() {
        return rating;
    }

    public String getState() {
        return state;
    }
}
