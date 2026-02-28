//
// Created by Blondi on 4/1/2025.
//

#include "Teste.h"
#include <cassert>
#include <fstream>
#include <iostream>
#include "../Erori/Exceptii.h"
using std::cout;
Teste::Teste(const Repo_oferta& repo, Service_oferta& service, const Cos& cos): repo{repo}, service{service}, cos{cos}{

}

void Teste::test_get() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    assert(oferta.getId()==1);
    assert(oferta.getDenumire()=="Afandou");
    assert(oferta.getDestinatie()=="Grecia");
    assert(oferta.getTip()=="Vacanta");
    assert(oferta.getPret()==1688);
}

void Teste::test_set() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    oferta.setDenumire("Evita");
    oferta.setDestinatie("Spania");
    oferta.setTip("CityBreak");
    oferta.setPret(1500);
    assert(oferta.getId()==1);
    assert(oferta.getDenumire()=="Evita");
    assert(oferta.getDestinatie()=="Spania");
    assert(oferta.getTip()=="CityBreak");
    assert(oferta.getPret()==1500);
}

void Teste::test_validare() {
    Validator_oferta validator;
    validator.validare_oferta(1,"Afandou","Gercia","Vacanta",1688);
    assert(true);
    try {
        validator.validare_oferta(0,"Afandou","Gercia","Vacanta",1688);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    try {
        validator.validare_oferta(1,"","Gercia","Vacanta",1688);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    try {
        validator.validare_oferta(1,"Afandou","","Vacanta",1688);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    try {
        validator.validare_oferta(1,"Afandou","Gercia","",1688);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    try {
        validator.validare_oferta(1,"Afandou","Gercia","Vacanta",-1688);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
}

void Teste::test_repo_adauga() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    vector<Oferta> oferte = repo.get_all();
    assert(oferte.size()==1);
    try {
        repo.adauga(oferta);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    repo.sterge(oferta);
}

void Teste::test_repo_sterge() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta1{2,"Evita","Gercia","Vacanta",1500};
    repo.adauga(oferta1);
    vector<Oferta> oferte = repo.get_all();
    assert(oferte.size()==2);
    repo.sterge(oferta1);
    oferte = repo.get_all();
    assert(oferte.size()==1);
    try {
        repo.sterge(oferta1);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    repo.sterge(oferta);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}

void Teste::test_repo_modifica() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta1{1,"Evita","Spania","CityBreak",1500};
    repo.modifica(oferta1);
    vector<Oferta> oferte = repo.get_all();
    assert(oferte[0].getDenumire()=="Evita");
    assert(oferte[0].getDestinatie()=="Spania");
    assert(oferte[0].getTip()=="CityBreak");
    assert(oferte[0].getPret()==1500);
    Oferta oferta2{2,"Evita","Spania","CityBreak",1500};
    try {
        repo.modifica(oferta2);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    repo.sterge(oferta1);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}

void Teste::test_repo_cautare_denumire() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta1{2,"Evita","Gercia","Vacanta",1500};
    repo.adauga(oferta1);
    vector<Oferta> oferte = repo.get_all();
    vector<Oferta> oferte_cautare = repo.cautare_denumire("Afandou");
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getDenumire()=="Afandou");
    repo.sterge(oferta);
    repo.sterge(oferta1);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}

void Teste::test_repo_cautare_destinatie() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta2{3,"Afandou","Spania","CityBreak",2000};
    repo.adauga(oferta2);
    vector<Oferta> oferte = repo.get_all();
    vector<Oferta> oferte_cautare = repo.cautare_destinatie("Gercia");
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getDestinatie()=="Gercia");
    repo.sterge(oferta);
    repo.sterge(oferta2);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}

void Teste::test_repo_cautare_tip() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta2{3,"Afandou","Spania","CityBreak",2000};
    repo.adauga(oferta2);
    vector<Oferta> oferte = repo.get_all();
    vector<Oferta> oferte_cautare = repo.cautare_tip("Vacanta");
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getTip()=="Vacanta");
    repo.sterge(oferta);
    repo.sterge(oferta2);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}

void Teste::test_repo_cautare_pret() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta1{2,"Evita","Gercia","Vacanta",1500};
    repo.adauga(oferta1);
    vector<Oferta> oferte = repo.get_all();
    vector<Oferta> oferte_cautare = repo.cautare_pret(1688);
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getPret()==1688);
    repo.sterge(oferta);
    repo.sterge(oferta1);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}

