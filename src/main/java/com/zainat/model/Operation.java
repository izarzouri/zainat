package com.zainat.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Operation")
@Table(name = "zain_operation")
public class Operation extends GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer operationId;

	private String categorie;
	private String type;
	
	@Column(unique=true)
	private String libelle;
	private Float montant;
	private Float tva;
	private Float montantHT;
	
	private Date dateValeur;

	public Operation(String categorie, String type, String libelle, Float montant, Date dateValeur) {
		this.categorie = categorie;
		this.type = type;
		this.libelle = libelle;
		this.montant = montant;
		this.dateValeur = dateValeur;
	}

	public Operation() {
		super();
	}
	
	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Float getMontant() {
		return montant;
	}

	public Float getTva() {
		return tva;
	}

	public void setTva(Float tva) {
		this.tva = tva;
	}

	public Float getMontantHT() {
		return montantHT;
	}

	public void setMontantHT(Float montantHT) {
		this.montantHT = montantHT;
	}

	public void setMontant(Float montant) {
		this.montant = montant;
	}

	public Date getDateValeur() {
		return dateValeur;
	}

	public void setDateValeur(Date dateValeur) {
		this.dateValeur = dateValeur;
	}

	@Override
	public String toString() {
		return "Operation [categorie=" + categorie + ", type=" + type + ", libelle=" + libelle + ", montant=" + montant
				+ ", tva=" + tva + ", montantHT=" + montantHT + ", dateValeur=" + dateValeur + "]";
	}
}
