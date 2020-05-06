/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

/**
 *
 * @author dev_tan
 */
public class PagamentoServico {

    public static void main(String[] args) {
        try {
//            DbProcessor db = new DbProcessor();
//            ArrayList<Long> lstContract = db.getAllContract();
//            String ref = "";
//            for (long contract : lstContract) {
//                ref = genReferenceId(contract + "");
//                db.updateContract(contract, ref);
//            }
//            System.out.println("Finish list contract: " + lstContract.size());
//        String entite = "60001";
//        String referrence = "003934006"; // 26
//        String amoun = "";
//        getCheckDigitoReferencia(entite, referrence, amoun);   
        } catch (Exception e) {
            System.out.println("Have exception: " + e.toString());
        }
    }

    public static void getCheckDigitoReferencia(String entidade, String referenciaSemCheckDigito, String montanteComDuasCasasDecimais) {
        StringBuffer digitos = new StringBuffer();
        digitos.append(entidade);
        digitos.append(referenciaSemCheckDigito);
        digitos.append(montanteComDuasCasasDecimais);
        int s = 0;
        int p = 0;
        for (int i = 0; i < digitos.length(); i++) {
            s = Integer.parseInt(String.valueOf(digitos.charAt(i))) + p;
            p = s * 10 % 97;
        }
        p = p * 10 % 97;
        int checkDigitoCalculado = 98 - p;
        int checkDigito = Integer.parseInt(referenciaSemCheckDigito);
        System.out.println("CheckDigito Calculado: " + checkDigitoCalculado);
        System.out.println("Referência com CheckDigito: " + (referenciaSemCheckDigito + "" + checkDigitoCalculado));
    }

    public static String genReferenceCounterId(String counterId) throws Exception {
        StringBuffer digitos = new StringBuffer();
        String fullContract = counterId;
        String referenceId;
        if (counterId == null || counterId.trim().length() <= 0) {
            System.out.println("ContractId is null or empty");
            return "E1";
        }
        if (counterId.length() > 7) {
            System.out.println("ContractId is to long over 7 character " + counterId);
            return "E2";
        }
        if (counterId.length() < 7) {
            for (int i = 0; i < 7 - counterId.length(); i++) {
                fullContract = "0" + fullContract;
            }
            System.out.println("ContractId is to shorter 7 character, the value after modify " + fullContract);
        }
        fullContract = fullContract + "10"; //Fix using month 10
        digitos.append("86871"); //Fix entidade for Movitel Payment System
        digitos.append(fullContract);
        digitos.append("");//Fix amount is empty
        int s = 0;
        int p = 0;
        for (int i = 0; i < digitos.length(); i++) {
            s = Integer.parseInt(String.valueOf(digitos.charAt(i))) + p;
            p = s * 10 % 97;
        }
        p = p * 10 % 97;
        int checkDigitoCalculado = 98 - p;
        if (checkDigitoCalculado < 10) {
            referenceId = fullContract + "0" + checkDigitoCalculado;
        } else {
            referenceId = fullContract + "" + checkDigitoCalculado;
        }
        System.out.println("Two last digit for checking : " + checkDigitoCalculado + " counterId " + counterId);
        System.out.println("ReferenceId : " + referenceId + " counterId " + counterId);
        return referenceId;
    }
    
}
