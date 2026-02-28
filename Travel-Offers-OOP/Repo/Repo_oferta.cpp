//
// Created by Blondi on 3/31/2025.
//

#include "Repo_oferta.h"

#include <algorithm>

#include "../VectorDinamic.h"

#include "../Domeniu/oferta.h"
#include "../Erori/Exceptii.h"

void Repo_oferta::adauga(const Oferta& oferta) {
    auto it = std::find_if(oferte.begin(),oferte.end(),[&oferta](const Oferta &oferta2) {
        return oferta.getId() == oferta2.getId();
    });
    if (it != oferte.end()) {
        throw Exceptii("Oferta deja adaugata!\n");
    }
    oferte.push_back(oferta);
}

void Repo_oferta::sterge(const Oferta& oferta) {
    auto it = std::find_if(oferte.begin(), oferte.end(), [&oferta](const Oferta &oferta2) {return oferta.getId() == oferta2.getId();});
    if (it != oferte.end()) {
        oferte.erase(it); ///oferta.getId()
        return;
    }
    throw Exceptii("Oferta nu exista in lista pentru a putea fi stearsa!\n");
}



// void Repo_oferta::sterge(const Oferta& oferta) {
//     for (auto it = oferte.begin(); it != oferte.end(); it.next()) {
//         if (it.element().getId() == oferta.getId()) {
//             oferte.remove(it.curent());
//             return;
//         }
//     }
//     throw Exceptii("Oferta nu exista in lista pentru a putea fi stearsa!\n");
// }


void Repo_oferta::modifica(const Oferta& oferta_noua) {
    for (auto& oferta : oferte) {
        if (oferta.getId() == oferta_noua.getId()) {
            oferta.setDenumire(oferta_noua.getDenumire());
            oferta.setDestinatie(oferta_noua.getDestinatie());
            oferta.setTip(oferta_noua.getTip());
            oferta.setPret(oferta_noua.getPret());
            return;
        }
    }

    throw Exceptii("Oferta nu exista in lista pentru a putea fi modificata!\n");
}

vector<Oferta> Repo_oferta::cautare_id(const int &id)  {
    vector<Oferta> oferte_cautate;
    auto it = std::find_if(oferte.begin(),oferte.end(),[&id](const Oferta& oferta2) {return id == oferta2.getId(); });
    if (it == oferte.end()) {throw Exceptii("Nu exista nicio oferta cu denumirea aleasa!\n");}
    else{
        oferte_cautate.push_back(*it);
        return oferte_cautate;
    }
}

vector<Oferta> Repo_oferta::cautare_denumire(const std::string &denumire)  {
    vector<Oferta> oferte_cautate;
    auto it = std::find_if(oferte.begin(),oferte.end(),[&denumire](const Oferta& oferta2) {return denumire == oferta2.getDenumire(); });
    if (it == oferte.end()) {throw Exceptii("Nu exista nicio oferta cu denumirea aleasa!\n");}
    else{
        oferte_cautate.push_back(*it);
        return oferte_cautate;
    }
}



vector<Oferta>  Repo_oferta::cautare_destinatie(const string& destinatie) {
    vector<Oferta> oferte_cautate;
    auto it = std::find_if(oferte.begin(),oferte.end(),[&destinatie](const Oferta& oferta2) {return destinatie == oferta2.getDestinatie(); });
    if (it != oferte.end()) {
        oferte_cautate.push_back(*it);
        return oferte_cautate;
    }
    throw Exceptii("Nu exista nicio oferta cu destinatia aleasa!\n");
}

vector<Oferta> Repo_oferta::cautare_tip(const string& tip) {
    vector<Oferta> oferte_cautate;
    auto it = std::find_if(oferte.begin(),oferte.end(),[&tip](const Oferta& oferta2) {return tip == oferta2.getTip(); });
    if (it != oferte.end()) {
        oferte_cautate.push_back(*it);
        return oferte_cautate;
    }
    throw Exceptii("Nu exista nicio oferta cu tipul ales!\n");
}

vector<Oferta> Repo_oferta::cautare_pret(const double& pret) {
    vector<Oferta> oferte_cautate;
    auto it = std::find_if(oferte.begin(),oferte.end(),[&pret](const Oferta& oferta2) {return pret == oferta2.getPret(); });
    if (it != oferte.end()) {
        oferte_cautate.push_back(*it);
        return oferte_cautate;
    }
    throw Exceptii("Nu exista nicio oferta cu pretul ales!\n");
}

vector<Oferta> Repo_oferta::filtrare(const string& destinatie, const double& pret) {
    vector<Oferta> oferte_cautate;
    std::copy_if(oferte.begin(),oferte.end(), std::back_inserter(oferte_cautate), [&destinatie, &pret](const Oferta& oferta2) {
        return (destinatie == oferta2.getDestinatie() && pret == oferta2.getPret());
    });
    if (oferte_cautate.empty()) {
        throw Exceptii("Nu exista nicio oferta gasita!\n");
    }
    return oferte_cautate;
}




vector<Oferta> Repo_oferta::get_all() {
    return oferte;
}