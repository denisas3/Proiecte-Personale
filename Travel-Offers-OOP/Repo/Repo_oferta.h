//
// Created by Blondi on 3/31/2025.
//

#ifndef REPO_OFERTA_H
#define REPO_OFERTA_H
#include "../Domeniu/oferta.h"
#include <vector>
using std::vector;


class Repo_oferta {

    private:
        vector<Oferta> oferte;

    public:
        void adauga(const Oferta& oferta);
        void sterge(const Oferta& oferta);
        void modifica(const Oferta& oferta_noua);
        vector<Oferta> cautare_denumire(const string& denumire);
        vector<Oferta> cautare_destinatie(const string& destinatie);
        vector<Oferta> cautare_tip(const string& tip);
        vector<Oferta> cautare_pret(const double& pret);
        vector<Oferta> filtrare(const string& destinatie, const double& pret);
        vector<Oferta> get_all();
        vector<Oferta> cautare_denumire_id(const string& denumire, const int& id);
        vector<Oferta> cautare_id(const int &id);

};


#endif //REPO_OFERTA_H
