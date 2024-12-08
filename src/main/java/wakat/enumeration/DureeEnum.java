package wakat.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum DureeEnum {
    quart(0.25),
    demi(0.5),
    tierce(0.75),
    normal(1);

    private double valeur;
    private static Map map = new HashMap<>();

    private DureeEnum(double valeur){
        this.valeur = valeur;
    }

    static {
        for (DureeEnum duree : DureeEnum.values()){
            map.put(duree.valeur, duree);
        }
    }

    public static DureeEnum valeurOf(float duree){
        return (DureeEnum) map.get(duree);
    }

    public double getValeur() {
        return valeur;
    }
}
