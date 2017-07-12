package com.zainat.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.zainat.dao.OperationRepository;
import com.zainat.model.Operation;

@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@Service
public class ZainatServiceImpl implements ZainatService {

	@Resource
	OperationRepository operationRepositoryImpl;

	@Value("${zainat.compta.operation.annee}")
	private String anneeComptable;
	
	@Value("${zainat.compta.operation.csv.separator:;}")
	private String csvSeparator;

	@Override
	public void process() throws ParseException {
		Document doc = null;
		try {
			doc = Jsoup.parse(new File("C://workarea//workspace-zainat//zainat//resources//Releve.html"), null);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		final Element operationsDiv = doc.getElementById("template-liste-rop-pro");
		final Elements operqtionsUL = operationsDiv.getElementsByTag("ul");
		final Elements operations = operqtionsUL.get(0).getElementsByTag("li");

		final List<Operation> ops = new ArrayList<>();

		for (final Element operation : operations) {
			final Elements details = operation.getElementsByTag("div");

			String categorie = "";
			String type = "";
			String libelle = "";
			Float montant = 0f;
			String dateValeurStr = "";
			Date dateValeur = new Date();

			for (final Element detail : details) {

				if (detail.toString().contains("rop-detail col-45")) {
					libelle = detail.getElementsByTag("p").get(0).ownText();
				} else if (detail.toString().contains("col-20 rop-montant pull-right")) {
					montant = Float.valueOf(detail.getElementsByTag("strong").get(0).ownText().replace("€", "")
							.replace(".", "").replace(",", "."));
					categorie = montant > 0 ? "Crédit" : "Débit";
				} else if (detail.toString().contains("rop-cat") && detail.toString().contains("Valeur")) {
					dateValeurStr = detail.ownText().replace("Valeur", "").concat("/" + anneeComptable).trim();
					dateValeur = new SimpleDateFormat("dd/MM/yyyy").parse(dateValeurStr);
				}
			}
			type = buildTypeDebit(montant, libelle);
			final Operation op = new Operation(categorie, type, libelle, montant, dateValeur);
			ops.add(op);
		}

		// final List<Operation> credits = ops.stream().filter(op ->
		// op.getMontant() > 0).collect(Collectors.toList());
		// final List<Operation> debits = ops.stream().filter(op ->
		// op.getMontant() < 0).collect(Collectors.toList());

		final Comparator<Operation> comparator = Comparator.comparing(Operation::getDateValeur);
		ops.sort(comparator);

		for (final Operation operation : ops) {
			List<Operation> foundOperations = operationRepositoryImpl.findByLibelleAndDateValeur(operation.getLibelle(),
					operation.getDateValeur());
			if (CollectionUtils.isEmpty(foundOperations)) {
				operationRepositoryImpl.save(operation);
			}
			System.out.println(this.toCSV(foundOperations.get(0)));
		}

	}

	private static String buildTypeDebit(Float montant, String libelle) {
		String type = "Autre";

		if (montant > 0) {
			if (libelle.toLowerCase().contains("odhcom")) {
				type = "Paiement facture client";
			} else if (libelle.toLowerCase().contains("rembours")) {
				type = "Avoir/remboursement";
			}
		} else {
			if (libelle.toLowerCase().contains("retrait")) {
				type = "Retrait espèce";
			} else if (libelle.toLowerCase().contains("facture") || libelle.toLowerCase().contains("paiement")) {
				type = "Paiement par carte";
			} else if (libelle.toLowerCase().contains("salaire")) {
				type = "Virement salaire";
			} else if (libelle.toLowerCase().contains("prlv") || libelle.toLowerCase().contains("prelevement")) {
				type = "Prélèvement";
			} else if (libelle.toLowerCase().contains("chq") || libelle.toLowerCase().contains("cheque")) {
				type = "Paiement par chèque";
			} else if (libelle.toLowerCase().contains("vir")) {
				type = "Virement";
			}
		}
		return type;
	}

	@Override
	public String toCSV(Operation op) {
		return op.getDateValeur() + csvSeparator + op.getLibelle() + csvSeparator + op.getMontant() + csvSeparator
				+ op.getTva() + csvSeparator + op.getMontantHT() + csvSeparator;
	}
	
}
