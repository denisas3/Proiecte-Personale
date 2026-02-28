//
// Created by Blondi on 3/31/2025.
//

#include "Consola.h"

//#include <cstring>
#include <iostream>

#include "../Erori/Exceptii.h"
using std::cin;
using std::cout;

Consola::Consola(Service_oferta& service, Teste& teste) : service{service}, teste{teste} {

}
void Consola::runUi() {
    while (true) {
        cout<<"\nMeniu:\n1.Adauga oferta.\n2.Afisare oferte.\n3.Sterge oferta.\n4.Modifica oferta.\n5.Cautare oferta.\n6.Filtrare oferte.\n7.Sortare oferte.\n8.Make wishlist.\n9.Salvare wishlist in csv.\n10.Afiseaza oferte pe genuri.\n11.Undo.\n0.Exit\n";
        int optiune = 0;
        cout<<"Alegeti o comanda: ";
        cin>>optiune;
        if (optiune == 0) {
            break;
        }
        if (optiune == 1) {
            int id;
            string denumire;
            string destinatie;
            string tip;
            double pret;
            cout<<"Introduceti id: ";
            cin>>id;
            cout<<"Introduceti denumire: ";
            cin>>denumire;
            cout<<"Introduceti destinatie: ";
            cin>>destinatie;
            cout<<"Introduceti tip: ";
            cin>>tip;
            cout<<"Introduceti pret: ";
            cin>>pret;
            try{
                service.service_adauga(id,denumire,destinatie,tip,pret);
                cout<<"Oferta a fost adaugata cu succes!\n";
            }
            catch (Exceptii& mesaj) {
                cout<<mesaj.what();
            }
        }
        if (optiune == 2) {
            vector<Oferta> oferte;
            oferte = service.get_oferte();
            int ok=0;
            for (const Oferta& oferta1 : oferte) {
                ok=1;
                cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
            }
            if (ok==0)
                cout<<"Nu exista oferte introduse!\n";
        }
        if (optiune == 3) {
            int id;
            string denumire;
            string destinatie;
            string tip;
            double pret;
            cout<<"Introduceti id: ";
            cin>>id;
            cout<<"Introduceti denumire: ";
            cin>>denumire;
            cout<<"Introduceti destinatie: ";
            cin>>destinatie;
            cout<<"Introduceti tip: ";
            cin>>tip;
            cout<<"Introduceti pret: ";
            cin>>pret;
            try{
                service.service_sterge(id,denumire,destinatie,tip,pret);
                cout<<"Oferta a fost stearsa cu succes!\n";
            }
            catch (Exceptii& mesaj) {
                cout<<mesaj.what();
            }
        }
        if (optiune == 4) {
            int id;
            string denumire_noua;
            string destinatie_noua;
            string tip_nou;
            double pret_nou;
            cout<<"Introduceti id: ";
            cin>>id;
            cout<<"Introduceti denumirea noua: ";
            cin>>denumire_noua;
            cout<<"Introduceti destinatia noua: ";
            cin>>destinatie_noua;
            cout<<"Introduceti tipul nou: ";
            cin>>tip_nou;
            cout<<"Introduceti pretul nou: ";
            cin>>pret_nou;
            try{
                service.service_modifica(id,denumire_noua,destinatie_noua,tip_nou,pret_nou);
                cout<<"Oferta a fost modificata cu succes!\n";
            }
            catch (Exceptii& mesaj) {
                cout<<mesaj.what();
            }
        }
        if (optiune == 5) {
            cout<<"Dupa ce criteriu sa se faca cautarea?\n1.Dupa denumire.\n2.Dupa destinatie.\n3.Dupa tip.\n4.Dupa pret.\n";
            int cauta;
            cin>>cauta;
            if (cauta==1) {
                cout<<"Introduceti denumirea: ";
                string denumire;
                cin>>denumire;
                vector<Oferta> oferte;
                try {
                    oferte = service.service_cautare_denumire(denumire);
                    for (const Oferta& oferta1 : oferte) {
                        cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                    }
                }
                catch (Exceptii& mesaj) {
                    cout<<mesaj.what();
                }
            }
            else if (cauta==2) {
                cout<<"Introduceti destinatia: ";
                string destinatie;
                cin>>destinatie;
                vector<Oferta> oferte;
                try {
                    oferte = service.service_cautare_destinatie(destinatie);
                    for (const Oferta& oferta1 : oferte) {
                        cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                    }
                }
                catch (Exceptii& mesaj) {
                    cout<<mesaj.what();
                }
            }
            else if (cauta==3) {
                cout<<"Introduceti tip: ";
                string tip;
                cin>>tip;
                vector<Oferta> oferte;
                try {
                    oferte = service.service_cautare_tip(tip);
                    for (const Oferta& oferta1 : oferte) {
                        cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                    }
                }
                catch (Exceptii& mesaj) {
                    cout<<mesaj.what();
                }
            }
            else if (cauta==4) {
                cout<<"Introduceti pert: ";
                double pret;
                cin>>pret;
                vector<Oferta> oferte;
                try {
                    oferte = service.service_cautare_pret(pret);
                    for (const Oferta& oferta1 : oferte) {
                        cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                    }
                }
                catch (Exceptii& mesaj) {
                    cout<<mesaj.what();
                }
            }
            else {
                cout<<"Nu se poate executa cautarea!\n";
            }
        }
        if (optiune == 6) {
            cout<<"Introduceti destinatia: ";
            string destinatie;
            cin>>destinatie;
            cout<<"Introduceti pretul: ";
            double pret;
            cin>>pret;
            vector<Oferta> oferte;
            try {
                oferte = service.service_filtrare(destinatie,pret);
                for (const Oferta& oferta1 : oferte) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
            catch (Exceptii& mesaj) {
                cout<<mesaj.what();
            }
        }
        if (optiune == 7) {
            cout<<"Dupa ce criteriu sa se faca cautarea?\n1.Dupa denumire.\n2.Dupa destinatie.\n3.Dupa tip si pret.\n";
            int cauta;
            cin>>cauta;
            if (cauta==1) {
                cout<<"1.Sortare descrescatoare.\n2.Sortare crescatoare.\n";
                cout<<"Introduceti sortarea dorita: ";
                int ok;
                cin>>ok;
                vector<Oferta> oferte;
                oferte = service.service_sortare_denumire(ok);
                for (const Oferta& oferta1 : oferte) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
            if (cauta==2) {
                cout<<"1.Sortare descrescatoare.\n2.Sortare crescatoare.\n";
                cout<<"Introduceti sortarea dorita: ";
                int ok;
                cin>>ok;
                vector<Oferta> oferte;
                oferte = service.service_sortare_destinatie(ok);
                for (const Oferta& oferta1 : oferte) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
            if (cauta==3) {
                cout<<"1.Sortare descrescatoare.\n2.Sortare crescatoare.\n";
                cout<<"Introduceti sortarea dorita: ";
                int ok;
                cin>>ok;
                vector<Oferta> oferte;
                oferte = service.service_sortare_tip_pret(ok);
                for (const Oferta& oferta1 : oferte) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
        }
        if (optiune == 8) {
            cout<<"1.Adauga oferta in wishlist.\n2.Goleste wishlist-ul.\n3.Genereaza un wishlist.\n4.Afiseaza ofertele din wishlist.\n";
            int op;
            cin>>op;
            if (op==1) {
                cout<<"Introduceti denumirea ofertei: ";
                int id;
                cin>>id;
                vector<Oferta> oferte;
                oferte = service.service_adauga_in_cos(id);
                int lung_wishlist = service.service_size_cos();
                cout<<"Exista "<<lung_wishlist<<" oferte in wishlist.\nAceste sunt:\n";
                for (const Oferta& oferta1 : oferte) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
            if (op==2) {
                service.service_goleste_cos();
                cout<<"Wishlist-ul a fost golit cu succes!";
            }
            if (op==3) {
                vector<Oferta> oferte;
                oferte = service.service_genereaza_cos();
                int lung_wishlist = service.service_size_cos();
                cout<<"Exista "<<lung_wishlist<<" oferte in wishlist.\nAceste sunt:\n";
                for (const Oferta& oferta1 : oferte) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
            if (op==4) {
                vector<Oferta> oferte;
                oferte = service.service_get_cos();
                int lung_wishlist = service.service_size_cos();
                cout<<"Exista "<<lung_wishlist<<" oferte in wishlist.\n";
                if (lung_wishlist==0)
                    cout<<"Nu exista oferte in wishlist.\n";
                else {
                    cout<<"Acestea sunt: \n";
                    for (const Oferta& oferta1 : oferte) {
                        cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                    }
                }

            }
        }
        if (optiune == 9) {
            string numeFisier;
            cout<<"Introduceti numele fisierului CVS: ";
            cin>>numeFisier;

            service.service_exportCvs(numeFisier);
            cout<<"Wishlist-ul s-a exportat in fisierul "<<numeFisier<<" cu succes.\n";
        }
        if (optiune == 10) {
            std::map<std::string , std::vector<Oferta>> afisare = service.service_dictionar_tip();
            if (afisare.size()==0) {
                cout<<"Nu exista oferte in lista.\n";
            }
            for (const auto oferta : afisare) {
                cout<<"Tipul: "<<oferta.first<<"\n";
                for (const Oferta& oferta1 : oferta.second) {
                    cout<<"Oferta: "<<oferta1.getId()<<" - "<<oferta1.getDenumire()<<" - "<<oferta1.getDestinatie()<<" - "<<oferta1.getTip()<<" - "<<oferta1.getPret()<<"\n";
                }
            }
        }
        if (optiune == 11) {
            service.service_undo();
            cout<<"Undo efectuat cu succes.\n";
        }
    }
}

void Consola::ruleaza_toate_testele() {
    cout<<"Ruleaza testele...\n";
    teste.test_get();
    teste.test_set();
    teste.test_validare();
    teste.test_repo_adauga();
    teste.test_repo_sterge();
    teste.test_repo_modifica();
    teste.test_repo_cautare_denumire();
    teste.test_repo_cautare_destinatie();
    teste.test_repo_cautare_tip();
    teste.test_repo_cautare_pret();
    teste.test_repo_filtrare();
    teste.test_service_adauga();
    teste.test_service_modifica();
    teste.test_service_cautare_denumire();
    teste.test_service_cautare_destinatie();
    teste.test_service_cautare_tip();
    teste.test_service_cautare_pret();
    teste.test_service_filtrare();
    teste.test_service_sortare_denumire();
    teste.test_service_sortare_destinatie();
    teste.test_service_sortare_tip_pret();
    teste.test_adauga_in_cos();
    teste.test_goleste_cos();
    teste.test_get_cos();
    teste.test_size_cos();
    teste.teste_service_adauga_in_cos();
    teste.teste_service_size_cos();
    teste.test_service_goleste_cos();
    teste.test_service_get_cos();
    teste.test_exportCos();
    teste.test_servicev_export_cos();
    teste.test_service_undo_adauga();
    teste.test_service_undo_sterge();
    teste.test_service_undo_modifica();
    teste.test_service_dictionar_tip();
    cout<<"Testele au fost rulate cu succes!\n";

}