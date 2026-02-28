//
// Created by Blondi on 3/31/2025.
//

#include "Service_oferta.h"

#include <algorithm>

#include "../Domeniu/oferta.h"
#include "../Repo/Repo_oferta.h"
#include "../Wishlist/cos.h"
#include <vector>

#include "../Erori/Exceptii.h"
using std::vector;

Service_oferta::Service_oferta(Repo_oferta& oferte, const Validator_oferta& valid, const Cos& cos): repo{oferte}, validator {valid}, cos {cos}{
}


void Service_oferta::service_adauga(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret) {
    Oferta oferta{id, denumire, destinatie, tip, pret};
    validator.validare_oferta(id, denumire, destinatie, tip, pret);
    repo.adauga(oferta);
    undo.push_back(std::make_unique<UndoAdauga>(repo,oferta));
    notify();
}

void Service_oferta::service_sterge(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret) {
    Oferta oferta{id, denumire, destinatie, tip, pret};
    repo.sterge(oferta);
    undo.push_back(std::make_unique<UndoSterge>(repo,oferta));
    notify();
}

// void Service_oferta::service_modifica(const int &id, const string &denumire_noua, const string &destinatie_noua, const string &tip_nou, const double &pret_nou) {
//     Oferta oferta_noua{id,denumire_noua,destinatie_noua, tip_nou, pret_nou};
//     validator.validare_oferta(id,denumire_noua,destinatie_noua, tip_nou, pret_nou);
//     repo.modifica(oferta_noua);
//     undo.push_back(std::make_unique<UndoModifica>(repo,oferta_noua));
// }

void Service_oferta::service_modifica(const int &id, const string &denumire_noua, const string &destinatie_noua, const string &tip_nou, const double &pret_nou) {
    Oferta oferta_noua{id, denumire_noua, destinatie_noua, tip_nou, pret_nou};
    validator.validare_oferta(id, denumire_noua, destinatie_noua, tip_nou, pret_nou);
    auto toate = repo.get_all();
    for (const auto& oferta : toate) {
        if (oferta.getId() == id) {
            undo.push_back(std::make_unique<UndoModifica>(repo, oferta));
            break;
        }
    }
    repo.modifica(oferta_noua);
}

vector<Oferta>  Service_oferta::service_cautare_denumire_id(const string& denumire, const int& id) {
    vector<Oferta> oferte_cautate ;
    for (const auto& oferta : repo.get_all()) {
        if (oferta.getId() == id && oferta.getDenumire() == denumire) {
            oferte_cautate.push_back(oferta);
        }
    }
    return oferte_cautate;
}

vector<Oferta>  Service_oferta::service_cautare_denumire(const string& denumire) {
    return repo.cautare_denumire(denumire);
}

vector<Oferta>  Service_oferta::service_cautare_destinatie(const string& destinatie) {
    return repo.cautare_destinatie(destinatie);
}

vector<Oferta>  Service_oferta::service_cautare_tip(const string& tip) {
    return repo.cautare_tip(tip);
}

vector<Oferta>  Service_oferta::service_cautare_pret(const double& pret) {
    return repo.cautare_pret(pret);
}

vector<Oferta> Service_oferta::service_filtrare(const string& destinatie, const double& pret) {
    return repo.filtrare(destinatie, pret);
}


vector<Oferta> Service_oferta::get_oferte() {
    return repo.get_all();
}

vector<Oferta> Service_oferta::service_sortare_denumire(int ok) {
    vector<Oferta> listaSortata = repo.get_all();

    std::sort(listaSortata.begin(), listaSortata.end(),[ok](const Oferta &oferta1, const Oferta &oferta2 ) {
        if (ok==1) {
            return oferta1.getDenumire() > oferta2.getDenumire();
        }
            return oferta1.getDenumire() < oferta2.getDenumire();

    });

    return listaSortata;
}

vector<Oferta> Service_oferta::service_sortare_destinatie(int ok) {
    vector<Oferta> listaSortata = repo.get_all();

    std::sort(listaSortata.begin(), listaSortata.end(),[ok](const Oferta &oferta1, const Oferta &oferta2 ) {
        if (ok==1) {
            return oferta1.getDestinatie() >= oferta2.getDestinatie();
        }
            return oferta1.getDestinatie() < oferta2.getDestinatie();
    });

    return listaSortata;
}


vector<Oferta> Service_oferta::service_sortare_tip_pret(int ok) {
    vector<Oferta> listaSortata = repo.get_all();

    std::sort(listaSortata.begin(), listaSortata.end(),[ok](const Oferta &oferta1, const Oferta &oferta2 ) {
        if (ok==1) {
            if (oferta1.getTip() == oferta2.getTip()) {
                return oferta1.getPret() >= oferta2.getPret();
            }
            return oferta1.getTip() >= oferta2.getTip();
        }
        else {
            if (oferta1.getTip() == oferta2.getTip()) {
                return oferta1.getPret() < oferta2.getPret();
            }
            return oferta1.getTip() < oferta2.getTip();
        }
    });

    return listaSortata;
}

vector<Oferta> Service_oferta::service_adauga_in_cos(int id) {
    auto oferte = repo.cautare_id(id);
    cos.adauga_in_cos(oferte[0]);
    notify();
    return cos.get_cos();
}

int Service_oferta::service_size_cos() {
    return cos.size_cos();
}

void Service_oferta::service_goleste_cos() {
    notify();
    cos.goleste_cos();
}

vector<Oferta> Service_oferta::service_genereaza_cos() {
    auto oferte = repo.get_all();
    auto oferte_generate = cos.genereaza_cos(repo.get_all().size(),oferte);
    notify();
    return oferte_generate;
}

vector<Oferta> Service_oferta::service_get_cos() {
    //notify();
    return cos.get_cos();
}

void Service_oferta::service_exportCvs(const string& numeFisier) const {
    cos.exportCos(numeFisier);
}

std::map<std::string , std::vector<Oferta>> Service_oferta::service_dictionar_tip() {
    std::map<std::string , std::vector<Oferta>> oferte_dupa_tip;
    const auto &oferte = repo.get_all();
    for (const auto &oferta : oferte) {
        oferte_dupa_tip[oferta.getTip()].push_back(oferta);
    }
    return oferte_dupa_tip;
}

void Service_oferta::service_undo() {
    if (undo.empty()) {
        throw Exceptii("Nu mai exista operatii.");
    }
    undo.back()->doUndo();
    undo.pop_back();
}