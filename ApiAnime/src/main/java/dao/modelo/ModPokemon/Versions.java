package dao.modelo.ModPokemon;

import com.google.gson.annotations.SerializedName;

public class Versions {

    @SerializedName("generation-iii")
    private GenerationIii generationIii;

    @SerializedName("generation-ii")
    private GenerationIi generationIi;

    @SerializedName("generation-v")
    private GenerationV generationV;

    @SerializedName("generation-iv")
    private GenerationIv generationIv;

    @SerializedName("generation-vii")
    private GenerationVii generationVii;

    @SerializedName("generation-i")
    private GenerationI generationI;

    @SerializedName("generation-viii")
    private GenerationViii generationViii;

    @SerializedName("generation-vi")
    private GenerationVi generationVi;

    public GenerationIii getGenerationIii() {
        return generationIii;
    }

    public GenerationIi getGenerationIi() {
        return generationIi;
    }

	public GenerationV getGenerationV(){
		return generationV;
	}

	public GenerationIv getGenerationIv(){
		return generationIv;
	}

	public GenerationVii getGenerationVii(){
		return generationVii;
	}

	public GenerationI getGenerationI(){
        return generationI;
    }

    public GenerationViii getGenerationViii() {
        return generationViii;
    }

    public GenerationVi getGenerationVi() {
        return generationVi;
    }

    @Override
    public String toString() {
        return
                "Versions{" +
                        "generation-iii = '" + generationIii + '\'' +
                        ",generation-ii = '" + generationIi + '\'' +
                        ",generation-v = '" + generationV + '\'' +
                        ",generation-iv = '" + generationIv + '\'' +
                        ",generation-vii = '" + generationVii + '\'' +
                        ",generation-i = '" + generationI + '\'' +
                        ",generation-viii = '" + generationViii + '\'' +
                        ",generation-vi = '" + generationVi + '\'' +
                        "}";
    }
}