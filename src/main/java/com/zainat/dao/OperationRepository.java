package com.zainat.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zainat.model.Operation;

public interface OperationRepository extends CrudRepository<Operation, Integer> {
	public List<Operation> findByLibelle(String libelle);
	public List<Operation> findByLibelleAndDateValeur(String libelle, Date dateValeur);
	public List<Operation> findByDateValeur(Date datevaleur);
	public List<Operation> findByMontantHT(Float montantHT);
	public List<Operation> findByMontantAndLibelle(Float montant, String libelle);
	public List<Operation> findByCategorie(String categorie);
}