void Teste::test_repo_filtrare() {
    Oferta oferta{1,"Afandou","Gercia","Vacanta",1688};
    repo.adauga(oferta);
    Oferta oferta1{2,"Evita","Gercia","Vacanta",1688};
    repo.adauga(oferta1);
    Oferta oferta2{3,"Afandou","Spania","CityBreak",2000};
    repo.adauga(oferta2);
    vector<Oferta> oferte = repo.get_all();
    vector<Oferta> oferte_cautare = repo.filtrare("Gercia",1688);
    assert(oferte_cautare.size()==2);
    assert(oferte_cautare[0].getPret()==1688);
    assert(oferte_cautare[1].getPret()==1688);
    assert(oferte_cautare[0].getDestinatie()=="Gercia");
    assert(oferte_cautare[1].getDestinatie()=="Gercia");
    repo.sterge(oferta);
    repo.sterge(oferta1);
    repo.sterge(oferta2);
    oferte = repo.get_all();
    assert(oferte.size()==0);
}


void Teste::test_service_adauga() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    vector<Oferta> oferte_sort = service.get_oferte();
    assert(oferte_sort.size()==1);
    assert(oferte_sort[0].getId()==1);
    assert(oferte_sort[0].getDenumire()=="Afandou");
    assert(oferte_sort[0].getDestinatie()=="Gercia");
    assert(oferte_sort[0].getTip()=="Vacanta");
    assert(oferte_sort[0].getPret()==1688);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    oferte_sort = service.get_oferte();
    assert(oferte_sort.size()==0);
}

void Teste::test_service_modifica() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_modifica(1,"Evita","Spania","CityBreak",2000);
    vector<Oferta> oferte_sort = service.get_oferte();
    assert(oferte_sort.size()==1);
    assert(oferte_sort[0].getId()==1);
    assert(oferte_sort[0].getDenumire()=="Evita");
    assert(oferte_sort[0].getDestinatie()=="Spania");
    assert(oferte_sort[0].getTip()=="CityBreak");
    assert(oferte_sort[0].getPret()==2000);
    service.service_sterge(1,"Evita","Spania","CityBreak",2000);
    oferte_sort = service.get_oferte();
    assert(oferte_sort.size()==0);
}

void Teste::test_service_cautare_denumire() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Evita","Gercia","Vacanta",1500);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_cautare = service.service_cautare_denumire("Afandou");
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getDenumire()=="Afandou");
    service.service_sterge(2,"Evita","Gercia","Vacanta",1500);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    oferte =  service.get_oferte();
    assert(oferte.size()==0);
    service.service_adauga(4,"Afandou","Gercia","Vacanta",1688);
    try {
        oferte_cautare = service.service_cautare_denumire("Perla");
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    service.service_sterge(4,"Afandou","Gercia","Vacanta",1688);
    assert(oferte.size()==0);
}

void Teste::test_service_cautare_destinatie() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(3,"Afandou","Spania","CityBreak",2000);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_cautare = service.service_cautare_destinatie("Gercia");
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getDestinatie()=="Gercia");
    service.service_sterge(3,"Afandou","Spania","CityBreak",2000);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
    service.service_adauga(4,"Afandou","Gercia","Vacanta",1688);
    try {
        oferte_cautare = service.service_cautare_destinatie("Italia");
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    service.service_sterge(4,"Afandou","Gercia","Vacanta",1688);
    assert(oferte.size()==0);
}

void Teste::test_service_cautare_tip() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(3,"Afandou","Spania","CityBreak",2000);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_cautare = service.service_cautare_tip("Vacanta");
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getTip()=="Vacanta");
    service.service_sterge(3,"Afandou","Spania","CityBreak",2000);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
    service.service_adauga(4,"Afandou","Gercia","Vacanta",1688);
    try {
        oferte_cautare = service.service_cautare_tip("Delegatie");
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    service.service_sterge(4,"Afandou","Gercia","Vacanta",1688);
    assert(oferte.size()==0);
}

void Teste::test_service_cautare_pret() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Evita","Gercia","Vacanta",1500);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_cautare = service.service_cautare_pret(1688);
    assert(oferte_cautare.size()==1);
    assert(oferte_cautare[0].getPret()==1688);
    service.service_sterge(2,"Evita","Gercia","Vacanta",1500);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
    service.service_adauga(4,"Afandou","Gercia","Vacanta",1688);
    try {
        oferte_cautare = service.service_cautare_pret(3000);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    service.service_sterge(4,"Afandou","Gercia","Vacanta",1688);
    assert(oferte.size()==0);
}

void Teste::test_service_filtrare() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Evita","Gercia","Vacanta",1688);
    service.service_adauga(3,"Afandou","Spania","CityBreak",2000);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_cautare = service.service_filtrare("Gercia",1688);
    assert(oferte_cautare.size()==2);
    assert(oferte_cautare[0].getPret()==1688);
    assert(oferte_cautare[1].getPret()==1688);
    assert(oferte_cautare[0].getDestinatie()=="Gercia");
    assert(oferte_cautare[1].getDestinatie()=="Gercia");
    service.service_sterge(3,"Afandou","Spania","CityBreak",2000);
    service.service_sterge(2,"Evita","Gercia","Vacanta",1688);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
    service.service_adauga(4,"Afandou","Gercia","Vacanta",1688);
    try {
        oferte_cautare = service.service_filtrare("Spania",2000);
        assert(false);
    }
    catch (Exceptii& ) {
        assert(true);
    }
    service.service_sterge(4,"Afandou","Gercia","Vacanta",1688);
    assert(oferte.size()==0);
}

