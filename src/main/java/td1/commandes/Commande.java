package td1.commandes;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import td1.paires.Paire;

import static com.sun.tools.javac.comp.Resolve.ReferenceLookupResult.StaticKind.reduce;

public class Commande {
    private List<Paire<Produit, Integer>> lignes;

    public Commande() {
        this.lignes = new ArrayList<>();
    }

    public Commande ajouter(Produit p, int q) {
        lignes.add(new Paire<>(p, q));
        return this;
    }

    public List<Paire<Produit, Integer>> lignes() {
        return lignes;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Commande\n");
        for (Paire<Produit, Integer> ligne : lignes) {
            str.append(String.format("%s x%d\n", ligne.fst(), ligne.snd()));
        }
        return str.toString();
    }


    public String formateurLigne(Paire<Produit, Integer> ligne){
        StringBuilder str = new StringBuilder();
        str.append(String.format("%s x%d\n", ligne.fst(), ligne.snd()));
        return str.toString();
    }

    public String toString1(){
        // return lignes.stream().map((Paire<Produit, Integer> ligne)->formateurLigne(ligne)).collect(Collectors.toList().toString();
        return lignes.stream().map(this::formateurLigne).collect(Collectors.toList()).toString();
    }


    /**
     * cumule les lignes en fonction des produits
     */
    public Commande normaliser() {
        Map<Produit, Integer> lignesCumulees = new HashMap<>();
        for (Paire<Produit, Integer> ligne : lignes) {
            Produit p = ligne.fst();
            int qte = ligne.snd();
            if (lignesCumulees.containsKey(ligne.fst())) {
                lignesCumulees.put(p, lignesCumulees.get(p) + qte);
            } else {
                lignesCumulees.put(p, qte);
            }
        }
        Commande commandeNormalisee = new Commande();
        for (Produit p : lignesCumulees.keySet()) {
            commandeNormalisee.ajouter(p, lignesCumulees.get(p));
        }
        return commandeNormalisee;
    }

    public Double cout(Function<Paire<Produit, Integer>, Double> calculLigne) {
        double rtr = 0;
        for (Paire<Produit, Integer> l : normaliser().lignes) {
            rtr += calculLigne.apply(l);
        }
        return rtr;
    }


    public Double cout1 (Function<Paire<Produit, Integer>, Double> calculLigne){

        return normaliser().lignes.stream().map((Paire<Produit,Integer> l) ->calculLigne.apply(l)).reduce(0.0,(x,y)->x+y);
    }

    public String affiche(Function<Paire<Produit, Integer>, Double> calculLigne) {
        Commande c = this.normaliser();
        final String HLINE = "+------------+------------+-----+------------+--------+------------+\n";
        StringBuilder str = new StringBuilder();
        str.append("\n\nCommande\n");
        str.append(HLINE);
        str.append("+ nom        + prix       + qté + prix ht    + tva    + prix ttc   +\n");
        str.append(HLINE);
        for (Paire<Produit, Integer> ligne : c.lignes) {
            str.append(String.format("+ %10s + %10.2f + %3d + %10.2f + %5.2f%% + %10.2f +\n", ligne.fst(), // nom
                    ligne.fst().prix(), // prix unitaire
                    ligne.snd(), // qté
                    ligne.fst().prix() * ligne.snd(), // prix ht
                    ligne.fst().cat().tva() * 100, // tva
                    calculLigne.apply(ligne)));
        }
        str.append(HLINE);
        str.append(String.format("Total : %10.2f", c.cout(calculLigne)));
        return str.toString();
    }

    public static <A,B> Map<A,List<B>> regrouper(List<Paire<A,B>> lignes){
        Map<A,List<B>> map = new HashMap<>();
        for(Paire<A,B> ligne : lignes){
            if(!map.containsKey(ligne.fst()))
                map.put(ligne.fst(),new ArrayList<>());

        }

    }


}
