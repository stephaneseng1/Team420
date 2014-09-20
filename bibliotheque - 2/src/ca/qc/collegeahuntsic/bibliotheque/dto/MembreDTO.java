package ca.qc.collegeahuntsic.bibliotheque.dto;
/**
 * Permet de représenter un tuple de la table membre.
 * 
 */

public class MembreDTO {

	public int idMembre;
	public String nom;
	public long telephone;
	public int limitePret;
	public int nbPret;

	private int getIdMembre() {
		return idMembre;
	}

	private void setIdMembre(int idMembre) {
		this.idMembre = idMembre;
	}

	private String getNom() {
		return nom;
	}

	private void setNom(String nom) {
		this.nom = nom;
	}

	private long getTelephone() {
		return telephone;
	}

	private void setTelephone(long telephone) {
		this.telephone = telephone;
	}

	private int getLimitePret() {
		return limitePret;
	}

	private void setLimitePret(int limitePret) {
		this.limitePret = limitePret;
	}

	private int getNbPret() {
		return nbPret;
	}

	private void setNbPret(int nbPret) {
		this.nbPret = nbPret;
	}
}