void Teste::test_service_sortare_denumire() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Evita","Gercia","Vacanta",1688);
    service.service_adauga(3,"Afandou","Spania","CityBreak",2000);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_sort = service.service_sortare_denumire(2);
    assert(oferte_sort.size()==3);
    assert(oferte_sort[0].getDenumire()=="Afandou");
    assert(oferte_sort[1].getDenumire()=="Afandou");
    assert(oferte_sort[2].getDenumire()=="Evita");
    assert(oferte_sort[0].getPret()==1688);
    assert(oferte_sort[1].getPret()==2000);
    assert(oferte_sort[2].getPret()==1688);
    vector<Oferta> oferte_sort1 = service.service_sortare_denumire(1);
    assert(oferte_sort1.size()==3);
    assert(oferte_sort1[2].getDenumire()=="Afandou");
    assert(oferte_sort1[1].getDenumire()=="Afandou");
    assert(oferte_sort1[0].getDenumire()=="Evita");
    assert(oferte_sort1[2].getPret()==2000);
    assert(oferte_sort1[1].getPret()==1688);
    assert(oferte_sort1[0].getPret()==1688);
    service.service_sterge(3,"Afandou","Spania","CityBreak",2000);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    service.service_sterge(2,"Evita","Gercia","Vacanta",1688);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
}

void Teste::test_service_sortare_destinatie() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Evita","Gercia","Vacanta",1688);
    service.service_adauga(3,"Afandou","Spania","CityBreak",2000);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_sort = service.service_sortare_destinatie(2);
    assert(oferte_sort.size()==3);
    assert(oferte_sort[0].getDestinatie()=="Gercia");
    assert(oferte_sort[1].getDestinatie()=="Gercia");
    assert(oferte_sort[2].getDestinatie()=="Spania");
    assert(oferte_sort[0].getPret()==1688);
    assert(oferte_sort[1].getPret()==1688);
    assert(oferte_sort[2].getPret()==2000);
    vector<Oferta> oferte_sort1 = service.service_sortare_destinatie(1);
    assert(oferte_sort1.size()==3);
    assert(oferte_sort1[2].getDestinatie()=="Gercia");
    assert(oferte_sort1[1].getDestinatie()=="Gercia");
    assert(oferte_sort1[0].getDestinatie()=="Spania");
    assert(oferte_sort1[2].getPret()==1688);
    assert(oferte_sort1[1].getPret()==1688);
    assert(oferte_sort1[0].getPret()==2000);
    service.service_sterge(2,"Evita","Gercia","Vacanta",1688);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    service.service_sterge(3,"Afandou","Spania","CityBreak",2000);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
}

void Teste::test_service_sortare_tip_pret() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",2000);
    service.service_adauga(2,"Evita","Gercia","Vacanta",1688);
    service.service_adauga(3,"Afandou","Spania","CityBreak",2000);
    vector<Oferta> oferte = service.get_oferte();
    vector<Oferta> oferte_sort = service.service_sortare_tip_pret(2);
    assert(oferte_sort.size()==3);
    assert(oferte_sort[0].getTip()=="CityBreak");
    assert(oferte_sort[1].getTip()=="Vacanta");
    assert(oferte_sort[2].getTip()=="Vacanta");
    assert(oferte_sort[0].getPret()==2000);
    assert(oferte_sort[1].getPret()==1688);
    assert(oferte_sort[2].getPret()==2000);
    vector<Oferta> oferte_sort1 = service.service_sortare_tip_pret(1);
    assert(oferte_sort1.size()==3);
    assert(oferte_sort1[2].getTip()=="CityBreak");
    assert(oferte_sort1[1].getTip()=="Vacanta");
    assert(oferte_sort1[0].getTip()=="Vacanta");
    assert(oferte_sort1[2].getPret()==2000);
    assert(oferte_sort1[1].getPret()==1688);
    assert(oferte_sort1[2].getPret()==2000);
    service.service_sterge(3,"Afandou","Spania","CityBreak",2000);
    service.service_sterge(2,"Evita","Gercia","Vacanta",1688);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",2000);
    oferte = service.get_oferte();
    assert(oferte.size()==0);
}

void Teste::test_adauga_in_cos() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    cos.adauga_in_cos(oferta);
    assert(cos.size_cos()==1);
    cos.goleste_cos();
    assert(cos.size_cos()==0);
}

