package td2.universite;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class Matiere {
    private String nom;

    public Matiere(String nom) {
        this.nom = nom;
    }

    public String nom() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Matiere))
            return false;
        return ((Matiere) obj).nom().equals(nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }

    @Override
    public String toString() {
        return nom;
    }

    public static final Function<Annee, Stream<Matiere>> matieresA = ;


    public static final Function<Etudiant,Stream<Map.Entry<Matiere,Integer>>> matieresCoefE
}
