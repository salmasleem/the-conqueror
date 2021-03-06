package views.button;

import engine.City;

public class CityButton extends StyledButton {

    private City city;
    private boolean built = false;

    public CityButton(String s, int size) {
        super(s, size);
        setEnabled(false);
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