void Teste::test_goleste_cos() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    cos.adauga_in_cos(oferta);
    assert(cos.size_cos()==1);
    cos.goleste_cos();
    assert(cos.size_cos()==0);
}

void Teste::test_get_cos() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    cos.adauga_in_cos(oferta);
    auto cos1=cos.get_cos();
    assert(cos.size_cos()==1);
    cos.goleste_cos();
    assert(cos.size_cos()==0);
}

void Teste::test_size_cos() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    cos.adauga_in_cos(oferta);
    int lung_cos=cos.size_cos();
    assert(lung_cos==1);
    cos.goleste_cos();
    lung_cos=cos.size_cos();
    assert(lung_cos==0);
}

void Teste::teste_service_adauga_in_cos() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    auto cos = service.service_adauga_in_cos(1);
    assert(cos.size()==1);
    service.service_goleste_cos();
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
}

void Teste::teste_service_size_cos() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    auto cos = service.service_adauga_in_cos(1);
    int lung_cos = service.service_size_cos();
    assert(lung_cos==1);
    service.service_goleste_cos();
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
}

void Teste::test_service_goleste_cos() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    auto cos = service.service_adauga_in_cos(1);
    service.service_goleste_cos();
    auto cos_afisare =  service.service_get_cos();
    assert(cos_afisare.size()==0);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
}

void Teste::test_service_get_cos() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    auto cos = service.service_adauga_in_cos(1);
    auto cos_afisare = service.service_get_cos();
    assert(cos_afisare.size()==1);
    service.service_goleste_cos();
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
}

void Teste::test_exportCos() {
    Oferta oferta = {1,"Afandou","Grecia","Vacanta",1688};
    cos.adauga_in_cos(oferta);
    string numeFisier = "../wishlist.cvs";
    cos.exportCos(numeFisier);
    std::ifstream fisier(numeFisier);
    assert(fisier.is_open());

    std::string linie;
    int nr_linii=0;
    while (std::getline(fisier, linie)) {
        nr_linii++;
    }
    assert(nr_linii==1);
    fisier.close();
}

void Teste::test_servicev_export_cos() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    auto cos = service.service_adauga_in_cos(1);
    string numeFisier = "../wishlist.cvs";
    service.service_exportCvs(numeFisier);
    std::ifstream fisier(numeFisier);
    assert(fisier.is_open());
    std::string linie;
    int nr_linii=0;
    while (std::getline(fisier, linie)) {
        nr_linii++;
    }
    assert(nr_linii==1);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    fisier.close();
}

void Teste::test_service_undo_adauga() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    vector<Oferta> oferte = service.get_oferte();
    assert(oferte.size() == 1);
    service.service_undo();
    oferte = service.get_oferte();
    assert(oferte.size() == 0);
}

void Teste::test_service_undo_sterge() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Afandou","Spania","Vacanta",2000);
    vector<Oferta> oferte = service.get_oferte();
    assert(oferte.size() == 2);
    service.service_sterge(2,"Afandou","Spania","Vacanta",2000);
    oferte = service.get_oferte();
    assert(oferte.size() == 1);
    service.service_undo();
    oferte = service.get_oferte();
    assert(oferte.size() == 2);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    service.service_sterge(2,"Afandou","Spania","Vacanta",2000);
}

void Teste::test_service_dictionar_tip() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Afandou","Gercia","CityBreak",1688);
    service.service_adauga(3,"Afandou","Gercia","Vacanta",1688);
    std::map<std::string , std::vector<Oferta>> oferte_dupa_tip = service.service_dictionar_tip();
    assert(oferte_dupa_tip["Vacanta"].size()==2);
    assert(oferte_dupa_tip["CityBreak"].size()==1);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    service.service_sterge(2,"Afandou","Gercia","CityBreak",1688);
    service.service_sterge(3,"Afandou","Gercia","Vacanta",1688);
}

void Teste::test_service_undo_modifica() {
    service.service_adauga(1,"Afandou","Gercia","Vacanta",1688);
    service.service_adauga(2,"Afandou","Spania","Vacanta",2000);
    vector<Oferta> oferte = service.get_oferte();
    assert(oferte.size() == 2);
    assert(oferte[0].getPret()==1688);
    assert(oferte[1].getPret()==2000);
    service.service_modifica(2,"Baivillage","Grecia","Trip",2100);
    oferte = service.get_oferte();
    assert(oferte[1].getPret()==2100);
    service.service_undo();
    oferte = service.get_oferte();
    assert(oferte[0].getPret()==1688);
    assert(oferte[1].getPret()==2000);
    service.service_sterge(1,"Afandou","Gercia","Vacanta",1688);
    service.service_sterge(2,"Afandou","Spania","Vacanta",2000);
}