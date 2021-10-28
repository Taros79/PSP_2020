package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class GenerationVii {

    @SerializedName("icons")
    private Icons icons;

    @SerializedName("ultra-sun-ultra-moon")
    private UltraSunUltraMoon ultraSunUltraMoon;

    public Icons getIcons() {
        return icons;
    }

    public UltraSunUltraMoon getUltraSunUltraMoon() {
        return ultraSunUltraMoon;
    }

    @Override
    public String toString() {
        return
                "GenerationVii{" +
                        "icons = '" + icons + '\'' +
                        ",ultra-sun-ultra-moon = '" + ultraSunUltraMoon + '\'' +
                        "}";
    }
}